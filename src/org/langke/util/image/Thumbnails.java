package org.langke.util.image;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Thumbnails {
	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
	
	/**
	 * 通过流来操作
	 * @param inputStream
	 * @param outputStream
	 * @param width
	 * @param hight
	 * @param formatName文件格式名称
	 * @throws Exception
	 */
	public static boolean createBlobThumbnails(InputStream inputStream,OutputStream outputStream,int targetW, int targetH,String formatName) throws Exception{
		BufferedImage srcImage;
		srcImage = ImageIO.read(inputStream);
		int imgType = srcImage.TYPE_INT_RGB;
		if (targetW > 0 || targetW > 0) {
			srcImage = resize(srcImage, targetW, targetW);
		}
		double sx = (double) targetW / srcImage.getWidth();
		double sy = (double) targetH / srcImage.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * srcImage.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * srcImage.getHeight());
		}		
		BufferedImage      outImg   =   new   BufferedImage(targetW,targetH,imgType); 
		outImg.getGraphics().drawImage(srcImage,   0,   0,   null); 
		JPEGImageEncoder   encoder   =   JPEGCodec.createJPEGEncoder(outputStream); 
		encoder.encode(outImg); 
		outputStream.close(); 

		return true;//ImageIO.write(srcImage, formatName, outputStream);
	}	
	public static boolean createBlobThumbnails(InputStream inputStream,String targetImage,int width, int hight,String formatName) throws Exception{
		BufferedImage srcImage;
		srcImage = ImageIO.read(inputStream);
		File targetFile = new File(targetImage);
		if (width > 0 || hight > 0) {
			srcImage = resize(srcImage, width, hight);
		}
		return ImageIO.write(srcImage, formatName, targetFile);
	}
	public static boolean createThumbnails(String fromImage,String targetImage,int width, int hight,String formatName) {
		try{
			BufferedImage srcImage;
			File fromFile = new File(fromImage);
			File targetFile = new File(targetImage);
			if(!targetFile.exists()){
				targetFile.mkdirs();
			}
			srcImage = ImageIO.read(fromFile);
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight);
			}
			 String ex = fromImage.substring(fromImage.indexOf(".")+1,fromImage.length());
			if(ex.toUpperCase().equals("PNG")) formatName = "PNG";
			if(ex.toUpperCase().equals("GIF")) formatName = "GIF";
			return ImageIO.write(srcImage, formatName, targetFile);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 保存到文件
	 * @param fromFileStr
	 * @param saveToFileStr
	 * @param width
	 * @param hight
	 * @throws Exception
	 */
	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr,
			int width, int hight) throws Exception {
		BufferedImage srcImage;
		// String ex =
		// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
		String imgType = "JPEG";
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		srcImage = ImageIO.read(fromFile);
		if (width > 0 || hight > 0) {
			srcImage = resize(srcImage, width, hight);
		}
		ImageIO.write(srcImage, imgType, saveFile);
	}

	public static void main(String argv[]) {
		try {
			// 参数1(from),参数2(to),参数3(宽),参数4(高)
			Thumbnails.saveImageAsJpg("E:/langke/My Pictures/qq/tnml0005.jpg", "h:/thumbnails/tnml0005.jpg", 128, 128);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

} 
