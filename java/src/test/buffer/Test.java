package test.buffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.io.UnsupportedEncodingException;

public class Test {
    private static final int LARK_INITIAL_SIZE = 10;
    private static final int LARK_LENGTH_SIZE = 4;
    private static final int MESSAGE_FRAME_SIZE = 4;

    public static void main(String[] args) throws UnsupportedEncodingException {
        testBuffer();
    }

    private static String getMessage(){
        StringBuilder sb = new StringBuilder();
        int length = 10;
        for(int i=0;i<length;i++){
            sb.append(i);
        }
        return sb.toString();
    }

    public static void testBuffer(){
        String str = "hello";
        ChannelBuffer buffer = ChannelBuffers.buffer(LARK_LENGTH_SIZE  + str.length());
        buffer.writeInt(str.length());
        buffer.writeBytes(str.getBytes());


        //int l = buffer.getInt(4);
        buffer.readerIndex(4);
        System.out.println(buffer.readableBytes());

        /*int length = buffer.readInt();
        byte[] bts = new byte[length];
        buffer.readBytes(bts);
        System.out.println(new String(bts, "utf-8"));*/
    }
}
