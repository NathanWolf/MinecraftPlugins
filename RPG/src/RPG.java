import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author NathanWolf
*/
public class RPG extends RPGPlugin  
{
	private String version = "0.31";

	private static final Logger log = Logger.getLogger("Minecraft");

	private final Object playersLock = new Object();
	private final Object pluginsLock = new Object();
	
	private HashMap<String, RPGPlayer> players = new HashMap<String, RPGPlayer>();
	private HashMap<String, RPGPlugin> plugins = new HashMap<String, RPGPlugin>();
	private HashMap<RPGTextId, String> text = new HashMap<RPGTextId, String>();
	
	private String prefix = "";
	
	private final RPGMainListener listener = new RPGMainListener();

	private static String TABLE_PLAYERS = "rpg_players";
	private static String TABLE_TEXT = "rpg_text";
	
	private static String PREFIX_COLOR = Colors.Gray;
	public static String INFO_COLOR = Colors.White;
	public static String HEADER_COLOR = Colors.LightBlue;
	public static String ERROR_COLOR = Colors.Red;
	public static String DISABLED_COLOR = Colors.LightGray;
	
	public static int DEFAULT_MESSAGE_RANGE = 16;
	
	public void enable() 
	{
		log(Level.INFO, "RPG v" + version + " starting up");
		
		setRPG(this);
		
		// Read static properties, mainly database table names.
		PropertiesFile properties = new PropertiesFile("rpg.properties");
		TABLE_TEXT = properties.getString("table-text", TABLE_TEXT);
		TABLE_PLAYERS = properties.getString("table-players", TABLE_PLAYERS);
		
		// Load database data
		load();
		
		// Load plugins
		String pluginText = properties.getString("plugins");
		String[] pluginSplit = pluginText.split(",");
		for (String pluginName : pluginSplit)
		{
			loadPlugin(pluginName);
		}
	
		// Enable base and plugins
		super.enable();
		synchronized (pluginsLock) 
        {
			for (RPGPlugin plugin : plugins.values())
			{
				plugin.enable();
			}
        }
		
		// Reload everything, to give plugins a chance to load.
		load();
	}
	
	public void disable() 
	{
		// Save database data
		save();
		
		// Disable base and plugins
		super.disable();
		synchronized (pluginsLock) 
        {
			for (RPGPlugin plugin : plugins.values())
			{
				plugin.disable();
			}
        }
	}

	public void initialize() 
	{	
		super.initialize();
		synchronized (pluginsLock) 
        {
			for (RPGPlugin plugin : plugins.values())
			{
				plugin.initialize();
			}
        }
		log(Level.INFO, "RPG initialized");		
	}
	
	protected boolean loadPlugin(String fileName)
	{
		if (fileName.length() <= 0)
		{
			return false;
		}
		log(Level.INFO, "Loading RPG plugin " + fileName);
		try
		{
			File file = new File("plugins/" + fileName + ".jar");
            if (!file.exists()) 
            {
                log(Level.SEVERE, "Failed to find RPG plugin file: plugins/" + fileName + ".jar. Please ensure the file exists");
                return false;
            }
            URLClassLoader child = null;
            try 
            {
                child = new MyClassLoader(new URL[]{file.toURI().toURL()}, RPG.class.getClassLoader());
            } 
            catch (MalformedURLException ex) 
            {
                log(Level.SEVERE, "Exception while loading class", ex);
                return false;
            }

			@SuppressWarnings("unchecked")
            Class<RPGPlugin> c = (Class<RPGPlugin>)child.loadClass(fileName);
			RPGPlugin plugin = (RPGPlugin) c.newInstance();
            plugin.enable();
            synchronized (pluginsLock) 
            {
            	addPlugin(fileName, plugin);
            }
            return true;
		}
		catch(Exception ex)
		{
			log(Level.WARNING, "Unable to load RPG plugin: " + fileName);
		}
		return false;
	}
	
	protected void addPlugin(String pluginName, RPGPlugin plugin)
	{
		plugins.put(pluginName, plugin);
	}
	
	protected void removePlugin(String pluginName)
	{
		 synchronized (pluginsLock) 
         {
			 plugins.get(pluginName).disable();
			 plugins.remove(pluginName);
         }
	}
	
	public void savePlayers(Connection conn)
	{
		synchronized(playersLock)
		{
        	for (RPGPlayer rpgPlayer : players.values())
			{
        		rpgPlayer.save(conn, TABLE_PLAYERS);
            }
		}
	}
	
