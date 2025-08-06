package io.unitbean.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
@Getter
@Setter
public class Post {
    private Integer userId;
    private String content;
}
