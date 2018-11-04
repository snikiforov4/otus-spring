package ua.nykyforov.twitter.domain;

import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ua.nykyforov.twitter.dto.TweetDto;

import java.time.Instant;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@SuppressWarnings("WeakerAccess")
@Document(collection = "tweets")
public class Tweet {

    @Id
    private String id;

    @Field("user")
    private String userId;

    @Field("text")
    private String text;

    @Field("create")
    private Instant createDate;

    public Tweet() {
    }

    public Tweet(String id, String userId, String text) {
        this(userId, text);
        setId(id);
    }

    public Tweet(String userId, String text) {
        setUserId(userId);
        setText(text);
        this.createDate = Instant.now();
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
        checkArgument(isNotBlank(userId), "userId is blank");
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        checkArgument(isNotBlank(text), "text is blank");
        this.text = text;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public static Tweet fromDto(TweetDto dto) {
        Tweet tweet = new Tweet();
        tweet.setId(dto.getId());
        tweet.setText(dto.getText());
        tweet.setCreateDate(dto.getCreateDate());
        return tweet;
    }

    public TweetDto toDto() {
        return new TweetDto(id, text, createDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("text", text)
                .add("createDate", createDate)
                .add("userId", userId)
                .toString();
    }
}
