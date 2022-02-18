package core.util;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
    // 编码格式
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒
    private static final int SOCKET_TIMEOUT = 6000;

    // 不推迟实例化
    private static final HttpUtil httpUtil = new HttpUtil();

    private HttpUtil() {}

    /**
     * 使用单例模式
     * @return 唯一的 HttpUtil 实例
     */
    public static HttpUtil getInstance() { return httpUtil; }

    public String doGet(String url) throws Exception {
        // 待返回的结果
        String result = "";

        // 创建 httpClient 对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);

        // 创建 http 对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).setCookieSpec(CookieSpecs.STANDARD).build();
        httpGet.setConfig(requestConfig);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            // TODO：设置一下超时重试机制。这里经常发生 SSLException: Read timed out
            httpResponse = httpClient.execute(httpGet);
            // 获取返回结果
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                if (httpResponse.getEntity() != null) {
                    result = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
                }

            }

        } finally {
            // 释放资源
            if (httpResponse != null) httpResponse.close();
            if (httpClient != null) httpClient.close();
        }

        return result;
    }

    public static void main(String[] args) {
        try {
//            System.out.println(HttpUtil.getInstance().doGet("https://baike.baidu.com/item/%E5%BA%95%E7%89%B9%E5%BE%8B/1660180#hotspotmining"));
            System.out.println(HttpUtil.getInstance().doGet("https://www.baidu.com/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
