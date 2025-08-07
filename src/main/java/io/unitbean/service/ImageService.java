package io.unitbean.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String upload(MultipartFile image, Integer userId);

    byte[] getUserImage(String fileName);
}
