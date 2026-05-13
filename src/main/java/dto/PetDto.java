package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO means Data Transfer Object.
 * We use a DTO to represent request and response JSON as a Java object.
 * This keeps tests cleaner than writing raw JSON strings in every test.
 *
 * Updated to match full Swagger Pet Store schema with nested objects and arrays.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDto {
    private long id;
    private CategoryDto category;
    private String name;
    private List<String> photoUrls;
    private List<TagDto> tags;
    private String status;

    /**
     * Nested DTO for category.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryDto {
        private long id;
        private String name;
    }

    /**
     * Nested DTO for tags.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TagDto {
        private long id;
        private String name;
    }
}