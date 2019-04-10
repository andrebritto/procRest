package procuracoes.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import procuracoes.model.Procuracao;

public class BarCodeUtil {
	/*
	 * https://chart.googleapis.com/chart?cht=qr&chs=256x256&chld=L|1&chl={%
	 * 22niTitular%22:%2200000000000191%22,%22niProcurador%22:%2268281455500%22,%
	 * 22vigente%22:false}
	 * 
	 */

	public static void gravaImagemNoDisco(BufferedImage bi) {
		try {
			ImageIO.write(bi, "png", new File("/home/68281455500/git/procRest/qrCode.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BufferedImage geraQrCode(Procuracao p) {
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = null;
		BufferedImage bi = null;
		Gson json = new Gson();

		try {

			System.out.println(json.toJson(p));
			matrix = writer.encode(json.toJson(p), BarcodeFormat.QR_CODE, 256, 256);
			bi = MatrixToImageWriter.toBufferedImage(matrix);
			gravaImagemNoDisco(bi);			

		} catch (WriterException e) {
			e.printStackTrace();
		}

		return bi;
	}

	public static String leQrCode(BufferedImage bi) {
		String resultado = "";

		// 1
		LuminanceSource source = new BufferedImageLuminanceSource(bi);

		// 2
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		// 3
		Reader reader = new MultiFormatReader();

		// 4
		try {
			Result result = reader.decode(bitmap);
			resultado = result.getText();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChecksumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado;
	}

	public static void main(String[] args) {
		Procuracao p = new Procuracao();
		p.setNiProcurador("68281455500");
		p.setNiTitular("00000000000191");

		// BufferedImage qrCodeImg = geraQrCode(p);

		BufferedImage qrCodeImg;
		try {
			qrCodeImg = ImageIO.read(new File("/home/68281455500/git/procRest/qrCode.png"));
			System.out.println("QRCode: " + leQrCode(qrCodeImg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
