package br.com.catossi.bebedor_da_rodada.service;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.model.DrinkResponse;
import br.com.catossi.bebedor_da_rodada.model.RoundResponse;
import br.com.catossi.bebedor_da_rodada.model.User;
import br.com.catossi.bebedor_da_rodada.model.UserInsert;
import br.com.catossi.bebedor_da_rodada.model.UserRequest;
import br.com.catossi.bebedor_da_rodada.model.UserResponse;
import br.com.catossi.bebedor_da_rodada.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface APIInterface {

    @GET(Constants.URL_USUARIO)
    Call<UserRequest> getUser(@Path("email") String email);

    @GET(Constants.URL_BEBIDA)
    Call<DrinkResponse> getDrinks();

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(Constants.URL_USUARIO_INSERT)
    Call<UserResponse> insertUser(@Field("nome") String nome, @Field("email") String email, @Field("apelido") String apelido);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(Constants.URL_RODADA)
    Call<RoundResponse> insertRound(@Field("descricao") String descricao, @Field("titulo") String titulo, @Field("bebidas") List<Integer> bebidas, @Field("idUsuarioCriador") String idUsuarioCriador);


}
