package das.mobile.triptracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("articles")
    private List<News> articles;

    public List<News> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }
}
