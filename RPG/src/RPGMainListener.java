import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class RPGMainListener extends RPGPluginListener
{	
	private static String RPG_COMMAND = "rpg";
	private static String PLAYERS_COMMAND = "players";
	
	public boolean onCommand(Player player, String[] command) 
	{
		if (command[0].equalsIgnoreCase("/reload")) 
		{
			disable();
			enable();
			return false;
		} 
		if (command[0].equalsIgnoreCase("/" + RPG_COMMAND) && player.canUseCommand("/" + RPG_COMMAND))
		{
			String rpgCommand = "";
			if (command.length > 1)
			{
				rpgCommand = command[1];
			}
			if (rpgCommand.equalsIgnoreCase(PLAYERS_COMMAND))
			{
				ArrayList<RPGPlayer> players = new ArrayList<RPGPlayer>();
				rpg.getPlayers(players);
				for (RPGPlayer rpgPlayer : players)
				{
					String playerText = rpgPlayer.getName() + " : ";
					DateFormat dateFormat = new SimpleDateFormat();
					String color;
					if (rpgPlayer.isOnline())
					{
						color = RPG.INFO_COLOR;
						playerText += rpg.getText(RPGTextId.online) + dateFormat.format(rpgPlayer.getLastLogin());
					}
					else
					{
						color = RPG.DISABLED_COLOR;
						playerText +=  rpg.getText(RPGTextId.offline) + dateFormat.format(rpgPlayer.getLastDisconnect());
					}
					rpg.sendMessage(player, color, playerText);
				}
			}
			else
			{
				// no params - show help
				String [] rpgUsage = rpg.getText(RPGTextId.usage).split("@");
				for (int i = 0; i < rpgUsage.length; i++) 
				{
					rpg.sendMessage(player, Colors.White, rpgUsage[i]);
				}
			}
			return true;
		}		
		return false;
	}
	
	
	public void onLogin(Player player)
	{
		rpg.log(Level.INFO, "onLogin");
		RPGPlayer rpgPlayer = rpg.getPlayer(player);
		Date now = new Date();
		rpgPlayer.setLastLogin(now);
		rpgPlayer.setOnline(true);
		rpgPlayer.setPlayer(player);
		rpg.save();
	}
	
	public void onDisconnect(Player player)
	{
		RPGPlayer rpgPlayer = rpg.getPlayer(player);
		Date now = new Date();
		rpgPlayer.setLastDisconnect(now);
		rpgPlayer.setOnline(false);
		rpg.save();
	}
	
	public void initialize()
	{
		super.initialize();
		
		// Set up handlers
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, this, rpg, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.LOGIN, this, rpg, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.DISCONNECT, this, rpg, PluginListener.Priority.MEDIUM);
		
		rpg.log(Level.INFO, "Main RPG listener initialized");
	}
	
	public void enable()
	{
		super.enable();

		// Load data from properties file
		PropertiesFile properties = new PropertiesFile("rpg.properties");
		RPG_COMMAND = properties.getString("command-rpg", RPG_COMMAND);
		PLAYERS_COMMAND = properties.getString("command-players", PLAYERS_COMMAND);
		
		etc inst = etc.getInstance();
		inst.addCommand("/" + RPG_COMMAND, rpg.getText(RPGTextId.help));
	}
	
	public void disable()
	{
		super.disable();
		
		etc inst = etc.getInstance();
		inst.removeCommand("/" + RPG_COMMAND);		
	}

}
