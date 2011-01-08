
public class RPGDescendSpell extends RPGLocationSpell 
{
	public boolean cast(Player player, String[] parameters) 
	{
		Location location = findPlaceToStand(player, false);
		if (location != null) 
		{
			sendCastMessage(player, RPGTextId.castDescend, RPGTextId.viewDescend);
			player.teleportTo(location);
			return true;
		} 
		else 
		{		
			// no spot found to ascend
			rpg.sendMessage(player, RPG.ERROR_COLOR, RPGTextId.failDescend);
			return false;
		}
	}
}
