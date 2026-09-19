package ecom.base.app.category;

import lombok.Data;

@Data
public class CategoryUpdateRequestDTO {
    private String name;
    private String description;
    private Boolean active;
}
