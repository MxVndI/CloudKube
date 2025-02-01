package io.unitbean.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserMainImage {
    private MultipartFile file;
}
