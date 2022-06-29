package com.zss.structures.tips.randomized;

/**
 * @author ZSS
 * @date 2022/6/29 11:03
 * @desc 随机算法
 */
public class MyRandom {

    private static final int A = 48271;
    private static final int M = 2147483647;
    private static final int Q = M / A;
    private static final int R = M % A;

    private int state;

    public MyRandom(){
        state = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }

    /**
     * 返回一个伪随机整数，并改变内部状态
     *
     * @return 伪随机整数
     */
    public int randomInt() {
        int tmpState = A * (state % Q) - R * (state / Q);

        if (tmpState >= 0) {
            state = tmpState;
        } else {
            state = tmpState + M;
        }
        return state;
    }
}
