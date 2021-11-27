package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.LoginUser;
import isa.FishingBookingApp.dto.UserFromRequestDTO;
import isa.FishingBookingApp.dto.UserTokenState;
import isa.FishingBookingApp.exception.ResourceConflictException;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.model.UserRole;
import isa.FishingBookingApp.service.UserRoleService;
import isa.FishingBookingApp.service.UserService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private TokenUtils tokenUtils;
    private AuthenticationManager authenificationManager;
    private UserService userService;

    @Autowired
    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenificationManager, UserService userService) {
        this.tokenUtils = tokenUtils;
        this.authenificationManager = authenificationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createToken(@RequestBody LoginUser loginUser, HttpServletResponse response) {

        Authentication authentication = authenificationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getMailAddress(), loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody UserFromRequestDTO userFromRequestDTO, UriComponentsBuilder ucBuilder) {

        User existUser = this.userService.findByMailAddress(userFromRequestDTO.getMailAddress());
        if (existUser != null) {
            throw new ResourceConflictException(userFromRequestDTO.getMailAddress(), "Email address already exists");
        }

        if (!userFromRequestDTO.getPassword1().equals(userFromRequestDTO.getPassword2())) {
            throw new ResourceConflictException("", "Passwords not match");
        }

        User newUser = null;
        try {
            newUser = userService.saveNewUser(userFromRequestDTO);
        } catch (Exception e) {
            throw new ResourceConflictException("", e.getMessage());
        }

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
