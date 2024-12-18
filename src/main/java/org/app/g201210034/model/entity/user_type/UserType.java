package org.app.g201210034.model.entity.user_type;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.g201210034.model.enums.UserSignUpTypes;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_types")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTypeId;

    @Column(name = "user_type_name")
    @Enumerated(EnumType.STRING)
    private UserSignUpTypes userType;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
