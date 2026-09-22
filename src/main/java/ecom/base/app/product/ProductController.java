package ecom.base.app.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ecom.base.app.response.dtos.ApiResponseDTO;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> addProduct(
            @RequestPart("product") ProductRequestDTO productRequestDTO,
            @RequestPart("image") MultipartFile image) {

        ProductResponseDTO productResponseDTO = productService.addProduct(productRequestDTO, image);

        ApiResponseDTO<ProductResponseDTO> apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setData(productResponseDTO);
        apiResponseDTO.setMessage("Product added successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProduct(@PathVariable Long id) {

        ProductResponseDTO dto = productService.getProduct(id);

        ApiResponseDTO<ProductResponseDTO> apiResponseDTO = new ApiResponseDTO<>();

        apiResponseDTO.setData(dto);
        apiResponseDTO.setMessage("Product fetched");

        return ResponseEntity.ok(apiResponseDTO);
    }

    @GetMapping("/{id}/image")
    ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] image = productService.getImage(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping
    ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProducts() {

        List<ProductResponseDTO> productResponseDTOs = productService.getAllProducts();

        ApiResponseDTO<List<ProductResponseDTO>> apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setData(productResponseDTOs);
        apiResponseDTO.setMessage("Products fetched");

        return ResponseEntity.ok(apiResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> patchProduct(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<ProductResponseDTO>(
                        "Product status toggled successfully",
                        productService.patchProduct(id)));
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") ProductUpdateRequestDTO productUpdateRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        ProductResponseDTO productResponseDTO = productService.updateProduct(
                productId,
                productUpdateRequestDTO,
                image);

        ApiResponseDTO<ProductResponseDTO> apiResponseDTO = new ApiResponseDTO<>();

        apiResponseDTO.setData(productResponseDTO);
        apiResponseDTO.setMessage("Product updated successfully");

        return ResponseEntity.ok(apiResponseDTO);
    }

    // Product filteration functionalities
    @GetMapping("/price-range")
    ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductsBetweenPriceRange(
            @RequestParam("min") BigDecimal minValue, @RequestParam("max") BigDecimal maxValue) {

        List<ProductResponseDTO> productResponseDTOs = productService.getProductsInPriceRange(minValue, maxValue);

        ApiResponseDTO<List<ProductResponseDTO>> apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setData(productResponseDTOs);
        apiResponseDTO.setMessage("Products within price range: " + minValue + " & " + maxValue + " fetched");

        return ResponseEntity.ok(apiResponseDTO);
    }

    @PostMapping("/add-multiple")
    ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> insertMultipleProducts(
            @RequestBody ArrayList<ProductRequestDTO> productRequestDTOs) {

        System.out.println("----------------------------");
        System.out.println(productRequestDTOs.getClass());

        List<ProductResponseDTO> productResponseDTOs = productService.insertMultipleProducts(productRequestDTOs);

        ApiResponseDTO<List<ProductResponseDTO>> apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setMessage("Products added successfully");
        apiResponseDTO.setData(productResponseDTOs);

        return ResponseEntity.ok(apiResponseDTO);
    }

    @GetMapping("/category/{id}/count")
    public ResponseEntity<ApiResponseDTO<Long>> countProductsByCategoryId(
            @PathVariable Long id) {

        Long count = productService.countProductsByCategoryId(id);

        ApiResponseDTO<Long> response = new ApiResponseDTO<>();
        response.setData(count);
        response.setMessage("Product count fetched successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/brand/{name}")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductsByBrandOrderByPriceDesc(
            @PathVariable String name) {

        List<ProductResponseDTO> products = productService.getProductsByBrandOrderByPriceDesc(name);

        ApiResponseDTO<List<ProductResponseDTO>> response = new ApiResponseDTO<>();

        response.setData(products);
        response.setMessage("Products fetched successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> findByPriceAndBrand(
            @RequestParam("price") BigDecimal costPrice,
            @RequestParam("brand") String brandName) {

        List<ProductResponseDTO> products = productService.findByPriceAndBrand(costPrice, brandName);

        ApiResponseDTO<List<ProductResponseDTO>> response = new ApiResponseDTO<>();
        response.setData(products);
        response.setMessage("Products fetched successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/id-greater-than/{id}")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductsWithIdGreaterThan(
            @PathVariable Long id) {

        List<ProductResponseDTO> products = productService.getProductsWithIdGreaterThan(id);

        ApiResponseDTO<List<ProductResponseDTO>> response = new ApiResponseDTO<>();
        response.setData(products);
        response.setMessage("Products fetched successfully");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponseDTO<Integer>> deactivateProductById(
            @PathVariable Long id) {

        int result = productService.deactivateProductById(id);

        ApiResponseDTO<Integer> response = new ApiResponseDTO<>();

        response.setData(result);
        response.setMessage("Product deactivated successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponseDTO<Page<ProductResponseDTO>>> getProducts(
            Pageable pageable) {

        Page<ProductResponseDTO> products = productService.getProducts(pageable);

        ApiResponseDTO<Page<ProductResponseDTO>> response = new ApiResponseDTO<>(

                "Products fetched successfully", products);

        return ResponseEntity.ok(response);
    }
}