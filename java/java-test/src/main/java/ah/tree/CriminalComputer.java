package ah.tree;

public class CriminalComputer {
    private int[] list;
    private int length;

    public static void main(String[] args){
        String content = "1,2\n"+
                "3,4\n"+
                "5,2\n"+
                "4,6\n"+
                "2,6\n"+
                "8,7\n"+
                "9,7\n"+
                "1,6\n"+
                "2,4\n";
        int[] list = new int[]{-1,1,2,3,4,5,6,7,8,9,10};
        CriminalComputer computer = new CriminalComputer(list);
        computer.compute(content);
    }

    public CriminalComputer(int[] list){
        this.list = list;
        this.length = list.length - 1;
    }

    public void compute(String content){
        String[] rows = content.split("\n");
        for(String row : rows){
            int l = Integer.parseInt(row.split(",")[0]);
            int r = Integer.parseInt(row.split(",")[1]);
            dfs(l, r);
        }
        printList();
        printResult();
    }

    private void dfs(int parent, int son){
        if(parent == son) return;

        // parent has parent
        parent = getParent(parent);

        // son already has parent
        if(list[son] != son && list[list[son]] != parent){
            dfs(parent, list[son]);
        }

        list[son] = parent;
    }

    private int getParent(int parent){
        return list[parent] == parent ? parent : getParent(list[parent]);
    }

    private void printList(){
        for (int i = 1; i <= length; i++) {
            System.out.print(list[i]+"\t");
        }
        System.out.println("\n");
    }

    private void printResult(){
        int num = 0;
        for (int i = 1; i <= length; i++) {
            if(list[i] == i)
                num ++;
        }
        System.out.println("there ara "+num+" gangs");
    }
}
