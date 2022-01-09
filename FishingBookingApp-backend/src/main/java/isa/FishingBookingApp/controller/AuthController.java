package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.ChangePasswordDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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

            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user.getRole().getName(), user.getMailAddress(), user.getId()));
        }
        catch (Exception e){
            return new ResponseEntity<>("Email ili lozinka nisu tačni", HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody UserDTO userDTO, UriComponentsBuilder ucBuilder) {

        User existUser = this.userService.findByMailAddress(userDTO.getMailAddress());
        if (existUser != null) {
            return new ResponseEntity<>("Email već postoji, unesite drugi email", HttpStatus.BAD_REQUEST);
        }

        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            return new ResponseEntity<>("Lozinke se ne poklapaju", HttpStatus.BAD_REQUEST);
        }

        User newUser = null;
        try {
            if (userDTO.getUserRole() != null &&
                    (userDTO.getUserRole().equals("ROLE_cottageOwner") || userDTO.getUserRole().equals("ROLE_boatOwner"))) {
                newUser = userService.saveSpecificUser(userDTO);
            }
            else {
                newUser = userService.saveNewUser(userDTO);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
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

        return new ResponseEntity<>(retVal, responseHeaders, HttpStatus.OK);
    }
    @PutMapping("/changePassword")
    @PreAuthorize("hasRole('USER')" + "|| hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDto, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        if (Boolean.FALSE.equals(tokenUtils.isUserAuthorizedAndTokenNotExpired(mailAddress, request))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if (!changePasswordDto.getNewPassword1().equals(changePasswordDto.getNewPassword2())) {
            return new ResponseEntity<>("Lozinke se ne poklapaju", HttpStatus.BAD_REQUEST);
        }

        try {
            authenificationManager.authenticate(new UsernamePasswordAuthenticationToken(mailAddress, changePasswordDto.getOldPassword()));
        }
        catch (Exception e){
            return new ResponseEntity<>("Pogrešno uneta lozinka", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.changePassword(mailAddress, changePasswordDto.getNewPassword1()), HttpStatus.OK);
    }
}
