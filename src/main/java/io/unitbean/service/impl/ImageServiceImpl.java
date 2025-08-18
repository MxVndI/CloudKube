package io.unitbean.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.unitbean.exception.ImageUploadException;
import io.unitbean.model.User;
import io.unitbean.model.UserImage;
import io.unitbean.repository.UserImageRepository;
import io.unitbean.service.ImageService;
import io.unitbean.service.UserService;
import io.unitbean.service.props.MinioProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final UserService userService;
    private final UserImageRepository userImageRepository;

    @Override
    public String upload(MultipartFile file, Integer userId) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Файл должен иметь название");
        }
        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        saveImage(inputStream, fileName);
        System.out.println(fileName + " " + userId);
        setUserImage(fileName, userId);
        return fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    public void setUserImage(String fileName, Integer userId) {
        UserImage userImage = new UserImage();
        userImage.setUserId(userId);
        userImage.setUserImageName(fileName);
        User user = userService.getUserById(userId);
        userImage.setUser(user);
        if (Objects.nonNull(user)) {
            userImageRepository.deleteImageByUserId(userId);
        }
        userImageRepository.save(userImage);
    }

    private String generateFileName(final MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(final MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(final InputStream inputStream, final String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                   .stream(inputStream, inputStream.available(), -1)
                   .bucket(minioProperties.getBucket())
                   .object(fileName)
                   .build());
    }

    public byte[] getUserImage(String fileName) {
        System.out.println(fileName + " FILENAME EEEEE");
        String desiredKey = (fileName == null || fileName.isEmpty()) ? "defaultimage.png" : fileName;
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                                 .bucket(minioProperties.getBucket())
                                 .object(desiredKey)
                                 .build());
            return stream.readAllBytes();
        } catch (Exception primaryError) {
            // Fallback to default image if requested key does not exist
            try {
                InputStream fallbackStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                     .bucket(minioProperties.getBucket())
                                     .object("defaultimage.png")
                                     .build());
                return fallbackStream.readAllBytes();
            } catch (Exception fallbackError) {
                throw new ImageUploadException("Failed to get user image: " + primaryError.getMessage());
            }
        }
    }
}
