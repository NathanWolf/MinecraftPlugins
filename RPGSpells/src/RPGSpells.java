
public class RPGSpells extends RPGPlugin
{
	public final RPGSpellListener listener = new RPGSpellListener();
	
	public RPGPluginListener getListener()
	{
		return listener;
	}
}
