package com.nishant.comicslibrary

import com.nishant.comicslibrary.model.api.ApiService
import com.nishant.comicslibrary.model.api.MarvelApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApi() = MarvelApiRepo(ApiService.api)
}