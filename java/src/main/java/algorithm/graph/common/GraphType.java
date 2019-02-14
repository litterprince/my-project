package algorithm.graph.common;

public enum GraphType {
    Array(0, "array"), Link(1, "link");

    private int code;
    private String name;

    private GraphType(int code, String name){
        code = code;
        name = name;
    }
}
