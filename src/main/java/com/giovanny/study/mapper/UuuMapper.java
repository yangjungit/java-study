package com.giovanny.study.mapper;



import com.giovanny.study.config.mybatis.MyMapper;
import com.giovanny.study.entity.po.Uuu;
import com.giovanny.study.entity.vo.UuuVo;

import java.util.List;

public interface UuuMapper extends MyMapper<Uuu> {
    /**
     * 模糊查询
     *
     * @param uuuVo uuu
     * @return 列表
     */
    List<Uuu> findBy(UuuVo uuuVo);
}