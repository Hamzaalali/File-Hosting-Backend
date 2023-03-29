package com.example.demo.auth.services;
import com.example.demo.auth.entities.*;
import com.example.demo.auth.exceptions.EmailAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserTokensRepo userTokensRepo;
    public User createUser(User user) throws EmailAlreadyExists {
        if(user==null){
            throw new IllegalArgumentException();
        }
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
    public User findUserById(Long id){
        if(id==null){
            throw new IllegalArgumentException();
        }
        User user= userRepo.findUserById(id);
        return user;
    }
    public User findUserByEmail(String email){
        if(email==null){
            throw new IllegalArgumentException();
        }
        User user= userRepo.findUserByEmail(email);
        return user;
    }
}
