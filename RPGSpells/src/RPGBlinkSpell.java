
public class RPGBlinkSpell extends RPGSpell
{
	private int RANGE = 0;
	
	public boolean onCast(String[] parameters) 
	{
		Block target = getTargetBlock();
		Block face = getLastBlock();
		
		if (target == null) 
		{
			rpg.sendMessage(player, RPG.INFO_COLOR, rpg.getText(RPGTextId.blinkNoTarget));
			return false;
		} 
		else if (RANGE > 0 && getDistance(player,target) > RANGE) 
		{
			rpg.sendMessage(player, RPG.INFO_COLOR, rpg.getText(RPGTextId.blinkTooFar));
			return false;
		} 
		else if 
		(
			etc.getServer().getBlockIdAt(target.getX(),target.getY()+1,target.getZ()) == 0 
		&& 	etc.getServer().getBlockIdAt(target.getX(),target.getY()+2,target.getZ()) == 0
		) 
		{
			// teleport to top of target block if possible
			sendCastMessage(player, RPGTextId.castBlink, RPGTextId.viewBlink);
			player.teleportTo
			(
				new Location
				(
					target.getX() + 0.5,
					target.getY() + 1.0,
					target.getZ() + 0.5,
					player.getRotation(),
					player.getPitch()
				)
			);
			return true;
		} 
		else if 
		(
				face.getType() == 0 
		&& 		etc.getServer().getBlockIdAt(face.getX(), face.getY() + 1, face.getZ()) == 0
		) 
		{
			// otherwise teleport to face of target block
			sendCastMessage(player, RPGTextId.castBlink, RPGTextId.viewBlink);
			player.teleportTo
			(
				new Location
				(
					face.getX() + 0.5,
					face.getY(),
					face.getZ() + 0.5,
					player.getRotation(),
					player.getPitch()
				)
			);
			return true;
		} 
		else 
		{
			// no place to stand
			rpg.sendMessage(player, RPG.INFO_COLOR, rpg.getText(RPGTextId.blinkCant));
			return false;
		}
	}
	
	public double getDistance(Player player, Block target) 
	{
		return Math.sqrt(Math.pow(player.getX()-target.getX(),2) + Math.pow(player.getY()-target.getY(),2) + Math.pow(player.getZ()-target.getZ(),2));
	}
}
