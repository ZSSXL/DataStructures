package com.zss.structures.tree;

import com.zss.structures.exceptions.UnderFlowException;
import com.zss.structures.tree.inter.Tree;

import java.util.Random;

/**
 * @author ZSS
 * @date 2022/6/8 11:21
 * @desc treap树
 * 使用随机数并且对任意的输入都能给出O(log N)期望时间的性能
 * 树中的每个节点存储一项，一个左和右链，以及一个优先级，该优先级是建立节点时随机指定的。
 * 一个treap树就是一棵二叉查找树，其节点优先级满足堆序性质：
 * ---- 任意节点的优先级必须至少和它父节点的优先级一样大
 * ---- 这不是平衡树，它的旋转太随机
 */
@SuppressWarnings("unused")
public class TreapTree<T extends Comparable<? super T>> implements Tree<T> {

    private TreapNode<T> root;
    private final TreapNode<T> nullNode;

    TreapTree(){
        nullNode = new TreapNode<>(null);
        nullNode.left = nullNode.right = nullNode;
        root = nullNode;
    }

    @Override
    public void makeEmpty() {
        root = nullNode;
    }

    @Override
    public boolean isEmpty() {
        return root == nullNode;
    }

    @Override
    public boolean contains(T x) {
        return false;
    }

    @Override
    public T findMin() throws UnderFlowException {
        return null;
    }

    @Override
    public T findMax() throws UnderFlowException {
        return null;
    }

    @Override
    public void insert(T x) {
        root = insert(x, root);
    }

    @Override
    public void remove(T x) {
        root = remove(x, root);
    }

    @Override
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            System.out.println("========== Start print tree ==========");
            printTree(root);
            System.out.println("========== Stop print tree ==========");
        }
    }

    // ================= 私有方法 ==================== //

    private TreapNode<T> insert(T x, TreapNode<T> t){
        if (t == nullNode){
            return new TreapNode<>(x, nullNode, nullNode);
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0){
            t.left = insert(x, t.left);
            if (t.left.priority < t.priority){
                t = rotateWithLeftChild(t);
            }
        } else if (compareResult >0){
            t.right = insert(x, t.right);
            if (t.right.priority < t.priority){
                t = rotateWithRightChild(t);
            }
        } else {
            // 出现重复，do nothing
            System.out.println("已存在");
        }
        return t;
    }

    private TreapNode<T> remove(T x, TreapNode<T> t){
        if (t != nullNode){
            int compareResult = x.compareTo(t.element);

            if (compareResult < 0){
                t.left = remove(x, t.left);
            } else if (compareResult > 0){
                t.right = remove(x, t.right);
            } else {
                // 成功匹配
                if (t.left.priority < t.right.priority){
                    t = rotateWithLeftChild(t);
                } else{
                    t = rotateWithRightChild(t);
                }

                if (t != nullNode) {
                    // 继续向下
                    t = remove(x, t);
                } else {
                    // 是一片叶子
                    t.left = nullNode;
                }
            }
        }
        return t;
    }

    /**
     * 打印二叉树
     *
     * @param t 以当前节点为根节点
     */
    private void printTree(TreapNode<T> t) {
        if (t != nullNode) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * 单左旋
     *
     * @param k2 以当前节点作为根节点
     * @return node
     */
    private TreapNode<T> rotateWithLeftChild(TreapNode<T> k2) {
        TreapNode<T> k1 = k2.left;
        k2.left = k2.right;
        k1.right = k2;
        return k1;
    }

    /**
     * 单右旋
     *
     * @param k1 以当前节点作为根节点
     * @return node
     */
    private TreapNode<T> rotateWithRightChild(TreapNode<T> k1) {
        TreapNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    /**
     * treap树节点
     *
     * @param <T> anyType
     */
    private static class TreapNode<T> {
        T element;
        TreapNode<T> left;
        TreapNode<T> right;
        // 优先级
        int priority;

        TreapNode(T theElement) {
            this(theElement, null, null);
        }

        TreapNode(T theElement, TreapNode<T> lt, TreapNode<T> rt) {
            element = theElement;
            left = lt;
            right = rt;
            priority = RANDOM_OBJ.nextInt();
        }

        private static final Random RANDOM_OBJ = new Random();
    }
}
