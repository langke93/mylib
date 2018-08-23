package org.langke.util.image;   
/**  
 *   添加水印
 */  
import java.awt.AlphaComposite;   
import java.awt.Color;   
import java.awt.Graphics2D;   
import java.awt.Image;   
import java.awt.image.*;   
import java.io.*;   
import java.util.ArrayList;   
import java.util.Iterator;   
import java.util.List;   
  
import javax.swing.*;   
import com.sun.image.codec.jpeg.*;   
  
/**  
 * @author lizz  
 *   
 */  
  
public class WaterMark {   
    private static List logoImgs;   
    private static List bgImgs ;   
    private static float logoPosition = 0.65f;   
    private static float logoSrcOver = 0.7f;   
    private static float bgSrcOver = 0.7f;   
       
    private static boolean flag;//为true时生成背景水印，为false时生成图标水印   
       
    public static List getBgImgs() {   
        return bgImgs;   
    }   
  
    /**  
     * 设置背景水印图片的路径并把图片加载到内存  
     * @param bgImgUrls  
     * @param path  
     */  
    public static void setBgImgs(List bgImgUrls, String path) {   
        bgImgs = new ArrayList();   
        for (Iterator it = bgImgUrls.iterator(); it.hasNext(); ) {   
            String tem = (String)it.next();   
            ImageIcon waterIcon = new ImageIcon(path + tem);   
            bgImgs.add(waterIcon.getImage());   
        }   
    }   
    public static void setBgImg(String file_path) {   
        	bgImgs = new ArrayList();   
            ImageIcon waterIcon = new ImageIcon(file_path);   
            bgImgs.add(waterIcon.getImage());
    }  
  
    public static List getLogoImgs() {   
        return logoImgs;   
    }   
  
    /**  
     * 设置图标水印图片的路径并把图片加载到内存  
     * @param logoImgUrls  
     * @param path  
     */  
    public static void setLogoImgs(List logoImgUrls, String path) {   
        logoImgs = new ArrayList();   
        for (Iterator it = logoImgUrls.iterator(); it.hasNext(); ) {   
            String tem = (String)it.next();   
            ImageIcon waterIcon = new ImageIcon(path + tem);   
            logoImgs.add(waterIcon.getImage());   
        }   
    }   
    public static void setLogoImg(String file_path) {   
        logoImgs = new ArrayList();      
            ImageIcon waterIcon = new ImageIcon(file_path);   
            logoImgs.add(waterIcon.getImage());   
    }   
  
    public static float getLogoPosition() {   
        return logoPosition;   
    }   
  
    public static void setLogoPosition(float logoPosition) {   
        WaterMark.logoPosition = logoPosition;   
    }   
  
    public static float getBgSrcOver() {   
        return bgSrcOver;   
    }   
  
    public static void setBgSrcOver(float bgSrcOver) {   
        WaterMark.bgSrcOver = bgSrcOver;   
    }   
  
    public static float getLogoSrcOver() {   
        return logoSrcOver;   
    }   
  
    public static void setLogoSrcOver(float logoSrcOver) {   
        WaterMark.logoSrcOver = logoSrcOver;   
    }   
  
    /**  
     * 图片中添加图标水印并输出到指定流  
     * @param data  
     * @param out  
     * @param channel  
     * @return  
     * @throws Exception  
     */  
    public static boolean createLogoMark(byte[] data,    
            FileOutputStream out) throws Exception {   
        int i = (int)(logoImgs.size() * Math.random());   
        return createMark(data, out, (Image)logoImgs.get(i), true);   
    }   
       
    /**  
     * 图片中添加背景水印并输出到指定流  
     * @param data  
     * @param out  
     * @param channelName  
     * @return  
     * @throws Exception  
     */  
    public static boolean createBgMark(byte[] data,    
            FileOutputStream out, String channelName) throws Exception {   
        int i = (int)(bgImgs.size() * Math.random());   
        return createMark(data, out, (Image)bgImgs.get(i), false);   
    }   
       
