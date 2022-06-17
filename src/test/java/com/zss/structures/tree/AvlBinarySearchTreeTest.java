package com.zss.structures.tree;

import com.zss.structures.BaseTest;
import org.junit.Test;

/**
 * @author ZSS
 * @date 2022/5/13 16:26
 * @desc AVL -- 平衡二叉查找树 测试
 */
@SuppressWarnings("unused")
public class AvlBinarySearchTreeTest extends BaseTest {

    private AvlBinarySearchTree<Integer> avlTree;

    public void init() {
        avlTree = new AvlBinarySearchTree<>();
        avlTree.insert(7);
        avlTree.insert(5);
        avlTree.insert(9);
        avlTree.insert(3);
        /*
                    7
                   / \
                  5   9
                 /
                3
         */
    }

    @Test
    public void insertTest() {
        init();
        avlTree.insert(4);
        avlTree.printTree();
        System.out.println("success");
    }
}
