package com.project.java.utils;


import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.java.model.Momo.HttpRequest;
import com.project.java.model.Momo.HttpResponse;

public class Execute {

	private static final Logger log = LoggerFactory.getLogger(Execute.class);

	
    OkHttpClient client = new OkHttpClient();

    public HttpResponse sendToMoMo(String endpoint, String payload) {

        try {

            HttpRequest httpRequest = new HttpRequest("POST", endpoint, payload, "application/json");

            Request request = createRequest(httpRequest);

            log.debug("[HttpPostToMoMo] Endpoint:: " + httpRequest.getEndpoint() + ", RequestBody:: " + httpRequest.getPayload());

            Response result = client.newCall(request).execute();
            HttpResponse response = new HttpResponse(result.code(), result.body().string(), result.headers());

            log.info("[HttpResponseFromMoMo] " + response.toString());

            return response;
        } catch (Exception e) {
        	log.error("[ExecuteSendToMoMo] "+ e);
        }

        return null;
    }

    public static Request createRequest(HttpRequest request) {
        RequestBody body = RequestBody.create(MediaType.get(request.getContentType()), request.getPayload());
        return new Request.Builder()
                .method(request.getMethod(), body)
                .url(request.getEndpoint())
                .build();
    }

    public String getBodyAsString(Request request) throws IOException {
        Buffer buffer = new Buffer();
        RequestBody body = request.body();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }
}
