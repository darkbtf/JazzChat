package TaipeiHot.JazzChat.Client;

import javax.swing.JFrame;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import TaipeiHot.JazzChat.UI.RoomWindow;

import com.sun.jna.NativeLibrary;

public class MediaUtils {

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
		String[] localOptions = { formatRtpStream("230.0.0.1", 5555),
				":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all",
				":sout-keep", };

		// localMediaPlayer.getMediaPlayer().playMedia(mrl, localOptions);
		localMediaPlayer.getMediaPlayer().playMedia(mrl, localOptions);
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

	static public void main(String args[]) {
		JFrame frame = new JFrame("vlcj video chat");
		// frame.setIconImage(new ImageIcon(getClass().getResource(
		// "/icons/vlcj-logo.png")).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		frame.setContentPane(localMediaPlayer);
		// frame.add(remoteMediaPlayer);
		frame.setVisible(true);
		send();
		// receive();
	}

	static public void setLocalPlayer(int roomId) {
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		room.setLocalVideoFrame(localMediaPlayer);
	}

	static public void setRemotePlayer(int roomId) {
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		room.setRemoteVideoFrame(remoteMediaPlayer);
	}

}