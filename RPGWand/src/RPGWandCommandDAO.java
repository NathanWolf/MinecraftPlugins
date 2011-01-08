public class RPGWandCommandDAO extends RPGPersisted 
{
	@RPGPersist(id = true)
	public long id;
	
	@RPGPersist
	public Long wandId;
	
	@RPGPersist
	public String command;
	
	@RPGPersist(order = true)
	public int listOrder;
}
