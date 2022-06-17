package com.zss.structures;

import org.junit.After;
import org.junit.Before;

/**
 * @author ZSS
 * @date 2022/5/9 10:28
 * @desc 测试基类
 */
public class BaseTest {

    private long startTime = 0L;

    @Before
    public void before() {
        startTime = System.currentTimeMillis();
        System.out.println("================== 开始测试 ==================");
    }

    @After
    public void after() {
        System.out.println("================== 结束测试 ==================");
        System.out.println("================== 共耗时[ " + (System.currentTimeMillis() - startTime) + " ]ms");
    }

}
