package ecom.base.app.user;

import java.time.LocalDateTime;

import ecom.base.app.role.RoleResponseDTO;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private RoleResponseDTO role;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
