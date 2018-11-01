package com.frate.luxup.net

import com.frate.luxup.BuildConfig
import com.frate.luxup.scope.FeatureScope
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

@Module
class NetworkModule {
    @Provides
    @FeatureScope
    fun provideRestClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            gsonConverterFactory: GsonConverterFactory,
            rxJava2CallAdapterFactory: RxJava2CallAdapterFactory) =
            RestClient(RestClientConfig(gsonConverterFactory, rxJava2CallAdapterFactory).apply {
                addInterceptor(httpLoggingInterceptor)
            })

    @Provides
    @FeatureScope
    fun provideGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls()
            .create()

    @Provides
    @FeatureScope
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d("%s", message) })
        .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC }

    @Provides
    @FeatureScope
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @FeatureScope
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
}
