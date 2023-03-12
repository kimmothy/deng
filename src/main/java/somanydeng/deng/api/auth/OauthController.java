package somanydeng.deng.api.auth;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.net.HttpURLConnection;

@Controller("/oauth")
public class OauthController {

    @GetMapping("/")
    public String loginPage(Model model){

    }

    @GetMapping("/kakao")
    public String kakaoLogin(Model model){
        Get
        Hots: kauth.kakao.com
        directory: /oauth/authorize?client_id= {apikey}&redirect_uri=https://localhost/oauth/main&response_type=code

    }

}


https://kauth.kakao.com/oauth/authorize?client_id=0ba778fad07eee624f652049c09d91ac&redirect_uri=http://localhost/deng/oath&response_type=code

