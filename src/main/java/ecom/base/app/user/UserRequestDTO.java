package ecom.base.app.user;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String name;

    private String email;

    private String password;

    private String phone;

    private Long roleId;
}