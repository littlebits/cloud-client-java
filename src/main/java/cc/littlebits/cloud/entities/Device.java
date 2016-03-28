package cc.littlebits.cloud.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This class represents a cloudBit device.
 * <p>
 * JSON example representation:
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

    private String id;
    private String label;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("is_connected")
    private boolean isConnected;

    @JsonProperty("input_interval_ms")
    private long inputIntervalMs;

    private List<Subscription> subscriptions;

    private List<Subscription> subscribers;

    /**
     * @return the cloudBit device identifier.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the cloudBit device label.
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the cloudBit owner user identifier.
     */
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the cloudBit current connection status.
     */
    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * @return the cloudBit current input interval (the interval at which the cloudBit reads and sends amplitude to the Cloud Platform).
     * This value is in milliseconds.
     */
    public long getInputIntervalMs() {
        return inputIntervalMs;
    }

    public void setInputIntervalMs(long inputIntervalMs) {
        this.inputIntervalMs = inputIntervalMs;
    }

    /**
     * @return amplitude sources this cloudBit device is observing.
     */
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /**
     * @return observers of this cloudBit device amplitude.
     */
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
