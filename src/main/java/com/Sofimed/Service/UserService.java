package com.Sofimed.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Sofimed.DTO.AuthResponse;
import com.Sofimed.DTO.RegisterRequest;
import com.Sofimed.DTO.UserDTO;
import com.Sofimed.Dao.UserRepository;
import com.Sofimed.Dao.ClientRepository;
import com.Sofimed.Model.User;
import com.Sofimed.Model.Client;
import com.Sofimed.Model.Role;
import com.Sofimed.Security.JwtTokenProvider;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponse authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtTokenProvider.createToken(username, user.getRole().name());

        UserDTO userDTO = new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
        );

        return new AuthResponse(token, userDTO);
    }


    public Client registerNewClient(RegisterRequest request) {
        // Check if username or email already exists
        if (existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.CLIENT);
        user.setActive(true);
        
        User savedUser = userRepository.save(user);

        // Create new client
        Client client = new Client();
        client.setUser(savedUser);
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        client.setCompanyName(request.getCompanyName());
        
        return clientRepository.save(client);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Client findClientByUserId(Long userId) {
        return clientRepository.findByUserId(userId)
            ;
    }

    public Client findClientByUsername(String username) {
        User user = findByUsername(username);
        return findClientByUserId(user.getId());
    }
}