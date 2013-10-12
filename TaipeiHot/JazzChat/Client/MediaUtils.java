package TaipeiHot.JazzChat.Client;

import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;

public class MediaUtils {
	public static void main(String[] args) {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		BufferedImage image = webcam.getImage();

	}
}