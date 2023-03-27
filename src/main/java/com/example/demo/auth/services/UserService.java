package com.example.demo.auth.services;
import com.example.demo.auth.entities.*;
import com.example.demo.auth.exceptions.Authentication.EmailAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserTokensRepo userTokensRepo;
    public User createUser(User user) throws EmailAlreadyExists {
        User userWithEmail=findUserByEmail(user.getEmail());
        if(userWithEmail!=null){
            throw new EmailAlreadyExists();
        }
        User registeredUser=userRepo.save(user);
        UserTokens userTokens=new UserTokens();
        userTokens.setUser(registeredUser);
        userTokensRepo.save(userTokens);
        return registeredUser;
    }
    public List<User> getUsers(){
        return userRepo.findAll();
    }
    public User findUserById(Long id){
        User user= userRepo.findUserById(id);
        return user;
    }
    public User findUserByEmail(String email){
        User user= userRepo.findUserByEmail(email);
        return user;
    }
}
