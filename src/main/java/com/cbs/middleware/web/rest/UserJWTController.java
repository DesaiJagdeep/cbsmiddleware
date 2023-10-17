package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.security.jwt.JWTFilter;
import com.cbs.middleware.security.jwt.TokenProvider;
import com.cbs.middleware.web.rest.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    @Autowired
    UserRepository repository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        String username = authentication.getName();
        String authority = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        Optional<User> findOneByLogin = repository.findOneByLogin(authentication.getName());

        String fullName = findOneByLogin.get().getFirstName() + " " + findOneByLogin.get().getLastName();

        boolean passwordChange = findOneByLogin.get().isPasswordChanged();

        return new ResponseEntity<>(new JWTToken(jwt, authority, username, fullName, passwordChange), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String userName;
        private String idToken;
        private String authority;
        private String fullName;
        private boolean passwordChanged;

        JWTToken(String idToken, String authority, String userName, String fullName, boolean passwordChanged) {
            this.idToken = idToken;
            this.authority = authority;
            this.userName = userName;
            this.fullName = fullName;
            this.passwordChanged = passwordChanged;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public boolean isPasswordChanged() {
            return passwordChanged;
        }

        public void setPasswordChanged(boolean passwordChanged) {
            this.passwordChanged = passwordChanged;
        }
    }
}
