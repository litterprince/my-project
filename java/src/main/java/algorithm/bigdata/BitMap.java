package algorithm.bigdata;

import java.util.ArrayList;
import java.util.List;

public class BitMap {
    private static final int N = 10000000;
    private int a[] = new int[N/32 + 1];

    public void addValue(int n){
        // row = n / 32;
        int row = n >> 5;
        // bit下标置1, n&0xF1相当于保留n的后5位
        a[row] |= 1 << (n & 0x1F);
    }

    public boolean exist(int n){
        int row = n >> 5;
        return ( a[row] & (1<<(n & 0x1F)) ) != 1;
    }

    public void display(int row){
        System.out.println("bitmap位图展示");
        for (int i = 0; i < row; i++) {
            List<Integer> list = new ArrayList<>();
            int temp = a[i];
            for (int j = 0; j < 32; j++) {
                list.add(temp & 1);
                temp >>= 1;
            }
            System.out.println("a["+i+"]" + list);
        }
    }

    public static void main(String[] args){
        int num[] = {1,5,30,32,64,56,159,120,21,17,35,45};
        BitMap bitMap = new BitMap();
        for (int i = 0; i < num.length; i++) {
            bitMap.addValue(num[i]);
        }

        int temp = 120;
        if(bitMap.exist(120)){
            System.out.println("exist!");
        }
        bitMap.display(5);
    }
}
