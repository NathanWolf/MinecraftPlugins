import java.util.ArrayList;
import java.util.List;

public class RPGPlayerWandDAO extends RPGPersisted
{
	@RPGPersist(id = true)
	public String playerName;
	
	@RPGPersist
	public Long currentWandId;
	
	public RPGPlayerWandDAO()
	{
		wands = new ArrayList<RPGWandDAO>();
	}
	
	public List<RPGWandDAO> wands;
	public RPGWandDAO currentWand;
}
