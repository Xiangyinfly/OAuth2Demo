package com.xiang.server.oauth2;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import com.xiang.server.domain.entity.ClientInfo;
import com.xiang.server.service.ClientInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SaOAuth2TemplateImpl extends SaOAuth2Template {
    private final ClientInfoService clientInfoService;

    // 获得客户端信息
    @Override
    public SaClientModel getClientModel(String clientId) {
        ClientInfo clientInfo = clientInfoService.getById(clientId);
        if (!Objects.isNull(clientInfo)) {
            return new SaClientModel()
                    .setClientId(clientInfo.getClientId())
                    .setClientSecret(clientInfo.getClientSecret())
                    .setAllowUrl(clientInfo.getAllowUrl())
                    .setContractScope(clientInfo.getContractScope())
                    .setIsAutoMode(clientInfo.getAutoMode() == 1);
        }
        return null;
    }

    // 获得openId
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询
        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
    }

}
