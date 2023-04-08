package com.capa.boostify.authentication.service;

import com.capa.boostify.authentication.exception.InvalidRegisterDataException;
import com.capa.boostify.authentication.exception.UserAlreadyExistsException;
import com.capa.boostify.authentication.exception.UserDoesNotExistOrPasswordIsInvalidException;
import com.capa.boostify.authentication.utils.LoginRequest;
import com.capa.boostify.authentication.utils.LoginResponse;
import com.capa.boostify.authentication.utils.RegisterRequest;
import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.repository.UserRepository;
import com.capa.boostify.user.utils.Role;
import com.capa.boostify.user.utils.dto.UserDto;
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

    public UserDto register(RegisterRequest registerRequest) {

        if (!registerRequest.isValid()) throw new InvalidRegisterDataException();
        if (checkIfUserExist(registerRequest.getEmail())) throw new UserAlreadyExistsException();

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        User save = userRepository.save(user);

        return new UserDto(save.getId(), save.getEmail(), save.getRole());

    }

    public LoginResponse login(LoginRequest loginRequest) {

        if (!loginRequest.isValid()) throw new UserDoesNotExistOrPasswordIsInvalidException();

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(UserDoesNotExistOrPasswordIsInvalidException::new);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
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
