package ah.tree;

public class MinHeap {
    public static void main(String[] args){
        MinHeap minHeap = new MinHeap();
        // sort
        int[] list = new int[]{Integer.MAX_VALUE, 99,5,36,7,22,17,46,12,2,19,25,28,1,92};
        minHeap.heapSort(list, list.length-1);
        // big n
        list = new int[]{Integer.MAX_VALUE, 99,5,36,7,22,17,46,12,2,19,25,28,1,92};
        minHeap.bigN(list, list.length-1, 3);
    }

    public void heapSort(int[] list, int len){
        buildMinHeap(list, len);

        System.out.print("heapSort: ");
        for (int i = len; i > 0; i--) {
            int temp = list[1];
            list[1] = list[i];
            siftDown(list,1, i);
            System.out.print(temp+"\t");
        }
        System.out.println("\n");
    }

    public void bigN(int[] list, int len, int n){
        int[] bigN = new int[n+1];
        System.arraycopy(list,1,bigN,1,n);
        siftDown(bigN, 1, n);

        for (int i = n+1; i <= len; i++) {
            if(list[i] > bigN[1]) {
                bigN[1] = list[i];
                siftDown(bigN, 1, n);
            }
        }
        System.out.println("big "+n+": "+bigN[1]);
    }

    /**
     * build min heap
     */
    private void buildMinHeap(int[] list, int len){
        for (int i = len/2; i > 0; i--) {
            siftDown(list, i, len);
        }
    }

    private void siftDown(int[] list, int index, int len){
        if(index <= 0) return;

        // loop
        boolean needSwap = true;
        while (needSwap){
            needSwap = false;
            int temp = index;

            // left child
            if (index * 2 <= len && list[index * 2] < list[temp]) {
                temp = index * 2;
            }

            // right child
            if (index * 2 + 1 <= len && list[index * 2 + 1] < list[temp]) {
                temp = index * 2 + 1;
            }

            if(temp != index){
                swap(list, index, temp);
                index = temp;
                needSwap = true;
            }
        }
    }

    private void swap(int[] list, int index, int target){
        int temp = list[target];
        list[target] = list[index];
        list[index] = temp;
    }

    private void printTree(int list[], int len){
        int n = (int) Math.ceil(Math.log(len) / Math.log(2));
        int level = 1;
        while (level <= n){
            int start = 1<<(level - 1);
            int end = (1<<level) - 1;
            while (start <= end && start <= len){
                System.out.print(list[start] + "\t");
                start ++;
            }
            System.out.println();
            level++;
        }
        System.out.println();
    }

    private void printList(int[] list, int len){
        for (int i = 1; i <= len; i++) {
            System.out.print(list[i]+"\t");
        }
        System.out.println("\n");
    }
}
