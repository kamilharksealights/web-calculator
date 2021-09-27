package io.sl.webcalculator;

import okhttp3.*;
import org.junit.Assert;
import org.junit.Test;

public class SubtractingApplicationIT {


    @Test
    public void subtractTest() throws Exception {
        int port = 8881;
        SubtractingApplication application = new SubtractingApplication(port);
        application.start();


        OkHttpClient client = new OkHttpClient.Builder().build();

        String url = String.format("http://localhost:%d/subtract?a=%d&b=%d", port, 2, 4);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        int result = Integer.parseInt(body.string());

        Assert.assertEquals(-2, result);

        application.stop();
    }

}