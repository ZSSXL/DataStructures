package com.zss.structures.tree;

import com.zss.structures.exceptions.UnderFlowException;
import com.zss.structures.tree.inter.Tree;

/**
 * @author ZSS
 * @date 2022/5/8 16:24
 * @desc 查找树ADT -- 二叉查找树
 * 二叉查找树的性质：
 * -- 对于树中的每个节点X，它的左子树中所有项的值小于X中的项，而它的右子树中所有项的值大于X中的项。
 */
@SuppressWarnings("unused")
public class BinarySearchTree<T extends Comparable<? super T>> implements Tree<T> {

    private BinaryNode<T> root;

    public BinarySearchTree() {
        root = null;
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

    // ================= 私有方法 ==================== //

    /**
     * 如果树T中存在含有项X的系欸但，那么返回true，否则返回false
     *
     * @param x 需要查找的项
     * @param t 以当前节点为根节点
     */
    private boolean contains(T x, BinaryNode<T> t) {
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
    private BinaryNode<T> findMin(BinaryNode<T> t) {
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
    private BinaryNode<T> findMax(BinaryNode<T> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }
        return t;
    }

    /**
     * 插入新项
     *
     * @param x 需要插入的项
     * @param t 以当前节点为根节点
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if (t == null) {
            return new BinaryNode<>(x);
        }
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            // 如果目标值小于当前项值，则继续遍历左子树
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            // 如果目标值项大于当前项值，则继续遍历右子树
            t.right = insert(x, t.right);
        } else {
            // 0：如果匹配相等，那么说明有重复的项，则什么也不做或者做一些“更新”
            System.out.println("The element [" + x + "] already exist !!");
        }
        return t;
    }

    /**
     * 删除指定项的节点
     *
     * @param x 需要删除的项
     * @param t 以当前节点为根节点
     */
    private BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if (t == null) {
            // 要删除的项未找到
            System.out.println("要删除的项未找到");
            return null;
        }
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {
            // 有两个子节点
            t.element = findMin(t.right).element;
            // 可以使用 递归使用remove方法，但是也可以实现一个删除最小节点的方法
            t.right = removeMin(t.right);
        } else {
            // 只有一个子节点
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /**
     * 删除当前树的最小节点
     *
     * @param t 以当前节点为根节点
     */
    private BinaryNode<T> removeMin(BinaryNode<T> t) {
        if (t.left != null) {
            t.left = removeMin(t.left);
            return t;
        }
        t = (t.right == null) ? null : t.right;
        return t;
    }

    /**
     * 打印二叉树
     *
     * @param t 以当前节点为根节点
     */
    private void printTree(BinaryNode<T> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * 节点类
     */
    private static class BinaryNode<T> {
        /**
         * The data in the node
         */
        T element;
        /**
         * Left child
         */
        BinaryNode<T> left;
        /**
         * Right Child
         */
        BinaryNode<T> right;

        /**
         * Constructors
         */
        BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        BinaryNode(T theElement) {
            this(theElement, null, null);
        }
    }
}
