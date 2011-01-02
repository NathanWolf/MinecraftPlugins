import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;


public class RPGCommand 
{
	private String command;
	private String name;
	
	public void load(ResultSet rs)
	{
		try 
        {
			name = rs.getString("name");
			command = rs.getString("command");
        }
		catch (SQLException ex) 
        {
            RPG.getRPG().log(Level.SEVERE, "Error loading command", ex);
        } 		 
	}

	public static void loadCommands(HashMap<String, RPGCommand> commands, Connection conn, String tableName)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try 
        {
			ps = conn.prepareStatement("SELECT * FROM " + tableName);
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                RPGCommand newCommand = new RPGCommand();
                newCommand.load(rs);
                commands.put(newCommand.getName(), newCommand);
            }     
		}
		catch (SQLException ex) 
        {
			RPG.getRPG().log(Level.SEVERE, "Error loading commands", ex);
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
	

	public void save(Connection conn, String tableName)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try 
        {
			String sqlUpdate = 
				"INSERT INTO " 
			+ 	tableName 
			+ 	" (name, command) VALUES(?, ?)"
			+	" ON DUPLICATE KEY UPDATE command = ?" +
					"";
			ps = conn.prepareStatement(sqlUpdate);
				
			ps.setString(1, name);
			ps.setString(2,command);
			ps.setString(3,command);
			
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
	
	public String getName()
	{
		return name;
	}
	
	public String getCommand()
	{
		return command;
	}
}
