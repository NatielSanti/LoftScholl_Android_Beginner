package ru.natiel.week1homework.api;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.*;
import ru.natiel.week1homework.models.AuthResponse;
import ru.natiel.week1homework.models.ItemRemote;

import java.util.List;

public interface Api {
	@GET("./items")
	Single<List<ItemRemote>> request(@Query("type") String type, @Query("auth-token") String authToken);

	@POST("./items/add")
	@FormUrlEncoded
	Completable request(@Field("type") String type, @Field("name") String name,
							   @Field("price") Integer price, @Field("auth-token") String authToken);

	@DELETE("./items/{id}")
	Completable remove(@Path("id") Integer id, @Query("auth-token") String authToken);

	@GET("./auth")
	Single<AuthResponse> request(@Query("social_user_id") String userId);

}