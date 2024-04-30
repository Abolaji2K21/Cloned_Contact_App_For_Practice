//package africa.semicolon.services;
//
//import africa.semicolon.contactException.BigContactException;
//import africa.semicolon.contactException.UserNotFoundException;
//import africa.semicolon.data.models.Category;
//import africa.semicolon.data.models.Contact;
//import africa.semicolon.data.repositories.CategoryRepository;
//import africa.semicolon.data.repositories.ContactRepository;
//import africa.semicolon.dtos.requests.*;
//import africa.semicolon.dtos.response.CreateCategoryResponse;
//import africa.semicolon.dtos.response.DeleteCategoryResponse;
//import africa.semicolon.dtos.response.EditCategoryResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static africa.semicolon.utils.Mapper.mapCreateCategoryResponse;
//import static africa.semicolon.utils.Mapper.mapEditCategoryResponse;
//
//@Service
//public class CategoryServiceImpl implements CategoryService {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private ContactRepository contactRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
//        String username = createCategoryRequest.getUsername();
//        String firstName = createCategoryRequest.getFirstName();
//        validateUser(username);
//
//        Category savedCategory = saveCategory(createCategoryRequest.getDescription(), username);
//
//        saveContactWithCategory(savedCategory, username, firstName);
//
//        return mapCreateCategoryResponse(savedCategory);
//    }
//
//    @Override
//    public EditCategoryResponse editCategory(EditCategoryRequest editCategoryRequest) {
//        String description = editCategoryRequest.getDescription();
//        String username = editCategoryRequest.getUsername();
//
//        validateUser(username);
//        Category existingCategory = getCategoryByDescription(description);
//        validateCategoryOwnership(existingCategory, username);
//
//        updateCategory(existingCategory, description, username);
//
//        return mapEditCategoryResponse(existingCategory);
//    }
//
//    @Override
//    public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {
//        String username = deleteCategoryRequest.getUsername();
//        validateUser(username);
//
//        Category existingCategory = getCategoryByDescription(deleteCategoryRequest.getDescription());
//        validateCategoryOwnership(existingCategory, username);
//
//        categoryRepository.delete(existingCategory);
//
//        return new DeleteCategoryResponse();
//    }
//
//    @Override
//    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//    @Override
//    public List<Contact> getContactByCategoryDescription(String description) {
//        Category category = getCategoryByDescription(description);
//        return category.getContacts();
//    }
//
//    @Override
//    public void addContactToCategory(AddContactToCategoryRequest request) {
//        Optional<Contact> contactOptional = contactRepository.findByContactId(request.getContactId());
//        Optional<Category> categoryOptional = categoryRepository.findByCategoryId(request.getCategoryId());
//        if (contactOptional.isEmpty()) {
//            throw new BigContactException("Contact not found");
//        }
//        if (categoryOptional.isEmpty()) {
//            throw new BigContactException("Category not found");
//        }
//
//        Contact contact = contactOptional.get();
//        Category category = categoryOptional.get();
//        category.getContacts().add(contact);
//        categoryRepository.save(category);
//    }
//
//
//    @Override
//    public void removeContactFromCategory(RemoveContactFromCategoryRequest request) {
//        String contactId = request.getContactId();
//        String categoryId = request.getCategoryId();
//
//        Category category = categoryRepository.findByCategoryId(categoryId)
//                .orElseThrow(() -> new BigContactException("Category not found"));
//
//        Contact contact = contactRepository.findByContactId(contactId)
//                .orElseThrow(() -> new BigContactException("Contact not found"));
//
//        category.getContacts().removeIf(newContact -> contact.getContactId().equals(contactId));
//        categoryRepository.save(category);
//    }
//
//    private void validateUser(String username) {
//        if (!userService.isUserRegistered(username) || !userService.isUserLoggedIn(username)) {
//            throw new BigContactException("User with username " + username + " is not registered or logged in");
//        }
//    }
//
//    private Category getCategoryByDescription(String description) {
//        Optional<Category> optionalCategory = categoryRepository.findByDescription(description);
//        return optionalCategory.orElseThrow(() -> new BigContactException("Category not found with description: " + description));
//    }
//
//    private void saveContactWithCategory(Category savedCategory, String username,String firstname) {
//        Contact contact = new Contact();
//        contact.setUsername(username);
//        contact.setFirstName(firstname);
//        contactRepository.save(contact);
//    }
//
//    private Category saveCategory(String description, String username) {
//        Category category = new Category();
//        category.setDescription(description);
//        category.setUsername(username);
//        return categoryRepository.save(category);
//    }
//
//    private void validateCategoryOwnership(Category category, String username) {
//        if (!category.getUsername().equals(username)) {
//            throw new UserNotFoundException("User with username " + username + " is not authorized to perform this action on the category");
//        }
//    }
//
//    private void updateCategory(Category category, String description, String username) {
//        if (!category.getDescription().equals(description) || !category.getUsername().equals(username)) {
//            category.setDescription(description);
//            category.setUsername(username);
//            categoryRepository.save(category);
//        }
//    }
//}
