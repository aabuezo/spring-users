package com.example.users.services;

import com.example.users.dto.UserDTO;
import com.example.users.dto.UserDTOMapper;
//import com.example.users.entities.User;
import com.example.users.entities.User;
import com.example.users.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTOMapper userDTOMapper;

    public static final Logger log = LoggerFactory.getLogger(UserService.class);

    public Page<UserDTO> getUsers(int page, int size) {
        List<UserDTO> userList = userRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
        return new PageImpl<UserDTO>(userList);
    }

    public UserDTO getUserById(Integer userId) {
        return userRepository
                .findById(userId)
                .map(userDTOMapper)
                .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("UserId %d not found.", userId)));
    }

    @Cacheable("users")
    public UserDTO getUserByUsername(String username) {
        log.info("Getting user by username {}", username);
        // simulate external system
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return userRepository.findByUsername(username)
                .map(userDTOMapper).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Username %s not found.", username)));
    }

    public UserDTO getByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(userDTOMapper)
                .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad username or password"));
    }

    public Page<String> getUsernames(Integer page, Integer size) {
        return userRepository.findUsernames(PageRequest.of(page, size));
    }

    @CacheEvict("users")
    public void deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User %s not found.", username));
        }
    }
}
