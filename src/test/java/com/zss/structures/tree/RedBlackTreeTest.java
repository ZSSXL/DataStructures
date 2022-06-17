package com.zss.structures.tree;

import com.zss.structures.BaseTest;
import org.junit.Test;

/**
 * @author ZSS
 * @date 2022/6/7 10:22
 * @desc 红黑树测试
 */
public class RedBlackTreeTest extends BaseTest {

    private RedBlackTree<Integer> redBlackTree;

    private void init(){
        redBlackTree = new RedBlackTree<>();
    }

    @Test
    public void insertTest(){
        init();
        redBlackTree.insert(30);
        redBlackTree.insert(15);
        redBlackTree.insert(70);
        redBlackTree.insert(10);
        redBlackTree.insert(20);
        redBlackTree.insert(60);
        redBlackTree.insert(85);
        redBlackTree.insert(5);
        redBlackTree.insert(50);
        redBlackTree.insert(65);
        redBlackTree.insert(80);
        redBlackTree.insert(90);
        redBlackTree.insert(40);
        redBlackTree.insert(55);
        /*
            注：[]代表红色

                        30
                      /     \
                    15        70
                  /   \      /      \
                10     20  [60]     85
               /           /  \    /   \
             [5]         50   65  [80]  [90]
                        /  \
                      [40] [55]
         */

        redBlackTree.printTree();

        redBlackTree.remove(85);

        redBlackTree.printTree();
    }

}
