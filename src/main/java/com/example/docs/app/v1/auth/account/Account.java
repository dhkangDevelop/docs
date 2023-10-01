package com.example.docs.app.v1.auth.account;

import com.example.docs.app.v1.auth.account.req.CreateAccountRequest;
import com.example.docs.global.enums.AuthEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "A_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long idx;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "nickname", length = 100)
    private String nickname;
    @Column(name = "auth_type")
    @Enumerated(EnumType.STRING)
    private AuthEnums authType;

    @Column(name = "code")
    private String code;

    @Column(name = "register_dt")
    private LocalDateTime registerDt;

    @Column(name = "last_access_dt")
    private LocalDateTime lastAccessDt;

    @Column(name = "token", length = 1000, columnDefinition = "TEXT")
    private String token;

    @Column(name = "is_use")
    private boolean isUse;

    static public Account createFirstGeneralUser(CreateAccountRequest createAccountRequest) {
        return Account.builder()
                .email(createAccountRequest.getEmail())
                .nickname(createAccountRequest.getEmail())
                .authType(AuthEnums.GENERAL)
                .code(null)
                .registerDt(LocalDateTime.now())
                .lastAccessDt(LocalDateTime.now())
                .token(null)
                .isUse(true)
                .build();
    }
}
