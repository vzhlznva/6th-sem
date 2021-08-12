import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Choice {
    //всі наші речі з ціною та вагою
    static enum Clothes{BLAYSER(0.5,6),BUSHLAT(4,48),WATNI_SHTANU(2,24),
        VETNAMKI(0.5,6),JEANS(1,12),KEPKA(0.5,6),KROSIVKU(1,12),
        KURTKA(2,24),PALTO(3,36),RUKAVUZI(0.5,6),SWETR(1,12),
        SOROCHKA(0.5,6),FUTBOLKA(0.5,6),CHEREVIKI(1.5,18),
        CHOBOTY(2,24),SHAPKA(1,12),SHORTY(0.5,6);
        public int price;
        public double weight;
        Clothes( double weight, int price){
            this.price = price;
            this.weight = weight;
        }
        int getPrice(){
            return this.price;
        }
        double getWeight() {
            return this.weight;
        }
    };
    //набори одягу
    static Clothes[][] clothesSets = new Clothes[][]{{Clothes.SHAPKA,Clothes.BUSHLAT,Clothes.RUKAVUZI,Clothes.WATNI_SHTANU,Clothes.CHOBOTY},
            {Clothes.SHAPKA,Clothes.PALTO,Clothes.RUKAVUZI,Clothes.JEANS,Clothes.CHOBOTY},
            {Clothes.KEPKA,Clothes.KURTKA,Clothes.JEANS,Clothes.KROSIVKU},
            {Clothes.SWETR,Clothes.JEANS,Clothes.KROSIVKU},
            {Clothes.BLAYSER,Clothes.SOROCHKA,Clothes.JEANS,Clothes.KROSIVKU},
            {Clothes.BLAYSER, Clothes.FUTBOLKA, Clothes.SHORTY,Clothes.VETNAMKI}
    };
    //температури
    static int [] temperature = new int[]{-15, -12, -6, 0, 7, 13, 16, 14,	8, 2, -5, -10};

    //функція отримання витрат в залежності від місяця
    static int[] getLoses(int clothesSetNumber){
        int[] result = new int[12];
        int taxForTransportation = 0;
        int setPrice = 0;
        //розрахунок витрат на транспортування та на купівлю завчасно
        for (int i = 0; i < clothesSets[clothesSetNumber].length; i++) {
            taxForTransportation+=(int)(clothesSets[clothesSetNumber][i].getWeight()*10);
            setPrice += clothesSets[clothesSetNumber][i].getPrice();
        }
        System.out.println("for " + (clothesSetNumber + 1 + " ") + "Set we have to pay for set " + (setPrice +""));
        for (int i = 0; i < 12; i++) {
            System.out.print(" to buy ");
            //розраховуємо який сет оптимальний для нашої температури
            int setNumber = temperature[i] < -10 ? 0: (temperature[i] - 1) / 10 + 2;
            for(int j = 0; j < clothesSets[setNumber].length;j++){
                //перевіряємо чи є потрібний нам предмет у нас в сеті
                boolean found = false;
                for (int k = 0; k < clothesSets[clothesSetNumber].length; k++) {
                    if(clothesSets[setNumber][j]==clothesSets[clothesSetNumber][k]) found = true;
                }
                if(!found){
                    System.out.print( clothesSets[setNumber][j]+ " ");
                    result[i] += (clothesSets[setNumber][j].getPrice()+2);
                }
            }
            System.out.println("in " + (Month.of(i+1)+" ") + "and it will cost " + (result[i]+""));
        }
        for (int i = 0; i < 12; i++) {
            result[i]+= (taxForTransportation + setPrice);
        }
        System.out.println(" and to pay for transportation " + (taxForTransportation+""));
        return result;
    }
    //задання вірогідностей де всі одакові
    static double[] getProbabilities(){
        double result[] = new double[12];
        for (int i = 0; i < 12; i++) {
            result[i] = 1./12;
        }
        return result;
    }
    //вірогідності для кожного сезону
    static double[] getProbabilities2(){
        double result[] = new double[12];
        for (int i = 0; i < 12; i++) {
            result[i] = 1./3;
        }
        return result;
    }
    //вірогідності коли зимою більше
    static double[] getProbabilities3(){
        double result[] = new double[12];
        for (int i = 0; i < 12; i++) {
            result[i] = 1./18;
        }
        result[0] *=3;
        result[1] *=3;
        result[11] *=3;
        return result;
    }
    //вірогідності в залежності від кількості днів
    static double[] getProbabilities4(){
        double result[] = new double[12];
        result[0] =31./365;
        result[1] =28./365;
        result[2] =31./365;
        result[3] =30./365;
        result[4] =31./365;
        result[5] =30./365;
        result[6] =31./365;
        result[7] =31./365;
        result[8] =30./365;
        result[9] =31./365;
        result[10] =30./365;
        result[11] =31./365;
        return result;
    }
    public static void main(String[] args) {
        System.out.println(Month.of(1));
        int[][] arr = new int[6][];
        for (int i = 0; i < 6; i++) {
            arr[i]=getLoses(i);
            System.out.println("--------------------------");
        }
        for (int i = 0; i < 6; i++) {
            double[] prob = getProbabilities();
            double loses = 0;
            for (int j = 0; j < 12; j++){
                loses += arr[i][j]*prob[j];
            }
            System.out.println("for " +(i+" set")+ " we have loses "+ (loses+""));
        }
        System.out.println("--------------------------");
        for (int i = 0; i < 6; i++) {
            double[] prob = getProbabilities2();

            System.out.println("for " +(i+" set")+ " we have loses in Winter "+ ((arr[i][0]*prob[0]+arr[i][1]*prob[1]+arr[i][11]*prob[11])+""));
            System.out.println("in Spring "+ ((arr[i][2]*prob[2]+arr[i][3]*prob[3]+arr[i][4]*prob[4])+""));
            System.out.println("in Summer "+ ((arr[i][5]*prob[5]+arr[i][6]*prob[6]+arr[i][7]*prob[7])+""));
            System.out.println("in Autamn "+ ((arr[i][8]*prob[8]+arr[i][9]*prob[9]+arr[i][10]*prob[10])+""));


        }
        System.out.println("--------------------------");
        for (int i = 0; i < 6; i++) {
            double[] prob = getProbabilities3();
            double loses = 0;
            for (int j = 0; j < 12; j++){
                loses += arr[i][j]*prob[j];
            }
            System.out.println("for " +(i+" set")+ " we have loses "+ (loses+""));
        }
        System.out.println("--------------------------");
        for (int i = 0; i < 6; i++) {
            double[] prob = getProbabilities4();
            double loses = 0;
            for (int j = 0; j < 12; j++){
                loses += arr[i][j]*prob[j];
            }
            System.out.println("for " +(i+" set")+ " we have loses "+ (loses+""));
        }
        System.out.println("--------------------------");
        Clothes.BUSHLAT.price/=3;
        Clothes.VETNAMKI.price/=3;
        Clothes.PALTO.price/=3;
        Clothes.CHOBOTY.price/=3;
        Clothes.SHAPKA.price/=3;
        for (int i = 0; i < 6; i++) {
            arr[i]=getLoses(i);
        }
        for (int i = 0; i < 6; i++) {
            double[] prob = getProbabilities();
            double loses = 0;
            for (int j = 0; j < 12; j++){
                loses += arr[i][j]*prob[j];
            }
            System.out.println("for " +(i+" set")+ " we have loses "+ (loses+""));
        }
    }
}

