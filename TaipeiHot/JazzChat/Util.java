package TaipeiHot.JazzChat;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.Queue;

public class Util {
        public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
        }
	public static Boolean errorReport(String msg){
		System.out.println(msg);
		return false;
	}
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
			return Util.errorReport("Byte not enough");
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
