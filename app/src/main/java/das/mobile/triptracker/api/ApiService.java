package das.mobile.triptracker.api;

import das.mobile.triptracker.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Call<NewsResponse> getAllNews(@Url String url);
}
