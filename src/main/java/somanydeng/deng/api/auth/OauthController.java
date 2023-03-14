package somanydeng.deng.api.auth;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.util.HashMap;

@Controller("/oauth")
public class OauthController {

    static final String rest_key = "0ba778fad07eee624f652049c09d91ac";
    static final String redirect_uri = "";

    @GetMapping("/")
    public String loginPage(Model model){

    }

    @GetMapping("/kakao")
    public String kakaoLogin(Model model, @ModelAttribute("code") String code, HttpSession session){
        model.addAttribute("goaway", "goaway");

        System.out.println(code+"bbb");
        String url = "https://kauth.kakao.com/oauth/token";
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("client_id", rest_key);
        paramMap.put("redirect_uri", redirect_uri);
        paramMap.put("code", code);

        RequestUtil util = new RequestUtil();
        HashMap<String,Object> map = util.sendPostReturnMap(util.conn(url, "POST"), paramMap);


        String profileURL = "https://kapi.kakao.com/v2/user/me";

        HttpURLConnection conn2 = util.conn(profileURL, "POST");
        conn2.setRequestProperty("Authorization", "Bearer " + map.get("access_token"));
        conn2.setRequestProperty("property_keys", "[\"kakao_account.email\"]");
        HashMap<String,Object> userMap = util.sendGetReturnMap(conn2);
        UserVO uvo = new UserVO();
        double id = (double) userMap.get("id");

        LinkedTreeMap<String,Object> kakao_account = (LinkedTreeMap<String,Object>) userMap.get("kakao_account");
        LinkedTreeMap<String,Object> profile = (LinkedTreeMap<String,Object>) kakao_account.get("profile");
        String nickname = (String)profile.get("nickname");
        String thumbnail = (String)profile.get("thumbnail_image_url");
        uvo.setId(id);
        uvo.setNickname(nickname);
        uvo.setThumbnailSrc(thumbnail);

        if(svc.selectUser(id) == null) {
            svc.insertUser(uvo);
        } else {
            svc.updateUser(uvo);
        }

        session.setAttribute("User", uvo);
        return "redirect:showmain";

    }

}




