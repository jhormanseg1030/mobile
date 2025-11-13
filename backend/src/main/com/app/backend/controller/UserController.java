package com.app.backend.controller;

import com.app.backend.models.User;
import com.app.backend.service.UserService;
import com.app.backend.Dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.Http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*")
public class UserController{
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<List<User>>
    getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/id")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(userService.create(userCreateRequest));
    }

    @PutMapping("/id")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.ok(userService.update(id, userUpdateRequest));
    }

    @DeleteMapping(value ="/id", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id, @ResquestBody User user){
        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Usuario borrado con exito"));
    }

}