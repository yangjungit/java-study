package com.giovanny.study.service.impl;

import com.giovanny.study.entity.po.Uuu;
import com.giovanny.study.mapper.UuuMapper;
import com.giovanny.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @packageName: com.giovanny.study.service.impl
 * @className: UserServiceImpl
 * @description: UserServiceImpl
 * @author: YangJun
 * @date: 2020/4/27 10:33
 * @version: v1.0
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UuuMapper uuuMapper;

    @Override
    public int insert(Uuu uuu) {
        int insert = uuuMapper.insert(uuu);
//      log.info("insert uuu:{}",insert);
      return insert;
    }
}
