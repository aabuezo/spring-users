package com.example.users.controllers;

import com.example.users.entities.Profile;
import com.example.users.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// para asegurar que cada perfil tenga un usuario
@RequestMapping("/users/{userId}/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfileById(@PathVariable("userId") Integer userId,
                                                  @PathVariable("profileId") Integer profileId) {
        return new ResponseEntity<>(profileService.getByUserIdAndProfileId(userId, profileId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@PathVariable("userId") Integer userId, @RequestBody Profile profile) {
        return new ResponseEntity<>(profileService.createProfile(userId, profile), HttpStatus.CREATED);
    }
}
