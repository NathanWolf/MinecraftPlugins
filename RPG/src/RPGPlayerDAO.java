import java.util.Date;

public class RPGPlayerDAO extends RPGPersisted
{
	@RPGPersist(id = true)
	public String name;
	
	@RPGPersist
	public Date firstLogin;

	@RPGPersist 
	public Date lastLogin;

	@RPGPersist
	public Date lastDisconnect;

	@RPGPersist
	public boolean online;
}