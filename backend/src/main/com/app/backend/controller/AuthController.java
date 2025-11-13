package com.app.backend.controller;

import com.app.backend.Dto.loginRequest;
import com.app.backend.Dto.loginResponse;
import com.app.backend.models.User;
import com.app.backend.repository.UserRepository;
import com.app.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.security.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", )
public class AuthController{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login", consume = "application/json", produces = "appilcation/json")
    public ResponseEntity<?> login(@ResquestBody LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()));

                    SecurityContextHolder.getContext().setAuthentiation(authentication);

                    String jwt = tokenProvider.generateToken(authentication);

                    User user = userRepository.finByUsername(loginRequest.getUsername())
                        .orElseThow(() -> new RuntimeExpection("Usuario no encontrado"))

                    return ResponseEntity.ok(new loginResponse(jwt, user));
        }catch(Exception e){
            return ResponseEntity.badRequest().body("{\"error\":\"Credenciales invalidas}");
        }   
    }
}