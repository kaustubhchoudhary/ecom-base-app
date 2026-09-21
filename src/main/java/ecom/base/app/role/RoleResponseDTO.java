package ecom.base.app.role;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoleResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
