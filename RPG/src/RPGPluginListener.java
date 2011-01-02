import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class RPGPluginListener extends PluginListener 
{
	protected RPG rpg;
	
	public void initialize()
	{
		// Register command handlers here
	}
	
	public void enable() 
	{
		// make sure this gets called if you override enable()!
		this.rpg = RPGPlugin.getRPG();
		if (this.rpg == null)
		{
			Logger.getLogger("minecraft").log(Level.SEVERE, "No RPG instance found!");
		}
	}
	
	public void disable() 
	{
	}
	
}
