package com.cavdarfurkan.librarymanagement.service;

import com.cavdarfurkan.librarymanagement.auth.*;
import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.model.book.BookDTOMapper;
import org.springframework.data.util.Streamable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BookService bookService;
    private final RoleRepository roleRepository;

    public AdminService(UserRepository userRepository, UserService userService, BookService bookService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public List<UserDetailsDTO> getAllUsers() {
        return Streamable.of(userRepository.findAll()).map(UserDTOMapper::toDetailsDTO).toList();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserDetailsDTO getUserDetails(Long id) {
        return userService.getUserDetailsById(id).orElseThrow(NoSuchElementException::new);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public Set<BookDTO> getPublishedBooksByUserId(Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(NoSuchElementException::new);

        return user.getPublishedBooks().stream()
                .map(BookDTOMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public BookDTO getBookById(Long id) throws Exception {
        return bookService.getBookById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserDetailsDTO updateUser(UserDetailsDTO userDetailsDTO) {
        Role updatedRole = roleRepository.findByRole(userDetailsDTO.getRole())
                .orElseThrow(NoSuchElementException::new);
        User originalUser = userService.getUserById(Long.valueOf(userDetailsDTO.getId()))
                .orElseThrow(NoSuchElementException::new);

        User tempUser = UserDTOMapper.toUser(userDetailsDTO, updatedRole);
        tempUser.setPassword(originalUser.getPassword());

        User updatedUser = userRepository.save(tempUser);
        return UserDTOMapper.toDetailsDTO(updatedUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void deleteUserById(Long id) {
        userService.deleteUserById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void deleteBookById(Long id) {
        bookService.deleteBook(id);
    }
}
