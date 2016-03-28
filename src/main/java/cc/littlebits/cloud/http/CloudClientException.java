package cc.littlebits.cloud.http;

/**
 * Placeholder for errors with Cloud Platform requests.
 */
public class CloudClientException extends Exception {

    public CloudClientException() {
    }

    public CloudClientException(String message) {
        super(message);
    }

    public CloudClientException(String message, Throwable cause) {
        super(message, cause);
    }

}
