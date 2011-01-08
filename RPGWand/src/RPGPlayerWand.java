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
	
	public void nextWand()
	{
		wands.nextWand();
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
	
	public void use(Player player)
	{
		if (wands.currentWand != null && wands.currentWand.currentCommand != null)
		{
			String command = wands.currentWand.currentCommand.command;
			if (command != null)
			{
				if (command.length() > 0)
				{
					if (command.charAt(0) != '/')
					{
						command = "/cast " + command;
					}
					player.chat(command);
				}
			}
		}
	}
	
	public void nextCommand()
	{
		if (wands.currentWand != null)
		{
			wands.currentWand.nextCommand();
		}
	}

	public static void load(Connection conn, String playerTableName, String wandTableName, String commandTableName, HashMap<RPGPlayer, RPGPlayerWand> wands)
	{
		List<RPGPersisted> playerDAOList = new ArrayList<RPGPersisted>();
		List<RPGPersisted> wandDAOList = new ArrayList<RPGPersisted>();
		List<RPGPersisted> commandDAOList = new ArrayList<RPGPersisted>();

		RPGPlayerWandDAO playerLoader = new RPGPlayerWandDAO();
		RPGWandDAO wandLoader = new RPGWandDAO();
		RPGWandCommandDAO commandLoader = new RPGWandCommandDAO();
		
		playerLoader.load(conn, playerTableName, playerDAOList);
		wandLoader.load(conn, wandTableName, wandDAOList);
		commandLoader.load(conn, commandTableName, commandDAOList);
		
		// Bind everything together
		for (RPGPersisted playerDAO : playerDAOList)
		{
			RPG rpg = RPG.getRPG();
			RPGPlayerWandDAO playerWandDAO = (RPGPlayerWandDAO)playerDAO;
			
			// Bind all the wands to the PlayerWand object
			for (RPGPersisted wandDAO : wandDAOList)
			{
				RPGWandDAO wand = (RPGWandDAO)wandDAO;
				if (wand.playerName.equalsIgnoreCase(playerWandDAO.playerName))
				{
					playerWandDAO.wands.add(wand);
					if (playerWandDAO.currentWandId == null || wand.id == playerWandDAO.currentWandId)
					{
						playerWandDAO.currentWandId = wand.id;
						playerWandDAO.currentWand = wand;
					}
					
					// Bind commands to wand
					for (RPGPersisted commandDAO : commandDAOList)
					{
						RPGWandCommandDAO command = (RPGWandCommandDAO)commandDAO;
						if (command.wandId != wand.id) continue;
						wand.commands.add(command);
						if (wand.currentCommandId == null || command.id == wand.currentCommandId)
						{
							wand.currentCommandId = command.id;
							wand.currentCommand = command;
						}
					}							
				}
			}
			
			// Bind the PlayerWand object to its player
			RPGPlayerWand playerWand = new RPGPlayerWand(playerWandDAO);
			RPGPlayer player = rpg.findPlayer(playerWandDAO.playerName);
			
			if (player == null)
			{
				rpg.log(Level.WARNING, "Can't find player '" + playerWandDAO.playerName + "' for wand");
				continue;
			}
			
			wands.put(player, playerWand);
		}
	}
	
	public List<RPGWandDAO> getWands()
	{
		return wands.wands;
	}
}
