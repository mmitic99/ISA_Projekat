package isa.FishingBookingApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isa.FishingBookingApp.dto.ClassDTO;
import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.model.FishingClass;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.service.ClassService;
import isa.FishingBookingApp.service.UserService;
import isa.FishingBookingApp.util.TokenUtils;

@RestController
@RequestMapping(value="api/class")
public class ClassController {

	private ClassService classService;
	private UserService userService;
	private TokenUtils tokenUtils;
	
	@Autowired
	public ClassController(ClassService classService, TokenUtils tokenUtils, UserService userService) {
		this.classService = classService;
		this.userService = userService;
		this.tokenUtils = tokenUtils;
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<FishingClass> createFishingClass(@RequestBody ClassDTO newClassDTO, HttpServletRequest request) {
        if (!authorizedUser(newClassDTO.getFishingInstructorUsername(), request)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        FishingClass newClass= classService.saveOrUpdate(newClassDTO);
        if (newClass == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newClass, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<FishingClass> updateCottage(@RequestBody ClassDTO classDTO, HttpServletRequest request) {
        if (!authorizedUser(classDTO.getFishingInstructorUsername(), request)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        if (!classService.exists(classDTO.getId())){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        // TODO: uraditi proveru da li je moguce izmeniti cas pecanj (ne sme biti rezervisana)
        FishingClass updatedClass= classService.saveOrUpdate(classDTO);
        return new ResponseEntity<>(updatedClass, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/allClasses", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<FishingClass>> getClassesOfUser(@PathVariable String username, HttpServletRequest request) {
        if (!authorizedUser(username, request)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        User user = userService.findByMailAddress(username);
        List<FishingClass> allClassesOfUser = classService.getAllOfUser(user.getId());
        return new ResponseEntity<>(allClassesOfUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<FishingClass> deleteClass(@PathVariable Long id, HttpServletRequest request) {
        FishingClass class1 = classService.get(id);
        if (class1 == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!authorizedUser(class1.getFishingInstructor().getUsername(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        // TODO: uraditi proveru da li je moguce obrisati vikendicu (ne sme biti rezervisana)
        if (classService.delete(id)){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private boolean authorizedUser(String FishingInstructorUsername, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        return username.equals(FishingInstructorUsername);
    }
}
