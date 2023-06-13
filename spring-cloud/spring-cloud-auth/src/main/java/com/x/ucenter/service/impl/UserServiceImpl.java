package com.x.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.ucenter.mapper.XcUserMapper;
import com.x.ucenter.model.dto.AuthParamsDto;
import com.x.ucenter.model.dto.XcUserExt;
import com.x.ucenter.model.po.XcUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Mr.M
 * @version 1.0
 * @description 屏蔽oauth2配置用户信息服务的bean,自定义bean 实现接口 从数据库获取用户信息
 * @date 2022/10/20 10:54
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    XcUserMapper userMapper;

    //传入的是AuthParamsDto中的json串
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AuthParamsDto authParamsDto = null;
        try {
            //将认证参数转为AuthParamsDto类型
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        } catch (Exception e) {
            log.info("认证请求不符合项目要求:{}",s);
            throw new RuntimeException("认证请求数据格式不对");
        }
        //账号
        String username1 = authParamsDto.getUsername();

        //从数据库查询用户信息
        XcUser xcUser = userMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getUsername,username1));
        if(xcUser == null){
            //账号不存在
            return null;
        }
        //账号
        String username = xcUser.getUsername();
        //获取正确的密码
        String passwordDb = xcUser.getPassword();
        //用户权限，如果不加报Cannot pass a null GrantedAuthority collection
        String[] authorities = {"test"};
        //将user转成json 将用户信息以json格式存放进jwt中
        xcUser.setPassword(null);
        String userJson = JSON.toJSONString(xcUser);
        return User.withUsername(userJson).password(passwordDb).authorities(authorities).build();
    }


}
