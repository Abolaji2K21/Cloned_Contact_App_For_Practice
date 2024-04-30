//package africa.semicolon.controller;
//
//import africa.semicolon.data.models.Category;
//import africa.semicolon.data.models.Contact;
//import africa.semicolon.dtos.requests.*;
//import africa.semicolon.dtos.response.ApiResponse;
//import africa.semicolon.dtos.response.CreateCategoryResponse;
//import africa.semicolon.dtos.response.DeleteCategoryResponse;
//import africa.semicolon.dtos.response.EditCategoryResponse;
//import africa.semicolon.services.CategoryService;
//import africa.semicolon.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//import static org.springframework.http.HttpStatus.CREATED;
//
//@RestController
//@RequestMapping("/api/Modern_Contact")
//public class CategoryController {
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/create_category")
//    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
//        try {
//            CreateCategoryResponse result = categoryService.createCategory(createCategoryRequest);
//            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//        }
//    }
//
//    @PatchMapping("/edit_category")
//    public ResponseEntity<?> editCategory(@RequestBody EditCategoryRequest editCategoryRequest) {
//        try {
//            EditCategoryResponse result = categoryService.editCategory(editCategoryRequest);
//            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/delete_category")
//    public ResponseEntity<?> deleteCategory(@RequestBody DeleteCategoryRequest deleteCategoryRequest) {
//        try {
//            DeleteCategoryResponse result = categoryService.deleteCategory(deleteCategoryRequest);
//            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/categories")
//    public ResponseEntity<?> getAllCategories() {
//        try {
//            List<Category> categories = categoryService.getAllCategories();
//            return new ResponseEntity<>(new ApiResponse(true, categories), CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/categories/{description}/contact")
//    public ResponseEntity<?> getContactByCategoryDescription(@PathVariable String description) {
//        try {
//            List<Contact> contacts = categoryService.getContactByCategoryDescription(description);
//            return new ResponseEntity<>(new ApiResponse(true, contacts), CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//        }
//    }
//
//    @PatchMapping("/add_Contact_to_category")
//    public ResponseEntity<?> addContactToCategory(@RequestBody AddContactToCategoryRequest request) {
//        Contact contact = new Contact();
//        contact.setUsername(request.getUsername());
//        try {
//            categoryService.addContactToCategory(request, request.getContact());
//            return new ResponseEntity<>(new ApiResponse(true, "Note added to category successfully"), CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//        }
//    }
//
//    @PatchMapping("/remove_Contact_from_category")
//    public ResponseEntity<?> removeContactFromCategory(@RequestBody RemoveContactFromCategoryRequest request) {
//        try {
//            categoryService.removeContactFromCategory(request.getUsername(), request.getDescription());
//            return new ResponseEntity<>(new ApiResponse(true, "Note removed from category successfully"), CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//        }
//    }
//}
