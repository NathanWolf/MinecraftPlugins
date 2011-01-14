
public class RPGTowerSpell extends RPGSpell 
{
	public boolean onCast(String[] parameters) 
	{
		Block target = getTargetBlock();
		if (target == null) 
		{
			rpg.sendMessage(player, RPG.INFO_COLOR, rpg.getText(RPGTextId.blinkNoTarget));
			return false;
		}
		int MAX_HEIGHT = 127;
		int height = 16;
		int maxHeight = 127;
		int material = 20;
		int midX = target.getX();
		int midY = target.getY();
		int midZ = target.getZ();
		
		Server s = etc.getServer();
		// Check for roof
		for (int i = height; i < maxHeight; i++)
		{
			int y = midY + i;
			if (y > MAX_HEIGHT)
			{
				maxHeight = MAX_HEIGHT - midZ;
				height = height > maxHeight ? maxHeight : height;
				break;
			}
			int blockId = s.getBlockIdAt(midX, y, midZ); 
			if (blockId != 0)
			{
				Item.Type itemType = Item.Type.values()[blockId];
				rpg.sendMessage(player, RPG.INFO_COLOR, "Found ceiling of " + itemType.toString());
				height = i;
				break;
			}
		}
		
		int blocksCreated = 0;
		for (int i = 0; i < height; i++)
		{
			midY++;
			for (int dx = -1; dx <= 1; dx++)
			{
				for (int dz = -1; dz <= 1; dz++)
				{
					int x = midX + dx;
					int y = midY;
					int z = midZ + dz;
					// Leave the middle empty
					if ((dx != 0 || dz != 0) && s.getBlockIdAt(x, y, z) == 0)
					{
						blocksCreated++;
						s.setBlockAt(material, x, y, z);
					}					
				}
			}
		}
		rpg.sendMessage(player, RPG.INFO_COLOR, "Made tower " + height + " high with " + blocksCreated + " blocks");
		return true;
	}
	
	
}
