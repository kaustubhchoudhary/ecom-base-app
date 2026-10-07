package ecom.base.app.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ecom.base.app.categories.Category;
import ecom.base.app.categories.CategoryRepository;
import ecom.base.app.exceptions.DuplicateResourceException;
import ecom.base.app.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

    final ProductRepository productRepository;

    final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO, MultipartFile image) {

        // What if category does not exist
        Long id = productRequestDTO.getCategoryId();
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product cannot be added as Category with id : " + id + " does not exist"));

        // Product name should be unique
        String name = productRequestDTO.getName();
        if (productRepository.existsByName(name)) {
            throw new DuplicateResourceException(
                    "Product cannot be added as Product with name : " + name + " already exists");
        }

        Product product = ProductMapper.toProduct(productRequestDTO);
        System.out.println(product);

        product.setCategory(category);
        product.setActive(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        try {
            product.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product addedProduct = productRepository.save(product);

        ProductResponseDTO productResponseDTO = ProductMapper.toProductResponseDTO(addedProduct);

        return productResponseDTO;
    }

    ProductResponseDTO getProduct(Long productId) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id : " + productId + " does not exist"));

        return ProductMapper.toProductResponseDTO(product);
    }

    byte[] getImage(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id : " + productId + " does not exist"));

        return product.getImage();
    }

    List<ProductResponseDTO> getAllProducts() {

        List<Product> productsList = productRepository.findAll();

        return productsList.stream().map(ProductMapper::toProductResponseDTO).toList();
    }

    @Transactional
    ProductResponseDTO patchProduct(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty())
            throw new ResourceNotFoundException(
                    "Product with id : " + id + " not found");

        Product product = optionalProduct.get();

        product.setActive(!product.getActive());

        return ProductMapper.toProductResponseDTO(product);
    }

    public ProductResponseDTO updateProduct(
            Long productId,
            ProductUpdateRequestDTO productUpdateRequestDTO,
            MultipartFile image) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));

        Category category = categoryRepository.findById(productUpdateRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException(
                        "Category with ID " +
                                productUpdateRequestDTO.getCategoryId() +
                                " not found"));

        product.setName(productUpdateRequestDTO.getName());
        product.setDescription(productUpdateRequestDTO.getDescription());
        product.setPrice(productUpdateRequestDTO.getPrice());
        product.setQuantity(productUpdateRequestDTO.getQuantity());
        product.setBrand(productUpdateRequestDTO.getBrand());
        product.setSpecifications(productUpdateRequestDTO.getSpecifications());
        product.setActive(productUpdateRequestDTO.getActive());

        product.setCategory(category);

        if (image != null && !image.isEmpty()) {
            try {
                product.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read product image", e);
            }
        }

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toProductResponseDTO(updatedProduct);
    }

    List<ProductResponseDTO> getProductsInPriceRange(BigDecimal min, BigDecimal max) {

        List<Product> productsList = productRepository.findByPriceBetween(min, max);

        return productsList.stream().map(ProductMapper::toProductResponseDTO).toList();
    }

    List<ProductResponseDTO> insertMultipleProducts(ArrayList<ProductRequestDTO> productRequestDTOs) {

        System.out.println(productRequestDTOs);

        List<Product> productsList = productRequestDTOs.stream()
                .map(dto -> {
                    Category category = categoryRepository
                            .findById(dto.getCategoryId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Category not found: " + dto.getCategoryId()));

                    Product product = ProductMapper.toProduct(dto);
                    product.setCategory(category);
                    return product;
                })
                .toList();

        System.out.println(productsList);

        List<Product> addedList = productRepository.saveAll(productsList);

        return ProductMapper.toProductResponseDTOList(addedList);
    }

    // Learning Spring Data JPA
    Long countProductsByCategoryId(@PathVariable Long id) {
        return productRepository.countProductsByCategoryId(id);
    }

    List<ProductResponseDTO> getProductsByBrandOrderByPriceDesc(@PathVariable String name) {
        List<Product> productsList = productRepository.findByBrandOrderByPriceDesc(name);
        return ProductMapper.toProductResponseDTOList(productsList);
    }

    List<ProductResponseDTO> findByPriceAndBrand(
            @RequestParam("price") BigDecimal costPrice,
            @RequestParam("brand") String brandName) {
        List<Product> productsList = productRepository.letsGetSomeProducts(costPrice, brandName);
        return ProductMapper.toProductResponseDTOList(productsList);
    }

    List<ProductResponseDTO> getProductsWithIdGreaterThan(Long id) {
        List<Product> productsList = productRepository.fetchProductsWithIdGreaterThan(id);
        return ProductMapper.toProductResponseDTOList(productsList);
    }

    int deactivateProductById(@PathVariable Long id) {
        return productRepository.deactivateById(id);
    }

    public Page<ProductResponseDTO> getProducts(Pageable pageable) {

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(ProductMapper::toProductResponseDTO);
    }
}
