import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;

public class RPGPlayer 
{
	protected RPGPlayerDAO dao;
	protected Player player;
	
	public RPGPlayer()
	{
		dao = new RPGPlayerDAO();
	}
	
	public RPGPlayer(RPGPlayerDAO dao)
	{
		this.dao = dao;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
		dao.name = player.getName();
	}
	
	public void setFirstLogin(Date d)
	{
		dao.firstLogin = d;
		dao.dirty = true;
	}
	
	public void setLastLogin(Date d)
	{
		dao.lastLogin = d;
		dao.dirty = true;
	}
	
	public void setLastDisconnect(Date d)
	{
		dao.lastDisconnect = d;
		dao.dirty = true;
	}

	public static void load(Connection conn, String tableName, HashMap<String, RPGPlayer> players)
	{
		List<RPGPersisted> objects = new ArrayList<RPGPersisted>();
		RPGPlayerDAO loader = new RPGPlayerDAO();
		loader.load(conn, tableName, objects);
		for (RPGPersisted o : objects)
		{
			RPGPlayerDAO dao = (RPGPlayerDAO)o;
			RPGPlayer player = new RPGPlayer(dao);
			players.put(player.getName(), player); 
		}
	}
	
	public static void save(Connection conn, String tableName, HashMap<String, RPGPlayer> players)
	{
		List<RPGPersisted> objects = new ArrayList<RPGPersisted>();	
		for (RPGPlayer player : players.values())
		{
			RPGPlayerDAO dao = player.dao;
			// Sanity check
			if (dao.name != null && dao.lastLogin != null && dao.firstLogin != null)
			{
				objects.add(dao);
			}
		}
		RPGPlayerDAO saver = new RPGPlayerDAO();
		saver.save(conn, tableName, objects);
	}
	
	public void setOnline(boolean online)
	{
		dao.online = online;
		if (!online)
		{
			player = null;
		}
	}
	
	public boolean isOnline()
	{
		return dao.online;
	}
	
	public String getName()
	{
		return dao.name;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Date getLastDisconnect()
	{
		return dao.lastDisconnect;
	}
	
	public Date getFirstLogin()
	{
		return dao.firstLogin;
	}	
	
	public Date getLastLogin()
	{
		return dao.lastLogin;
	}

}
