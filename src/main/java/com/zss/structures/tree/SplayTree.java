package com.zss.structures.tree;

import com.zss.structures.exceptions.UnderFlowException;
import com.zss.structures.tree.inter.Tree;

/**
 * @author ZSS
 * @date 2022/5/15 10:02
 * @desc 伸展树
 */
@SuppressWarnings("unused")
public class SplayTree<T extends Comparable<? super T>> implements Tree<T> {

    private BinaryNode<T> root;
    private final BinaryNode<T> nullNode;
    /**
     * for splay
     */
    private final BinaryNode<T> header = new BinaryNode<>(null);
    /**
     * Used between different inserts
     */
    private BinaryNode<T> newNode = null;

    public SplayTree() {
        nullNode = new BinaryNode<>(null);
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
        return contains(x, root);
    }

    @Override
    public T findMin() throws UnderFlowException {
        if (isEmpty()) {
            throw new UnderFlowException();
        }
        return findMin(root).element;
    }

    @Override
    public T findMax() throws UnderFlowException {
        if (isEmpty()) {
            throw new UnderFlowException();
        }
        return findMax(root).element;
    }

    @Override
    public void insert(T x) {
        if (newNode == null) {
            newNode = new BinaryNode<>(null);
        }
        newNode.element = x;

        if (root == nullNode) {
            newNode.left = newNode.right = nullNode;
            root = newNode;
        } else {
            root = splay(x, root);
            if (x.compareTo(root.element) < 0) {
                newNode.left = root.left;
                newNode.right = root;
                root.left = nullNode;
                root = newNode;
            } else if (x.compareTo(root.element) > 0) {
                newNode.right = root.right;
                newNode.left = root;
                root.right = nullNode;
                root = newNode;
            } else {
                // 没有重复
                return;
            }
            // 下一次插入将需要new
            newNode = null;
        }
    }

    @Override
    public void remove(T x) {
        BinaryNode<T> newTree;

        // 如果x找到，那么x必将是根
        if (!contains(x)) {
            // 未找到目标项， 啥都不用干
            return;
        }

        if (root.left == nullNode){
            newTree = root.right;
        } else {
            // 在左子树中找到最大值
            // 将其展开到根部，然后附加右孩子
            newTree = root.left;
            newTree = splay(x, newTree);
            newTree.right = root.right;
        }
        root = newTree;
    }

    @Override
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            System.out.println("========== Start print tree ==========");
            printTree(root);
            System.out.println("========== End print tree ==========");
        }
    }

    // ================= 私有方法 ==================== //

    /**
     * 执行自上而下展开的内部方法。最后访问的节点成为新的根
     *
     * @param x 需要展开的目标项
     * @param t 要展开的子树的根
     * @return 展开后的子树
     */
    private BinaryNode<T> splay(T x, BinaryNode<T> t) {
        BinaryNode<T> leftTreeMax, rightTreeMin;

        header.left = header.right = nullNode;
        leftTreeMax = rightTreeMin = header;

        // 保证匹配
        nullNode.element = x;

        for (; ; ) {
            if (x.compareTo(t.element) < 0) {
                if (x.compareTo(t.left.element) < 0) {
                    t = rotateWithLeftChild(t);
                }
                if (t.left == nullNode) {
                    break;
                }
                // Link Right
                rightTreeMin.left = t;
                rightTreeMin = t;
                t = t.left;
            } else if (x.compareTo(t.element) > 0) {
                if (x.compareTo(t.right.element) > 0) {
                    t = rotateWithRightChild(t);
                }
                if (t.right == nullNode) {
                    break;
                }
                // Link Left
                leftTreeMax.right = t;
                leftTreeMax = t;
                t = t.right;
            } else {
                break;
            }
        }
        leftTreeMax.right = t.left;
        rightTreeMin.left = t.right;
        t.left = header.right;
        t.right = header.left;
        return t;
    }

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
     * 单左旋
     *
     * @param k2 以当前节点作为根节点
     * @return node
     */
    private BinaryNode<T> rotateWithLeftChild(BinaryNode<T> k2) {
        BinaryNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * 单右旋
     *
     * @param k1 以当前节点作为根节点
     * @return node
     */
    private BinaryNode<T> rotateWithRightChild(BinaryNode<T> k1) {
        BinaryNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    /**
     * 节点类
     */
    private static class BinaryNode<T> {
        // 节点数据
        T element;
        // 左子树
        BinaryNode<T> left;
        // 右子树
        BinaryNode<T> right;

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
