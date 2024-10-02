package com.mfm.gansys.controllers;


import com.mfm.gansys.dto.response.HomeResponse;
import com.mfm.gansys.dto.response.JwtResponse;
import com.mfm.gansys.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/home")
public class HomeController {


    @GetMapping("/")
    public ResponseEntity<?> getHomePage(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();


        return ResponseEntity.ok(
                new HomeResponse(userDetails.getFirstName(), userDetails.getLastName(),roles)
        );
    }

}
