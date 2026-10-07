package ecom.base.app.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecom.base.app.categories.CategoryMapper;
import ecom.base.app.categories.CategorySummaryDTO;

public class ProductMapper {

    public static List<ProductResponseDTO> toProductResponseDTOList(List<Product> productsList) {
        return productsList.stream().map(ProductMapper::toProductResponseDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Product toProduct(ProductRequestDTO request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setBrand(request.getBrand());
        product.setSpecifications(request.getSpecifications());

        return product;
    }

    public static ProductResponseDTO toProductResponseDTO(Product product) {

        ProductResponseDTO response = new ProductResponseDTO();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());

        // To break cyclic calling -> introduced a new DTO
        CategorySummaryDTO dto = CategoryMapper.toCategorySummaryDTO(product.getCategory());
        response.setCategorySummaryDTO(dto);
        response.setBrand(product.getBrand());
        // response.setImage(product.getImage());
        response.setSpecifications(product.getSpecifications());
        response.setActive(product.getActive());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
}