package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.UserFromRequestDTO;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.service.UserService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    private UserService userService;
    private TokenUtils tokenUtils;

    @Autowired
    public UserController(UserService userService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping("getUser")
    @PreAuthorize("hasRole('USER')")
    public User getUser(HttpServletRequest request){
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        return userService.findByMailAddress(mailAddress);
    }

    @PutMapping("editUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> editUser(@RequestBody UserFromRequestDTO user, HttpServletRequest request){
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));

        if(!mailAddress.equals(user.getMailAddress())){
            return new ResponseEntity<>("Emails not matching", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(userService.editUser(user), HttpStatus.OK);
    }



}
