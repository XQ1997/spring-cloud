package com.x.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.ucenter.mapper.XcUserMapper;
import com.x.ucenter.model.dto.AuthParamsDto;
import com.x.ucenter.model.dto.XcUserExt;
import com.x.ucenter.model.po.XcUser;
import com.x.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Mr.M
 * @version 1.0
 * @description 账号密码认证
 * @date 2022/10/20 14:49
 */
@Slf4j
@Service("password_authservice")
public class PasswordAuthServiceImpl implements AuthService {

    @Autowired
    XcUserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    //实现账号和密码认证
    @Override
    public XcUserExt execute(AuthParamsDto authParamsDto) {

        //账号
        String username = authParamsDto.getUsername();
        //从数据库查询用户信息
        XcUser xcUser = userMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getUsername, username));
        if (xcUser == null) {
            //账号不存在
            throw new RuntimeException("账号不存在");
        }
        //比对密码
        String passwordDB = xcUser.getPassword();//正确的密码(加密后)
        String passwordInput = authParamsDto.getPassword();//输入的密码
        boolean matches = passwordEncoder.matches(passwordInput, passwordDB);
        if(!matches){
            throw new RuntimeException("账号或密码错误");
        }
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(xcUser,xcUserExt);
        return xcUserExt;
    }
}
