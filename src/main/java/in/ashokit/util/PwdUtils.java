package in.ashokit.util;


import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils {

    public static String generateRandomPassword(){

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        String pwd = RandomStringUtils.random( 8, characters );
        return pwd;
}
}
