package ah.search;

/**
 * problem describe:
 * find the right position that put bomb on and make the largest number of ghosts die
 * Note:
 * first, the position is reachable
 */
public class BomberManComputer {
    private Node[][] map;
    private int n = 0 ,m = 0;

    private Node[] queue;
    private int head = 0, tail = 0;

    private int bestX = 0, bestY = 0;
    private int maxDestroy = Integer.MIN_VALUE;

    public static void main(String[] args){
        String content = "#############\n" +
                "#GG.GGG#GGG.#\n" +
                "###.#G#G#G#G#\n" +
                "#.......#..G#\n" +
                "#G#.###.#G#G#\n" +
                "#GG.GGG.#.GG#\n" +
                "#G#.#G#.#.#.#\n" +
                "##G...G.....#\n" +
                "#G#.#G###.#G#\n" +
                "#...G#GGG.GG#\n" +
                "#G#.#G#G#.#G#\n" +
                "#GG.GGG#G.GG#\n" +
                "#############";
        BomberManComputer computer = new BomberManComputer(13, 13, content);
        computer.printMap();
        computer.compute(3,3);
    }

    public BomberManComputer(int n, int m, String content) {
        this.n = n;
        this.m = m;
        map = new Node[n][m];
        queue = new Node[n*m];
        initMap(content);
    }

    private void initMap(String content){
        String[] c = content.split("\n");
        for (int x = 0; x < c.length; x++) {
            String row = c[x];
            for (int y = 0; y < row.length(); y++) {
                String type = String.valueOf(row.charAt(y));
                Node node;
                switch (type){
                    case "#":
                        node = new Node(x, y, Type.WALL);
                        break;
                    case ".":
                        node = new Node(x, y, Type.ROAD);
                        break;
                    case "G":
                        node = new Node(x, y, Type.GHOST);
                        break;
                    default:
                        node = new Node(x, y, Type.WALL);
                        break;
                }
                map[x][y] = node;
            }
        }
    }

    public void printMap(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j].getType().getValue());
            }
            System.out.println();
        }
    }

    /**
     * first, enumerate every position and calculate the number
     * second, judge current position is reachable by using dfs method before calculate
     * finally, the best position comes
     */
    public void compute(int initX, int initY){
        queue[tail++] = map[initX][initY];
        while (head < tail){
            Node node = queue[head++];
            int x = node.getX();
            int y = node.getY();
            if(node.isVisited())
                continue;

            node.setVisited(true);

            // if it's a road
            if(node.getType().getValue().equals(Type.ROAD.getValue())) {
                // calculate current position
                int cost = getDestroy(x, y);
                if (cost > maxDestroy) {
                    maxDestroy = cost;
                    bestX = x;
                    bestY = y;
                }

                // go right
                addNode(x + Go.RIGHT.getX(), y + Go.RIGHT.getY());

                // go bottom
                addNode(x + Go.BOTTOM.getX(), y + Go.BOTTOM.getY());

                // go left
                addNode(x + Go.LEFT.getX(), y + Go.LEFT.getY());

                // go top
                addNode(x + Go.TOP.getX(), y + Go.TOP.getY());
            }
        }

        System.out.println("best place is ("+bestX+" , "+bestY +")");
        System.out.println("it can destroy "+ maxDestroy +" ghosts");
    }

    private void addNode(int x, int y){
        if(x < n && y < n){
            Node node = map[x][y];
            if(!node.isVisited() && node.getType().getValue().equals(Type.ROAD.getValue())){
                queue[tail++] = node;
            }
        }
    }

    /**
     * calculate the number
     * @param x x
     * @param y y
     * @return num
     */
    private int getDestroy(int x, int y){
        int num = 0;

        // go right
        for (int j = y + 1; j < m; j++) {
            String type = map[x][j].getType().getValue();
            if(type.equals(Type.WALL.getValue())){
                break;
            }
            // if current nde is ghost
            if(type.equals(Type.GHOST.getValue())){
                num ++;
            }
        }

        // go left
        for (int j = y - 1; 0 < j; j--) {
            String type = map[x][j].getType().getValue();
            if(type.equals(Type.WALL.getValue())){
                break;
            }
            if(type.equals(Type.ROAD.getValue())){
                continue;
            }
            num ++;
        }

        // go top
        for (int i = x -1; i > 0; i++) {
            String type = map[i][y].getType().getValue();
            if(type.equals(Type.WALL.getValue())){
                break;
            }
            // if current nde is ghost
            if(type.equals(Type.GHOST.getValue())){
                num ++;
            }
        }

        // go bottom
        for (int i = x + 1; i < n; i++) {
            String type = map[i][y].getType().getValue();
            if(type.equals(Type.WALL.getValue())){
                break;
            }
            if(type.equals(Type.ROAD.getValue())){
                continue;
            }
            num ++;
        }
        return num;
    }

    private class Node {
        private int x;
        private int y;
        private Type type;
        private boolean visited;

        private Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, Type type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Type getType() {
            return type;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }

    private enum Type{
        WALL("#"), GHOST("G"), ROAD(".");

        private String value;

        private Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private enum Go {
        RIGHT(0,1), BOTTOM(1,0), LEFT(0,-1), TOP(-1, 0);

        private int x;
        private int y;

        private Go(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
