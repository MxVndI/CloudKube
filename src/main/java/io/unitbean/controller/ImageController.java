package io.unitbean.controller;

import io.unitbean.exception.ImageUploadException;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.service.ImageService;
import io.unitbean.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    @PostMapping("/image")
    public String setUserImage(@RequestParam("file") MultipartFile file,
                               Principal principal,
                               Model model) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
            Integer userId = userDetails.getId();
            imageService.upload(file, userId);
            return "redirect:/users/" + userId;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to upload image: " + e.getMessage());
            return "user/profile";
        }
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            byte[] imageBytes = imageService.getUserImage(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (ImageUploadException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
