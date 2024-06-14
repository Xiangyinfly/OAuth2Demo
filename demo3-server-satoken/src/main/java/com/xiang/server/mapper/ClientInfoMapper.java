package com.xiang.server.mapper;

import com.xiang.server.domain.entity.ClientInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author sardinary
* @description 针对表【client_info】的数据库操作Mapper
* @createDate 2024-06-11 21:50:48
* @Entity com.xiang.server.domain.entity.ClientInfo
*/
@Mapper
public interface ClientInfoMapper extends BaseMapper<ClientInfo> {

}




