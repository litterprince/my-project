package design.decorator;

public class Client {
    public static void main(String[] args){
        String msg = "Jiqing9006";
        Cipher simpleCipher = new SimpleCipher();
        Cipher complexCipher = new ComplexCipher(simpleCipher);
        Cipher advanceCipher = new AdvancedCipher(simpleCipher);

        System.out.println("simple encrypt: "+ simpleCipher.encrypt(msg));
        System.out.println("complex encrypt: "+ complexCipher.encrypt(msg));
        System.out.println("advance encrypt: "+ advanceCipher.encrypt(msg));
    }
}
