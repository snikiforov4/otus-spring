package ua.nykyforov.twitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class TweetDto {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("text")
    private final String text;

    @JsonProperty("created")
    private final Instant createDate;

    public TweetDto(String id, String text, Instant createDate) {
        this.id = id;
        this.text = text;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Instant getCreateDate() {
        return createDate;
    }
}
