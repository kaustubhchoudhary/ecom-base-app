package ecom.base.app.role;

public class RoleMapper {
    public static Role toRole(RoleRequestDTO requestDTO) {

        Role role = new Role();

        role.setName(requestDTO.getName());
        role.setDescription(requestDTO.getDescription());

        return role;
    }

    public static RoleResponseDTO toRoleResponseDTO(Role role) {

        RoleResponseDTO responseDTO = new RoleResponseDTO();

        responseDTO.setId(role.getId());
        responseDTO.setName(role.getName());
        responseDTO.setDescription(role.getDescription());
        responseDTO.setActive(role.getActive());
        responseDTO.setCreatedAt(role.getCreatedAt());
        responseDTO.setUpdatedAt(role.getUpdatedAt());

        return responseDTO;
    }
}
