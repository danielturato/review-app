package com.danielturato.reviewapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document("accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {

    @Id
    private String id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private List<Review> reviews;
}
