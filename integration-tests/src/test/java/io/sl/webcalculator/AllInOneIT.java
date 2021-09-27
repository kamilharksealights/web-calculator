package io.sl.webcalculator;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AllInOneIT {

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    @Test
    public void shouldRunTwoApps() throws Exception {
        AddingApplication addingApplication = new AddingApplication(8881);
        addingApplication.start();

        SubtractingApplication subtractingApplication = new SubtractingApplication(8882);
        subtractingApplication.start();

        Request addingRequest = new Request.Builder()
                .url(String.format("http://localhost:%d/add?a=%d&b=%d", 8881, 2, 4))
                .build();

        Request subtractingRequest = new Request.Builder()
                .url(String.format("http://localhost:%d/subtract?a=%d&b=%d", 8882, 2, 4))
                .build();

        Call addingCall = client.newCall(addingRequest);
        Call subtractingCall = client.newCall(subtractingRequest);

        assertEquals(6, extractIntResult(addingCall.execute()));
        assertEquals(-2, extractIntResult(subtractingCall.execute()));

        subtractingApplication.stop();
        addingApplication.stop();
    }

    private int extractIntResult(Response response) throws IOException {
        return Integer.parseInt(response.body().string());
    }
}