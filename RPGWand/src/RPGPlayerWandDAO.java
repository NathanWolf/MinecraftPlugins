import java.util.List;

public class RPGPlayerWandDAO extends RPGPersisted
{
	@RPGPersist(id = true)
	public String playerName;
	
	@RPGPersist
	public int currentWandId;
	
	public List<RPGWandDAO> wands;
	public RPGWandDAO currentWand;
}
