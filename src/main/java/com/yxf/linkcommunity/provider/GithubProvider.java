package com.yxf.linkcommunity.provider;

import com.alibaba.fastjson.JSON;
import com.yxf.linkcommunity.dto.AccessTokenDto;
import com.yxf.linkcommunity.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
//        https://github.com/login/oauth/access_token?client_id=Iv1.233c470b3aa1382c&client_secret=e7b378f79f16ac9639076dae231a81bd4947e89e&code="+accessTokenDto.getCode()+"&redirect_uri=http://localhost:8080/callback&state=1"
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id=Iv1.233c470b3aa1382c&client_secret=912bf550f3c263258fcae02986983f116e22bdd1&code="+accessTokenDto.getCode()+"&redirect_uri=http://localhost:8080/callback&state=1")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string= response.body().string();
            System.out.println("accessToken"+string);
            String split=string.split("&")[0].split("=")[1];
            System.out.println(split);
            return split;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

}
