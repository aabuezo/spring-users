package com.example.users.services;

import com.example.users.entities.Profile;
import com.example.users.entities.User;
import com.example.users.repositories.ProfileRepository;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public Profile createProfile(Integer userId, Profile profile) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            profile.setUser(user.get());
            return profileRepository.save(profile);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("UserId %d not found.", userId));
        }
    }

    public Profile getByUserIdAndProfileId(Integer userId, Integer profileId) {
        Optional<Profile> result = profileRepository.findByUserIdAndProfileId(userId, profileId);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Profile not found for userId %d and profileId %d.", userId, profileId));
        }
    }
}
