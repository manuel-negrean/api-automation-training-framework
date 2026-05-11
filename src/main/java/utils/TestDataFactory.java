package utils;
import dto.PetDto;

import java.util.Collections;
import java.util.List;

public class TestDataFactory {
    public static PetDto createPet(long id, String petName, PetDto.CategoryDto categoryName, PetDto.TagDto petTag, String petStatus) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(petName);
        petDto.setCategory(categoryName);
        petDto.setTags(Collections.singletonList((petTag)));
        petDto.setStatus(petStatus);
        petDto.setPhotoUrls(Collections.EMPTY_LIST);
        return petDto;
    }
}

