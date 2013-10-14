package TaipeiHot.JazzChat.Client;

import java.awt.Canvas;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import TaipeiHot.JazzChat.UI.RoomWindow;

import com.sun.jna.NativeLibrary;

public class MediaUtils {

	private final static MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
	private static final String NATIVE_LIBRARY_SEARCH_PATH = "C:\\Program Files (x86)\\VideoLAN\\VLC";
	private static String mrl = "dshow://";
	public final static EmbeddedMediaPlayerComponent localMediaPlayer;
	public final static EmbeddedMediaPlayerComponent remoteMediaPlayer;

	static {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				NATIVE_LIBRARY_SEARCH_PATH);
		localMediaPlayer = new EmbeddedMediaPlayerComponent();
		remoteMediaPlayer = new EmbeddedMediaPlayerComponent();
	}

	public static void send() {
	}

	public static void receive() {
		remoteMediaPlayer.getMediaPlayer().playMedia("rtp://@" + mrl);
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

	static public void setLocalPlayer(int roomId) {
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		String[] localOptions = { formatRtpStream("230.0.0.1", 5555),
				":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all",
				":sout-keep", };

		Canvas localCanvas = new Canvas();
		CanvasVideoSurface localVideoSurface = mediaPlayerFactory
				.newVideoSurface(localCanvas);
		localMediaPlayer.getMediaPlayer().setVideoSurface(localVideoSurface);

		room.setLocalVideoFrame(localCanvas);
		localMediaPlayer.getMediaPlayer().playMedia(mrl, localOptions);
	}

	static public void setRemotePlayer(int roomId) {
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		//room.setRemoteVideoFrame(remoteMediaPlayer);

		// TODO: galagala
	}

}