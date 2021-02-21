package com.company;

import java.util.Arrays;

public class ThreadWork {
    public static final int ROWS = 1000;
    public static final int COLS = 1000;
    public static final int NUMBER_THREADS = 10;
    public static final int RAND_MIN = 1;
    public static final int RAND_MAX = 50000;

    public static void main(String[] args) throws InterruptedException {

        int[][] array = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) { //початкове заповнення векторів випадковими величинами з зазначеного проміжку
            for (int j = 0; j < COLS; j++) {
                array[i][j] = RAND_MIN + (int) (Math.random() * RAND_MAX);
            }
        }
        long start = System.nanoTime();

        int serialMax = RAND_MIN - 1;
        int serialMin = RAND_MAX + 1;
        int localMax;
        int localMin;
        for (int i = 0; i < ROWS; i++) {
            localMax = Arrays.stream(array[i]).max().orElseThrow();
            localMin = Arrays.stream(array[i]).min().orElseThrow();
            if (localMax > serialMax) {
                serialMax = localMax;
            }
            if (localMin < serialMin) {
                serialMin = localMin;
            }
        }
        long end = System.nanoTime();
        long serialTime = (end - start) / 1000;
        System.out.println("Serial time:   " + serialTime);
        System.out.println("Serial min:   " + serialMin);
        System.out.println("Serial max:   " + serialMax);

        ThreadCalc[] ThreadArray = new ThreadCalc[NUMBER_THREADS];

        start = System.nanoTime();
        for (int i = 0; i < NUMBER_THREADS; i++) { //розбиття на потоки
            ThreadArray[i] = new ThreadCalc(array,
                    ROWS / NUMBER_THREADS * i,
                    i == (NUMBER_THREADS - 1) ? ROWS : ROWS / NUMBER_THREADS * (i + 1)); //тернарна умовна операція
            ThreadArray[i].start();
        }
        for (int i = 0; i < NUMBER_THREADS; i++) { //очікування завершення усіх потоків
            ThreadArray[i].join();
        }

        int parallelMax = RAND_MIN - 1;
        int parallelMin = RAND_MAX + 1;
        double parallelResult = 0;
        for (int i = 0; i < NUMBER_THREADS; i++) { //збір результатів паралельної роботи
            localMax = ThreadArray[i].getMax();
            localMin = ThreadArray[i].getMin();
            if (localMax > parallelMax) {
                parallelMax = localMax;
            }
            if (localMin < parallelMin) {
                parallelMin = localMin;
            }
        }

        end = System.nanoTime();
        long parallelTime = (end - start) / 1000;

        System.out.println("Parallel time:   " + parallelTime);
        System.out.println("Parallel min:   " + parallelMin);
        System.out.println("Parallel max:   " + parallelMax);
    }
}
