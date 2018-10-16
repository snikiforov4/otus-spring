package ua.nykyforov.twitter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.time.Instant;

public final class TweetDto {

    @JsonProperty("id")
    private final String id;
    @JsonProperty("text")
    private final String text;
    @JsonProperty("created")
    private final Instant createDate;

    @JsonCreator
    public TweetDto(@JsonProperty("id") String id,
                    @JsonProperty("text") String text,
                    @JsonProperty("created") Instant createDate) {
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("text", text)
                .add("createDate", createDate)
                .toString();
    }
}
