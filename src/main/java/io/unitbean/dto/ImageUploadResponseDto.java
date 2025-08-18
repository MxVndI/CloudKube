package io.unitbean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadResponseDto {
    private String message;
    private Integer userId;
    private String imageUrl;
}
