
public class RPGCommandDAO extends RPGPersisted
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
}