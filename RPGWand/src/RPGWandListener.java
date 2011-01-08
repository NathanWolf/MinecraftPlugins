import java.util.logging.Level;

public class RPGWandListener extends RPGPluginListener
{
	private static String WAND_COMMAND = "wand";
	private static String WAND_LIST_COMMAND = "list";
	private static String WAND_NEXT_COMMAND = "next";
	private static int WAND_ITEM_ID;	
	private boolean showHelp = false;
	
	public void enable()
	{
		// Load data from properties file
		PropertiesFile properties = new PropertiesFile("rpg.properties");		
		WAND_COMMAND = properties.getString("command-wand", WAND_COMMAND);
		WAND_LIST_COMMAND = properties.getString("command-wand-list", WAND_LIST_COMMAND);
		WAND_ITEM_ID = properties.getInt("wand-item-id", 280);
		showHelp = properties.getBoolean("show-wand-help", showHelp);

		// Add a message to the help screen
		if (showHelp)
		{
			etc inst = etc.getInstance();
			inst.addCommand("/" + WAND_COMMAND, rpg.getText(RPGTextId.magicHelp));
		}
		rpg.log(Level.INFO, "RPG Wand plugin enabled");
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
		etc.getLoader().addListener(PluginLoader.Hook.ARM_SWING, this, rpg, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.ITEM_USE, this, rpg, PluginListener.Priority.MEDIUM);
	}
	
	public void onArmSwing(Player p) 
	{
		if (p.getItemInHand() == WAND_ITEM_ID) 
		{
			RPGPlayer player = rpg.getPlayer(p);
			if (player == null)
			{
				return;
			}
			RPGPlayerWand wand = ((RPGWand)plugin).getWand(player);
			if (wand == null)
			{
				return;
			}
			wand.use(p);
		}
	}

	public boolean onItemUse(Player p, Block placed, Block clicked, Item item) 
	{
		if (item.getItemId() == WAND_ITEM_ID) 
		{
			RPGPlayer player = rpg.getPlayer(p);
			if (player == null)
			{
				return false;
			}
			RPGPlayerWand wand = ((RPGWand)plugin).getWand(player);
			if (wand == null)
			{
				return false;
			}
			wand.nextCommand();
			rpg.sendMessage(p, RPG.INFO_COLOR, "Wand enchanted with '" + wand.wands.currentWand.currentCommand.command + "'");
			return true;
		}
		return false;
	}
	
	public boolean onCommand(Player p, String[] commandLine) 
	{
		if (commandLine[0].equalsIgnoreCase("/" + WAND_COMMAND))
		{
			String commandName = "";
			if (commandLine.length > 1)
			{
				commandName = commandLine[1];
			}
			RPGPlayer player = rpg.getPlayer(p);
			RPGPlayerWand wands = null;
			if (player != null)
			{
				wands = ((RPGWand)plugin).getWand(player);
			}
			
			if (commandName.equalsIgnoreCase(WAND_LIST_COMMAND))
			{
				if (wands != null)
				{
					rpg.sendMessage(p, RPG.HEADER_COLOR, wands.wands.wands.size() + " wands:");
					for (RPGWandDAO wand : wands.getWands())
					{
						String wandPrefix = " ";
						if (wand == wands.wands.currentWand)
						{
							wandPrefix = "*";
						}
						rpg.sendMessage(p, RPG.HEADER_COLOR, wandPrefix + wand.name + " : " + wand.description);
						for (RPGWandCommandDAO command : wand.commands)
						{
							String commandPrefix = "  ";
							if (command == wand.currentCommand)
							{ 
								commandPrefix = " *";
							}
							rpg.sendMessage(p, RPG.INFO_COLOR, commandPrefix + command.command);
						}
					}
				}
				else
				{
					// TODO
				}
			}
			else
			if (commandName.equalsIgnoreCase(WAND_NEXT_COMMAND))
			{
				if (wands != null)
				{
					wands.nextWand();
					rpg.sendMessage(p, RPG.HEADER_COLOR, "Selected wand: " + wands.wands.currentWand.name);
				}
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