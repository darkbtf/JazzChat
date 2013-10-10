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

import TaipeiHot.JazzChat.Util;

public class FtpUtils {

	final static String IP = "140.112.18.198";
	final static int PORT = 21;
	final static String ID = "nmlab198";
	final static String PASSWORD = "password";

	public static FTPClient createFtpConnection() throws NumberFormatException,
			SocketException, IOException, NoSuchAlgorithmException {
		FTPClient ftpClient;
		ftpClient = new FTPClient();

		// 登入ftp
		ftpClient.connect(IP, PORT);
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			throw new RuntimeException("FTP連線失敗");
		} else {
			boolean ftpConnect = ftpClient.login(ID, PASSWORD);
			if (!ftpConnect)
				throw new RuntimeException("FTP登入失敗[" + ID + "," + PASSWORD
						+ "]");
		}
		return ftpClient;
	}

	public static FTPClient createFtpConnection(String ip, String port,
			String id, String pwd, boolean isSSL) throws NumberFormatException,
			SocketException, IOException, NoSuchAlgorithmException {
		FTPClient ftpClient;

		ftpClient = new FTPClient();

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

	public static boolean downloadFTPFile(String fileName, String downloadPath,
			String myDownloadPath) {

		boolean downloadFile = false;
		FTPClient ftpClient = null;
		InputStream is = null;
		try {

			ftpClient = createFtpConnection();
			if (ftpClient.listFiles(downloadPath).length > 0) {
				is = ftpClient.retrieveFileStream(downloadPath);
				if (is == null) {
					Util.errorReport("FTP下載檔案[" + fileName + "]不存在");
				} else {
					OutputStream out = new FileOutputStream(new File(
							myDownloadPath + fileName));
					int read = 0;
					byte[] bytes = new byte[1024];

					while ((read = is.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}
					out.close();
					downloadFile = true;
				}

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

	public static void uploadFTPFile(String filePath, String fileName,
			int fileId) {
		FTPClient ftpClient = null;
		OutputStream os = null;
		try {
			ftpClient = createFtpConnection();
			InputStream is = new FileInputStream(new File(filePath));
			ftpClient.storeFile(fileName, is);

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
			Client.finishUpload(fileId);
		}
	}
}
