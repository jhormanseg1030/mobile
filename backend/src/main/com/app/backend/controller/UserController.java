package com.app.backend.controller;

import com.app.backend.models.User;
import com.app.backend.Service.UserService;
import com.app.backend.Dto.MessageResponse;
import com.app.backend.Dto.UserCreateRequest;
import com.app.backend.Dto.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINADOR')")
    public ResponseEntity<List<User>>
    getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINADOR')")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINADOR')")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(userService.create(userCreateRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        try{
            return ResponseEntity.ok(userService.update(id, userUpdateRequest));
        }catch(RuntimeException e){
            if(e.getMessage().contains("No tiene permisos")){
                return ResponseEntity.status(403).body(new MessageResponse(e.getMessage()));
            }
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id){
        try{
            userService.delete(id);
            return ResponseEntity.ok(new MessageResponse("Usuario borrado con exito"));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}