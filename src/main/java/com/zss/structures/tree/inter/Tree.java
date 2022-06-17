package com.zss.structures.tree.inter;

import com.zss.structures.exceptions.UnderFlowException;

/**
 * @author ZSS
 * @date 2022/5/10 15:03
 * @desc 树的接口
 */
public interface Tree<T> {

    /**
     * 将当前树清空
     */
    void makeEmpty();

    /**
     * 判断当前树是否为空
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 判断当前树内是否有该项
     *
     * @param x 匹配项
     * @return boolean
     */
    boolean contains(T x);

    /**
     * 找到该树中最小的节点
     *
     * @return element
     * @throws UnderFlowException 下溢
     */
    T findMin() throws UnderFlowException;

    /**
     * 找到该树中最大的节点
     *
     * @return element
     * @throws UnderFlowException 下溢
     */
    T findMax() throws UnderFlowException;

    /**
     * 插入新节点
     *
     * @param x element
     */
    void insert(T x);

    /**
     * 删除节点
     *
     * @param x 指定的项
     */
    void remove(T x);

    /**
     * 打印树
     */
    void printTree();
}
