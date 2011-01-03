import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RPGPlayer 
{
	String 	name;
	Player 	player;
	Date 	firstLogin;
	Date	lastLogin;
	Date	lastDisconnect;
	boolean	dirty;
	boolean online;
	
	public RPGPlayer()
	{
		player = null;
		dirty = true;
		online = false;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
		name = player.getName();
	}
	
	public void setFirstLogin(Date d)
	{
		firstLogin = d;
		dirty = true;
	}
	
	public void setLastLogin(Date d)
	{
		lastLogin = d;
		dirty = true;
	}
	
	public void setLastDisconnect(Date d)
	{
		lastDisconnect = d;
		dirty = true;
	}
	
	public boolean isDirty()
	{
		return dirty;
	}
	
	public void save(Connection conn, String tableName)
	{
		if (!dirty) return;
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try 
        {
			String sqlUpdate = 
				"INSERT INTO " 
			+ 	tableName 
			+ 	" (name, firstlogin, lastlogin, lastdisconnect, online) VALUES(?, ?, ?, ?, ?)"
			+	" ON DUPLICATE KEY UPDATE lastlogin = ?, lastdisconnect = ?, online = ?";
			ps = conn.prepareStatement(sqlUpdate);
				
			ps.setString(1, name);
			ps.setTimestamp(2, new java.sql.Timestamp(firstLogin.getTime()));
			
			ps.setTimestamp(3, new java.sql.Timestamp(lastLogin.getTime()));
			ps.setTimestamp(6, new java.sql.Timestamp(lastLogin.getTime()));
			
			if (lastDisconnect != null)
			{
				ps.setTimestamp(4, new java.sql.Timestamp(lastDisconnect.getTime()));
				ps.setTimestamp(7, new java.sql.Timestamp(lastDisconnect.getTime()));
			}
			else
			{
				ps.setNull(4, java.sql.Types.DATE);
				ps.setNull(7, java.sql.Types.DATE);
			}
			
			ps.setByte(5, (byte)(online ? 1 : 0));
			ps.setByte(8, (byte)(online ? 1 : 0));
			
			RPG.getRPG().log(Level.INFO, "Updating player " + name);
			ps.executeUpdate();    
		}
		catch (SQLException ex) 
        {
			RPG.getRPG().log(Level.SEVERE, "Error updating or inserting player", ex);
        } 
        finally 
        {
            try 
            {
                if (ps != null) 
                {
                    ps.close();
                }
                if (rs != null) 
                {
                    rs.close();
                }
            } 
            catch (SQLException ex) 
            {
            }
        }
		
		dirty = false;
	}
	
	public void load(ResultSet rs)
	{
		try 
        {
			name = rs.getString("name");
			firstLogin = rs.getTimestamp("firstLogin");
			lastLogin = rs.getTimestamp("lastLogin");
			lastDisconnect = rs.getTimestamp("lastDisconnect");
			online = rs.getByte("online") == 0 ? false : true;
        }
		catch (SQLException ex) 
        {
            RPG.getRPG().log(Level.SEVERE, "Error loading player", ex);
        } 		 
		dirty = false;
	}
		
	public static void loadPlayers(HashMap<String, RPGPlayer> players, Connection conn, String tableName)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try 
        {
			ps = conn.prepareStatement("SELECT * FROM " + tableName);
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                RPGPlayer newPlayer = new RPGPlayer();
                newPlayer.load(rs);
                players.put(newPlayer.name, newPlayer);
            }     
		}
		catch (SQLException ex) 
        {
			RPG.getRPG().log(Level.SEVERE, "Error loading players", ex);
        } 
        finally 
        {
            try 
            {
                if (ps != null) 
                {
                    ps.close();
                }
                if (rs != null) 
                {
                    rs.close();
                }
            } 
            catch (SQLException ex) 
            {
            }
        }
	}
	
	public void setOnline(boolean online)
	{
		this.online = online;
		if (!online)
		{
			player = null;
		}
	}
	
	public boolean isOnline()
	{
		return online;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Date getLastDisconnect()
	{
		return lastDisconnect;
	}
	
	public Date getFirstLogin()
	{
		return firstLogin;
	}	
	
	public Date getLastLogin()
	{
		return lastLogin;
	}

}
