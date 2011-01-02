import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;


public abstract class RPGPersisted
{
	protected boolean dirty = false;
	
	protected abstract void onSave(PreparedStatement ps);
	protected abstract void onLoad(ResultSet rs);
	protected abstract String getId();
	
	private static final List<String> fields = new ArrayList<String>();

	private static void findFields(Class<RPGPersisted> objectClass)
	{
		for (Field field : objectClass.getFields())
		{
			RPGPersist persist = field.getAnnotation(RPGPersist.class);
			if (persist != null)
			{
				RPG.getRPG().log(Level.INFO, "Persisting field " + field.getName() + " of class " + objectClass.getName());
				fields.add(field.getName());
			}
		}
	}
	
	protected static String listFields()
	{
		String sql = "";
		boolean first = true;
		for (String field : fields)
		{
			if (!first) sql += ", ";
			first = false;
			sql += field;
		}
		return sql;
	}
	
	public static void load(Connection conn, String tableName, HashMap<String, RPGPersisted> objects, Class<RPGPersisted> objectClass)
	{
		if (fields.size() <= 0)
		{
			findFields(objectClass);
			if (fields.size() <= 0) 
			{
				RPG.getRPG().log(Level.WARNING, "No fields defined for persisted class: " + objectClass.getName());
				return;
			}
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try 
        {
			String sqlSelect = "SELECT ";
			sqlSelect += listFields();
			sqlSelect += " FROM " + tableName;
			ps = conn.prepareStatement(sqlSelect);
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                RPGPersisted newObject = objectClass.newInstance();
                for (String field : fields)
                {
                	objectClass.getField(field).set(newObject, rs.getObject(field));
                }
                newObject.onLoad(rs);
                newObject.dirty = false;
                objects.put(newObject.getId(), newObject);
            }     
		}
		catch (Exception ex) 
        {
			RPG.getRPG().log(Level.SEVERE, "Error loading players", ex);
        } 
        finally 
        {
            try 
            {
                if (ps != null) 
                {
                    ps.close();
                }
                if (rs != null) 
                {
                    rs.close();
                }
            } 
            catch (SQLException ex) 
            {
            }
        }
	}
	
	public void save(Connection conn, String tableName, HashMap<String, RPGPersisted> objects)
	{
		if (fields.size() <= 0) return;
		
		for(RPGPersisted object : objects.values())
		{
			if (object.dirty)
			{
				PreparedStatement ps = null;
				ResultSet rs = null;		
				try 
		        {
					String fieldList = "";
					String valueList = "";
					String updateList = "";
					
					boolean first = true;
					for (String field : fields)
					{
						if (!first) 
						{
							fieldList += ", ";
							valueList += ", ";
							updateList += ", ";
						}
						first = false;
						fieldList += field;
						valueList += "?";
						updateList += field + " = ?";
					}
					
					String sqlUpdate = 
						"INSERT INTO " 
					+ 	tableName 
					+ 	" ("
					+	fieldList
					+	") VALUES ("
					+	valueList
					+	") ON DUPLICATE KEY UPDATE "
					+	updateList;
					
					ps = conn.prepareStatement(sqlUpdate);
					int index = 0;
					for (String field : fields)
	                {
						Object value = object.getClass().getField(field).get(field);
						ps.setObject(index, value);
						ps.setObject(index + fields.size(), value);
	                }
					object.onSave(ps);
					ps.executeUpdate();
					object.dirty = false;
		        }
				catch (Exception ex) 
		        {
					RPG.getRPG().log(Level.SEVERE, "Error updating or inserting player", ex);
		        } 
		        finally 
		        {
		            try 
		            {
		                if (ps != null) 
		                {
		                    ps.close();
		                }
		                if (rs != null) 
		                {
		                    rs.close();
		                }
		            } 
		            catch (SQLException ex) 
		            {
		            }
		        }
			}
		}
	}
	
	
	public boolean isDirty()
	{
		return dirty;
	}
}
