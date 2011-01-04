import java.util.logging.Level;


public class RPGSpellListener extends RPGPluginListener
{
	private static String SPELL_COMMAND = "spell";
	private boolean showHelp = false;
	
	// Spells
	private static final RPGBlinkSpell blink = new RPGBlinkSpell();
	private static final RPGHealSpell heal = new RPGHealSpell();
	
	public void enable()
	{
		super.enable();
		
		// Load data from properties file
		PropertiesFile properties = new PropertiesFile("rpg.properties");		
		SPELL_COMMAND = properties.getString("command-spell", SPELL_COMMAND);
		showHelp = properties.getBoolean("show-spell-help", showHelp);

		// Add a message to the help screen
		if (showHelp)
		{
			etc inst = etc.getInstance();
			inst.addCommand("/" + SPELL_COMMAND, rpg.getText(RPGTextId.castHelp));
		}

		rpg.log(Level.INFO, "RPG Spell plugin enabled");
	}
	
	public void disable()
	{
		super.disable();
		
		if (showHelp)
		{
			etc inst = etc.getInstance();
			inst.removeCommand("/" + SPELL_COMMAND);
		}
	}
	
	public void initialize()
	{
		super.initialize();
		
		// Self-register
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, this, rpg, PluginListener.Priority.MEDIUM);
	}
	
	public boolean onCommand(Player player, String[] command) 
	{
		String[] parameters = new String[command.length - 1];
		for (int i = 1; i < command.length; i++)
		{
			parameters[i - 1] = command[i];
		}
		if (command[0].equalsIgnoreCase("/" + SPELL_COMMAND))
		{
			String spell = "";
			if (command.length > 1)
			{
				spell = command[1];
			}
			if (spell.equalsIgnoreCase("blink")) 
			{
				blink.cast(player, parameters);
			}
			else
			if (spell.equalsIgnoreCase("heal"))
			{
				heal.cast(player, parameters);
			}
			else
			{
				// show help
				String [] castUsage = rpg.getText(RPGTextId.castUsage).split("@");
				for (int i = 0; i < castUsage.length; i++) 
				{
					rpg.sendMessage(player, RPG.INFO_COLOR, castUsage[i]);
				}
			}
			return true;
		}
	
		return false;
	}
	
	
}