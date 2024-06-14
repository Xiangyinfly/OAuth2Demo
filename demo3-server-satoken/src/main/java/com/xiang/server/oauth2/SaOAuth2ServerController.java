package com.xiang.server.oauth2;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xiang.server.domain.entity.User;
import com.xiang.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SaOAuth2ServerController {
    private final UserService userService;

    // 拦截关于oauth2的请求并处理
    @RequestMapping("/oauth2/*")
    public Object request() {
        log.info("URL{}发来请求",SaHolder.getRequest().getUrl());
        return SaOAuth2Handle.serverRequest();
    }

    // 自定义oauth2
    @Autowired
    public void setSaOAuth2Config(SaOAuth2Config cfg) {
        cfg
                // 设置未登录的界面
                .setNotLoginView(() -> new ModelAndView("login.html"))
                // 设置登录的handler
                .setDoLoginHandle((name, pwd) -> {
                    User user = new LambdaQueryChainWrapper<>(userService.getBaseMapper()).eq(User::getName, name).eq(User::getPassword, pwd).one();
                    if (user == null) {
                        return SaResult.error("账号名或密码错误");
                    }
                    StpUtil.login(user.getId());
                    return SaResult.ok();
                })
                // 设置确认界面
                .setConfirmView((clientId, scope)->{
                    Map<String, Object> map = new HashMap<>();
                    map.put("clientId", clientId);
                    map.put("scope", scope);
                    return new ModelAndView("confirm.html", map);
                });
    }


    // 异常处理
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }


    // 一个示例接口，可以获得用户信息，需要传入access_token才能访问
    @RequestMapping("/oauth2/userinfo")
    public SaResult userInfo() {
        String accessToken = SaHolder.getRequest().getParamNotNull("access_token");
        Object loginId = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        log.info("loginId:{}", loginId);

        SaOAuth2Util.checkScope(accessToken,"userinfo");

        User user = userService.getById(Long.valueOf(loginId.toString()));
        if (user != null) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("name", user.getName());
            map.put("sex", user.getGender() == 1 ? 'm' : 'f');
            return SaResult.data(map);
        }
        return SaResult.error();
    }
}


