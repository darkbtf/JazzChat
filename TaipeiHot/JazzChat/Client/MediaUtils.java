package TaipeiHot.JazzChat.Client;

import java.awt.Canvas;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.UI.RoomWindow;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class MediaUtils {

	private final static MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
	private static final String NATIVE_LIBRARY_SEARCH_PATH = "C:\\Program Files (x86)\\VideoLAN\\VLC";
	private static String mrl = "dshow://";
	public final static EmbeddedMediaPlayerComponent localMediaPlayer = new EmbeddedMediaPlayerComponent();;
	public final static EmbeddedMediaPlayerComponent remoteMediaPlayer = new EmbeddedMediaPlayerComponent();;

	static {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				NATIVE_LIBRARY_SEARCH_PATH);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
	}

	private static String formatRtpStream(String serverAddress, int serverPort) {
		StringBuilder sb = new StringBuilder(60);
		sb.append(":sout=#transcode{vcodec=mp4v,vb=2048,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:duplicate{dst=display,dst=rtp{dst=");
		sb.append(serverAddress);
		sb.append(",port=");
		sb.append(serverPort);
		sb.append(",mux=ts}}");
		return sb.toString();
	}

	static public void setLocalPlayer(int roomId, String remoteIp) {
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		String[] localOptions = { formatRtpStream(remoteIp, 5555),
				":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all",
				":sout-keep", };
		Canvas localCanvas = new Canvas();
		CanvasVideoSurface localVideoSurface = mediaPlayerFactory
				.newVideoSurface(localCanvas);
		localMediaPlayer.getMediaPlayer().setVideoSurface(localVideoSurface);

		localCanvas.setBounds(380, 270, 150, 110);
		room.videoWindow.jLayeredPane1.add(localCanvas,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		localMediaPlayer.getMediaPlayer().playMedia(mrl, localOptions);
		setRemotePlayer(roomId);
	}

	static public void setRemotePlayer(int roomId) {
		String myIp = "";
		try {
			myIp = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
		}
		RoomWindow room = Client.mainWindow.getRoomById(roomId);

		Canvas remoteCanvas = new Canvas();
		CanvasVideoSurface remoteVideoSurface = mediaPlayerFactory
				.newVideoSurface(remoteCanvas);
		remoteMediaPlayer.getMediaPlayer().setVideoSurface(remoteVideoSurface);

		remoteCanvas.setBounds(0, 0, 480, 340);
		room.videoWindow.jLayeredPane1.add(remoteCanvas,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		remoteMediaPlayer.getMediaPlayer()
				.playMedia("rtp://@" + myIp + ":5555");
	}

}