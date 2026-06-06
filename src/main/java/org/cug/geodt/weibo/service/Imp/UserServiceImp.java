package org.cug.geodt.weibo.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cug.geodt.weibo.entity.User;
import org.cug.geodt.weibo.mapper.UserMapper;
import org.cug.geodt.weibo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @className: UserServiceImp
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/5/14 16:02
 * @version: 1.0
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
}
