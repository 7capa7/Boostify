package com.capa.boostify.service;

import com.capa.boostify.exception.InvalidRegisterDataException;
import com.capa.boostify.exception.UserAlreadyExistsException;
import com.capa.boostify.exception.UserDoesNotExistOrPasswordIsInvalidException;
import com.capa.boostify.utils.LoginRequest;
import com.capa.boostify.utils.LoginResponse;
import com.capa.boostify.utils.RegisterRequest;
import com.capa.boostify.entity.User;
import com.capa.boostify.repository.UserRepository;
import com.capa.boostify.utils.Role;
import com.capa.boostify.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserDto register(RegisterRequest request) {

        if (!request.isValid()) throw new InvalidRegisterDataException();
        if (checkIfUserExist(request.getEmail())) throw new UserAlreadyExistsException();

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User save = userRepository.save(user);

        return new UserDto(save.getId(), save.getEmail(), save.getRole());

    }

    public LoginResponse login(LoginRequest request) {

        if (!request.isValid()) throw new UserDoesNotExistOrPasswordIsInvalidException();

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserDoesNotExistOrPasswordIsInvalidException::new);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new UserDoesNotExistOrPasswordIsInvalidException();
        }

        String jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(jwtToken)
                .build();
    }

    private boolean checkIfUserExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
