
public abstract class RPGPlugin extends Plugin   
{
	protected static RPG rpg = null;
	
	public static void setRPG(RPG rpg)
	{
		RPGPlugin.rpg = rpg;
	}
	
	public static RPG getRPG()
	{
		return rpg;
	}
	
	public void initialize()
	{
		getListener().initialize();
	}

	public void enable()
	{
		rpg = RPGPlugin.getRPG();
		getListener().enable();
	}

	public void disable()
	{
		rpg = null;
		getListener().disable();
	}
	
	public void load()
	{
		getListener().load();
	}

	public void save()
	{
		getListener().save();
	}
	
	public abstract RPGPluginListener getListener();
}
