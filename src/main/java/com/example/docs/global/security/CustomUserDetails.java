package com.example.docs.global.security;

import com.example.docs.app.v1.auth.account.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 174726374856727L;

    private Long id; // DB에서 PK 값
    private String email;	// 이메일
    private String token; // 비밀번호
    private boolean nonLocked = true; // 계정 잠김 여부
    private String nickname; // 닉네임
    private Collection<GrantedAuthority> authorities = new ArrayList<>(); //권한 목록

    public CustomUserDetails(Account account) {
        this.id = account.getIdx();
        this.email = account.getEmail();
        this.token = account.getToken();
        this.nonLocked = true;
        this.nickname = account.getNickname();
        this.authorities.add(new SimpleGrantedAuthority(account.getAuthType().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.token;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.nonLocked;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
