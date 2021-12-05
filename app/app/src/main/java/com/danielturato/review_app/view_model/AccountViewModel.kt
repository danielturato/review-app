package com.danielturato.review_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.danielturato.review_app.dto.Account
import java.lang.IllegalArgumentException

class AccountViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val accountId : String = savedStateHandle["accountId"] ?:
                throw IllegalArgumentException("An account ID is required for this view model")
    val account : LiveData<Account> = TODO()
}