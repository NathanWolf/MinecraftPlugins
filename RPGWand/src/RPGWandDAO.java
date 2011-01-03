import java.util.List;

public class RPGWandDAO extends RPGPersisted
{
	@RPGPersist(id = true)
	public int id;
	
	@RPGPersist
	public String playerName;
	
	@RPGPersist
	public String name;

	@RPGPersist
	public int currentCommandId;
	
	@RPGPersist
	public int order;
	
	public List<RPGWandCommandDAO> commands;
	public RPGWandCommandDAO currentCommand;
	
}
