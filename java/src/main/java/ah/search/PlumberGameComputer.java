package ah.search;

/**
 * plumber game
 * problem describe:
 * find the shortest way by change pipe's direction
 * Rules:
 * first, there are two type of pipes,straight and curved can change different directions
 * second, the tree sign means there's no way to go through
 * finally, start at (1,1) and end at (n,m)
 */
public class PlumberGameComputer {
    private int[][] book;
    private int[][] map;
    private int n;
    private int m;
    private boolean exist;

    public PlumberGameComputer(int n, int m, String content) {
        this.n = n;
        this.m = m;
        book = new int[n][m];
        map = new int[n][m];
        initMap(content);
    }

    private void initMap(String content) {
        String[] c = content.split("\n");
        for (int i = 0; i < c.length; i++) {
            String line = c[i];
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = Integer.valueOf(String.valueOf(line.charAt(j)));
            }
        }
    }

    public void printMap(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void compute(){
        dfs(0, 0, Front.LEFT.getValue());

        if(!exist){
            System.out.println("sorry, there's no solution to fix it!");
        }
    }

    /**
     *
     * @param x x
     * @param y y
     * @param front water intake's direction
     */
    private void dfs(int x, int y, int front){
        // if find
        if(x == n-1 && y == m && front == Front.LEFT.getValue()){
            exist = true;
            System.out.print("we find the solution and it is:");
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if(book[i][j] == 1) {
                        System.out.print("(" + i + " , " + j + ") ");
                    }
                }
            }
            System.out.println();
            return;
        }

        // if out of range
        if(x >= n || y >= m || x < 0 || y < 0){
            return;
        }

        // if is visited
        if(book[x][y] == 1){
            return;
        }

        // set visited
        book[x][y] = 1;

        int pipe = map[x][y];
        // pipe is straight
        if(pipe == Pipe.S_Left_Right.getValue() || pipe == Pipe.S_Top_Bottom.getValue()){
            int nextX = 0, nextY = 0, nextFront = 0;
            // water intake
            if(front == Front.LEFT.getValue()){
                map[x][y] = Pipe.S_Left_Right.getValue();
                nextX = x;
                nextY = y + 1;
                nextFront = Front.LEFT.getValue();
            }
            if(front == Front.RIGHT.getValue()){
                map[x][y] = Pipe.S_Left_Right.getValue();
                nextX = x;
                nextY = y - 1;
                nextFront = Front.RIGHT.getValue();
            }
            if(front == Front.BOTTOM.getValue()){
                map[x][y] = Pipe.S_Top_Bottom.getValue();
                nextX = x - 1;
                nextY = y;
                nextFront = Front.BOTTOM.getValue();
            }
            if(front == Front.TOP.getValue()){
                map[x][y] = Pipe.S_Top_Bottom.getValue();
                nextX = x + 1;
                nextY = y;
                nextFront = Front.TOP.getValue();
            }
            dfs(nextX, nextY, nextFront);
        }

        // if pipe is curved
        if(pipe == Pipe.C_Left_Bottom.getValue() || pipe == Pipe.C_Left_Top.getValue() || pipe == Pipe.C_Right_Bottom.getValue() || pipe == Pipe.C_Right_Top.getValue()){
            int next1X = 0, next1Y = 0, nextFront1 = 0;
            int next2X = 0, next2Y = 0, nextFront2 = 0;
            if(front == Front.LEFT.getValue() || front == Front.RIGHT.getValue()){
                // left in and go bottom
                map[x][y] = Pipe.C_Left_Bottom.getValue();
                next1X = x + 1;
                next1Y = y ;
                nextFront1 = Front.TOP.getValue();

                //left in and go top
                map[x][y] = Pipe.C_Left_Top.getValue();
                next2X = x -1;
                next2Y = y;
                nextFront2 = Front.BOTTOM.getValue();
            }
            if(front == Front.RIGHT.getValue()){
                // right in and go bottom
                map[x][y] = Pipe.C_Right_Bottom.getValue();
                next1X = x + 1;
                next1Y = y ;
                nextFront1 = Front.TOP.getValue();

                // right in and go top
                map[x][y] = Pipe.C_Right_Top.getValue();
                next2X = x -1;
                next2Y = y;
                nextFront2 = Front.BOTTOM.getValue();
            }
            if(front == Front.BOTTOM.getValue()){
                // bottom in and go left
                map[x][y] = Pipe.C_Left_Bottom.getValue();
                next1X = x;
                next1Y = y-1 ;
                nextFront1 = Front.RIGHT.getValue();

                // bottom in and go right
                map[x][y] = Pipe.C_Right_Bottom.getValue();
                next2X = x;
                next2Y = y+1;
                nextFront2 = Front.LEFT.getValue();
            }
            if(front == Front.TOP.getValue()){
                // top in and go left
                map[x][y] = Pipe.C_Left_Top.getValue();
                next1X = x;
                next1Y = y-1 ;
                nextFront1 = Front.RIGHT.getValue();

                // top in go right
                map[x][y] = Pipe.C_Left_Top.getValue();
                next2X = x;
                next2Y = y+1;
                nextFront2 = Front.LEFT.getValue();
            }
            dfs(next1X, next1Y, nextFront1);
            dfs(next2X, next2Y, nextFront2);
        }

        book[x][y] = 0;
    }

    private enum Pipe {
        S_Left_Right(5, "Straight,left-right"), S_Top_Bottom(6, "Straight,top-bottom"), C_Left_Bottom(3, "Curved,left-bottom"),
        C_Right_Bottom(2, "curved,right-bottom"), C_Left_Top(4, "Curved,left-top"), C_Right_Top(1, "curved,right-top");

        private int value;
        private String msg;

        private Pipe(int value, String msg){
            this.value = value;
            this.msg = msg;
        }

        public int getValue() {
            return value;
        }
    }

    private enum Front {
        RIGHT(0), LEFT(1), TOP(2), BOTTOM(3);

        private int value;

        private Front(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
