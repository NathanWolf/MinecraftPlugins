import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;


public class RPGWand extends RPGPlugin
{
	private static String TABLE_WANDS = "rpg_wands";
	private static String TABLE_WAND_COMMANDS = "rpg_wand_commands";
	private static String TABLE_PLAYERS_WANDS = "rpg_player_wands";
	
	public final RPGWandListener listener = new RPGWandListener();
	
	private final HashMap<RPGPlayer, RPGPlayerWand> wands = new HashMap<RPGPlayer, RPGPlayerWand>();
	private final Object wandsLock = new Object();
	
	public RPGPluginListener getListener()
	{
		return listener;
	}
	
	public void loadWands(Connection conn)
	{
		synchronized(wandsLock)
		{	
	        RPGPlayerWand.load(conn, TABLE_PLAYERS_WANDS, TABLE_WANDS, TABLE_WAND_COMMANDS, wands);
	        RPG.getRPG().log(Level.INFO, "Loaded " + wands.values().size() + " wands");
		}	
	}
	
	public void load(Connection conn)
	{
		super.load(conn);
		
		loadWands(conn);
	}
	
	public void getCommands(List<RPGPlayerWand> wandList)
	{
		synchronized(wandsLock)
		{
			wandList.addAll(wands.values());
		}
	}
	
	public RPGPlayerWand getWand(RPGPlayer player)
	{
		RPGPlayerWand wand = null;
		synchronized(wandsLock)
		{
			wand = wands.get(player);
		}
		return wand;
	}

}
