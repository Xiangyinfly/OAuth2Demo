package com.xiang.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.server.domain.entity.User;
import com.xiang.server.service.UserService;
import com.xiang.server.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author sardinary
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-06-11 21:57:30
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




