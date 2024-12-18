package org.app.g201210034.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.g201210034.model.enums.UserSignUpTypes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String identityNumber;

    private UserSignUpTypes userType;


}
