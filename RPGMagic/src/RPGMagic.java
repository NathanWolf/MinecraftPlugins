import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;


public class RPGMagic extends RPGPlugin
{
	private static String TABLE_MAGIC = "rpg_magic";
	
	public final RPGMagicListener listener = new RPGMagicListener();
	
	private final HashMap<String, RPGPersisted> commands = new HashMap<String, RPGPersisted>();
	private final Object commandsLock = new Object();
	
	public RPGPluginListener getListener()
	{
		return listener;
	}
	
	public void loadCommands(Connection conn)
	{
		synchronized(commandsLock)
		{	
	        RPGCommand.load(conn, TABLE_MAGIC, commands, RPGCommand.class);
	        RPG.getRPG().log(Level.INFO, "Loaded " + commands.values().size() + " commands");
		}	
	}
	
	public void load(Connection conn)
	{
		super.load(conn);
		
		loadCommands(conn);
	}
}
