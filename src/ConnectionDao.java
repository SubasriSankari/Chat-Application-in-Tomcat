import java.util.*;  
import java.sql.*;  
import java.time.*;  
import java.security.*;  
import javafx.util.Pair;
  
public class ConnectionDao {  
  
    public static Connection getConnection(){  
        Connection con=null;  
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_application","root","Suba@2000");  
        }catch(Exception e){System.out.println(e);}  
        return con;  
    }  
	
	public static int addUser(UserInfo users){
		int status = 0;
		try{
			Connection conn = ConnectionDao.getConnection();			
			PreparedStatement ps=conn.prepareStatement(  
                        "insert into users(email, firstname, lastname, created_at, password, salt_password) values (?,?,?,?,?,?)");  
            ps.setString(1, users.getEmail()); 
            ps.setString(2, users.getFirstName());  
            ps.setString(3, users.getLastName());
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());			
            ps.setTimestamp(4,date);  
            ps.setString(5, users.getPassword());  
            ps.setString(6, users.getSalt());  
              
            status=ps.executeUpdate(); 

			PreparedStatement ps1 = conn.prepareStatement(
						"insert into in_online(email, firstname, chat_to) values (?,?,?)");
			ps1.setString(1, users.getEmail()); 
            ps1.setString(2, users.getFirstName());  
            ps1.setString(3, null);  
			status=ps1.executeUpdate();
			ps.close();
			ps1.close();
			conn.close();  
		
		}catch(Exception e){
			System.out.println("Error " + e);
		}
		return status;
	}
	
	public static ReturnUserResult authenticateUser(String email, String userpassword){
		
		String databasePassword = null;
		String databaseSalt = null;
		String nickname = null;
		
		try{
			Connection conn = ConnectionDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(  
                         "select password, salt_password, firstname from users where email=?");  
            ps.setString(1, email); 
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                databasePassword = rs.getString(1);  
                databaseSalt = rs.getString(2);  
				nickname = rs.getString(3);
            } 
			ps.close();
            conn.close();  
		
		}catch(Exception e){
			System.out.println("Error " + e);
		}
		
		if(databasePassword != null){
			try{
				if(databasePassword.equals(HashPassword.getHashedPassword(userpassword, databaseSalt))){
					return new ReturnUserResult(true, nickname);
				}
			}catch(NoSuchAlgorithmException e){
				System.out.println("Exception " + e);
			}catch(NoSuchProviderException e){
				System.out.println("Exception " + e);
			}
		}
		return new ReturnUserResult(false, nickname);
	}
	
	public static String getContactList(String currUser){  
		boolean isFound = false;
        String buffer = new String();
		buffer += "[";
        try{  
            Connection conn = ConnectionDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(  
                         "select firstname from users where email != ?");  
            ps.setString(1, currUser); 
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
				if(isFound){
					buffer += ",";
				}
				isFound = true;
                buffer += JsonConversion.buildJSONStringArray(rs.getString(1));  
            }
			ps.close();
            conn.close();  
        }catch(Exception ex){ex.printStackTrace();}  
        buffer += "]";
        return JsonConversion.buildJSONRecord( isFound, buffer.toString()); 
    }
	
	public static boolean checkIsToUserChatWithFromUser(String currUser, String chatTo){  
		boolean isFound = false;
        try{  
            Connection conn = ConnectionDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(  
                         "select * from in_online where email = ? and chat_to=?");  
            ps.setString(1, currUser); 
            ps.setString(2, chatTo); 
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
				isFound = true;
            }   
			ps.close();
            conn.close();  
        }catch(Exception ex){ex.printStackTrace();}  
		
        return isFound; 
    }  
	
	public static void updateOnlineStatus(String email, String chatTo){
		try{  
            Connection conn = ConnectionDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(  
                         "update in_online set chat_to = ? where email = ?");  
            ps.setString(1, chatTo); 
            ps.setString(2, email); 
            int status=ps.executeUpdate();  
			ps.close();
            conn.close();  
        }catch(Exception ex){ex.printStackTrace();}  
	}  
	
	public static void resetOnlineStatus(String email){
		try{  
            Connection conn = ConnectionDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(  
                         "update in_online set chat_to = ? where email = ?");  
            ps.setString(1, null); 
            ps.setString(2, email); 
            int status=ps.executeUpdate(); 
			ps.close();
            conn.close();  
        }catch(Exception ex){ex.printStackTrace();}  
	}  
	
	public static void updateChatDatabase(String username, String chatTo, String chat, String time){
		try{  
            Connection conn = ConnectionDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(  
                         "insert into chat_info(from_user, chat_to, message, time_at, created_at) values(?,?,?,?,?)");  
            ps.setString(1, username); 
            ps.setString(2, chatTo); 
            ps.setString(3, chat); 
            ps.setString(4, time); 
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());			
            ps.setTimestamp(5,date); 
            int status=ps.executeUpdate(); 
			ps.close();
            conn.close();  
        }catch(Exception ex){ex.printStackTrace();}  
	}
}  