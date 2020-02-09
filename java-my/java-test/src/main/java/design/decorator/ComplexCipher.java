package design.decorator;

public class ComplexCipher extends CipherDecorate {
    ComplexCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    public String encrypt(String msg) {
        return reverse(super.encrypt(msg));
    }

    private String reverse(String text) {
        StringBuilder sb = new StringBuilder();
        for(int i=text.length();i>0;i--) {
            sb.append(text.substring(i-1,i));
        }
        return sb.toString();
    }
}
