package com.danielturato.review_app.dto

data class Account(val email: String, val firstName: String,
                   val lastName: String, val reviews: Set<Review>)
