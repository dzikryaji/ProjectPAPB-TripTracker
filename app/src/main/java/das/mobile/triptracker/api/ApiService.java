package das.mobile.triptracker.api;

import das.mobile.triptracker.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("https://newsapi.org/v2/everything?q=tesla&from=2023-11-01&sortBy=publishedAt&apiKey=47b5472777b2431db732548c3373b501")
    Call<NewsResponse> getNews();
}
