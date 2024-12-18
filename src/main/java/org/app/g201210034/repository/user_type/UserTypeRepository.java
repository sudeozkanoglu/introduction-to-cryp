package org.app.g201210034.repository.user_type;

import org.app.g201210034.model.entity.user_type.UserType;
import org.app.g201210034.model.enums.UserSignUpTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    Optional<UserType> findUserTypeByUserType(UserSignUpTypes userSignUpTypes);

}
