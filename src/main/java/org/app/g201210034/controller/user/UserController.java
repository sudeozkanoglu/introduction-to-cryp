package org.app.g201210034.controller.user;

import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.entity.user.User;
import org.app.g201210034.model.request.user.UserRequest;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.app.g201210034.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/user/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "getAllUsers")
    public DataResult<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "getUserById")
    public DataResult<User> getUserById(@RequestParam Long id){
        return userService.getUserById(id);
    }

    @PostMapping(path = "saveUser")
    public Result saveUser(@RequestBody UserRequest userRequest){ return userService.saveUser(userRequest);}

    @PostMapping(path = "updateUser")
    public Result updateUser(@RequestParam Long userId, @RequestBody UserRequest userRequest){ return userService.updateUser(userId, userRequest);}

    @PostMapping(path = "deleteUser")
    public Result deleteUser(@RequestParam Long userId){ return userService.deleteUser(userId);}





}
