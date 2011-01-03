import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RPGCommand
{	
	protected RPGCommandDAO dao;
	
	public RPGCommand()
	{
		dao = new RPGCommandDAO();
	}
	
	public RPGCommand(RPGCommandDAO dao)
	{
		this.dao = dao;
	}
	
	public String getName()
	{
		return dao.name;
	}
	
	public String getDescription()
	{
		return dao.description;
	}
	
	public String getCommand()
	{
		return dao.command;
	}
	
	public static void load(Connection conn, String tableName, HashMap<String, RPGCommand> commandMap)
	{
		List<RPGPersisted> objects = new ArrayList<RPGPersisted>();
		RPGCommandDAO loader = new RPGCommandDAO();
		loader.load(conn, tableName, objects);
		for (RPGPersisted o : objects)
		{
			RPGCommandDAO dao = (RPGCommandDAO)o;
			RPGCommand command = new RPGCommand(dao);
			commandMap.put(command.getName(), command); 
		}
	}
	
}
