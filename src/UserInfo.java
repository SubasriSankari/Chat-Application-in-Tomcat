public class UserInfo {  
	
	private static String firstname;
	private static String password;
	private static String salt;
	private static String email;
	private static String lastname;  
	
	public String getFirstName() {  
		return firstname;  
	}  
	public void setFirstName(String firstName) {  
		firstname = firstName;  
	}
	
	public String getLastName() {  
		return lastname;  
	}  
	public void setLastName(String lastName) {  
		lastname = lastName;  
	} 
	
	public String getPassword() {  
		return password;  
	}  
	public void setPassword(String passWord) {  
		password = passWord;  
	} 
	
	public String getSalt() {  
		return salt;  
	}  
	public void setSalt(String Salt) {  
		salt = Salt;  
	} 
	
	public String getEmail() {  
		return email;  
	}  
	public void setEmail(String Email) {  
		email = Email;  
	}   
  
}  