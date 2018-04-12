import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import util.MD5Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientPostDemo {
    public static String translate(String q) {
        // 1. 创建HttpClient对象
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // 2. 创建HttpPost对象
        HttpPost httpPost = new HttpPost("http://fanyi.sogou.com/reventondc/api/sogouTranslate");
//        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.setHeader("Accept", "application/json");
        String pid = "92d07c230dbb36d85f9732fbd9be3922";
        String salt = "45f6das";
        String secretKey = "b124999c03cfc9304b86337717d43204";
        String md5Param = pid + q + salt + secretKey;
        String sign = MD5Util.MD5Encode(md5Param);
        // 3. 设置POST请求传递参数
        List<NameValuePair> params = new ArrayList<>(6);
        params.add(new BasicNameValuePair("q", q));
        params.add(new BasicNameValuePair("from", "zh-CHS"));
        params.add(new BasicNameValuePair("to", "en"));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("salt", salt));
        params.add(new BasicNameValuePair("sign", sign));
        String translation = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
            httpPost.setEntity(entity);
            // 4. 执行请求并处理响应
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                translation = EntityUtils.toString(responseEntity);
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return translation;
    }

    public static void main(String[] args) {
        String q ="网易云 我跟你讲 我真的想稍微听点中文歌";
        String translation = translate(q);
        System.out.println(translation);
    }
}
