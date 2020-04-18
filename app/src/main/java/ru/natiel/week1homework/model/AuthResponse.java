package ru.natiel.week1homework.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    public static String AUTH_TOKEN_KEY = "Access-Token";

    private String status;
    private String id;
    private @SerializedName("auth_token") String authToken;
}
