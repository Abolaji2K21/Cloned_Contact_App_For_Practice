//package africa.semicolon.services;
//
//
//import africa.semicolon.data.models.Category;
//import africa.semicolon.data.models.Contact;
//import africa.semicolon.dtos.requests.*;
//import africa.semicolon.dtos.response.CreateCategoryResponse;
//import africa.semicolon.dtos.response.DeleteCategoryResponse;
//import africa.semicolon.dtos.response.EditCategoryResponse;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public interface CategoryService {
//    CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);
//
//    EditCategoryResponse editCategory(EditCategoryRequest editCategoryRequest);
//
//    DeleteCategoryResponse deleteCategory(DeleteCategoryRequest deleteCategoryRequest);
//
//    List<Category> getAllCategories();
//
//    List<Contact> getContactByCategoryDescription(String description);
//
//    void addContactToCategory(AddContactToCategoryRequest request);
//
//    void removeContactFromCategory(RemoveContactFromCategoryRequest request);
//}
