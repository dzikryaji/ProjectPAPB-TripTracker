package das.mobile.triptracker.model;

import java.util.List;

public class Post {
    private String id;
    private String userId;
    private String text;
    private String date;
    private List<String> imageUrl;

    public Post(String id, String userId, String text, String date, List<String> imageUrl) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