    /**  
     * 生成随机水印并输出到指定流  
     * @param data  
     * @param out  
     * @return  
     * @throws Exception  
     */  
    public static boolean createRandomMark(byte[] data,    
            FileOutputStream out) throws Exception {   
        int i = 0;   
        Image temImg = null;   
        int logoSize = logoImgs.size();   
        int bgSize = bgImgs.size();   
        if (logoSize <= 0 && bgSize <= 0) {   
            return createMark(data, out, temImg, flag);   
        }   
        if (logoSize <= 0) {   
            flag = true;   
        } else if (bgSize <= 0) {   
            flag = false;   
        }   
        if (!flag) {   
            i = (int)(logoImgs.size() * Math.random());   
            temImg = (Image)logoImgs.get(i);   
        } else {   
            i = (int)(bgImgs.size() * Math.random());   
            temImg = (Image)bgImgs.get(i);   
        }    
        flag = !flag;   
        return createMark(data, out, temImg, flag);   
    }   
       
    /**  
     * 生成水印并输出到指定流  
     * @param data 图片的二进制数据  
     * @param out 处理后图片的输出流  
     * @param waterImg 水印图片的类型（背景或图标），应与isLogoImg参数一致,如果为空不加水印  
     * @param isLogoImg 等于true 时生成图标水印，否则为背景水印  
     * @return  
     * @throws Exception  
     */  
    private static boolean createMark(byte[] data, FileOutputStream out,    
            Image waterImg ,boolean isLogoImg) throws Exception {   
        ImageIcon imgIcon = new ImageIcon(data);   
        Image theImg = imgIcon.getImage();   
  
        int width = theImg.getWidth(null);   
        int height = theImg.getHeight(null);   
        int w = waterImg == null ? 0 : waterImg.getWidth(null);   
        int h = waterImg == null ? 0 : waterImg.getHeight(null);   
        if (w == 0 || h == 0 || width <= w || height <= h) {//小图片不加水印真接输入,如头像图片   
            BufferedOutputStream fout = null;   
            ByteArrayInputStream in = new ByteArrayInputStream(data);   
            try {   
                byte[] b = new byte[1024*10];   
                fout = new BufferedOutputStream(out);   
                while(in.read(b) > 0) {   
                    out.write(b);   
                }   
                out.flush();   
                out.close();   
                in.close();   
            } catch(Exception e) {   
                e.printStackTrace();   
                throw e;   
            } finally {   
                if (in != null) {   
                    in.close();   
                }   
                if (fout != null) {   
                    fout.close();   
                }   
            }   
            return true;   
        }   
               
        BufferedImage bimage = new BufferedImage(width, height,   
                BufferedImage.TYPE_INT_RGB);   
        Graphics2D g = bimage.createGraphics();   
        g.setBackground(Color.white);   
        g.drawImage(theImg, 0, 0, null);   
           
        if (isLogoImg) {//生成图标水印   
            //设置图片透明度   
            g.setComposite(AlphaComposite   
                    .getInstance(AlphaComposite.SRC_OVER, logoSrcOver));   
               
            width = (int)(width * logoPosition) - w;   
            height = (int)(height * logoPosition) - h;   
               
            width = width < 0 ? 0 : width;   
            height = height < 0 ? 0 : height;   
               
            g.drawImage(waterImg, width, height, null);   
        } else {//生成背景水印   
            //'0.2f'设置字体透明度   
            g.setComposite(AlphaComposite   
                    .getInstance(AlphaComposite.SRC_OVER, bgSrcOver));   
            int sw = (width % w) /(width / w + 1);//填充水印图片横向间距   
            int sh = (height % h) /(height / h + 1);//填充水印图片纵向间距   
            for(int i = sw; i + w + sw <= width; i += w + sw) {   
                for (int j = sh; j + h + sh <= height; j += h + sh) {   
                    g.drawImage(waterImg, i, j, null);   
                }   
            }   
        }   
        g.dispose();   
        try {   
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);   
            param.setQuality(80f, true);   
            encoder.encode(bimage);   
        } catch (Exception e) {   
            throw e;   
        } finally {   
            if (out != null) {   
                out.close();   
            }   
        }   
        return true;   
    }   
  
    
    public  static void main(String[] args) throws Exception{
    	WaterMark.setLogoImg("e:/waterMarker.png"); //水印图片
    	FileInputStream fileInputStream = new FileInputStream("e:/house_pic/house2.jpg");//需要被添加水印的图片
    	int bytesRead = 0;
        byte[] buffer = new byte[fileInputStream.available()];
        fileInputStream.read(buffer);
        String str = new String(buffer,"iso8859-1");   

        
    	WaterMark.createLogoMark(str.getBytes("iso8859-1"), new FileOutputStream("e:/test1.JPG")); //输出的图片
    }
} 
