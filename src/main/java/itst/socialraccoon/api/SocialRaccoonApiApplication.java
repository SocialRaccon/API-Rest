package itst.socialraccoon.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SocialRaccoonApiApplication {
    public static void main(String[] args) {
        /*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String[] passwords = {"12tT3456", "12fF3456", "123Yy456"};
        String[] emails = {"juan@teziutlan.tecnm.mx", "maria@teziutlan.tecnm.mx", "pedro@teziutlan.tecnm.mx"};

        for (int i = 0; i < passwords.length; i++) {
            String encodedPassword = passwordEncoder.encode(passwords[i]);
            System.out.println("INSERT INTO authentication (email, password) VALUES ('" + emails[i] + "', '" + encodedPassword + "');");
        }*/
        SpringApplication.run(SocialRaccoonApiApplication.class, args);
    }
}
