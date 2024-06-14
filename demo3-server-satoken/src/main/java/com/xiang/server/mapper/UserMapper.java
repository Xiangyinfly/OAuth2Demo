package com.xiang.server.mapper;

import com.xiang.server.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author sardinary
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-06-11 21:57:30
* @Entity com.xiang.server.domain.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




