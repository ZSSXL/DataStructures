package com.zss.structures.tree;

import com.zss.structures.exceptions.UnderFlowException;
import com.zss.structures.tree.inter.Tree;

/**
 * @author ZSS
 * @date 2022/5/10 14:54
 * @desc AVL树 -- 带有平衡条件的二叉查找树
 */
@SuppressWarnings("unused")
public class AvlBinarySearchTree<T extends Comparable<? super T>> implements Tree<T> {

    private AvlNode<T> root;

    public AvlBinarySearchTree() {
        this.root = null;
    }

    @Override
    public void makeEmpty() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(T x) {
        return contains(x, root);
    }

    @Override
    public T findMin() throws UnderFlowException {
        if (isEmpty()) {
            throw new UnderFlowException();
        }
        // 因为已经过滤了空树的情况，所以无需担心此处会空指针
        return findMin(root).element;
    }

    @Override
    public T findMax() throws UnderFlowException {
        if (isEmpty()) {
            throw new UnderFlowException();
        }
        // 因为已经过滤了空树的情况，所以无需担心此处会空指针
        return findMax(root).element;
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


    // =============== 内部私有类和方法 =============== //

    /**
     * 如果树T中存在含有项X的系欸但，那么返回true，否则返回false
     *
     * @param x 需要查找的项
     * @param t 以当前节点为根节点
     */
    private boolean contains(T x, AvlNode<T> t) {
        if (t == null) {
            return false;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            // 如果目标值小于当前项值，则继续遍历左子树
            return contains(x, t.left);
        } else if (compareResult > 0) {
            // 如果目标值项大于当前项值，则继续遍历右子树
            return contains(x, t.right);
        } else {
            // 匹配
            return true;
        }
    }

    /**
     * 返回最小项：从根开始并且只要右左儿子就向左遍历
     *
     * @param t 以当前节点为根节点
     */
    private AvlNode<T> findMin(AvlNode<T> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        } else {
            return findMin(t.left);
        }
    }

    /**
     * 返回最小项：从根开始并且只要有右儿子就向右遍历
     *
     * @param t 以当前节点为根节点
     */
    private AvlNode<T> findMax(AvlNode<T> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }
        return t;
    }

    /**
     * 打印二叉树
     *
     * @param t 以当前节点为根节点
     */
    private void printTree(AvlNode<T> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * 插入
     */
    private AvlNode<T> insert(T x, AvlNode<T> t) {
        if (t == null) {
            return new AvlNode<>(x);
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else {
            System.out.println("The element [" + x + "] already exist !!");
        }
        return balance(t);
    }

    /**
     * 平衡差值: 1
     */
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * 保持平衡
     *
     * @param t 以当前节点为根节点
     * @return node
     */
    private AvlNode<T> balance(AvlNode<T> t) {
        if (t == null) {
            return null;
        }

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right.right) >= height(t.right.left)) {
                t = rotateWithRightChild(t);
            } else {
                t = doubleWithRightChild(t);
            }
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /**
     * 单左旋
     *
     * @param k2 以当前节点作为根节点
     * @return node
     */
    private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * 单右旋
     *
     * @param k1 以当前节点作为根节点
     * @return node
     */
    private AvlNode<T> rotateWithRightChild(AvlNode<T> k1) {
        AvlNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(k1.height, height(k2.right)) + 1;
        return k2;
    }

    /**
     * 双左旋
     *
     * @param k3 以当前节点为根节点
     * @return node
     */
    private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * 双右旋
     *
     * @param k3 以当前节点为根节点
     * @return node
     */
    private AvlNode<T> doubleWithRightChild(AvlNode<T> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    /**
     * 删除指定项的节点
     *
     * @param x 需要删除的项
     * @param t 以当前节点为根节点
     */
    private AvlNode<T> remove(T x, AvlNode<T> t){
        if (t == null){
            return null;
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0){
            t.left = remove(x, t.left);
        } else if (compareResult > 0){
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null){
            t.element = findMin(t.right).element;
            t.right = removeMin(t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }

        return balance(t);
    }

    /**
     * 删除当前树的最小节点
     *
     * @param t 以当前节点为根节点
     */
    public AvlNode<T> removeMin(AvlNode<T> t) {
        if (t.left != null) {
            t.left = removeMin(t.left);
            return t;
        }
        t = (t.right == null) ? null : t.right;
        return t;
    }

    /**
     * 获取节点高度，如果是空的，责返回 -1
     *
     * @param t 当前节点
     * @return 节点高度
     */
    private int height(AvlNode<T> t) {
        return t == null ? -1 : t.height;
    }

    /**
     * 节点类
     */
    private static class AvlNode<T> {
        // 节点数据
        T element;
        // 节点高度
        int height;
        // 左子树
        AvlNode<T> left;
        // 右子树
        AvlNode<T> right;

        AvlNode(T element, AvlNode<T> left, AvlNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        AvlNode(T theElement) {
            this(theElement, null, null);
        }
    }
}
