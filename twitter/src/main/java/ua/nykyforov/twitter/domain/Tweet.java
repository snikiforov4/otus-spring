package ua.nykyforov.twitter.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

import static com.google.common.base.Preconditions.checkArgument;

@Document(collection = "tweets")
public class Tweet {

    @Id
    private String id;

    @Field("text")
    private String text;

    @Field("create")
    private Instant createDate;

    public Tweet(String text) {
        checkArgument(StringUtils.isNotBlank(text), "text is blank");
        this.text = text;
        this.createDate = Instant.now();
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
