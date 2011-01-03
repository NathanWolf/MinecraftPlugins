import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;


public class RPGPlayerWand extends RPGPersisted
{
	protected RPGPlayerWandDAO wands;
	
	public RPGPlayerWand()
	{
		wands = new RPGPlayerWandDAO();
	}
	
	public RPGPlayerWand(RPGPlayerWandDAO wands)
	{
		this.wands = wands;
	}
	
	public void selectWand(RPGWandDAO wand)
	{
		wands.currentWand = wand;
		wands.currentWandId = wand.id;
	}
	
	public void addWand(RPGWandDAO wand)
	{
		wands.wands.add(wand);
		selectWand(wand);
	}

	public static void load(Connection conn, String playerTableName, String wandTableName, String commandTableName, HashMap<RPGPlayer, RPGPlayerWand> wands)
	{
		List<RPGPersisted> playerDAOList = new ArrayList<RPGPersisted>();
		List<RPGPersisted> wandDAOList = new ArrayList<RPGPersisted>();
		List<RPGPersisted> commandDAOList = new ArrayList<RPGPersisted>();

		RPGPlayerWandDAO playerLoader = new RPGPlayerWandDAO();
		RPGWandDAO wandLoader = new RPGWandDAO();
		RPGWandCommandDAO commandLoader = new RPGWandCommandDAO();
		
		playerLoader.load(conn, playerTableName, commandDAOList);
		wandLoader.load(conn, wandTableName, wandDAOList);
		commandLoader.load(conn, commandTableName, playerDAOList);
		
		// Map wands by id
		HashMap<Integer, RPGWandDAO> wandMap = new HashMap<Integer, RPGWandDAO>();
		for (RPGPersisted o : wandDAOList)
		{
			RPGWandDAO wandDAO = (RPGWandDAO)o;
			wandMap.put(wandDAO.id, wandDAO);
		}
		
		// Map commands by id
		HashMap<Integer, RPGWandCommandDAO> commandMap = new HashMap<Integer, RPGWandCommandDAO>();
		for (RPGPersisted o : commandDAOList)
		{
			RPGWandCommandDAO commandDAO = (RPGWandCommandDAO)o;
			commandMap.put(commandDAO.id, commandDAO);
		}		
		
		// Bind everything together
		for (RPGPersisted o : playerDAOList)
		{
			RPG rpg = RPG.getRPG();
			RPGPlayerWandDAO playerDAO = (RPGPlayerWandDAO)o;
			RPGPlayerWand playerWand = new RPGPlayerWand(playerDAO);
			RPGPlayer player = rpg.findPlayer(playerDAO.playerName);
			
			if (player == null)
			{
				rpg.log(Level.WARNING, "Can't find player '" + playerDAO.playerName + "' for wand");
				continue;
			}
			
			wands.put(player, playerWand);
		}
	}
	
}
