package ecom.base.app.category;

import java.util.List;

import ecom.base.app.product.ProductMapper;
import ecom.base.app.product.ProductResponseDTO;

public class CategoryMapper {

    public static Category toCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();

        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());

        return category;
    }

    public static Category toCategory(CategoryUpdateRequestDTO categoryUpdateRequestDTO) {
        Category category = new Category();

        category.setName(categoryUpdateRequestDTO.getName());
        category.setDescription(categoryUpdateRequestDTO.getDescription());
        category.setActive(categoryUpdateRequestDTO.getActive());

        return category;
    }

    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();

        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setDescription(category.getDescription());
        categoryResponseDTO.setActive(category.getActive());

        List<ProductResponseDTO> products = category
                .getProducts()
                .stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();

        categoryResponseDTO.setProducts(products);

        return categoryResponseDTO;
    }

    public static CategorySummaryDTO toCategorySummaryDTO(Category category) {

        CategorySummaryDTO response = new CategorySummaryDTO();

        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    }

}
