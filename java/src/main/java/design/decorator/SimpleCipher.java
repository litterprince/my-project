package design.decorator;

public class SimpleCipher implements Cipher {
    @Override
    public String encrypt(String msg) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<msg.length();i++) {
            char c = msg.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c += 6;
                if (c > 'z') c -= 26;
                if (c < 'a') c += 26;
            }
            if (c >= 'A' && c <= 'Z') {
                c += 6;
                if (c > 'Z') c -= 26;
                if (c < 'A') c += 26;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
