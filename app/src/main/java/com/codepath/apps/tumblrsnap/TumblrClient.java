package com.codepath.apps.tumblrsnap;

import android.content.Context;
import android.graphics.Bitmap;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TumblrApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TumblrClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TumblrApi.class;
    public static final String REST_URL = "http://api.tumblr.com/v2";
    public static final String REST_CONSUMER_KEY = "BcnUeYPIxBaVCz5sYcs4SkytRqM8azgLclb1PUpeFcknic9RYY";
    public static final String REST_CONSUMER_SECRET = "FoiHHbknPRFeyiaBpOxizSGzbYsflp6DiFfwBi85kCYEznKGGh";
    public static final String REST_CALLBACK_URL = "oauth://tumblrsnap";

    public TumblrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        client.get(getApiUrl("user/info"), null, handler);
    }

    public void getTaggedPhotos(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tag", "cptumblrsnap");
        params.put("limit", "20");
        params.put("api_key", REST_CONSUMER_KEY);
        client.get(getApiUrl("tagged"), params, handler);
    }

    public void getUserPhotos(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", "photo");
        params.put("limit", "20");
        params.put("api_key", REST_CONSUMER_KEY);
        client.get(getApiUrl("user/dashboard"), params, handler);
    }

    public void createPhotoPost(String blog, final byte[] bytes, final AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", "photo");
        params.put("tags", "cptumblrsnap");
        params.put("data", new ByteArrayInputStream(bytes), "image.png");

        client.post(getApiUrl(String.format("blog/%s/post?type=photo&tags=cptumblrsnap", blog)), params, handler);
    }
}
