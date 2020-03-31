package love.dragonist.classaide.pandleinterface;

import love.dragonist.classaide.Beans.InfoStud;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: gofreelee
 * \* Date: 2019/3/17
 * \* Time: 11:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class FaceDetection {
    public static String detectFaceNum="https://aip.baidubce.com/rpc/2.0/ai_custom/v1/detection/countpeople";
    public static String detectStudStatus="https://aip.baidubce.com/rpc/2.0/ai_custom/v1/detection/yjhmm2";
    public static String detectFace(String url,String picturePath,String faceField){
        File picture=new File(picturePath);
        String access_token= getAccessToken.getToken();
        String base64Pict="";
        try {
            FileInputStream fileInputStream=new FileInputStream(picture);
            byte[]bytes=new byte[(int)picture.length()];
            fileInputStream.read(bytes);
            base64Pict= Base64Util.encode(bytes);
            fileInputStream.close();
            Map<String,Object> params=new HashMap<>();
            params.put("image", base64Pict);
           // params.put("face_field",faceField);
            //params.put("image_type","BASE64");
            //params.put("")
            String param=mapToJSON(params);
            String result= HttpUtil.post(url,access_token,"application/json",param);
            System.out.println(result);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T,V>String mapToJSON(Map<T,V> params){
        JSONObject jsonObject=new JSONObject(params);
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }
    //来计算有几行，每行几个人
    public static int[] analysis(InfoStud[] infoStuds){
        int startIndex=0;
        int peopnum=0;
        //float tanNum=
        List<Integer> peonumEachline=new ArrayList<>();
        for(int i=0;i<infoStuds.length-1;i++){
            System.out.println((infoStuds[i+1].getLocation().getTop()-
                    infoStuds[0].getLocation().getTop())/
                    (infoStuds[i+1].getLocation().getLeft()-
                    infoStuds[0].getLocation().getLeft()));
        }
        return new int[1];
    }
}
