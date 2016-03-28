package cc.littlebits.cloud.http;

import cc.littlebits.cloud.entities.Device;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

interface CloudService {

    /**
     * Get all devices owned by a certain user.
     *
     * @param token the access-token identifying the user.
     * @return all devices owned by the identified user.
     */
    @GET("v2/devices")
    Call<List<Device>> getDevices(@Header("Authorization") final String token);

}
