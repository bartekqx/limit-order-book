package com.bartek.sulima.soft.infrastructure.jpa.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity  {

    @Id
    @GenericGenerator(name = "user_id_generator", strategy = "com.bartek.sulima.soft.infrastructure.jpa.user.UserIdGenerator")
    @GeneratedValue(generator = "user_id_generator")
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int phoneNumber;
}
