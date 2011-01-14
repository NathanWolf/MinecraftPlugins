public class RPGFireballSpell extends RPGSpell 
{
	
    public Vec3D getLocation(Player player, float f)
    {
    	float rotationYaw = player.getRotation();
    	float rotationPitch = player.getPitch();
    	float prevRotationYaw = player.getRotation();
    	float prevRotationPitch = player.getPitch();
        if(f == 1.0F)
        {
            float f1 = MathHelper.cos(-rotationYaw * 0.01745329F - 3.141593F);
            float f3 = MathHelper.sin(-rotationYaw * 0.01745329F - 3.141593F);
            float f5 = -MathHelper.cos(-rotationPitch * 0.01745329F);
            float f7 = MathHelper.sin(-rotationPitch * 0.01745329F);
            return Vec3D.createVector(f3 * f5, f7, f1 * f5);
        } else
        {
            float f2 = prevRotationPitch + (rotationPitch - prevRotationPitch) * f;
            float f4 = prevRotationYaw + (rotationYaw - prevRotationYaw) * f;
            float f6 = MathHelper.cos(-f4 * 0.01745329F - 3.141593F);
            float f8 = MathHelper.sin(-f4 * 0.01745329F - 3.141593F);
            float f9 = -MathHelper.cos(-f2 * 0.01745329F);
            float f10 = MathHelper.sin(-f2 * 0.01745329F);
            return Vec3D.createVector(f8 * f9, f10, f6 * f9);
        }
    }	
	
	public boolean onCast(String[] parameters) 
	{
		HitBlox hit = new HitBlox(player);
		Block target = hit.getTargetBlock();
		if (target == null) 
		{
			rpg.sendMessage(player, RPG.ERROR_COLOR, rpg.getText(RPGTextId.failFireball));
			return false;
		}
		
		double dx = target.getX() - player.getX();
		double height = 1;
        double dy = (target.getY() + (double)(height / 2.0F)) - (player.getY() + (double)(height / 2.0F));
        double dz = target.getZ() - player.getZ();
		
		sendCastMessage(player, RPGTextId.castFireball, RPGTextId.viewFireball);
		ff world = player.getEntity().l;
		ca fireball = new ca(world, player.getEntity(), dx, dy, dz);
		double d8 = 4D;
        Vec3D vec3d = getLocation(player, 1.0F);
        fireball.p = player.getX() + vec3d.xCoord * d8;
        fireball.q = player.getY() + (double)(height / 2.0F) + 0.5D;
        fireball.r = player.getZ() + vec3d.zCoord * d8;
        
		world.a(fireball);
		return true;
	}
}
