package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.RequestForDeletingAccountDTO;
import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.dto.UserDTO;
import isa.FishingBookingApp.service.RequestForDeletingAccountService;
import isa.FishingBookingApp.service.SubscriptionService;
import isa.FishingBookingApp.service.UserService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;
    private RequestForDeletingAccountService requestForDeletingAccountService;
    private TokenUtils tokenUtils;

    @Autowired
    public UserController(UserService userService, RequestForDeletingAccountService requestForDeletingAccountService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.requestForDeletingAccountService = requestForDeletingAccountService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping(value="getUser")
    @PreAuthorize("hasRole('USER')" + "|| hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public UserDTO getUser(HttpServletRequest request){
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        return new UserDTO(userService.findByMailAddress(mailAddress));
    }

    @PutMapping(value="editUser")
    @PreAuthorize("hasRole('USER')" + "|| hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> editUser(@RequestBody UserDTO user, HttpServletRequest request){
        if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(user.getMailAddress(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.editUser(user), HttpStatus.OK);
    }

    @PostMapping(value = "deletingRequest")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteRequest(@RequestBody RequestForDeletingAccountDTO requestForDeletingAccountDTO, HttpServletRequest request){
        if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(requestForDeletingAccountDTO.getMailAddress(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(requestForDeletingAccountService.createNewRequest(requestForDeletingAccountDTO), HttpStatus.OK);
    }


}
