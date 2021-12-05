package com.danielturato.review_app.dto

import java.util.*

data class Review(val id: String, val accountId: String, val restaurantId: String,
                  val createdDate: Date, val updateDate: Date, val rating : Int,
                  val description: String)
