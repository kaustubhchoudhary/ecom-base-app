package ecom.base.app.role;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecom.base.app.response.dtos.ApiResponseDTO;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RoleResponseDTO>>> getAllRoles() {

        return ResponseEntity.ok(
                new ApiResponseDTO<List<RoleResponseDTO>>(
                        "Roles fetched successfully",
                        roleService.getAllRoles()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> getRoleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<RoleResponseDTO>(
                        "Role fetched successfully",
                        roleService.getRoleById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> addRole(
            @RequestBody RoleRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponseDTO<RoleResponseDTO>(
                                "Role added successfully",
                                roleService.addRole(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequestDTO request) {

        return ResponseEntity.ok(
                new ApiResponseDTO<RoleResponseDTO>(
                        "Role updated successfully",
                        roleService.updateRole(id, request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> toggleRoleStatus(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<RoleResponseDTO>(
                        "Role status toggled successfully",
                        roleService.toggleRoleStatus(id)));
    }
}