package com.mfm.gansys.controllers;


import com.mfm.gansys.dto.LoginRequest;
import com.mfm.gansys.dto.SignupRequest;
import com.mfm.gansys.dto.response.JwtResponse;
import com.mfm.gansys.dto.response.SigninResponse;
import com.mfm.gansys.dto.response.SignupResponse;
import com.mfm.gansys.model.Users;
import com.mfm.gansys.repositories.RoleRepository;
import com.mfm.gansys.repositories.UserRepository;
import com.mfm.gansys.security.Jwt.JwtUtils;
import com.mfm.gansys.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthControllers {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AuthControllers(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(
                new SigninResponse(200L, "Login berhasil",
                        new JwtResponse(userDetails.getFirstName(),
                                userDetails.getLastName(), jwt, roles)));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){

        // user dahulu
        if (userRepository.existsByEmail(signupRequest.email())){

            return ResponseEntity.badRequest().body(
                    new SignupResponse("Email is alreadey exist"));
        }

        // Check role
        if (!roleRepository.existsById(signupRequest.roleId())){
            return ResponseEntity.badRequest().body(
                    new SignupResponse("Role is not found"));
        }

        // create user
        Users users =  new Users();
        users
                .setFirst_name(signupRequest.firstName())
                .setLast_name(signupRequest.lastName())
                .setEmail(signupRequest.email())
                .setPassword(encoder.encode(signupRequest.password()))
                .setIsEnable(1L) // default nya 1
                .setRole(roleRepository.getReferenceById(1L)); // default nya 1

        // save user
        userRepository.save(users);

        return ResponseEntity.ok(
                new SignupResponse("User registered is successfully"));
    }
}
