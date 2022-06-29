package com.zss.structures.tips;

import com.zss.structures.BaseTest;
import com.zss.structures.tips.dynamic.Fibonacci;
import org.junit.Test;

/**
 * @author ZSS
 * @date 2022/6/29 10:29
 * @desc 动态规划算法测试
 */
public class DynamicTest extends BaseTest {

    @Test
    public void fibonacciTest(){
        int n = 10;
        int fibonacci = Fibonacci.fibonacci(n);
        System.out.println(fibonacci);
    }
}
