package cc.littlebits.cloud.http;

import cc.littlebits.cloud.entities.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import wiremock.org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Here will be tests for both internal {@link CloudService} and public {@link CloudHttpClient} classes.
 */
public class CloudHttpClientTest {

    // instrumentation
    private WireMockServer wireMockServer;
    private final ObjectMapper mapper = new ObjectMapper();

    // library internal client
    private CloudService service;

    // library exposed client
    private CloudHttpClient client;

    @BeforeSuite
    void setupSuite() {
        // server
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().bindAddress("localhost").port(9000));
            wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/access_tokens/VALID"))
                    .willReturn(WireMock.aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                            .withBody("{\"user_id\":1,\"scopes\":\"write read admin\",\"expires_in\":null,\"created_at\":1438697199,\"app_label\":\"cloud-client\"}")));
        }

        if (!wireMockServer.isRunning()) {
            wireMockServer.start();
        }

        // internal client
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:9000/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(CloudService.class);

        // client
        client = new CloudHttpClient("http://localhost:9000/", "VALID");
    }

    @Test
    void test_get_all_devices() throws IOException, CloudClientException {
        // prepare mocked response
        final List<String> deviceIds = Arrays.asList("deadbeef", "deadbeef-1");
        final List<Device> devices = new ArrayList<>();
        for (String deviceId : deviceIds) {
            final Device device = new Device();
            device.setId(deviceId);
            device.setUserId(1L);
            device.setConnected(false);
            device.setLabel(deviceId);
            device.setInputIntervalMs(750L);

            devices.add(device);
        }

        final String mockedResponse = mapper.writeValueAsString(devices);
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlPathMatching("/v2/devices"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(HttpStatus.SC_OK)
                                        .withBody(mockedResponse))
        );

        // execute internal call
        final Response<List<Device>> internalResponse = service.getDevices("Bearer VALID").execute();
        // assert call to mocked server
        wireMockServer.verify(
                WireMock.getRequestedFor(WireMock.urlPathMatching("/v2/devices"))
        );

        // assert internal response
        assertEquals(internalResponse.code(), HttpStatus.SC_OK);
        assertEquals(internalResponse.body().size(), devices.size());

        // execute call
        final List<Device> response = client.getDevices();
        // assert another call to mocked server
        wireMockServer.verify(
                WireMock.getRequestedFor(WireMock.urlPathMatching("/v2/devices"))
        );

        // assert response
        assertEquals(response.size(), devices.size());
    }

    @Test
    void test_get_all_devices_empty() throws IOException, CloudClientException {
        final List<String> deviceIds = Arrays.asList("deadbeef", "deadbeef-1");
        final List<Device> devices = new ArrayList<>();
        for (String deviceId : deviceIds) {
            final Device device = new Device();
            device.setId(deviceId);
            device.setUserId(1L);
            device.setConnected(false);
            device.setLabel(deviceId);
            device.setInputIntervalMs(750L);

            devices.add(device);
        }

        // mock empty response
        final String mockedResponse = mapper.writeValueAsString(new ArrayList<>());
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlPathMatching("/v2/devices"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(HttpStatus.SC_OK)
                                        .withBody(mockedResponse))
        );

        // execute internal call
        final Response<List<Device>> internalResponse = service.getDevices("Bearer INVALID").execute();
        // assert call to mocked server
        wireMockServer.verify(
                WireMock.getRequestedFor(WireMock.urlPathMatching("/v2/devices"))
        );

        // assert internal response
        assertEquals(internalResponse.code(), HttpStatus.SC_OK);
        assertNotEquals(internalResponse.body().size(), devices.size());

        // client with different credentials
        final CloudHttpClient clientWhoOwnsNoDevices = new CloudHttpClient("http://localhost:9000/", "INVALID");
        // execute call
        final List<Device> response = clientWhoOwnsNoDevices.getDevices();
        // assert another call to mocked server
        wireMockServer.verify(
                WireMock.getRequestedFor(WireMock.urlPathMatching("/v2/devices"))
        );

        // assert response
        assertEquals(response.size(), 0);
    }

    @Test(expectedExceptions = CloudClientException.class)
    void test_get_all_devices_no_server() throws CloudClientException {
        // client with invalid URL to simulate server down
        final CloudHttpClient clientWithInvalidUrl = new CloudHttpClient("http://localhost:9123/", "VALID");
        // execute call, must throw exception
        clientWithInvalidUrl.getDevices();
    }

}
