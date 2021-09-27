package io.sl.webcalculator;

import okhttp3.*;
import org.junit.Assert;
import org.junit.Test;


public class AddingApplicationIT {

    @Test
    public void addTest() throws Exception {
        int port = 8881;
        AddingApplication addingApplication = new AddingApplication(port);
        addingApplication.start();

        OkHttpClient client = new OkHttpClient.Builder().build();

        String url = String.format("http://localhost:%d/add?a=%d&b=%d", port, 2, 4);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        int result = Integer.parseInt(body.string());

        Assert.assertEquals(6, result);

        addingApplication.stop();
    }

}