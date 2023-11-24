package util;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {

    public static String hashSenha(String senhaPura){
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }
}
