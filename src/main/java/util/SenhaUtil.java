package util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;

public class SenhaUtil {

    public static String hashSenha(String senhaPura){
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }
    public static String createToken(){
        SecureRandom random = new SecureRandom();
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder token = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        return token.toString();
    }
}


