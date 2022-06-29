package com.zss.structures.list.inter;

/**
 * @author ZSS
 * @date 2022/6/17 16:51
 * @desc 表接口
 */
@SuppressWarnings("unused")
public interface MyList<T> {

    /**
     * 清空表
     */
    void clear();

    /**
     * 获取表的大小
     *
     * @return 表的大小
     */
    int size();

    /**
     * 判断表是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 调整到适当的大小
     */
    void trimToSize();

    /**
     * 获取该项内容
     *
     * @param idx 指定位置
     * @return 内容
     */
    T get(int idx);

    /**
     * 设置该项内容
     *
     * @param idx    指定位置
     * @param newVal 新内容
     * @return 旧内容
     */
    T set(int idx, T newVal);

    /**
     * 确保容量
     *
     * @param newCapacity 新的边界
     */
    void ensureCapacity(int newCapacity);
}
