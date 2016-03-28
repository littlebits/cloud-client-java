package cc.littlebits.cloud.http;

import cc.littlebits.cloud.Constants;
import cc.littlebits.cloud.entities.Device;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * littleBits Cloud Platform HTTP API client.
 */
public class CloudHttpClient {

    private final CloudService service;

    // for more information on tokens, check http://tools.ietf.org/html/rfc6750#section-2.1
    private final String accessToken;

    public CloudHttpClient(final String accessToken) {
        // add "Bearer " if not present
        this.accessToken = accessToken.startsWith("Bearer ") ? accessToken : "Bearer ".concat(accessToken);

        this.service = new Retrofit.Builder()
                .baseUrl(Constants.SERVICE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(CloudService.class);
    }

    public CloudHttpClient(final String url, final String accessToken) {
        // add "Bearer " if not present
        this.accessToken = accessToken.startsWith("Bearer ") ? accessToken : "Bearer ".concat(accessToken);

        final String serviceUrl = url == null || url.isEmpty() ? Constants.SERVICE_URL : url;
        this.service = new Retrofit.Builder()
                .baseUrl(serviceUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(CloudService.class);
    }

    /**
     * Get all devices owned by the currently identified user.
     *
     * @return all devices owned by the currently identified user.
     */
    List<Device> getDevices() throws CloudClientException {
        final Response<List<Device>> response;
        try {
            response = service.getDevices(accessToken).execute();
        } catch (IOException e) {
            throw new CloudClientException("There was an error while retrieving all devices owned by currently identified user.", e);
        }

        if (!response.isSuccessful()) {
            throw new CloudClientException("There was an error while retrieving all devices owned by currently identified user. Bad status: "
                    + response.code()
                    + ", Error message:"
                    + response.errorBody());
        }

        return response.body();
    }

}
