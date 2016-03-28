package cc.littlebits.cloud.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subscription {

    @JsonProperty("publisher_id")
    protected String publisherId;

    @JsonProperty("subscriber_id")
    protected String subscriberId;

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "SubscriptionPayload{" +
                "publisherId='" + publisherId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
