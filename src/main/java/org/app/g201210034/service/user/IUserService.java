package org.app.g201210034.service.user;

import org.app.g201210034.model.entity.user.User;
import org.app.g201210034.model.request.user.UserRequest;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;

import java.util.List;

public interface IUserService {

    DataResult<List<User>> getAllUsers();

    DataResult<User> getUserById(Long id);

    //DataResult<User> getUserByIdentityNumber(String identityNumber);

    Result saveUser(UserRequest userRequest);

    Result updateUser(Long userId, UserRequest userRequest);

    Result deleteUser(Long userId);
}
