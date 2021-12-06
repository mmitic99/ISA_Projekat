package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.UserDTO;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.service.UserService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(value="getUser")
    @PreAuthorize("hasRole('USER')" + "|| hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public UserDTO getUser(HttpServletRequest request){
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        return new UserDTO(userService.findByMailAddress(mailAddress));
    }

    // treba put, ali angular ne zeli da posalje put zahtev
    @PostMapping(value="editUser")
    @PreAuthorize("hasRole('USER')" + "|| hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> editUser(@RequestBody UserDTO user, HttpServletRequest request){
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        if(!mailAddress.equals(user.getMailAddress())){
            return new ResponseEntity<>("Emails not matching", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.editUser(user), HttpStatus.OK);
    }



}
