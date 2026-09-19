package ecom.base.app.product;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Long categoryId;
    private String brand;
    private byte[] image;
    private String specifications;
}