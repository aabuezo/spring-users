package com.example.users.services;

import com.example.users.entities.Address;
import com.example.users.entities.Profile;
import com.example.users.repositories.AddressRepository;
import com.example.users.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<Address> getAddressByUserIdAndProfileId(Integer userId, Integer profileId) {
        return addressRepository.findAddressByUserIdAndProfileId(userId, profileId);
    }

    public Address createAddress(Integer userId, Integer profileId, Address address) {
        Optional<Profile> profile = profileRepository.findByUserIdAndProfileId(userId, profileId);
        if (profile.isPresent()) {
            address.setProfile(profile.get());
            addressRepository.save(address);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Profile not found for userId %d and profileId %d.", userId, profileId));
        }
        return null;
    }
}
