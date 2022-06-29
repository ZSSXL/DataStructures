package com.zss.structures.tips;

import com.zss.structures.BaseTest;
import com.zss.structures.tips.randomized.MyRandom;
import com.zss.structures.tips.randomized.Random48;
import org.junit.Test;

/**
 * @author ZSS
 * @date 2022/6/29 11:10
 * @desc 随机数测试
 */
public class RandomTest extends BaseTest {

    @Test
    public void myRandomTest(){
        for (int i = 0; i < 10; i++){
            MyRandom myRandom = new MyRandom();
            System.out.println(myRandom.randomInt());
        }
    }

    @Test
    public void random48Test(){
        for (int i = 0; i < 10; i++){
            Random48 random48 = new Random48();
            System.out.println(random48.randomDouble());
            System.out.println(random48.randomInt());
        }
    }
}
