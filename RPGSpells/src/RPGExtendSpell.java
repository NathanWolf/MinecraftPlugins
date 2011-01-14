
public class RPGExtendSpell extends RPGSpell 
{
	int MAX_SEARCH_DISTANCE = 16;
	
	@Override
	public boolean onCast(String[] parameters) 
	{
		Block playerBlock = getPlayerBlock();
		if (playerBlock == null) 
		{
			// no spot found to ascend
			rpg.sendMessage(player, RPG.ERROR_COLOR, RPGTextId.failDescend);
			return false;
		}		

		float playerRot = getPlayerRotation();
		Block.Face direction = Block.Face.Back;
		if (playerRot <= 45 || playerRot > 315)
		{
			direction = Block.Face.Left;
		}
		else if (playerRot > 45 && playerRot <= 135)
		{
			direction = Block.Face.Back;
		}
		else if (playerRot > 135 && playerRot <= 225)
		{
			direction = Block.Face.Right;
		}
		else if (playerRot > 225 && playerRot <= 315)
		{
			direction = Block.Face.Front;
		}
		Block attachBlock = playerBlock;
		Block targetBlock = attachBlock.getFace(direction);
		int distance = 0;
		while (targetBlock.blockType != Block.Type.Air && distance <= MAX_SEARCH_DISTANCE)
		{
			distance++;
			attachBlock = targetBlock;
			targetBlock = attachBlock.getFace(direction);
		}
		if (targetBlock.blockType != Block.Type.Air)
		{
			rpg.sendMessage(player, RPG.ERROR_COLOR, RPGTextId.failDescend);
			return false;
		}
		targetBlock.setType(attachBlock.getType());
		targetBlock.update();
		sendCastMessage(player, RPGTextId.castDescend, RPGTextId.viewDescend);		
		rpg.sendMessage(player, RPG.INFO_COLOR, "Facing " + playerRot + " : " + direction.name() + ", " + distance + " spaces to " + attachBlock.blockType.name());
		
		return true;
	}

}
