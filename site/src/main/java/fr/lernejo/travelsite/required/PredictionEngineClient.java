package fr.lernejo.travelsite.required;

import fr.lernejo.travelsite.controllers.dto.PredictionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PredictionEngineClient {

    //    http://localhost:7080/api/temperature?country=France
    @GET("api/temperature")
    Call<PredictionResponse> prediction(@Query("country") String country);
}
