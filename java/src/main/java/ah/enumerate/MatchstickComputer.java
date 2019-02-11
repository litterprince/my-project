package ah.enumerate;

/**
 * problem describe：
 * if has n matchsticks, hope you to list all expressions such as A+B=C
 * for example there's 14 matchsticks，can work out 0+1=1 and 1+0=1
 * Note：
 * first, both of equals and plus needs two matchsticks
 * second, they are valid between A+B=C and B+A=C
 * third, all matchsticks must be used
 */
public class MatchstickComputer {
    private final static int[] NUM_COST = new int[]{6,2,5,5,4,5,6,3,7,6};
    private final static int PLUS_COST = 2;
    private final static int EQUALS_COST = 2;

    /**
     * A + B = C
     * list all expressions like above, every matchstick must be used
     * @param size the number of matchstick
     */
    public void compute(int size){
        int A,B,C;

        // first, calculate the max value
        int max = getMax(size);


        // start calculate
        for (int i = 0; i < max; i++) {
            A = getCost(i);
            for (int j = 0; j < max; j++) {
                B = getCost(j);
                C = getCost((i+j));
                if(A + B + C == size - PLUS_COST - EQUALS_COST){
                    System.out.println(i + " + " + j + " = " + (i+j));
                }
            }
        }
    }

    private int getCost(int number){
        int cost = 0;
        char[] c = String.valueOf(number).toCharArray();
        for (int i = 0; i < c.length; i++) {
            cost += NUM_COST[Integer.valueOf(String.valueOf(c[i]))];
        }
        return cost;
    }

    /**
     * 因为题目中m根(m<=24)火柴棍，除去"+"号和"="号占用的4根火柴棍，那么最多剩下20根火柴棍。
     * 而0~9这10个数字中，数字1所需火柴棍最少(2根)，而20根火柴棍最多只能组成10个1。
     * 因此A+B=C这个等式中A,B,C中的任意一个数都不能超过11111。(因为等号两边要相等）
     * @param size size
     * @return max
     */
    private int getMax(int size){
        int n = size / getCost(1);
        n = n / 2;
        char[] c = new char[n];
        for (int i = 0; i < n; i++) {
            c[i] = '1';
        }
        return Integer.valueOf(String.valueOf(c));
    }
}
