package io.unitbean.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp = Instant.now();

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}