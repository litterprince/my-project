package design.decorator;

public class CipherDecorate implements Cipher {
    private Cipher cipher;

    CipherDecorate(Cipher cipher){
        this.cipher = cipher;
    }

    @Override
    public String encrypt(String msg){
        return cipher.encrypt(msg);
    }
}
