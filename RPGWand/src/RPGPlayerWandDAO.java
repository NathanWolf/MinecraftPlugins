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
	
	public void nextWand()
	{
		int indexOfCurrent = wands.indexOf(currentWand);
		indexOfCurrent = (indexOfCurrent + 1) % wands.size();
		currentWand = wands.get(indexOfCurrent);
		currentWandId = currentWand.id;
		dirty = true;
	}	
	
	public List<RPGWandDAO> wands;
	public RPGWandDAO currentWand;
}
