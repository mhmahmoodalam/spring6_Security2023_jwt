package com.example.ss6.service;

import com.example.ss6.auth.model.AuthenticationRequestDto;
import com.example.ss6.auth.model.AuthenticationResponse;
import com.example.ss6.auth.model.RegisterRequestDTO;
import com.example.ss6.entity.SecurityUser;
import com.example.ss6.model.enums.AccountState;
import com.example.ss6.model.enums.CloudRole;
import com.example.ss6.model.enums.UserType;
import com.example.ss6.repository.SecurityUserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final SecurityUserRespository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequestDTO registerRequestDTO) {
        var user = SecurityUser.builder()
                .username(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .usertype(UserType.valueOf(registerRequestDTO.getUserType()))
                .accountState(AccountState.PENDING)
                .roles(CloudRole.assignRolesByUser(UserType.valueOf(registerRequestDTO.getUserType())))
                .build();
        var jwtToken = jwtService.generateToken(user);
        userRepository.save(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequestDto authenticationRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(),
                        authenticationRequestDto.getPassword()));
        var user = userRepository.findOneByUsername(authenticationRequestDto.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }



}
