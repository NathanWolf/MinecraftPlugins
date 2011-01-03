public class RPGWandCommandDAO extends RPGPersisted 
{
	@RPGPersist(id = true)
	public int id;
	
	@RPGPersist
	public int wandId;
	
	@RPGPersist
	public String command;
	
	@RPGPersist
	public int order;
}
