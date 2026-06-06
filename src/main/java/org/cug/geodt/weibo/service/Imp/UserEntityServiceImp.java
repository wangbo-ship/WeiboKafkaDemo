package org.cug.geodt.weibo.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cug.geodt.weibo.entity.UserEntity;
import org.cug.geodt.weibo.mapper.UserEntityMapper;
import org.cug.geodt.weibo.service.UserEntityService;
import org.springframework.stereotype.Service;

/**
 * @className: UserEntityServiceImp
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/5/30 16:33
 * @version: 1.0
 */
@Service
public class UserEntityServiceImp extends ServiceImpl<UserEntityMapper, UserEntity> implements UserEntityService {
}
