package pl.michalboryczko.exercise.source.api.rest;

import java.io.IOException;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

@Singleton
public class HeaderInterceptor implements Interceptor {

    public static final String HEADER_AUTHORIZATION = "X-Auth-Token";
    public static final String HEADER_CLIENT_ID_KEY = "Client-Id";
    public static final String HEADER_CLIENT_ID_VALUE = "android";
    public static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_VALUE = "application/json; charset=utf-8";

    String apiToken = "c0f10b08e0d54a65bb56487865320444";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE);
        builder.addHeader(HEADER_CLIENT_ID_KEY, HEADER_CLIENT_ID_VALUE);

        if (apiToken != null) {
            Timber.d("networkclient API-TOKEN: %s", apiToken);
            builder.addHeader(HEADER_AUTHORIZATION, apiToken);
        }

        Response response = chain.proceed(builder.build());
        return response;
    }
}
