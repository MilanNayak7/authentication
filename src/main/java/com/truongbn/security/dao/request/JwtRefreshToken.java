package com.truongbn.security.dao.request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "jwt_refresh_token")
public class JwtRefreshToken {

    @Id
    @Column(name = "token_id",nullable = false)
    private String tokenId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "create_date",nullable = false)
    private LocalDateTime createDate;


    @Column(name = "expiration_date",nullable = false)
    private LocalDateTime expirationDate;
}
