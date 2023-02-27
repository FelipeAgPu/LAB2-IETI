package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto user) {
        User newUser = new User(user);
        URI createdUserUri = URI.create("http://localhost:8080/v1/users/" + newUser.getId());
        return ResponseEntity.created(createdUserUri).body(usersService.save(newUser));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = usersService.all();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        Optional<User> user = usersService.findById(id);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else {
            throw new UserNotFoundException(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UserDto user) {
        Optional<User> userResponse = usersService.findById(id);
        if (userResponse.isPresent()){
            usersService.save(userResponse.get());
            User userUpdated = usersService.update(user,id);
            return ResponseEntity.ok(userUpdated);
        }else {
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        Optional<User> userResponse = usersService.findById(id);
        if (userResponse.isPresent()){
            usersService.save(userResponse.get());
            usersService.deleteById(id);
            return ResponseEntity.ok().build();
        }else {
            throw new UserNotFoundException(id);
        }
    }
}
