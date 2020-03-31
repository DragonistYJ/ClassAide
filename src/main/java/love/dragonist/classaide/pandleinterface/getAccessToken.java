package love.dragonist.classaide.pandleinterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

//获取token
public class getAccessToken {
    public static String getToken(){
        String clientId="4jXxljxXKfQPMSTI553QipH3";
        String clientSecret="MGtqkVxgSBr5DgVB83GvUCTLbrWS7656";
        return getToken(clientId,clientSecret);
    }
    public static String getToken(String ak,String sk){
        String host="https://aip.baidubce.com/oauth/2.0/token?";
        String getTockenUrl=host
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try{
            URL realurl=new URL(getTockenUrl);
            HttpURLConnection conn=(HttpURLConnection)realurl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Map<String,List<String>> map=conn.getHeaderFields();
            for(String key:map.keySet()){
                System.out.println(key+"------>"+map.get(key));
            }
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String access_token="";
            String temp;
            while((temp=bufferedReader.readLine())!=null){
                access_token=access_token+temp;
            }
            JSONObject jsonObject=new JSONObject(access_token);
            return jsonObject.getString("access_token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
