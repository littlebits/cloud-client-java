package cc.littlebits.cloud.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Expected JSON output:
 * <pre>
 * {
 *   "label":"foobar",
 *   "id":"00e04c223a74",
 *   "user_id":73105,
 *   "is_connected":false,
 *   "ap":null,
 *   "subscriptions":[],
 *   "subscribers":[],
 *   "input_interval_ms":750
 * }
 * </pre>
 */
public class Device {

    private String label;
    private String id;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("is_connected")
    private boolean isConnected;
    @JsonProperty("input_interval_ms")
    private long inputIntervalMs;
    private List<Subscription> subscriptions;
    private List<Subscription> subscribers;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public long getInputIntervalMs() {
        return inputIntervalMs;
    }

    public void setInputIntervalMs(long inputIntervalMs) {
        this.inputIntervalMs = inputIntervalMs;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Subscription> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscription> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public String toString() {
        return "Device{" +
                "label='" + label + '\'' +
                ", id='" + id + '\'' +
                ", userId=" + userId +
                ", isConnected=" + isConnected +
                ", inputIntervalMs=" + inputIntervalMs +
                ", subscriptions=" + subscriptions +
                ", subscribers=" + subscribers +
                '}';
    }
}
