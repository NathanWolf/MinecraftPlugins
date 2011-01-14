
public class RPGLightSpell extends RPGSpell 
{

	@Override
	public boolean onCast(String[] parameters) 
	{
		Block face = getLastBlock();
		if (face == null || face.blockType != Block.Type.Air)
		{
			rpg.sendMessage(player, RPG.ERROR_COLOR, "Can't put a torch there");
			return false;
		}
		
		rpg.sendMessage(player, RPG.INFO_COLOR, "Flame on!");
		HitBlox hit = new HitBlox(player);
		hit.setFaceBlock(50);
		
		return true;
	}

}
