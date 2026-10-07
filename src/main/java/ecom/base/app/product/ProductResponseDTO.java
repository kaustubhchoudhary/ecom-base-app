package ecom.base.app.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ecom.base.app.categories.CategorySummaryDTO;
import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private CategorySummaryDTO categorySummaryDTO;
    private String brand;
    private byte[] image;
    private String specifications;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}