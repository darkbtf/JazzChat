package TaipeiHot.JazzChat.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import TaipeiHot.JazzChat.Util;

public class FtpUtils {
	public static FTPClient createFtpConnection(String ip, String port,
			String id, String pwd, boolean isSSL) throws NumberFormatException,
			SocketException, IOException, NoSuchAlgorithmException {
		FTPClient ftpClient;
		if (isSSL) {
			ftpClient = new FTPSClient();
		} else {
			ftpClient = new FTPClient();
		}
		// 登入ftp
		ftpClient.connect(ip, Integer.parseInt(port));
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			throw new RuntimeException("FTP連線失敗");
		} else {
			boolean ftpConnect = ftpClient.login(id, pwd);
			if (!ftpConnect) {
				throw new RuntimeException("FTP登入失敗[" + id + "," + pwd + "]");
			} else {
				// 設定ftp
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				return ftpClient;
			}
		}
	}

	/**
	 * 下載FTP檔案
	 * 
	 * @param ip
	 *            FTP IP
	 * @param port
	 *            FTP PORT
	 * @param id
	 *            FTP 帳號
	 * @param pwd
	 *            FTP密碼
	 * @param isSSL
	 *            是否透過SSL
	 * @param dir
	 *            FTP路徑
	 * @param fileName
	 *            檔名
	 * @param downloadPath
	 *            下載位置
	 * @return 是否下載成功
	 */
	public static boolean downloadFTPFile(String ip, String port, String id,
			String pwd, boolean isSSL, String dir, String fileName,
			String downloadPath) {
		boolean downloadFile = false;
		FTPClient ftpClient = null;
		InputStream is = null;
		try {
			ftpClient = createFtpConnection(ip, port, id, pwd, isSSL);
			boolean changeDir;
			if (/* StringUtils.isBlank(dir) */dir.equals("")) {// 當不需要切換路徑
				changeDir = true;
			} else {
				changeDir = ftpClient.changeWorkingDirectory(dir);
			}
			if (changeDir) {
				// 下載檔案
				if (ftpClient.listFiles(fileName).length > 0) {// 當檔案存在
					is = ftpClient.retrieveFileStream(fileName);
					if (is == null) {// 當檔案不存在
						Util.errorReport("FTP下載檔案[" + fileName + "]不存在");
					} else {// 當有檔案則轉成Stream
						OutputStream out = new FileOutputStream(new File(
								"C:\\Users\\User\\Downloads\\" + fileName));
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = is.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
						out.close();
						downloadFile = true;
					}
				}
			} else {
				Util.errorReport("FTP目錄[" + dir + "]不存在");
			}
		} catch (ConnectException e) {
			Util.errorReport("FTP連線失敗: " + e);
		} catch (Exception e) {
			Util.errorReport("下載FTP資料失敗:" + e);
			throw new RuntimeException(e);
		} finally {
			closeFtpConnection(ftpClient);// 中斷FTP連線
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// DO NOTHING
				}
			}
		}
		return downloadFile;
	}

	/**
	 * 關閉FTP連線
	 * 
	 * @param ftpClient
	 */
	public static void closeFtpConnection(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				// 可能因為已經關閉連線, do nothing
			}
		}
	}

	public static void uploadFTPFile(String ip, String port, String id,
			String pwd, boolean isSSL, String fileName) {
		FTPClient ftpClient = null;
		OutputStream os = null;
		try {
			ftpClient = createFtpConnection(ip, port, id, pwd, isSSL);
			InputStream is = new FileInputStream(
					new File(
							"C:\\Users\\Public\\Pictures\\Sample Pictures\\jellyfish.jpg"));
			if (!ftpClient.storeFile(fileName, is))
				System.out.println("fuck");
			else
				System.out.println("yeah");
		} catch (ConnectException e) {
			Util.errorReport("FTP連線失敗: " + e);
		} catch (Exception e) {
			Util.errorReport("下載FTP資料失敗:" + e);
			throw new RuntimeException(e);
		} finally {
			closeFtpConnection(ftpClient);// 中斷FTP連線
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// DO NOTHING
				}
			}
		}
	}

	public static void main(String args[]) {
		downloadFTPFile("140.112.18.198", "21", "nmlab198", "taipeihot", false,
				"", "jizz", "");
		uploadFTPFile("140.112.18.198", "21", "nmlab198", "taipeihot", false,
				"jizzer2.jpg");
	}
}
