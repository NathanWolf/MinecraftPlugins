
public abstract class RPGLocationSpell extends RPGSpell
{
	public static Location findPlaceToStand(Player player, boolean goUp) 
	{
		int step;
		if (goUp) 
		{
			step = 1;
		} 
		else
		{
			step = -1;
		}
		
		// get player position
		int x = (int)Math.round(player.getX()-.5);
		int y = (int)Math.round(player.getY()+step+step);
		int z = (int)Math.round(player.getZ()-.5);
		
		Server s = etc.getServer();
				
		// search for a spot to stand
		while (2 < y && y < 125) 
		{
			if (s.getBlockIdAt(x,y,z) != 0 && s.getBlockIdAt(x,y+1,z) == 0 && s.getBlockIdAt(x,y+2,z) == 0) {
				// spot found - return location
				return new Location((double)x+.5,(double)y+1,(double)z+.5,player.getRotation(),player.getPitch());
			}
			y += step;
		}
		
		// no spot found
		return null;
	}
}
