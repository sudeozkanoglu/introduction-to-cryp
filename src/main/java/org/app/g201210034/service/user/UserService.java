package org.app.g201210034.service.user;

import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.entity.user.User;
import org.app.g201210034.model.entity.user_type.UserType;
import org.app.g201210034.model.request.user.UserRequest;
import org.app.g201210034.repository.user.UserRepository;
import org.app.g201210034.repository.user_type.UserTypeRepository;
import org.app.g201210034.results.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    @Override
    public DataResult<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(!users.isEmpty()){
            return new SuccessDataResult<>(users, Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Users listed successfully"));
        }
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Users not found"));
    }

    @Override
    public DataResult<User> getUserById(Long id) {
        if(id == null)
            return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "User id cannot be empty"));
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return new SuccessDataResult<>(user.get(), Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "User found successfully"));
        }
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "User not found"));
    }

    @Override
    public Result saveUser(UserRequest userRequest) {
        if(userRequest.getPassword().isEmpty() && userRequest.getUsername().isEmpty())
        {
           return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Username and password cannot be empty");
        }
        Optional<UserType> userType = userTypeRepository.findUserTypeByUserType(userRequest.getUserType());
        User userSaved = new User();
        userSaved.setFirstName(userRequest.getFirstName());
        userSaved.setLastName(userRequest.getLastName());
        userSaved.setIdentityNumber(userRequest.getIdentityNumber());
        userSaved.setPhoneNumber(userRequest.getPhoneNumber());
        userSaved.setEmail(userRequest.getEmail());
        userSaved.setUserType(userType.get());
        userSaved.setUsername(userRequest.getUsername());
        userSaved.setPassword(userRequest.getPassword());
        userRepository.save(userSaved);
        return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "User saved successfully");
    }

    @Override
    public Result updateUser(Long userId, UserRequest userRequest) {
        if(userId == null)
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "User id cannot be empty");
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Optional<UserType> userType = userTypeRepository.findUserTypeByUserType(userRequest.getUserType());
            User userUpdated = user.get();
            userUpdated.setFirstName(userRequest.getFirstName());
            userUpdated.setUsername(userRequest.getUsername());
            userUpdated.setPassword(userRequest.getPassword());
            userUpdated.setLastName(userRequest.getLastName());
            userUpdated.setIdentityNumber(userRequest.getIdentityNumber());
            userUpdated.setPhoneNumber(userRequest.getPhoneNumber());
            userUpdated.setEmail(userRequest.getEmail());
            userUpdated.setUserType(userType.get());
            userRepository.save(userUpdated);
            return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "User updated successfully");
        }
        return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "User not found");
    }

    @Override
    public Result deleteUser(Long userId) {
        if(userId == null)
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "User id cannot be empty");
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            userRepository.deleteById(userId);
            return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "User deleted successfully");
        }
        return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "User not found");
    }
}
