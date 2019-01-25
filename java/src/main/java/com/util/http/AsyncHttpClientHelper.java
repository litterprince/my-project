package main.java.com.util.http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author feic1@jumei.com
 * @ClassName: AsyncHttpClientHelper
 * @Description: http client 工具类
 * @date 2017年5月9日 上午11:40:29
 */
public class AsyncHttpClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(AsyncHttpClientHelper.class);

    private static final long MAX_TIME_OUT = 30;

    private static AsyncHttpClient asyncHttpClient;

    static {
        init();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() { //关闭
                try {
                    AsyncHttpClientHelper.destroy();
                } catch (Throwable ignore) {
                }
            }
        });
    }

    /**
     * @param url url地址
     * @return response string or null
     */
    public static String fetchHttpGet(String url) {
        return fetchHttpGet(url, MAX_TIME_OUT);
    }

    /**
     * @param url              url地址
     * @param timeoutInSeconds 超时时间，second
     * @return response string or null
     */
    public static String fetchHttpGet(String url, long timeoutInSeconds) {
        try {
            Future<Response> f = asyncHttpClient.prepareGet(url).execute();
            Response r = f.get(timeoutInSeconds, TimeUnit.SECONDS);
            return r.getResponseBody();
        } catch (NullPointerException e) {
            logger.error("AsyncHttpClientHelper.fetchHttpGet meet NullPointerException", e);
            init();
            return null;
        } catch (Throwable e) {
            logger.error("AsyncHttpClientHelper.fetchHttpGet meet error.", e);
            return null;
        }
    }

    public static String fetchHttpPost(String url, Map<String, String> params) {
        return fetchHttpPost(url, params, MAX_TIME_OUT);
    }

    public static String fetchHttpPost(String url, Map<String, String> params, long timeoutInSeconds) {
        try {
            AsyncHttpClient.BoundRequestBuilder rb = asyncHttpClient.preparePost(url);
            if (params != null && params.size() > 0)
                for (Map.Entry<String, String> entry : params.entrySet())
                    rb.addQueryParam(entry.getKey(), entry.getValue());

            Response response = rb.execute().get(timeoutInSeconds, TimeUnit.SECONDS);
            return response.getResponseBody();
        } catch (Throwable e) {
            logger.error("AsyncHttpClientHelper.fetchHttpPost meet error.", e);
            return null;
        }


    }

    /**
     * init method
     */
    public static void init() {
        if (asyncHttpClient == null) {
            asyncHttpClient = new AsyncHttpClient();
        }
    }

    /**
     * destroy method
     */
    public static void destroy() {
        if (asyncHttpClient != null)
            asyncHttpClient.close();
    }


}
