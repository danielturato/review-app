package com.danielturato.reviewapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewAccountDto {
    private String email;

    private String password;

    private String firstName;

    private String lastName;
}
