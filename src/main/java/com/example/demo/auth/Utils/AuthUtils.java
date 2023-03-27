package com.example.demo.auth.Utils;
import com.example.demo.auth.entities.User;
import com.example.demo.auth.exceptions.Authentication.InvalidAccessToken;
import jakarta.servlet.http.HttpServletRequest;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthUtils {
    public static String getToken(HttpServletRequest request) throws InvalidAccessToken {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            return token;
        }
        throw new InvalidAccessToken();
    }
    public static User getUserFromRequest(HttpServletRequest request){
        User user= (User) request.getAttribute("user");
        return user;
    }
}
