package com.backend.reactspring.controller;

import com.backend.reactspring.exception.UserNotFoundException;
import com.backend.reactspring.model.User;
import com.backend.reactspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User newUser(@RequestBody User newUser){
        return userService.save(newUser);
    }

    @GetMapping("/all")
    public List<User> listAllUser(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteByID(@PathVariable("id") Long id){
        if(userService.findById(id).isEmpty()){
            throw new UserNotFoundException(id);
        }
        userService.delete(id);
        return "user with id "+id+" has been deleted";
    }

    @PutMapping("/update/{id}")
    public User updateUserById(@RequestBody User newUser, @PathVariable Long id){
        return userService.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userService.save(user);
                }).orElseThrow(()->new UserNotFoundException(id));
    }
}
