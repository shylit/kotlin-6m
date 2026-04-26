//----
package com.example.nobelapp.domain.usecase

import com.example.nobelapp.domain.repository.NobelRepository

class LoginUseCase(
    private val repository: NobelRepository
) {
    suspend operator fun invoke(username: String, password: String): String {
        return repository.login(username, password)
    }
}
