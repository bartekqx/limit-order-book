package com.bartek.sulima.soft.infrastructure.jpa.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class TokenEntity {

    @Id
    private String userId;

    @Column(length = 1024)
    private String token;

    private LocalDateTime issueTime;

    private LocalDateTime expirationTime;
}
