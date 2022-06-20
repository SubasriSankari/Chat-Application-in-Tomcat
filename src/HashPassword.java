import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class HashPassword{
	
    public static String getHashedPassword(String password, String salt) throws NoSuchAlgorithmException, NoSuchProviderException {
		        
        String securePassword = generatePassword(password, salt);
		return securePassword;
    }
	
	public static String getHashedPassword(String password, UserInfo users) throws NoSuchAlgorithmException, NoSuchProviderException {
		
        String salt = getSalt();
        String securePassword = generatePassword(password, salt);
		
		users.setPassword(securePassword);
		users.setSalt(salt);
		
		return securePassword;
    }

    private static String generatePassword(String passwordToHash, String salt) {
		
        String generatedPassword = null;
        try {
			
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(salt.getBytes());
			
            byte[] bytes = messageDigest.digest(passwordToHash.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
			
            for (int byteChar = 0; byteChar < bytes.length; byteChar++) {
                stringBuilder.append(Integer.toString((bytes[byteChar] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();
			
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException{
       
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
        return salt.toString();
    }
}