package ecom.base.app.categories;

import java.util.List;

import ecom.base.app.product.ProductResponseDTO;
import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private List<ProductResponseDTO> products;
}