import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RPGCommand extends RPGPersisted
{
	@RPGPersist(id = true)
	public String name;
	
	@RPGPersist
	public String command;
	
	@RPGPersist
	public String description;
	
	@RPGPersist
	public double initialCost;
	
	@RPGPersist
	public double costPerSecond;
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getCommand()
	{
		return command;
	}
	
	public void load(Connection conn, String tableName, HashMap<String, RPGCommand> commandMap)
	{
		List<RPGPersisted> objects = new ArrayList<RPGPersisted>();
		load(conn, tableName, objects);
		for (RPGPersisted o : objects)
		{
			RPGCommand command = (RPGCommand)o;
			commandMap.put(command.getName(), command); 
		}
	}
	
}
