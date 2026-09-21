package ecom.base.app.user;

import ecom.base.app.role.RoleMapper;

public class UserMapper {

    public static User toUser(UserRequestDTO requestDTO) {

        User user = new User();

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setPhone(requestDTO.getPhone());

        return user;
    }

    public static UserResponseDTO toUserResponseDTO(User user) {

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setRole(
                RoleMapper.toRoleResponseDTO(user.getRole()));
        responseDTO.setActive(user.getActive());
        responseDTO.setCreatedAt(user.getCreatedAt());
        responseDTO.setUpdatedAt(user.getUpdatedAt());

        return responseDTO;
    }
}