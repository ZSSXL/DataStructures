package com.zss.structures.tips.dynamic;

/**
 * @author ZSS
 * @date 2022/6/29 10:20
 * @desc 斐波那契数
 * example: 1，1，2，3，5，8，13，21，34，55，89...
 */
public class Fibonacci {

    public static void main(String[] args) {
        int fib = fib(9);
        System.out.println(fib);
    }

    /**
     * 计算斐波那契数 -- 低效算法 -- 指数级增长
     *
     * @param n 第n项
     * @return 第n项值
     */
    public static int fib(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    /**
     * 计算斐波那契数 -- 高效算法 -- O(N)
     *
     * @param n 第n项
     * @return 第n项值
     */
    public static int fibonacci(int n){
        if (n <= 1){
            return 1;
        }
        int last = 1;
        int nextToLast = 1;
        int answer = 1;

        for (int i = 2; i <= n; i++){
            answer = last + nextToLast;
            nextToLast = last;
            last = answer;
        }
        return answer;
    }
}
