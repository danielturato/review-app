package com.danielturato.reviewapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {

    @Id
    private String id;

    @Field("account_id")
    @JsonProperty("account_id")
    private String accountId;

    @Field("restaurant_id")
    @JsonProperty("restaurant_id")
    private String restaurantId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Field("created_date")
    @JsonProperty("created_date")
    private Date createdDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Field("update_date")
    @JsonProperty("update_date")
    private Date updateDate;

    private int rating;

    private String description;

}
