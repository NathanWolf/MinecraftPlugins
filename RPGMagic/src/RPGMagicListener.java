import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class RPGMagicListener extends RPGPluginListener
{
	private static String MAGIC_COMMAND = "cast";
	private boolean showHelp = false;
	
	public void enable()
	{
		// Load data from properties file
		PropertiesFile properties = new PropertiesFile("rpg.properties");		
		MAGIC_COMMAND = properties.getString("command-magic", MAGIC_COMMAND);
		showHelp = properties.getBoolean("show-magic-help", showHelp);

		// Add a message to the help screen
		if (showHelp)
		{
			etc inst = etc.getInstance();
			inst.addCommand("/" + MAGIC_COMMAND, rpg.getText(RPGTextId.magicHelp));
		}

		rpg.log(Level.INFO, "RPG Magic plugin enabled");
	}
	
	public void disable()
	{
		if (showHelp)
		{
			etc inst = etc.getInstance();
			inst.removeCommand("/" + MAGIC_COMMAND);
		}	
	}
	
	public void initialize()
	{
		// Self-register
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, this, rpg, PluginListener.Priority.MEDIUM);
	}
	
	public boolean onCommand(Player player, String[] commandLine) 
	{	
		RPGMagic magic = (RPGMagic)plugin;
		if (magic == null)
		{
			rpg.log(Level.SEVERE, "Missing or incompatible RPGMagic plugin");
			return false;
		}
		if (commandLine[0].equalsIgnoreCase("/" + MAGIC_COMMAND))
		{
			RPGCommand command = null;
			String commandName = "";
			if (commandLine.length > 1)
			{
				commandName = commandLine[1];
				command = magic.getCommand(commandName);
			}
			
			if (command != null)
			{
				String commandText = command.getCommand(); 
				if (commandText == null || commandText.length() <=0)
				{
					rpg.log(Level.WARNING, "RPG command '" + commandName + "' missing command");
				}
				else
				{
					player.chat(commandText);
				}
			}
			else
			{
				// show help
				String [] spellList = rpg.getText(RPGTextId.magicSpellList).split("@");
				for (int i = 0; i < spellList.length; i++) 
				{
					rpg.sendMessage(player, RPG.HEADER_COLOR, spellList[i]);
				}
				List<RPGCommand> allCommands = new ArrayList<RPGCommand>();
				magic.getCommands(allCommands);
				for (RPGCommand c : allCommands)
				{
					rpg.sendMessage(player, RPG.INFO_COLOR, c.getName() + " : " + c.getDescription());
				}
			}
			return true;
		}
		return false;
	}

}