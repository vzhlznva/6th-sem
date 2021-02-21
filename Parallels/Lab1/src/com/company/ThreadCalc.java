package com.company;

import java.util.Arrays;

class ThreadCalc extends Thread{

    int[][] array;
    int startIndex;
    int endIndex;
    int max = ThreadWork.RAND_MIN - 1;
    int min = ThreadWork.RAND_MAX + 1;

    public ThreadCalc(int[][] array, int startIndex, int endIndex) { //конструктор класу, приймає дані для обчислень
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }


    public int getMax() {
        return max;
    }
    public int getMin() { return min; }

    @Override
    public void run(){
        int localMax;
        int localMin;
        for(int i = startIndex; i<endIndex; i++ ){
            localMax= Arrays.stream(array[i]).max().orElseThrow();
            localMin= Arrays.stream(array[i]).min().orElseThrow();
            if (localMax > max){
                max = localMax;
            }
            if (localMin < min) {
                min = localMin;
            }
        }
    }
}

