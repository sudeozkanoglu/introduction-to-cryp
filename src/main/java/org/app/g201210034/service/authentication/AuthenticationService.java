package org.app.g201210034.service.authentication;

import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.entity.user.User;
import org.app.g201210034.model.entity.user_type.UserType;
import org.app.g201210034.model.request.auth.LoginRequest;
import org.app.g201210034.model.request.user.UserRequest;
import org.app.g201210034.model.response.AuthResponse;
import org.app.g201210034.repository.user.UserRepository;
import org.app.g201210034.repository.user_type.UserTypeRepository;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.app.g201210034.results.ResultMessageType;
import org.app.g201210034.results.SuccessDataResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public Result save(UserRequest userRequest) {
        Optional<UserType> foundType = userTypeRepository.findUserTypeByUserType(userRequest.getUserType());
        User foundUserByName = userRepository.findByUsername(userRequest.getUsername()).orElse(null);
        if(foundUserByName != null)
        {
            return Result.showMessage(Result.SERVER_ERROR, ResultMessageType.ERROR, "User already exists");
        }
        if(foundType.isPresent())
        {
            User user = User.builder()
                    .email(userRequest.getEmail())
                    .username(userRequest.getUsername())
                    .firstName(userRequest.getFirstName())
                    .userType(foundType.get())
                    .lastName(userRequest.getLastName())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .build();
            userRepository.save(user);
            return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "User saved successfully");
        }
        return Result.showMessage(Result.SERVER_ERROR, ResultMessageType.ERROR, "User could not be saved");
    }

    public DataResult<AuthResponse> auth(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .userSignUpTypes(user.getUserType().getUserType())
                .userId(user.getUserId())
                .build();

        return new SuccessDataResult<>(response, Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "User authenticated successfully"));
    }
}
