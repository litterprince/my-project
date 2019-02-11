package ah.search;

/**
 * total ordering
 * input one array randomly
 * output the total ordering they can make
 */
public class TotalOrderingComputer {
    private int[] array;
    private int[] box;
    private int size;
    private int totalNum;

    public TotalOrderingComputer(int[] array) {
        this.array = array;
        this.size = array.length;
        this.box = new int[size];
    }

    public void compute(){
        dfs(0);
        System.out.println("total number: "+totalNum);
    }

    private void dfs(int n){
        if(n == size){
            totalNum ++;
            print();
            return;
        }

        for (int i = 0; i < array.length; i++) {
            int value = array[i];

            // check if used
            int pre = n-1;
            boolean isUsed = false;
            while (pre > -1){
                if(box[pre--] == value){
                    isUsed = true;
                }
            }
            if(isUsed) continue;

            // set value to current box
            box[n] = value;
            dfs(n+1);
        }
    }

    private void print(){
        for (int i = 0; i < box.length; i++) {
            System.out.print(box[i] + " ");
        }
        System.out.println();
    }
}
