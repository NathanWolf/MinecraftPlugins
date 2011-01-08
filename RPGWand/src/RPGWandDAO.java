import java.util.ArrayList;
import java.util.List;

public class RPGWandDAO extends RPGPersisted
{
	@RPGPersist(id = true)
	public long id;
	
	@RPGPersist
	public String playerName;
	
	@RPGPersist
	public String name;

	@RPGPersist
	public String description;
	
	@RPGPersist
	public Long currentCommandId;
	
	@RPGPersist(order = true)
	public int listOrder;
	
	public RPGWandDAO()
	{
		commands = new ArrayList<RPGWandCommandDAO>();
	}
	
	public List<RPGWandCommandDAO> commands;
	public RPGWandCommandDAO currentCommand;
	
	public void nextCommand()
	{
		int indexOfCurrent = commands.indexOf(currentCommand);
		indexOfCurrent = (indexOfCurrent + 1) % commands.size();
		currentCommand = commands.get(indexOfCurrent);
		currentCommandId = currentCommand.id;
		dirty = true;
	}
}
