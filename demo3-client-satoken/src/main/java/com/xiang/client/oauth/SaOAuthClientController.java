package com.xiang.client.oauth;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.ejlchina.okhttps.OkHttps;
import com.xiang.client.utils.SoMap;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SaOAuthClientController {
    private static final String clientId = "1";
    private static final String clientSecret = "aaaa-bbbb-cccc-dddd-eeee";
    private static final String serverUrl = "http://localhost:8001";

    @RequestMapping("/")
    public Object index(HttpServletRequest request) {
        request.setAttribute("uid", StpUtil.getLoginIdDefaultNull());
        return new ModelAndView("index.html");
    }

    @RequestMapping("/codeLogin")
    public SaResult codeLogin(String code) {
        String rst = OkHttps
                .sync(serverUrl + "/oauth2/token")
                .addBodyPara("grant_type", "authorization_code")
                .addBodyPara("code", code)
                .addBodyPara("client_id", clientId)
                .addBodyPara("client_secret", clientSecret)
                .post()
                .getBody()
                .toString();
        SoMap soMap = SoMap.getSoMap().setJsonString(rst);

        if (soMap.getInt("code") != 200) {
            return SaResult.error(soMap.getString("msg"));
        }

        SoMap data = soMap.getMap("data");
        Long uid = getUserIdByOpenid(data.getString("openid"));
        data.set("uid", uid);

        StpUtil.login(uid);
        return SaResult.data(data);
    }

    @RequestMapping("/refresh")
    public SaResult refresh(String refreshToken) {
        String rst = OkHttps
                .sync(serverUrl + "/oauth2/refresh")
                .addBodyPara("grant_type", "refresh_token")
                .addBodyPara("client_id", clientId)
                .addBodyPara("client_secret", clientSecret)
                .addBodyPara("refresh_token", refreshToken)
                .post()
                .getBody()
                .toString();
        SoMap soMap = SoMap.getSoMap().setJsonString(rst);
        if (soMap.getInt("code") != 200) {
            return SaResult.error(soMap.getString("msg"));
        }
        SoMap data = soMap.getMap("data");
        return SaResult.data(data);
    }

    @RequestMapping("/getUserinfo")
    public SaResult getUserinfo(String accessToken) {
        String rst = OkHttps.sync(serverUrl + "/oauth2/userinfo")
                .addBodyPara("access_token", accessToken)
                .post()
                .getBody()
                .toString();
        SoMap soMap = SoMap.getSoMap().setJsonString(rst);

        if (soMap.getInt("code") != 200) {
            return SaResult.error(soMap.getString("msg"));
        }
        SoMap data = soMap.getMap("data");
        return SaResult.data(data);
    }

    @ExceptionHandler
    public SaResult handleException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

    @RequestMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    // TODO 不懂什么是openid 暂且返回1
    private Long getUserIdByOpenid(String openid) {
        return 1L;
    }
}
