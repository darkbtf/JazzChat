package TaipeiHot.JazzChat;

import java.util.Deque;
import java.util.Queue;

public class Function {

	public static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) value };
	}
	
	public static Boolean parseByte(Deque<Byte> bufferInput, Queue<String> messages){
		if(bufferInput.size() < Parameter.bytesForLength)return false;
		Byte[] tmp=new Byte[Parameter.bytesForLength];
		int length=0;
		for(int i=0;i<Parameter.bytesForLength;i++){
			tmp[i] = bufferInput.pollFirst();
			length = (length << 1) + tmp[i].intValue(); 
		}
		if(bufferInput.size() < length){
			for(int i=Parameter.bytesForLength-1;i>=0;i--)
				bufferInput.addFirst(tmp[i]);
			return false;
		}
		String data = "";
		byte[] b = new byte[length];
		for(int i=0;i<length;i++)
			b[i]=bufferInput.pollFirst();
        data += new String(b, 0, length);
        messages.add(data);
        return true;
	}
}