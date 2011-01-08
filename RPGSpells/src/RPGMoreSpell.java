
public class RPGMoreSpell extends RPGSpell
{
	public boolean cast(Player player, String[] parameters) 
	{
		HitBlox hit = new HitBlox(player);
		Block target = hit.getTargetBlock();
		
		if (target == null) 
		{
			rpg.sendMessage(player, RPG.INFO_COLOR, rpg.getText(RPGTextId.blinkNoTarget));
			return false;
		}
		int amount = 64;
		Item item = new Item(target.getType(), amount);
		player.giveItem(item);
		return true;
	}
}
