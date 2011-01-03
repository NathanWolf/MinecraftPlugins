import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class RPGPluginListener extends PluginListener 
{
	protected RPG rpg;
	protected RPGPlugin plugin;
	
	public void initialize()
	{
	}

	public void enable() 
	{
	}
	
	public void disable() 
	{
	}
	
	public void setPlugin(RPGPlugin plugin, RPG rpg)
	{
		this.rpg = rpg;
		this.plugin = plugin;
		if (this.rpg == null)
		{
			Logger.getLogger("minecraft").log(Level.SEVERE, "No RPG instance found!");
		}
		else
		if (this.plugin == null)
		{
			rpg.log(Level.SEVERE, "No plugin instance bound!");
		}			
	}
	
}
