
public abstract class RPGSpell 
{
	protected RPG rpg;
	private static String MAGIC_CAST_COLOR = RPG.INFO_COLOR;
	private static String MAGIC_VIEW_COLOR = RPG.DISABLED_COLOR;
	
	public RPGSpell()
	{
		this.rpg = RPGPlugin.getRPG();
	}
	
	public boolean cast(Player player, String[] parameters)
	{
		return false;
	}
	
	public void sendCastMessage(Player player, RPGTextId cast, RPGTextId view)
	{
		rpg.sendMessage(player, MAGIC_CAST_COLOR, cast);
		rpg.sendMessageToPlayersInRange(player, MAGIC_VIEW_COLOR, view);
	}
}
