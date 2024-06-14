package com.xiang.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.server.domain.entity.ClientInfo;
import com.xiang.server.service.ClientInfoService;
import com.xiang.server.mapper.ClientInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author sardinary
* @description 针对表【client_info】的数据库操作Service实现
* @createDate 2024-06-11 21:50:48
*/
@Service
public class ClientInfoServiceImpl extends ServiceImpl<ClientInfoMapper, ClientInfo>
    implements ClientInfoService{

}




