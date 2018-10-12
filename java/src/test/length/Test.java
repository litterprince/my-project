package test.length;


public class Test {
    public static void main(String[] args) {
        System.out.println(getMessage());
        System.out.println(getMessage().length()+"字符");
        System.out.println(getMessage().getBytes().length+"字节");
    }

    private static String getMessage(){
        StringBuilder sb = new StringBuilder();
        int length = 34;
        for(int i=0;i<length;i++){
            sb.append(i);
        }
        return sb.toString();
    }
}
