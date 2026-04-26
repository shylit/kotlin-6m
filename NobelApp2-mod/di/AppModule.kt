//---
package com.example.nobelapp.di

import com.example.nobelapp.data.remote.KtorClientFactory
import com.example.nobelapp.data.remote.NobelApi
import com.example.nobelapp.data.repository.NobelRepositoryImpl
import com.example.nobelapp.domain.repository.NobelRepository
import com.example.nobelapp.domain.usecase.AddFavoritePrizeUseCase
import com.example.nobelapp.domain.usecase.DeleteFavoritePrizeUseCase
import com.example.nobelapp.domain.usecase.GetFavoritePrizesUseCase
import com.example.nobelapp.domain.usecase.GetPrizesUseCase
import com.example.nobelapp.domain.usecase.LoginUseCase

object AppModule {

    private val client = KtorClientFactory.create()

    private val api = NobelApi(client)

    private val repository: NobelRepository = NobelRepositoryImpl(api)

    val loginUseCase = LoginUseCase(repository)

    val getPrizesUseCase = GetPrizesUseCase(repository)

    val getFavoritePrizesUseCase = GetFavoritePrizesUseCase(repository)

    val addFavoritePrizeUseCase = AddFavoritePrizeUseCase(repository)

    val deleteFavoritePrizeUseCase = DeleteFavoritePrizeUseCase(repository)
}
