package me.erano.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    //put final for required argument in constructor
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        return userService.fetchAllUsers();
    }
    @PostMapping("/api/users")
    public String createUser(@RequestBody User user){
        userService.addUser(user);
        return "User added successfully";
    }
}
