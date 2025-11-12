package com.app.backend.Service;

import com.app.backend.Dto.UserCreateRequest;
import com.app.backend.Dto.UserUpdateRequest;
import com.app.backend.models.User;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User create(UserCreateRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setActive(request.getActive() != null ? request.getActive() : true);
        return userRepository.save(user);
    }

    public User update(UserUpdateRequest request){
        User user = findById(id);

        if(id == 1L && isCoordinador()){
            throw new RuntimeException("No tienes permisos para modificar, el administrador principal");

        }
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setActive(request.getActive());

        if(request.getPassword() != null && !request.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.emcode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    private boolean isCoordinador(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getAuthentication() != null){
            return authentication.getAuthorities().steam()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_COORDINADOR"));
        }
        return false;
    }
    public void delete(Long id){
        User user = findById(id);

        if(id == null){
            throw new RuntimeException("No se puede eliminar administrador principal");
        }

        if(user == null){
            throw new RuntimeException("Usuario no encontrado")
        }
        userRepository.delete(user);
    }
}