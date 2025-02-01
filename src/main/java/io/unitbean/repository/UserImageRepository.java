package io.unitbean.repository;

import io.unitbean.model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
    UserImage findByUserImageName(String userImageName);

    @Modifying
    @Query("UPDATE UserImage u SET u.userImageName = :userImageName WHERE u.userId = :userId")
    void updateImageNameByUserId(@Param("userId") Integer userId, @Param("userImageName") String userImageName);

    UserImage findByUserId(Integer userId);

    @Modifying
    @Query("DELETE FROM UserImage u WHERE u.userId = :userId")
    void deleteImageByUserId(@Param("userId") Integer userId);

}
