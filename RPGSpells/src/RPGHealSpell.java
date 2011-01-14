
public class RPGHealSpell extends RPGSpell
{
	private int HEAL_AMOUNT = 20;
	
	public boolean onCast(String[] parameters) 
	{
		int health;
		try 
		{
			health = player.getHealth();
		}
		catch (Exception e) 
		{
			rpg.sendMessage(player, RPG.ERROR_COLOR, "The heal spell is currently broken, sorry!");
			return false;
		}
		if (health == 20) 
		{
			rpg.sendMessage(player, RPG.INFO_COLOR, rpg.getText(RPGTextId.healFullHealth));
			return false;
		} 
		else 
		{
			health += HEAL_AMOUNT;
			if (health > 20) 
			{
				health = 20;
			}
			player.setHealth(health);
			sendCastMessage(player, RPGTextId.castHeal, RPGTextId.viewHeal);
			return true;
		}
	}
	
}
