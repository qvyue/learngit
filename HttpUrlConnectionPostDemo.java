import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnectionPostDemo {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://fanyi.sogou.com/reventondc/api/sogouTranslate");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();
            String params ="q=翻译API接入文档&from=zh-CHS&to=en&pid=92d07c230dbb36d85f9732fbd9be3922&salt=45f6das&sign=76f57178ccfb2d3c489f52354bfd3fd5";
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();
            // 从连接中读取响应信息
            String msg = "";
            int responseCode = connection.getResponseCode();
            if (responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line=reader.readLine()) != null){
                    msg += line +"\n";
                }
                reader.close();
            }
            connection.disconnect();
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
