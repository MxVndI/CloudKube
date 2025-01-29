package io.unitbean.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
@Getter
@Setter
public class Post {
    @Id
    private Long id;
    private Integer userId;
    private String content;
}
