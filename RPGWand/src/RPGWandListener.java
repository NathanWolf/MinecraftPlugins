import java.util.logging.Level;

public class RPGWandListener extends RPGPluginListener
{
	private static String WAND_COMMAND = "wand";
	private static String WAND_LIST_COMMAND = "list";
	private boolean showHelp = false;
	
	public void enable()
	{
		// Load data from properties file
		PropertiesFile properties = new PropertiesFile("rpg.properties");		
		WAND_COMMAND = properties.getString("command-wand", WAND_COMMAND);
		WAND_LIST_COMMAND = properties.getString("command-wand-list", WAND_LIST_COMMAND);
		showHelp = properties.getBoolean("show-wand-help", showHelp);

		// Add a message to the help screen
		if (showHelp)
		{
			etc inst = etc.getInstance();
			inst.addCommand("/" + WAND_COMMAND, rpg.getText(RPGTextId.magicHelp));
		}	
	}
	
	public void disable()
	{
		if (showHelp)
		{
			etc inst = etc.getInstance();
			inst.removeCommand("/" + WAND_COMMAND);
		}	
	}
	
	public void initialize()
	{
		// Self-register
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, this, rpg, PluginListener.Priority.MEDIUM);
		rpg.log(Level.INFO, "RPG Wand plugin initialized");
	}
	
	public boolean onCommand(Player player, String[] command) 
	{
		if (command[0].equalsIgnoreCase("/" + WAND_COMMAND))
		{
			String commandName = "";
			if (command.length > 1)
			{
				commandName = command[1];
			}
			
			if (commandName.equalsIgnoreCase(WAND_LIST_COMMAND))
			{
				// TODO
			}
			else
			{
				// show help
				// TODO
			}
			return true;
		}
		return false;
	}

}