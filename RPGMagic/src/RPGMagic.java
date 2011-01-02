import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;


public class RPGMagic extends RPGPlugin
{
	public final RPGMagicListener listener = new RPGMagicListener();
	
	private final HashMap<String, RPGCommand> commands = new HashMap<String, RPGCommand>();
	private final Object commandsLock = new Object();
	
	public RPGPluginListener getListener()
	{
		return listener;
	}
	
	public void loadCommands(Connection conn)
	{
		synchronized(commandsLock)
		{	
	        RPGCommand.loadCommands(commands, conn, TABLE_PLAYERS);
	        log(Level.INFO, "Loaded " + players.values().size() + " players");
		}	
	}
}
