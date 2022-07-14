package com.example.ridecellexample.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.ridecellexample.data.ApiService
import com.example.ridecellexample.data.db.AppDatabase
import com.example.ridecellexample.data.db.CharacterDao
import com.example.ridecellexample.data.CharacterListRepository
import com.example.ridecellexample.data.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)

@Module
object AppModule {

    @Provides
    fun providesBaseUrl(): String {
        return "https://rickandmortyapi.com"
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun providesRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    fun providesRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "ride_well_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.characterDao()
    }

    @Provides
    fun providesNetwork(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Provides
    fun providesCharacterListRepository(
        networkHelper: NetworkHelper,
        apiService: ApiService,
        characterDao: CharacterDao
    ): CharacterListRepository {
        return CharacterListRepository(networkHelper,apiService, characterDao)
    }
}
