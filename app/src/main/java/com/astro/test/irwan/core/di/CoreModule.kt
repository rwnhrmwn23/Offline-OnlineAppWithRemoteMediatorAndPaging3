package com.astro.test.irwan.core.di

import androidx.room.Room
import com.astro.test.irwan.BuildConfig
import com.astro.test.irwan.BuildConfig.BASE_URL
import com.astro.test.irwan.BuildConfig.DB_NAME
import com.astro.test.irwan.core.data.source.local.LocalDataSource
import com.astro.test.irwan.core.data.source.local.room.GithubDatabase
import com.astro.test.irwan.core.data.source.remote.RemoteDataSource
import com.astro.test.irwan.core.data.source.remote.network.ApiService
import com.astro.test.irwan.core.domain.repository.AstroRepository
import com.astro.test.irwan.core.domain.repository.AstroRepositoryImpl
import com.astro.test.irwan.core.domain.usecase.AstroInteractor
import com.astro.test.irwan.core.domain.usecase.AstroUseCase
import com.astro.test.irwan.ui.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<GithubDatabase>().githubDao() }
    factory { get<GithubDatabase>().remoteKeysDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            GithubDatabase::class.java,
            DB_NAME
        ).build()
    }
}

val networkModule = module {
    single {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        client.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", "token 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")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        client.build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val useCaseModule = module {
    factory<AstroUseCase> { AstroInteractor(get()) }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get(), get()) }
    single<AstroRepository> {
        AstroRepositoryImpl(get(), get())
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}