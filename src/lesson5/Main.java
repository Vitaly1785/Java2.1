package lesson5;



public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) throws InterruptedException {
        createArr1();
        createArr2();
    }

    public static void createArr1() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {

            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - a);

    }

    public static void createArr2() throws InterruptedException {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {

                a2[i] = (float) (a2[i] * Math.sin(0.2f + (i+HALF) / 5) * Math.cos(0.2f + (i+HALF)/ 5) * Math.cos(0.4f + (i + HALF)/ 2));
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - a);
    }

}

