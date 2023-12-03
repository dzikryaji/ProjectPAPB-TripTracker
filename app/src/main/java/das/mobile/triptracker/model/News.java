package das.mobile.triptracker.model;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;


    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

}
