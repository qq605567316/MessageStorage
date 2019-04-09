package com.tt.msg.Service;

import com.tt.msg.BaseTest;
import com.tt.msg.service.RecordService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @ClassName RecordServiceTest
 * @Description TODO
 * @Author tanjiang
 * @CreateTime 2019/4/9 9:36
 * @Version 1.0
 **/

public class RecordServiceTest extends BaseTest {
    @Autowired
    private RecordService recordService;


    @Test
    public void getTableInfo(){
        Map<String,Object> map = recordService.getTableInfo();
        System.out.println(map);
    }

}
