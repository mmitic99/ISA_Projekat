package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.LoginUser;
import isa.FishingBookingApp.dto.UserDTO;
import isa.FishingBookingApp.dto.UserTokenState;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.service.UserService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Object> createToken(@RequestBody LoginUser loginUser, HttpServletResponse response) {

        try {
            Authentication authentication = authenificationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getMailAddress(), loginUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user.getRole().getName()));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody UserDTO userDTO, UriComponentsBuilder ucBuilder) {

        User existUser = this.userService.findByMailAddress(userDTO.getMailAddress());
        if (existUser != null) {
            return new ResponseEntity<>("Email address already exists", HttpStatus.BAD_REQUEST);
        }

        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            return new ResponseEntity<>("Passwords not match", HttpStatus.BAD_REQUEST);
        }

        User newUser = null;
        try {
            if (userDTO.getUserRole() != null &&
                    (userDTO.getUserRole().equals("cottageOwner") || userDTO.getUserRole().equals("boatOwner"))) {
                newUser = userService.saveSpecificUser(userDTO);
            }
            else {
                newUser = userService.saveNewUser(userDTO);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/verify/{id}")
    public ResponseEntity<String> verifyAccount(@PathVariable Long id) {
        boolean verified = userService.verifyAccount(id);
        String retVal = "<html> Pozdrav, <br>";
        if (verified) {
            retVal += "Uspesno ste verifikovali nalog, sada se možete ulogovati na našoj aplikaciji:<br>" +
                    "<a href=\"http://localhost:4200\">Aplikacija</a>";
        } else {
            retVal += "Niste verifikovali nalog, možete se obratiti našem administratoru.<br>";
        }

        retVal += "</html>";

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        return new ResponseEntity<String>(retVal, responseHeaders, HttpStatus.OK);
    }
}
