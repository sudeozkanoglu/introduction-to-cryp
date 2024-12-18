package org.app.g201210034.controller.authentication;

import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.request.auth.LoginRequest;
import org.app.g201210034.model.request.user.UserRequest;
import org.app.g201210034.model.response.AuthResponse;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.app.g201210034.service.authentication.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/save")
    public Result save(@RequestBody UserRequest userRequest) {
        return authenticationService.save(userRequest);
    }
    @PostMapping("/auth")
    public DataResult<AuthResponse> auth(@RequestBody LoginRequest loginRequest) {
        return authenticationService.auth(loginRequest);
    }
}
