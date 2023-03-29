package com.example.demo.auth.services;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.auth.entities.*;
import com.example.demo.auth.exceptions.*;
import com.example.demo.auth.requests.RegisterRequest;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserTokensRepo userTokensRepo;
    @Value( "${accessTokenSecret}" )
    String accessTokenSecret;
    @Value( "${refreshTokenSecret}" )
    String refreshTokenSecret;
    public User registerUser(RegisterRequest registerRequest) throws EmailAlreadyExists {
        if(registerRequest==null){
            throw new IllegalArgumentException();
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUserName(registerRequest.getUserName());
        Argon2 argon2 = Argon2Factory.create();
        String hash = argon2.hash(10, 65536, 1, registerRequest.getPassword().toCharArray());
        user.setHash(hash);
        return userService.createUser(user);
    }
    public Map<String, String> login(String email, String password) throws Exception {
        if(email==null || password==null){
            throw new IllegalArgumentException();
        }
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new EmailNotFound();
        }
        Argon2 argon2 = Argon2Factory.create();
        if (!argon2.verify(user.getHash(), password.toCharArray())) {
            throw new InvalidPassword();
        }
        String accessToken =  generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        UserTokens userTokens = userTokensRepo.findUserTokensByUser(user);
        userTokens.setAccessToken(accessToken);
        userTokens.setRefreshToken(refreshToken);
        userTokensRepo.save(userTokens);
        Map<String, String> accessAndRefreshObject = new HashMap<>();
        accessAndRefreshObject.put("accessToken", accessToken);
        accessAndRefreshObject.put("refreshToken", refreshToken);
        return accessAndRefreshObject;
    }

    public Map<String, String> refresh(String refreshToken) throws InvalidRefreshToken, Forbidden {
        if(refreshToken==null){
            throw new IllegalArgumentException();
        }
        try {
            return getNewAccessToken(refreshToken);
        } catch (Forbidden e) {
            throw new Forbidden();
        } catch (Exception e) {
            throw new InvalidRefreshToken();
        }
    }

    private Map<String, String> getNewAccessToken(String refreshToken) throws Forbidden {
        DecodedJWT decodedJWT = validRefreshToken(refreshToken);
        User user = userService.findUserById(decodedJWT.getClaim("id").asLong());
        UserTokens userTokens = userTokensRepo.findUserTokensByUser(user);
        try {
            if (!Objects.equals(refreshToken, userTokens.getRefreshToken())) {
                throw new Forbidden();
            }
            validAccessToken(userTokens.getAccessToken());
            throw new Forbidden();
        } catch (Forbidden e) {
            deleteTokens(user);
            throw new Forbidden();
        } catch (Exception e) {
            //ignore
        }
        String accessToken = generateAccessToken(user);
        userTokens.setAccessToken(accessToken);
        userTokensRepo.save(userTokens);
        Map<String, String> accessTokenObject = new HashMap<>();
        accessTokenObject.put("accessToken", accessToken);
        return accessTokenObject;
    }

    private String generateAccessToken(User user) {
        if(user==null){
            throw new IllegalArgumentException();
        }
        return generateJwtToken(user, (long) 1000 *60* 15, accessTokenSecret);
    }
    private String generateRefreshToken(User user) {
        if(user==null){
            throw new IllegalArgumentException();
        }
        return generateJwtToken(user, (long) 1000 * 60 *60*60, refreshTokenSecret);
    }
    private String generateJwtToken(User user, Long timeInMilliSecond, String secret) {
        if(user==null || timeInMilliSecond==null || secret==null){
            throw new IllegalArgumentException();
        }
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String token = JWT.create()
                .withClaim("email", user.getEmail())
                .withClaim("id", user.getId())
                .withClaim("userName", user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + timeInMilliSecond))
                .sign(algorithm);
        return token;
    }
    public DecodedJWT validAccessToken(String token){
        return UserFromAccessToken(token, accessTokenSecret);
    }
    public DecodedJWT validRefreshToken(String token){
        return UserFromAccessToken(token, refreshTokenSecret);
    }
    public DecodedJWT UserFromAccessToken(String token, String secret) {
        if(secret==null || token==null){
            throw new IllegalArgumentException();
        }
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }
    public User UserFromAccessToken(String token) throws InvalidAccessToken {
        if(token==null){
            throw new IllegalArgumentException();
        }
        try{
            DecodedJWT decodedJWT =   UserFromAccessToken(token,accessTokenSecret);
            Long id = decodedJWT.getClaim("id").asLong();
            User user = userService.findUserById(id);
            UserTokens userTokens = userTokensRepo.findUserTokensByUser(user);
            if (!Objects.equals(token, userTokens.getAccessToken())) {
                throw new InvalidAccessToken();
            }
            return user;
        }catch (Exception e){
            throw new InvalidAccessToken();
        }
    }
    public void logout(User user) throws FailedToLogout {
        if(user==null){
            throw new IllegalArgumentException();
        }
        try {
            deleteTokens(user);
        } catch (Exception e) {
            throw new FailedToLogout();
        }
    }

    private void deleteTokens(User user) {
        if(user==null){
            throw new IllegalArgumentException();
        }
        UserTokens userTokens = userTokensRepo.findUserTokensByUser(user);
        userTokens.setRefreshToken("");
        userTokens.setAccessToken("");
        userTokensRepo.save(userTokens);
    }
}
