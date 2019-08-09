package algorithm.sort;

public class SortTest {
    private static int[] numbers = {2, 3, 1, 4, 0, 8, 6};

    public static void main(String[] args){
        mergeSort(numbers, 0, numbers.length-1);
        printInfo(numbers);
    }

    /**
     *  冒泡法排序
     *  比较相邻的元素。如果第一个比第二个小，就交换他们两个。
     *  对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最小的数。
     *  针对所有的元素重复以上的步骤，除了最后一个。
     *  持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     *
     * @param numbers 需要排序的整型数组
     */
    public static void bubbleSort(int[] numbers){
        if(numbers == null || numbers.length < 1)
            return;

        int temp;
        for(int j=1;j<numbers.length;j++) {
            for (int i=0; i < numbers.length-j; i++) {
                if (numbers[i] > numbers[i + 1]) {
                    temp = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = temp;
                }
            }
        }

        printInfo(numbers);
    }

    /**
     * 选择排序
     * 在未排序序列中找到最小元素，存放到起始位置
     * 再从剩余未排序元素中继续寻找最小元素，然后放到起始位置
     * 以此类推，直到所有元素均排序完毕。
     *
     * @param numbers 需要排序的整型数组
     */
    public static void selectSort(int[] numbers){
        if(numbers == null || numbers.length < 1)
            return;

        int index, temp;
        for(int j=0;j<numbers.length;j++){
            index = j;
            for(int i=j;i<numbers.length&&numbers[i] < numbers[index];i++){
                if(numbers[i] < numbers[index]){
                    temp = numbers[index];
                    numbers[index] = numbers[i];
                    numbers[i] = temp;
                }
            }
        }

        printInfo(numbers);
    }

    /**
     * 插入排序
     *
     *  从第一个元素开始，该元素可以认为已经被排序
     *  取出下一个元素，在已经排序的元素序列中从后向前扫描
     *  如果该元素（已排序）大于新元素，将该元素移到下一位置
     *  重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     *  将新元素插入到该位置中
     *  重复步骤2
     * @param numbers 需要排序的整型数组
     */
    public static void insertSort(int[] numbers){
        if(numbers == null || numbers.length < 1)
            return;

        int size = numbers.length, temp, j;
        for(int i=1; i<size; i++) {
            temp = numbers[i];
            for(j = i; j > 0 && temp < numbers[j-1]; j--) {
                numbers[j] = numbers[j - 1];
            }
            numbers[j] = temp;
        }

        printInfo(numbers);
    }

    /**
     * 归并排序   
     *
     *  申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列 
     *  设定两个指针，最初位置分别为两个已经排序序列的起始位置   
     *  比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置 
     *  重复步骤3直到某一指针达到序列尾   
     *  将另一序列剩下的所有元素直接复制到合并序列尾  
     *
     * @param numbers 需要排序的整型数组
     */
    public static void mergeSort(int[] numbers, int start, int end){
        if(start >= end)
            return;

        int mid = (start + end)/2;
        mergeSort(numbers, start, mid);
        mergeSort(numbers,mid+1, end);
        //左右归并
        merge(numbers, start, mid, end);
    }
    private static void merge(int[] data, int start, int mid, int end) {
        int[] B = new int[data.length];
        int lStart = start;
        int rStart = mid + 1;
        int index = start;
        do {
            if (data[lStart] <= data[rStart]) {
                B[index++] = data[lStart++];
            } else {
                B[index++] = data[rStart++];
            }
        }while(lStart <= mid && rStart <= end);

        while(lStart<=mid) {
            B[index++] = data[lStart++];
        }
        while(rStart<=end) {
            B[index++] = data[rStart++];
        }

        /*for (int i = start; i <= end; i++) {
            data[i] = B[i];
        }*/
        System.arraycopy(B, start, data, start, end-start+1);
    }

    /**
     * 快速排序
     *
     *  从数列中挑出一个元素，称为“基准”
     *  重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分割之后，
     *  该基准是它的最后位置。这个称为分割（partition）操作。
     *  递归地把小于基准值元素的子数列和大于基准值元素的子数列排序。
     *  注意：一定是右哨兵先走（这样左右哨兵碰面的时候是停在了比基准小的数字上面）
     *
     * @param numbers 需要排序的整型数组
     */
    public static void quickSort(int[] numbers, int start, int end){
        if(start > end)
            return;

        int base = numbers[start];
        int i=start, j=end, temp;
        while (i < j){
            while(i < j && numbers[j] >= base){
                j --;
            }
            while(i < j && numbers[i] <= base){
                i ++;
            }
            if(i < j) {
                temp = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = temp;
            }
        }

        // i equals j, base equals numbers[start]
        // change base
        numbers[start] = numbers[i];
        numbers[i] = base;

        quickSort(numbers, start, j-1);
        quickSort(numbers, j+1, end);
    }

    private static void printInfo(int[] numbers){
        if(numbers == null || numbers.length < 1)
            return;

        for(int i=0;i<numbers.length;i++){
            System.out.print(numbers[i]);
        }
        System.out.println();
    }
}
