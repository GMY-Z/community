package life.manong.community.provider;

import com.alibaba.fastjson.JSON;
import life.manong.community.dto.AccessTokenDTO;
import life.manong.community.dto.GIthubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @authon GMY
 * @create 2020-05-22 20:01
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            String[] split = s.split("&");
            String token = split[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GIthubUser gIthubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GIthubUser gIthubUser = JSON.parseObject(string, GIthubUser.class);
            return gIthubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
