package main.java.com.error;

public class test {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try {
            f();
        } catch (Throwable e1) {
            for( ; ;){
                sb.append(e1.getMessage()).append("\n");
                if(e1.getCause() != null){
                    e1 = e1.getCause();
                    continue;
                }
                break;
            }
        }
        System.out.println(sb.toString());
    }
    static void f() {
        try {
            throw new E1("test"); //抛出E1
        } catch (E1 e) {
            throw new RuntimeException("ceshi", e); //把E1包装进RuntimeException，E1成了它的cause
        }
    }

    static class E1 extends Exception{
        E1(String message){
            super(message);
        }
    }
}
