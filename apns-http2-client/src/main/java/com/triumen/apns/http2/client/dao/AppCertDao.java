package com.triumen.apns.http2.client.dao;

import com.triumen.apns.http2.client.domain.AppCert;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by by Martin Lee on 2019/2/13 18:07.
 */
public interface AppCertDao extends BaseMapper<AppCert> {


    @Select("<script>select tab.id, tab.name, tab.app_id as appId, tab.type, tab.update_time AS updateTime from app_cert tab where app_id = #{0}</script>")
    List<AppCert> selectAppCertUpdateListByAppId(Integer id);

}
