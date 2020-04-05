package ru.natiel.week1homework.api;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.*;
import ru.natiel.week1homework.models.AuthResponse;
import ru.natiel.week1homework.models.ItemRemote;

import java.util.List;

public interface Api {
	@GET("./items")
	public Single<List<ItemRemote>> request(@Query("type") String type, @Query("auth-token") String authToken);

	@POST("./items/add")
	@FormUrlEncoded
	public Completable request(@Field("type") String type, @Field("name") String name,
							   @Field("price") Integer price, @Field("auth-token") String authToken);

	@GET("./items/{id}")
	public Completable request(@Path("id") Integer id);


	@GET("./auth")
	public Single<AuthResponse> request(@Query("social_user_id") String userId);

}