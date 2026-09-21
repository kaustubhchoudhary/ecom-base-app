package ecom.base.app.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ecom.base.app.exceptions.ResourceAlreadyExistsException;
import ecom.base.app.exceptions.ResourceNotFoundException;
import ecom.base.app.role.Role;
import ecom.base.app.role.RoleRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toUserResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new ResourceNotFoundException(
                    "User with id : " + id + " not found");

        return UserMapper.toUserResponseDTO(
                optionalUser.get());
    }

    @Transactional
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {

        if (userRepository.existsByEmail(userRequestDTO.getEmail()))
            throw new ResourceAlreadyExistsException(
                    "User with email " + userRequestDTO.getEmail() + " already exists");

        if (userRepository.existsByPhone(userRequestDTO.getPhone()))
            throw new ResourceAlreadyExistsException(
                    "User with phone " + userRequestDTO.getPhone() + " already exists");

        Role role = roleRepository.findById(userRequestDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role with id : "
                                + userRequestDTO.getRoleId()
                                + " not found"));

        User user = UserMapper.toUser(userRequestDTO);

        user.setRole(role);
        user.setActive(true);

        User savedUser = userRepository.save(user);

        return UserMapper.toUserResponseDTO(savedUser);
    }

    @Transactional
    public UserResponseDTO updateUser(
            Long id,
            UserRequestDTO userRequestDTO) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new ResourceNotFoundException(
                    "User with id : " + id + " not found");

        String email = userRequestDTO.getEmail();
        if (userRepository.existsByEmailAndIdNot(email, id))
            throw new ResourceAlreadyExistsException(
                    "User with email " + email + " already exists");

        String phone = userRequestDTO.getPhone();
        if (userRepository.existsByPhoneAndIdNot(phone, id))
            throw new ResourceAlreadyExistsException(
                    "User with phone " + phone + " already exists");

        Long roleId = userRequestDTO.getRoleId();
        Role role = roleRepository.findById(
                roleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role with id : " + roleId + " not found"));

        User user = optionalUser.get();

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setPhone(userRequestDTO.getPhone());
        user.setRole(role);

        // No userRepository.save(user) as user is in persistent state
        return UserMapper.toUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO toggleUserStatus(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new ResourceNotFoundException(
                    "User with id : " + id + " not found");

        User user = optionalUser.get();

        user.setActive(!user.getActive());

        // No userRepository.save(user) as user is in persistent state
        return UserMapper.toUserResponseDTO(user);
    }

    // Temporary implementation until Spring Security is introduced
    public UserResponseDTO getCurrentUser(Long id) {
        return getUserById(id);
    }

    // Temporary implementation until Spring Security is introduced
    @Transactional
    public UserResponseDTO updateCurrentUser(
            Long id,
            UserRequestDTO request) {

        return updateUser(id, request);
    }
}