package ecom.base.app.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecom.base.app.response.dtos.ApiResponseDTO;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getAllUsers() {

        return ResponseEntity.ok(
                new ApiResponseDTO<List<UserResponseDTO>>(
                        "Users fetched successfully",
                        userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<UserResponseDTO>(
                        "User fetched successfully",
                        userService.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> addUser(
            @RequestBody UserRequestDTO userRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponseDTO<UserResponseDTO>(
                                "User added successfully",
                                userService.addUser(userRequestDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO) {

        return ResponseEntity.ok(
                new ApiResponseDTO<UserResponseDTO>(
                        "User updated successfully",
                        userService.updateUser(id, userRequestDTO)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> toggleUserStatus(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<UserResponseDTO>(
                        "User status toggled successfully",
                        userService.toggleUserStatus(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getCurrentUser(
            @RequestParam Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<UserResponseDTO>(
                        "Current user fetched successfully",
                        userService.getCurrentUser(id)));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateCurrentUser(
            @RequestParam Long id,
            @RequestBody UserRequestDTO userRequestDTO) {

        return ResponseEntity.ok(
                new ApiResponseDTO<UserResponseDTO>(
                        "Current user updated successfully",
                        userService.updateCurrentUser(id, userRequestDTO)));
    }
}