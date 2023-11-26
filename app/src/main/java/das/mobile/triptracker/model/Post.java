package das.mobile.triptracker.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private String id;
    private String text;
    private String imageUrl;
    private String date;

    public Post(String id, String text, String imageUrl) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        this.date = dateFormat.format(today);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
