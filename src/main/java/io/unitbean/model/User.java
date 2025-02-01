package io.unitbean.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true)
    @Size(min = 6, max = 20, message = ("Username length must be in between 6 and 20 symbols"))
    private String username;

    @Column(name = "password")
    @Size(min = 8, max = 100, message = ("Password length must be in between 8 and 100 symbols"))
    private String password;

    @OneToMany(mappedBy = "firstUser")
    private Set<Friendship> subscribes;

    @OneToMany(mappedBy = "secondUser")
    private Set<Friendship> subscribers;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "user_id", nullable = false)
    private UserImage userImage;
}
