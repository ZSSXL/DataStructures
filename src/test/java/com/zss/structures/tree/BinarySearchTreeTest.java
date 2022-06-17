package com.zss.structures.tree;

import com.zss.structures.BaseTest;
import com.zss.structures.exceptions.UnderFlowException;
import org.junit.Test;

import java.util.Random;

/**
 * @author ZSS
 * @date 2022/5/9 10:27
 * @desc ADT -- 二叉查找树测试
 */
public class BinarySearchTreeTest extends BaseTest {

    private BinarySearchTree<Integer> bSTree;

    public void initBSTreeRandom() {
        bSTree = new BinarySearchTree<>();
        for (int i = 0; i < 21; i++) {
            int num = new Random().nextInt(20);
            bSTree.insert(num);
        }
    }

    public void initBSTree() {
        bSTree = new BinarySearchTree<>();
        bSTree.insert(6);
        bSTree.insert(5);
        bSTree.insert(10);
        bSTree.insert(2);
        bSTree.insert(0);
        bSTree.insert(3);
        bSTree.insert(4);
        bSTree.insert(18);
        bSTree.insert(14);
        bSTree.insert(11);
        bSTree.insert(13);
        bSTree.insert(16);
        bSTree.insert(15);
        bSTree.insert(19);
    }

    @Test
    public void printTree() {
        initBSTree();
        bSTree.printTree();
    }

    @Test
    public void removeTest() {
        initBSTree();
        bSTree.printTree();
        bSTree.remove(2);
        bSTree.remove(14);
        bSTree.printTree();
    }

    @Test
    public void containsTest() {
        initBSTreeRandom();
        bSTree.printTree();
        boolean contains = bSTree.contains(13);
        System.out.println(contains);
    }

    @Test
    public void findMaxTest() throws UnderFlowException {
        initBSTreeRandom();
        bSTree.printTree();
        Integer max = bSTree.findMax();
        System.out.println(max);
    }

    @Test
    public void findMinTest() throws UnderFlowException {
        initBSTreeRandom();
        bSTree.printTree();
        Integer min = bSTree.findMin();
        System.out.println(min);
    }

}
