//package africa.semicolon.services;
//
//import africa.semicolon.contactException.BigContactException;
//import africa.semicolon.data.models.Category;
//import africa.semicolon.data.models.Contact;
//import africa.semicolon.data.repositories.CategoryRepository;
//import africa.semicolon.data.repositories.ContactRepository;
//import africa.semicolon.data.repositories.UserRepository;
//import africa.semicolon.dtos.requests.*;
//import africa.semicolon.dtos.response.CreateCategoryResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.Matchers.*;
//
//@SpringBootTest
//public class CategoryServiceImplTest {
//
//    @Autowired
//    private CategoryService categoryService;
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
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        categoryRepository.deleteAll();
//        userRepository.deleteAll();
//        contactRepository.deleteAll();
//    }
//
//    @Test
//    public void testingTheCreateCategoryMethod() {
//        RegisterUserRequest registerRequest = new RegisterUserRequest();
//        registerRequest.setFirstname("PenIs");
//        registerRequest.setLastname("Up");
//        registerRequest.setUsername("penisup");
//        registerRequest.setPassword("Holes");
//        userService.register(registerRequest);
//
//        LoginUserRequest loginRequest = new LoginUserRequest();
//        loginRequest.setUsername("penisup");
//        loginRequest.setPassword("Holes");
//        userService.login(loginRequest);
//
//        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
//        createCategoryRequest.setDescription("HappyCategories");
//        createCategoryRequest.setUsername("penisup");
//
//        CreateCategoryResponse response = categoryService.createCategory(createCategoryRequest);
//
//        assertEquals("HappyCategories", response.getDescription());
//        assertEquals("penisup", response.getUsername());
//    }
//
//    @Test
//    public void testingTheEditCategoryMethodWhen_CategoryIsNotRegistered() {
//        EditCategoryRequest editCategoryRequest = new EditCategoryRequest();
//        editCategoryRequest.setCategoryId(editCategoryRequest.getCategoryId());
//        editCategoryRequest.setDescription("HappyCategories");
//        editCategoryRequest.setUsername("penisup");
//
//        assertThrows(BigContactException.class, () -> categoryService.editCategory(editCategoryRequest));
//    }
//
//    @Test
//    public void testingTheDeleteCategoryMethodWhen_CategoryIsNotRegistered() {
//        DeleteCategoryRequest deleteCategoryRequest = new DeleteCategoryRequest();
//        deleteCategoryRequest.setDescription(deleteCategoryRequest.getDescription());
//
//        assertThrows(BigContactException.class, () -> categoryService.deleteCategory(deleteCategoryRequest));
//    }
//
//    @Test
//    void testingThatTheCreateCategoryMethodThrowsException_UserIsNotLoggedIn(){
//        RegisterUserRequest registerRequest = new RegisterUserRequest();
//        registerRequest.setFirstname("PenIs");
//        registerRequest.setLastname("Up");
//        registerRequest.setUsername("penisup");
//        registerRequest.setPassword("Holes");
//        userService.register(registerRequest);
//
//        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
//        createCategoryRequest.setDescription("HappyCategories");
//        createCategoryRequest.setUsername("penisup");
//
//        assertThrows(BigContactException.class, () -> categoryService.createCategory(createCategoryRequest));
//
//    }
//
//    @Test
//    void testingThatTheCreateCategoryMethodThrowsException_UserIsNotRegistered(){
//        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
//        createCategoryRequest.setDescription("HappyCategories");
//        createCategoryRequest.setUsername("penisup");
//
//        assertThrows(BigContactException.class, () -> categoryService.createCategory(createCategoryRequest));
//
//    }
//
//    @Test
//    void testThatTheEditCategoryMethodsThrowsException_UserIsNotLoggedIn(){
//        RegisterUserRequest registerRequest = new RegisterUserRequest();
//        registerRequest.setFirstname("PenIs");
//        registerRequest.setLastname("Up");
//        registerRequest.setUsername("penisup");
//        registerRequest.setPassword("Holes");
//        userService.register(registerRequest);
//
//        EditCategoryRequest editCategoryRequest = new EditCategoryRequest();
//        editCategoryRequest.setCategoryId(editCategoryRequest.getCategoryId());
//        editCategoryRequest.setDescription("HappyCategories");
//        editCategoryRequest.setUsername("penisup");
//
//        assertThrows(BigContactException.class, () -> categoryService.editCategory(editCategoryRequest));
//
//    }
//
//    @Test
//    void testThatEditCategoryWorks(){
//        RegisterUserRequest registerRequest = new RegisterUserRequest();
//        registerRequest.setFirstname("PenIs");
//        registerRequest.setLastname("Up");
//        registerRequest.setUsername("penisup");
//        registerRequest.setPassword("Holes");
//        userService.register(registerRequest);
//
//        LoginUserRequest loginRequest = new LoginUserRequest();
//        loginRequest.setUsername("penisup");
//        loginRequest.setPassword("Holes");
//        userService.login(loginRequest);
//
//        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
//        createCategoryRequest.setDescription("HappyCategories");
//        createCategoryRequest.setUsername("penisup");
//        categoryService.createCategory(createCategoryRequest);
//
//        EditCategoryRequest editCategoryRequest = new EditCategoryRequest();
//        editCategoryRequest.setCategoryId("Okay");
//        editCategoryRequest.setDescription("HappyCategories");
//        editCategoryRequest.setUsername("penisup");
//        assertEquals("Okay", editCategoryRequest.getCategoryId());
//
//    }
//
//    @Test
//    void testThatDeleteCategoryThrowsExceptions_UserIsNotLoggedIn(){
//        RegisterUserRequest registerRequest = new RegisterUserRequest();
//        registerRequest.setFirstname("PenIs");
//        registerRequest.setLastname("Up");
//        registerRequest.setUsername("penisup");
//        registerRequest.setPassword("Holes");
//        userService.register(registerRequest);
//
//        DeleteCategoryRequest deleteCategoryRequest = new DeleteCategoryRequest();
//        deleteCategoryRequest.setDescription(deleteCategoryRequest.getDescription());
//
//        assertThrows(BigContactException.class, () -> categoryService.deleteCategory(deleteCategoryRequest));
//
//    }
//
////    @Test
////    void testAddContactToCategory() {
////        RegisterUserRequest registerRequest = new RegisterUserRequest();
////        registerRequest.setFirstname("PenIs");
////        registerRequest.setLastname("Up");
////        registerRequest.setUsername("penisup");
////        registerRequest.setPassword("Holes");
////        userService.register(registerRequest);
////
////        LoginUserRequest loginRequest = new LoginUserRequest();
////        loginRequest.setUsername("penisup");
////        loginRequest.setPassword("Holes");
////        userService.login(loginRequest);
////
////        Contact contact = new Contact();
////        contactRepository.save(contact);
////
////        // Create a category
////        Category category = new Category();
////        categoryRepository.save(category);
////
////        // Call the method
////        categoryService.addContactToCategory(new AddContactToCategoryRequest(category.getCategoryId(), contact.getContactId()));
////
////        // Verify the contact is added to the category
////        Optional<Category> savedCategory = categoryRepository.findByCategoryId(category.getCategoryId());
////        assertTrue(savedCategory.isPresent());
////        assertTrue(savedCategory.get().getContacts().contains(contact));
////    }
////
////    @Test
////    void testRemoveContactFromCategory() {
////        RegisterUserRequest registerRequest = new RegisterUserRequest();
////        registerRequest.setFirstname("PenIs");
////        registerRequest.setLastname("Up");
////        registerRequest.setUsername("peniscovered");
////        registerRequest.setPassword("Holes");
////        userService.register(registerRequest);
////
////        LoginUserRequest loginRequest = new LoginUserRequest();
////        loginRequest.setUsername("peniscovered");
////        loginRequest.setPassword("Holes");
////        userService.login(loginRequest);
////
////        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
////        createCategoryRequest.setDescription("HappyCategories");
////        createCategoryRequest.setUsername("peniscovered");
////        CreateCategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);
////        Contact createContactRequest = new Contact();
////
////
////        createContactRequest.setFirstName("PenIs");
////        createContactRequest.setUsername("peniscovered");
////        createContactRequest.setPhoneNumber("08165269244");
////        createContactRequest.setLastName("Up");
////        createContactRequest.setEmail("PenIsUp@gmail.com");
////
////        categoryService.addContactToCategory(new AddContactToCategoryRequest(categoryResponse.getCategoryId(), createContactRequest.getContactId()));
////
////        categoryRepository.findByCategoryId(categoryResponse.getCategoryId())
////                .orElseThrow(() -> new BigContactException("Category not found"));
////
////        Contact newContact = contactRepository.findByContactId(createContactRequest.getContactId())
////                .orElseThrow(() -> new BigContactException("New contact not found"));
////
////
////
////        assertTrue(categoryRepository.findByCategoryId(categoryResponse.getCategoryId()).isPresent());
////        categoryService.removeContactFromCategory(new RemoveContactFromCategoryRequest(categoryResponse.getCategoryId(), createContactRequest.getContactId()));
////        Category category = categoryRepository.findByCategoryId(categoryResponse.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
////
////        assertEquals(0,category.getContacts().size());
////        assertFalse(category.getContacts().contains(newContact));
////    }
////
////
////    @Test
////    void testGetContactsByCategoryDescription() {
////        RegisterUserRequest registerRequest = new RegisterUserRequest();
////        registerRequest.setFirstname("PenIs");
////        registerRequest.setLastname("Up");
////        registerRequest.setUsername("penisup");
////        registerRequest.setPassword("Holes");
////        userService.register(registerRequest);
////
////        LoginUserRequest loginRequest = new LoginUserRequest();
////        loginRequest.setUsername("penisup");
////        loginRequest.setPassword("Holes");
////        userService.login(loginRequest);
////
////        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
////        createCategoryRequest.setDescription("HappyCategories");
////        createCategoryRequest.setUsername("penisup");
////        CreateCategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);
////
////        Contact createContactRequest = new Contact();
////        createContactRequest.setFirstName("PenIs");
////        createContactRequest.setUsername("penisup");
////        createContactRequest.setPhoneNumber("08165269244");
////        createContactRequest.setLastName("Up");
////        createContactRequest.setEmail("PenIsUp@gmail.com");
////        contactRepository.save(createContactRequest);
////
////        Contact createContactRequestOne = new Contact();
////        createContactRequestOne.setFirstName("PenIs");
////        createContactRequestOne.setUsername("penisup");
////        createContactRequestOne.setPhoneNumber("08165269245");
////        createContactRequestOne.setLastName("Down");
////        createContactRequestOne.setEmail("PenIsDown@gmail.com");
////        contactRepository.save(createContactRequestOne);
////
////        categoryService.addContactToCategory(new AddContactToCategoryRequest(categoryResponse.getCategoryId(), createContactRequest.getContactId()));
////        categoryService.addContactToCategory(new AddContactToCategoryRequest(categoryResponse.getCategoryId(), createContactRequestOne.getContactId()));
////
////        List<Contact> contacts = categoryService.getContactByCategoryDescription(categoryResponse.getDescription());
////
////        assertEquals(2, contacts.size());
////    }
//
//
//    @Test
//    void testGetAllCategories() {
//        RegisterUserRequest registerRequest = new RegisterUserRequest();
//        registerRequest.setFirstname("PenIs");
//        registerRequest.setLastname("Up");
//        registerRequest.setUsername("penisup");
//        registerRequest.setPassword("Holes");
//        userService.register(registerRequest);
//
//        LoginUserRequest loginRequest = new LoginUserRequest();
//        loginRequest.setUsername("penisup");
//        loginRequest.setPassword("Holes");
//        userService.login(loginRequest);
//
//        CreateCategoryRequest createCategoryRequest1 = new CreateCategoryRequest();
//        createCategoryRequest1.setDescription("Category1");
//        createCategoryRequest1.setUsername("penisup");
//        categoryService.createCategory(createCategoryRequest1);
//
//        CreateCategoryRequest createCategoryRequest2 = new CreateCategoryRequest();
//        createCategoryRequest2.setDescription("Category2");
//        createCategoryRequest2.setUsername("penisup");
//        categoryService.createCategory(createCategoryRequest2);
//
//        assertEquals(2, categoryService.getAllCategories().size());
//    }
//
//}
