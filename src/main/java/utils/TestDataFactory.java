package utils;

import dto.PetDto;

import java.util.Collections;

import java.util.List;

public class TestDataFactory {

    public static PetDto createAndUpdatePetBody(long id, String petName, PetDto.CategoryDto categoryName, PetDto.TagDto petTag, String petStatus) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(petName);
        petDto.setCategory(categoryName);
        petDto.setTags(Collections.singletonList(petTag));
        petDto.setStatus(petStatus);
        petDto.setPhotoUrls(Collections.emptyList());
        return petDto;
    }

//        Since POST and PUT requests are using the same body, I have changed
//        the createPet method to a general createAndUpdatePetBody method that can be used for both creating and updating a pet.
}
