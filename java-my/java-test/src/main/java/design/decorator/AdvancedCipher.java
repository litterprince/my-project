package design.decorator;

public class AdvancedCipher extends CipherDecorate {
    AdvancedCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    public String encrypt(String msg){
        return mod(super.encrypt(msg));
    }

    private String mod(String text) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<text.length();i++) {
            String c = String.valueOf(text.charAt(i)%6);
            sb.append(c);
        }
        return sb.toString();
    }
}
