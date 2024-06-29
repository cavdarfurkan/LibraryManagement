package com.cavdarfurkan.librarymanagement.auth;

import com.cavdarfurkan.librarymanagement.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookService bookService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, BookService bookService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookService = bookService;
    }

    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(UserDTOMapper::toDTO);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserDetailsDTO> getUserDetailsById(Long id) {
        return userRepository.findById(id).map(UserDTOMapper::toDetailsDTO);
    }

    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDTO, String roleName) {
        if (userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        User user = UserRegisterDTOMapper.toEntity(userRegisterDTO);
        Optional<Role> userRole = roleRepository.findByRole(roleName);
        userRole.ifPresentOrElse(role -> user.getRoles().add(role), NoSuchElementException::new);

        userRepository.save(user);
    }

    public User getCurrentUser() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new Exception("User not found");
        }

        return user.get();
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = getUserById(id).orElseThrow(NoSuchElementException::new);

        user.getBorrowedBooks().forEach(book -> {
            try {
                bookService.returnBook(book.getId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });

        userRepository.deleteById(id);
    }
}
