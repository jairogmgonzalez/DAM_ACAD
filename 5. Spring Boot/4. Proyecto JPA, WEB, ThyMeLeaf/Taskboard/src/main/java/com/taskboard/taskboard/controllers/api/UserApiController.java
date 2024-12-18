package com.taskboard.taskboard.controllers.api;

import com.taskboard.taskboard.entities.User;
import com.taskboard.taskboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PatchMapping("/update-name/{id}")
    public void updateUserName(@PathVariable("id") Long id, @RequestParam String newName) {
        userService.updateUserName(id, newName);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/by-name")
    public User getUserByName(@RequestParam("name") String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/count")
    public Long getCountUsers() {
        return userService.getCountUsers();
    }

    @GetMapping("/search")
    public List<User> getUsersByNameContains(@RequestParam("keyword") String keyword) {
        return userService.getUsersByNameContains(keyword);
    }

    @GetMapping("/filtered")
    public List<User> getUsersFiltered(@RequestParam("hasBoards") boolean hasBoards) {
        return hasBoards ? userService.getUsersWithBoards() : userService.getUsersWithoutBoards();
    }

    @GetMapping("/with-more-than/{count}")
    public List<String> getUsersNamesWithMoreThanXBoards(@PathVariable("count") long count) {
        return userService.getUsersNamesWithMoreThanXBoards(count);
    }

    @GetMapping("/by-email/{domain}")
    public List<User> getUsersByEmailDomain(@PathVariable("domain") String domain) {
        return userService.getUsersByEmailDomain(domain);
    }

    @GetMapping("/longest-name")
    public List<User> getUsersWithLongestName() {
        return userService.getUsersWithLongestName();
    }
}
