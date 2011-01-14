import java.util.HashMap;
import java.util.logging.Level;


public class RPGSpellListener extends RPGPluginListener
{
	private static String SPELL_COMMAND = "spell";
	private boolean showHelp = false;
	
	// Spells
	private final HashMap<String, RPGSpell> spells = new HashMap<String, RPGSpell>();
	
	protected void loadSpells()
	{
		spells.put("blink", new RPGBlinkSpell());
		spells.put("heal", new RPGHealSpell());
		spells.put("ascend", new RPGAscendSpell());
		spells.put("descend", new RPGDescendSpell());
		spells.put("fireball", new RPGFireballSpell());
		spells.put("more", new RPGMoreSpell());
		spells.put("tower", new RPGTowerSpell());
		spells.put("light", new RPGLightSpell());
		spells.put("extend", new RPGExtendSpell());
	}
	
	protected void clearSpells()
	{
		spells.clear();
	}
	
	public void enable()
	{
		super.enable();
		
		// Load data from properties file
		PropertiesFile properties = new PropertiesFile("rpg.properties");		
		SPELL_COMMAND = properties.getString("command-spell", SPELL_COMMAND);
		showHelp = properties.getBoolean("show-spell-help", showHelp);

		loadSpells();
		
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

		clearSpells();
		
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
			String spellName = "";
			RPGSpell spell = null;
			if (command.length > 1)
			{
				spellName = command[1];
				spell = spells.get(spellName);
			}
			if (spell != null)
			{
				spell.cast(player, parameters);
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