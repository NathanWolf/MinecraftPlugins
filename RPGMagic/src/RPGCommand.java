
public class RPGCommand extends RPGPersisted
{
	@RPGPersist
	@RPGPersistId
	private String name;
	
	@RPGPersist
	private String command;
	
	public String getName()
	{
		return name;
	}
	
	public String getCommand()
	{
		return command;
	}
}
