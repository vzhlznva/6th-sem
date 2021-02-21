package com.company;

import java.util.Arrays;

public class ThreadWork {
    public static final int ROWS = 5000;
    public static final int COLS = 6000;
    public static final int NUMBER_THREADS = 4;
    public static final int RAND_MIN = 1;
    public static final int RAND_MAX = 250000000;
    public static final int TESTS = 10;
    public static final int TABLE_HEADER = 2;
    public static final int TABLE_FOOTER = 2;

    public static void main(String[] args) throws InterruptedException {

        final Object[][] resultsTable = new String[TABLE_HEADER + TESTS + TABLE_FOOTER][];
        resultsTable[0] = new String[]{"", "Serial", "", "", "Parallel", ""};
        resultsTable[1] = new String[]{"min", "max", "time (us)", "min", "max", "time (us)"};
        long[] serialTimes = new long[TESTS];
        long[] parallelTimes = new long[TESTS];

        for (int test_i = 0; test_i < TESTS; test_i++) {
            int[][] array = new int[ROWS][COLS];
            for (int i = 0; i < ROWS; i++) {
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
            serialTimes[test_i] = serialTime;


            start = System.nanoTime();
            ThreadCalc[] ThreadArray = new ThreadCalc[NUMBER_THREADS];

            for (int i = 0; i < NUMBER_THREADS; i++) {
                ThreadArray[i] = new ThreadCalc(array,
                        ROWS / NUMBER_THREADS * i,
                        i == (NUMBER_THREADS - 1) ? ROWS : ROWS / NUMBER_THREADS * (i + 1));
                ThreadArray[i].start();
            }
            for (int i = 0; i < NUMBER_THREADS; i++) {
                ThreadArray[i].join();
            }

            int parallelMax = RAND_MIN - 1;
            int parallelMin = RAND_MAX + 1;
            for (int i = 0; i < NUMBER_THREADS; i++) {
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
            parallelTimes[test_i] = parallelTime;

            resultsTable[TABLE_HEADER + test_i] = new String[]{String.valueOf(serialMin), String.valueOf(serialMax), String.valueOf(serialTime), String.valueOf(parallelMin), String.valueOf(parallelMax), String.valueOf(parallelTime)};
        }

        resultsTable[TABLE_HEADER + TESTS] = new String[]{"", "", "", "", "", ""};
        resultsTable[TABLE_HEADER + TESTS + 1] = new String[]{"", "Average: ", String.valueOf(Arrays.stream(serialTimes).average().orElse(-1)), "", "Average:", String.valueOf(Arrays.stream(parallelTimes).average().orElse(-1))};
        System.out.println("Rows = " + String.valueOf(ROWS));
        System.out.println("Cols = " + String.valueOf(COLS));
        System.out.println("Number of threads  = " + String.valueOf(NUMBER_THREADS));
        System.out.println("");

        for (final Object[] row : resultsTable) {
            System.out.format("%10s%10s%10s%10s%10s%10s%n", row);

        }
    }
}
