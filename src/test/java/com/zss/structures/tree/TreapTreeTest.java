package com.zss.structures.tree;

import com.zss.structures.BaseTest;
import org.junit.Test;

import java.util.Random;

/**
 * @author ZSS
 * @date 2022/6/8 13:49
 * @desc treap树测试
 */
public class TreapTreeTest extends BaseTest {

    private TreapTree<Integer> root;

    private void init(){
        root = new TreapTree<>();
    }

    @Test
    public void insertTest(){
        init();
        root.insert(30);
        root.insert(20);
        root.insert(40);
        root.insert(50);
        root.insert(15);
        root.insert(45);
        root.printTree();
    }

    @Test
    public void randomTest() {
        Random RANDOM_OBJ = new Random();
        for (int i = 0; i < 20; i ++){
            System.out.println(RANDOM_OBJ.nextInt());
        }
    }
}
