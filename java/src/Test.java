import sun.misc.BASE64Encoder;

public class Test {
    public static void main(String[] args) {
        String value = "demo.test";
        String str = new BASE64Encoder().encode(value.getBytes());
        System.out.println(str);
    }
}
