package ah.search;

/**
 * test search
 */
public class AMain {
    private static int[] array = new int[]{1,2,3,4};

    public static void main(String[] args){
        testPlumberGame();
    }

    /**
     *
     */
    public static void testTotalOrdering(){
        TotalOrderingComputer ordering = new TotalOrderingComputer(array);
        ordering.compute();
    }

    public static void testBomberMan(){
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

    public static void testPlumberGame(){
        String content = "5353\n" +
                "1530\n" +
                "2351\n" +
                "6115\n" +
                "1554";
        PlumberGameComputer computer = new PlumberGameComputer(5, 4, content);
        computer.printMap();
        computer.compute();
    }
}
