public class ParetoSleyter {
    static int[][] arr = new int[][]{
            {98, 90, 24, 90, 9, 81, 19, 36, 32, 55, 94, 4, 79, 69, 73, 76, 50, 55, 60, 42},
            {79, 84, 93, 05, 21, 67, 04, 13, 61, 54, 26, 59, 44, 02, 02, 06, 84, 21, 42, 68},
            {28, 89, 72, 8, 58, 98, 36, 8, 53, 48, 03, 33, 54, 48, 90, 33, 67, 46, 68, 29}};
    static int[] arr1 = new int[]{98, 90, 24, 90, 9, 81, 19, 36, 32, 55, 94, 4, 79, 69, 73, 76, 50, 55, 60, 42,
            79, 84, 93, 05, 21, 67, 04, 13, 61, 54, 26, 59, 44, 02, 02, 06, 84, 21, 42, 68,
            28, 89, 72, 8, 58, 98, 36, 8, 53, 48, 03, 33, 54, 48, 90, 33, 67, 46, 68, 29};

    //домінація по парето
    static boolean ParetoDominant(int first, int second) {
        if ((first / 10 >= second / 10 && first % 10 > second % 10) || (first / 10 > second / 10 && first % 10 >= second % 10))
            return true;
        return false;
    }

    //домінація по слейтеру
    static boolean SleyterDominant(int first, int second) {
        if (first / 10 > second / 10 && first % 10 > second % 10) return true;
        return false;
    }

    static int[] Pareto(int array[]) {
        int[] res = new int[array.length];
        for (int i = 1; i < array.length; i++) {//по одному перевіряємо вхідний массив
            for (int j = 0; j < i; j++) {
                if (res[i] == 0 && res[j] == 0) {
                    if (ParetoDominant(array[j], array[i])) {//перевіряємо чи з массиву результатів немає елемента
                        // який домінує над новим елементом
                        res[i] = j + 1;
                        //"вилучаємо елемент" з множини результатів
                        break;
                    }

                    if (ParetoDominant(array[i], array[j])) {
                        //навпаки з минулим
                        res[j] = i + 1;
                        //"вилучаємо елемент" з множини результатів
                    }
                }
            }
        }

        return res;
    }

    static int[] Sleyter(int array[]) {
        int[] res = new int[array.length];
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (res[i] == 0 && res[j] == 0) {
                    if (SleyterDominant(array[j], array[i])) {//перевіряємо чи з массиву результатів немає елемента
                        // який домінує над новим елементом
                        res[i] = j + 1;
                        //"вилучаємо елемент" з множини результатів
                        break;
                    }
                    if (SleyterDominant(array[i], array[j])) {
                        //навпаки з минулим
                        res[j] = i + 1;
                        //"вилучаємо елемент" з множини результатів
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] ParetoRes = Pareto(arr1);
        int[] SlayterRes = Sleyter(arr1);
        for (int i = 0; i < 60; i++)
            System.out.print(arr1[i] + "\t");
        System.out.println();
        for (int i = 0; i < 60; i++) {
            if (ParetoRes[i] == 0)
                System.out.print(" " + "\t");
            else
                System.out.print("A" + (ParetoRes[i] + "") + "\t");
        }
        System.out.println();
        for (int i = 0; i < 60; i++) {
            if (SlayterRes[i] == 0)
                System.out.print(" " + "\t");
            else
                System.out.print("A" + (SlayterRes[i] + "") + "\t");
        }

    }
}
