package com.tt.msg.dao;

import com.tt.msg.entity.Manager;
import org.apache.ibatis.annotations.Param;

/**
 * @InterfaceName ManagerDao
 * @Description Manager模块Dao层接口
 * @Author tanjiang
 * @CreateTime 2019/1/12 10:39
 * @Version 1.0
 **/

public interface ManagerDao {

    /**
     * 登录验证
     *
     * @param username
     * @param password
     * @return
     */
    Manager login(@Param("username") String username, @Param("password") String password);
}
