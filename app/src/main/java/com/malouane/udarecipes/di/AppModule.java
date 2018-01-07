package com.malouane.udarecipes.di;

import android.app.Application;
import com.malouane.udarecipes.BuildConfig;
import com.malouane.udarecipes.data.DataManager;
import com.malouane.udarecipes.data.network.ApiConstants;
import com.malouane.udarecipes.data.network.ApiService;
import com.malouane.udarecipes.data.network.IRecipesHttp;
import com.malouane.udarecipes.data.network.RecipesHttp;
import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class) public class AppModule {

  @Provides @Singleton OkHttpClient provideOkHttpClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    logging.setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);

    return new OkHttpClient.Builder().addInterceptor(chain -> {
      Request request = chain.request();
      Request newReq = request.newBuilder().build();
      return chain.proceed(newReq);
    }).addInterceptor(logging).build();
  }

  @Provides @Singleton ApiService provideRetrofit(OkHttpClient okHttpClient) {
    OkHttpClient.Builder httpClientBuilder = okHttpClient.newBuilder();

    return new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .callFactory(httpClientBuilder.build())
        .client(okHttpClient)
        .build().create(ApiService.class);
  }

  @Provides
  @Singleton DataManager provideDataManager(IRecipesHttp http, Application app) {
    return new DataManager(http, app);
  }

  @Provides @Singleton IRecipesHttp provideHttpHelper(RecipesHttp http) {
    return http;
  }
}