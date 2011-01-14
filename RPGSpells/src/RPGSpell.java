
public abstract class RPGSpell 
{
	protected RPG rpg;
	protected Player player;
	private static String MAGIC_CAST_COLOR = RPG.INFO_COLOR;
	private static String MAGIC_VIEW_COLOR = RPG.DISABLED_COLOR;
	
	public RPGSpell()
	{
		this.rpg = RPGPlugin.getRPG();
	}
	
	public Block getTargetBlock()
	{
		HitBlox hit = new HitBlox(player);
		return hit.getTargetBlock();
	}
	
	public Block getLastBlock()
	{
		HitBlox hit = new HitBlox(player);
		return hit.getLastBlock();
	}
	
	public float getPlayerRotation()
	{
		float playerRot = player.getRotation();
		while (playerRot < 0) playerRot += 360;
		while (playerRot > 360) playerRot -= 360;
		return playerRot;
	}
	
	public Block getPlayerBlock()
	{
		Block playerBlock = null;
		int x = (int)Math.round(player.getX() - 0.5);
		int y = (int)Math.round(player.getY() - 0.5);
		int z = (int)Math.round(player.getZ() - 0.5);
		int dy = 0;
		Server s = etc.getServer();
		{
			while (dy > -3 && (playerBlock == null || playerBlock.blockType == Block.Type.Air))
			{
				playerBlock = s.getBlockAt(x, y + dy, z);
				dy--;
			}			
		}
		return playerBlock;
	}
	
	public abstract boolean onCast(String[] parameters);
	
	public boolean cast(Player player, String[] parameters)
	{
		this.player = player;
		return onCast(parameters);
	}
	
	public void sendCastMessage(Player player, RPGTextId cast, RPGTextId view)
	{
		rpg.sendMessage(player, MAGIC_CAST_COLOR, cast);
		rpg.sendMessageToPlayersInRange(player, MAGIC_VIEW_COLOR, view);
	}
}
