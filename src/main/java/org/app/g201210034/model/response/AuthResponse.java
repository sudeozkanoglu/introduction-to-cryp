package org.app.g201210034.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.g201210034.model.enums.UserSignUpTypes;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserSignUpTypes userSignUpTypes;
    private Long userId;
}
