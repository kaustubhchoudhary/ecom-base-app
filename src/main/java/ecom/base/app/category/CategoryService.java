package ecom.base.app.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ecom.base.app.exceptions.CategoryAlreadyInactiveException;
import ecom.base.app.exceptions.DuplicateResourceException;
import ecom.base.app.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO) {

        String categoryName = categoryRequestDTO.getName();

        if (categoryRepository.existsByName(categoryName)) {
            throw new DuplicateResourceException(
                    "Category with name: " + categoryName + " already exists");
        }

        Category category = CategoryMapper.toCategory(categoryRequestDTO);
        System.out.println(category);

        category.setActive(true);

        Category categorysavedInDb = categoryRepository.save(category);

        System.out.println(categorysavedInDb);

        return CategoryMapper.toCategoryResponseDTO(categorysavedInDb);
    }

    List<CategoryResponseDTO> getAllCategories() {

        List<Category> categoriesList = categoryRepository.findAll();
        if (categoriesList.isEmpty()) {
            throw new ResourceNotFoundException("No Categories found in the database");
        }

        List<CategoryResponseDTO> categoryResponseDTOsList = categoriesList
                .stream()
                .map(CategoryMapper::toCategoryResponseDTO)
                .toList();

        return categoryResponseDTOsList;
    }

    CategoryResponseDTO getCategoryById(Long id) {
        // System.out.println("\n Before find By Id");
        Category categoryFetchedFromDb = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id : " + id + " not found"));

        // System.out.println("\n After find By Id");
        System.out.println(categoryFetchedFromDb);
        CategoryResponseDTO categoryResponseDTO = CategoryMapper.toCategoryResponseDTO(categoryFetchedFromDb);

        return categoryResponseDTO;

    }

    public CategoryResponseDTO updateCategory(Long id,
            CategoryUpdateRequestDTO categoryUpdateRequestDTO) {

        if (!categoryRepository.existsById(id)) // At this point categroy entity is not managed by JPA, Transient state
            throw new ResourceNotFoundException("Category with id : " + id + " does not exist");

        String categoryName = categoryUpdateRequestDTO.getName();

        if (categoryRepository.existsByName(categoryName))
            throw new DuplicateResourceException("Category " + categoryName + " already exists");

        Category categoryToSave = CategoryMapper.toCategory(categoryUpdateRequestDTO);// Transient

        categoryToSave.setId(id);
        Category updatedCategory = categoryRepository.save(categoryToSave);// updatedCategory: Persistent

        return CategoryMapper.toCategoryResponseDTO(updatedCategory);

    }

    @Transactional
    CategoryResponseDTO patchCategory(Long id) {

        Optional<Category> optionalCategory = categoryRepository.findById(id); // Persistent

        if (optionalCategory.isEmpty())
            throw new ResourceNotFoundException("Category with id : " + id + " not found");

        Category category = optionalCategory.get();

        if (!category.getActive())
            throw new CategoryAlreadyInactiveException("Category with id : " + id + " already inactive");

        category.setActive(false);

        return CategoryMapper.toCategoryResponseDTO(category);

    }
}
