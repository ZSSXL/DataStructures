package com.zss.structures.tips.randomized;

/**
 * @author ZSS
 * @date 2022/6/29 11:16
 * @desc 48比特随机数发生器
 */
public class Random48 {

    private static final long A = 25_214_903_917L;
    private static final long B = 48;
    private static final long C = 11;
    private static final long M = (1L << B);
    private static final long MASK = M - 1;

    private int state;

    public Random48 (){
        state = (int) (System.nanoTime() & MASK);
    }

    public int randomInt(){
        try {
            return next(32);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double randomDouble(){
        try {
            return (((long)(next(26)) << 27 ) + next(27) / (double) (1L << 53));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0.00;
    }

    private int next(int bits) throws IllegalAccessException {
        if (bits <= 0 || bits > 32){
            throw new IllegalAccessException();
        }
        state = (int) ((A * state + C) & MASK);
        return (int) (state >>> (B - bits));
    }
}
