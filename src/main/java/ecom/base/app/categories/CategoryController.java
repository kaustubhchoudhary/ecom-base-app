package ecom.base.app.categories;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecom.base.app.product.Product;
import ecom.base.app.response.dtos.ApiResponseDTO;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

        final CategoryService categoryService;

        public CategoryController(CategoryService categoryService) {
                this.categoryService = categoryService;
        }

        @PostMapping
        ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> addCategory(
                        @RequestBody CategoryRequestDTO categoryRequestDTO) {

                CategoryResponseDTO categoryResponseDTO = categoryService.addCategory(categoryRequestDTO);

                // Response Preparation
                ApiResponseDTO<CategoryResponseDTO> apiResponseDTO = new ApiResponseDTO<>();
                apiResponseDTO.setMessage("Category Added Successfully");
                apiResponseDTO.setData(categoryResponseDTO);

                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponseDTO);
        }

        @GetMapping("/{id}")
        ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> getCategoryById(@PathVariable Long id) {

                CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(id);

                ApiResponseDTO<CategoryResponseDTO> apiResponseDTO = new ApiResponseDTO<>();

                apiResponseDTO.setMessage("Category with id : " + id + " found");
                apiResponseDTO.setData(categoryResponseDTO);

                return ResponseEntity.ok(apiResponseDTO);
        }

        @GetMapping
        ResponseEntity<ApiResponseDTO<List<CategoryResponseDTO>>> getAllCategories() {

                List<CategoryResponseDTO> dtos = categoryService.getAllCategories();

                ApiResponseDTO<List<CategoryResponseDTO>> responseDTO = new ApiResponseDTO<>(
                                "Categories Fetched Successfully",
                                dtos);

                return ResponseEntity.ok(responseDTO);

        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> updateCategory(
                        @PathVariable Long id,
                        @RequestBody CategoryUpdateRequestDTO categoryUpdateRequestDTO) {

                CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id,
                                categoryUpdateRequestDTO);

                ApiResponseDTO<CategoryResponseDTO> apiResponseDTO = new ApiResponseDTO<>();
                apiResponseDTO.setMessage("Category updated successfully");
                apiResponseDTO.setData(categoryResponseDTO);

                return ResponseEntity.ok(apiResponseDTO);
        }

        @PatchMapping("/{id}")
        public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> patchCategory(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                new ApiResponseDTO<CategoryResponseDTO>(
                                                "Category status toggled successfully",
                                                categoryService.patchCategory(id)));
        }

        @GetMapping("/{categoryId}/products")
        public ResponseEntity<List<Product>> getProductsByCategory(
                        @PathVariable Long categoryId) {

                List<Product> products = categoryService.getProductsByCategory(categoryId);

                return ResponseEntity.ok(products);
        }
}