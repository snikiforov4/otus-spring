package ua.nykyforov.twitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class TweetDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("created")
    private Instant createDate;

    public TweetDto() {
    }

    public TweetDto(String id, String text, Instant createDate) {
        this.id = id;
        this.text = text;
        this.createDate = createDate;
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

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }
}