	public void save()
	{
		Connection conn = etc.getSQLConnection();
		if (conn == null)
		{
            log(Level.SEVERE, "Can't get SQL connection");
            return;
		}
		savePlayers(conn);
		super.save(conn);
		synchronized(pluginsLock)
		{
			for (RPGPlugin plugin : plugins.values())
			{
				plugin.save(conn);
			}
		}
		try
		{
			if (conn != null) 
			{
				conn.close();
			}
		}
		catch (SQLException ex) 
		{
		}
	}
	
	public void loadText(Connection conn)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try 
        {
			ps = conn.prepareStatement("SELECT * FROM " + TABLE_TEXT);
            rs = ps.executeQuery();
            while (rs.next()) 
            {
            	int textId = rs.getInt("id");
            	RPGTextId id = RPGTextId.values()[textId];
    			String value = rs.getString("text");
    			text.put(id, value);
            }     
		}
		catch (SQLException ex) 
        {
            log(Level.SEVERE, "Error loading players", ex);
        } 
        finally 
        {
            try 
            {
                if (ps != null) 
                {
                    ps.close();
                }
                if (rs != null) 
                {
                    rs.close();
                }
            } 
            catch (SQLException ex) 
            {
            }
        }
        
        // Cache the prefix, it's used frequently.
        prefix = getText(RPGTextId.prefix);
	}
	
	public void loadPlayers(Connection conn)
	{
		synchronized(playersLock)
		{	
	        RPGPlayer.loadPlayers(players, conn, TABLE_PLAYERS);
	        log(Level.INFO, "Loaded " + players.values().size() + " players");
		}	
	}
	
	public void load()
	{
		Connection conn = etc.getSQLConnection();
		if (conn == null)
		{
            log(Level.SEVERE, "Can't get SQL connection");
            return;
		}
		loadText(conn);
		loadPlayers(conn);
		super.load(conn);
		synchronized(pluginsLock)
		{
			for (RPGPlugin plugin : plugins.values())
			{
				plugin.load(conn);
			}
		}
		try
		{
			if (conn != null) 
			{
				conn.close();
			}
		}
		catch (SQLException ex) 
		{
		}
	}
	
	public void getPlayers(List<RPGPlayer> playerList)
	{
		synchronized(playersLock)
		{	
			for (RPGPlayer player : players.values())
			{
				playerList.add(player);
			}
		}
	}

	public RPGPlayer createPlayer(Player player)
	{
		RPGPlayer rpgPlayer = new RPGPlayer();
		Date now = new Date();
		rpgPlayer.setFirstLogin(now);	
		rpgPlayer.setPlayer(player);
		players.put(player.getName(), rpgPlayer);	
		return rpgPlayer;
	}
	
	public RPGPlayer getPlayer(Player player)
	{
		RPGPlayer rpgPlayer = null;
		synchronized(playersLock)
		{
			rpgPlayer = players.get(player.getName());
			if (rpgPlayer == null)
			{
				rpgPlayer = createPlayer(player);
				log(Level.INFO, "Player " + player.getName() + " joined, new RPG profile created");
				save();
			}
			else
			{
				log(Level.INFO, "Player " + player.getName() + " joined, RPG profile loaded");
			}
		}
		return rpgPlayer;
	}
	
	public void sendMessage(Player player, String color, String message)
	{
		if (message.length() <= 0)
		{
			return;
		}
		player.sendMessage(PREFIX_COLOR + prefix + color + message);
	}
		
	public void sendMessageToPlayersInRange(Player player, String color, String message) 
	{
		sendMessageToPlayersInRange(player, color, message, DEFAULT_MESSAGE_RANGE);
	}
	
	public void sendMessageToPlayersInRange(Player player, String color, String message, int range) 
	{
		message = message.replace("[caster]", player.getName());
		List<Player> allPlayers = etc.getServer().getPlayerList();

		// get players in range
		for (Player p : allPlayers) 
		{
			if 
			(
					!p.getName().equalsIgnoreCase(player.getName())
			&& 		isPlayerInRange(p,player,range)
			) 
			{
				// send msg
				sendMessage(p, color, message);
			}
		}
	}
	
	public boolean isPlayerInRange(Player p1, Player p2, int range) 
	{
		return 
		(
			Math.sqrt
			(
				Math.pow(p1.getX() - p2.getX(), 2)
			+	Math.pow(p1.getY() - p2.getY(), 2)
			+	Math.pow(p1.getZ() - p2.getZ(), 2)
			)
			<= range
		);
	}
	
	public String getText(RPGTextId id)
	{
		return text.get(id);
	}
	
	public void log(Level level, String message)
	{
		log.log(level, prefix + message);
	}
	
	public void log(Level level, String message, Exception ex)
	{
		log.log(level, prefix + message, ex);
	}
	
	// Returns the main listener- needed for abstract base class RPGPLugin.
	public RPGPluginListener getListener()
	{
		return listener;
	}
}
