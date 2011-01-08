
public class RPGAscendSpell extends RPGLocationSpell
{
	public boolean cast(Player player, String[] parameters) 
	{
		Location location = findPlaceToStand(player, true);
		if (location != null) 
		{
			sendCastMessage(player, RPGTextId.castAscend, RPGTextId.viewAscend);
			player.teleportTo(location);
			return true;
		} 
		else 
		{		
			// no spot found to ascend
			rpg.sendMessage(player, RPG.ERROR_COLOR, RPGTextId.failAscend);
			return false;
		}
	}
}
