package org.langke.util.file;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
 
  
public class FileUtil {

	/**
	 * 取文件内容
	 * @param fileName
	 * @return
	 */
	public StringBuffer getFile(String fileName){
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)),"UTF-8"));
			while ((line = br.readLine()) != null) {
				stringBuffer.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuffer;
	}
	
	/**
	 * 判断文件编码
	 * @param file
	 * @return
	 */
	public static String getCharset( File file ) {   
        String charset = "GBK";   
        byte[] first3Bytes = new byte[3];   
        try {   
            boolean checked = false;   
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );   
            bis.mark( 0 );   
            int read = bis.read( first3Bytes, 0, 3 );   
            if ( read == -1 ) return charset;   
            if ( first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE ) {   
                charset = "UTF-16LE";   
                checked = true;   
            }   
            else if ( first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF ) {   
                charset = "UTF-16BE";   
                checked = true;   
            }   
            else if ( first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF ) {   
                charset = "UTF-8";   
                checked = true;   
            }   
            bis.reset();   
            if ( !checked ) {   
            //    int len = 0;   
                int loc = 0;   
  
                while ( (read = bis.read()) != -1 ) {   
                    loc++;   
                    if ( read >= 0xF0 ) break;   
                    if ( 0x80 <= read && read <= 0xBF ) // 单独出现BF以下的，也算是GBK   
                    break;   
                    if ( 0xC0 <= read && read <= 0xDF ) {   
                        read = bis.read();   
                        if ( 0x80 <= read && read <= 0xBF ) // 双字节 (0xC0 - 0xDF) (0x80   
                                                                        // - 0xBF),也可能在GB编码内   
                        continue;   
                        else break;   
                    }else if ( 0xE0 <= read && read <= 0xEF ) {// 也有可能出错，但是几率较小   
                        read = bis.read();   
                        if ( 0x80 <= read && read <= 0xBF ) {   
                            read = bis.read();   
                            if ( 0x80 <= read && read <= 0xBF ) {   
                                charset = "UTF-8";   
                                break;   
                            }   
                            else break;   
                        }   
                        else break;   
                    }   
                }   
                //System.out.println( loc + " " + Integer.toHexString( read ) );   
            }   
  
            bis.close();   
        } catch ( Exception e ) {   
            e.printStackTrace();   
        }   
  
        return charset;   
    }   

	public static void deleteFile(File oldPath) {
		if (oldPath.isDirectory() && oldPath.exists()) {
			File[] files = oldPath.listFiles();
			for (File file : files) {
				deleteFile(file);
			}
		} else if(oldPath.exists()){
			oldPath.delete();
		}
	}
	/**
	 * String转JDOM Element
	 * @param xmlString
	 * @return
	 */
/*	public static Element getJdomElement(String xmlString){
		xmlString = xmlString.replaceAll("UTF-8", "gb2312");//UTF-8无法识别中文
		InputStream inputStream;
		SAXBuilder builder = new SAXBuilder();
		Document document;
		Element element = null;
		try {
			inputStream = new ByteArrayInputStream(xmlString.getBytes());
			document = builder.build(inputStream);		      
		    element = document.getRootElement(); // 获得根节点
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}*/
	
	/**
	 * 把文件读成二进制
	 * 
	 * @param filePath
	 *            文件全路径(含扩展名)
	 * @param txt
	 *            内容
	 * @return
	 */
	public byte[] readFile_TO_Byte(String srcFile) {
		File f1 = new File(srcFile);// 源文件
		try {
			BufferedInputStream my_in = new BufferedInputStream(
					new FileInputStream(f1));

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int ch;
			byte[] buffer = new byte[1024*200];
			while ((ch = my_in.read(buffer)) != -1) {
				bos.write(buffer,0,ch);//ch为缓冲区读到的长度，此操作可截掉最后一部分空的数组
			}
			my_in.close();

			byte[] bs = bos.toByteArray();
			bos.close();
			return bs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 把文件读成字符串
	 * 
	 * @param filePath
	 *            文件全路径(含扩展名)
	 * @param txt
	 *            内容
	 * @return
	 */
	public String readFile_TO_String(String srcFile) {
		return new String (this.readFile_TO_Byte(srcFile));

	}
	
	/**
	 * 把srcFile源文件，复制到另一个地方destFile(已经改名); 这个2个文件都是全路径
	 * 
	 * @param filePath
	 *            文件全路径(含扩展名)
	 * @param txt
	 *            内容
	 * @return
	 */
	public boolean writeFile(String srcFile, String destFile) {
		File f1 = new File(srcFile);// 源文件
		File f2 = new File(destFile);// 生成的目的文件
		if(f2.exists()){
			f2.delete();
		}
		int n;
		try {
			BufferedInputStream my_in = new BufferedInputStream(
					new FileInputStream(f1));
			FileOutputStream fout = new FileOutputStream(f2);
			BufferedOutputStream my_out = new BufferedOutputStream(fout);
			byte[] b = new byte[10000];

			while ((n = my_in.read(b)) != -1) {
				// sb.append(new String(b));
				my_out.write(b, 0, n);
			}

			// my_out.write(sb.toString().getBytes());
			my_out.flush();
			my_out.close();
			fout.close();
			my_in.close();
			
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
 
	/**
	 * 
	 * @param destFile
	 * @param content
	 * @return
	 */
	public boolean writeFile(String destFile,byte[] content){
		FileOutputStream fos = null;
		try {
			File f2 = new File(destFile);// 生成的目的文件
			if(f2.exists()){
				f2.delete();
			}
			fos = new FileOutputStream(destFile);
			BufferedOutputStream my_out = new BufferedOutputStream(fos);
			my_out.write(content);
			my_out.flush();
			my_out.close();
			fos.close();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public static void FileLog(String fileName,String content){
		try{
			FileWriter fileWriter=new FileWriter(fileName,true);//true参数表追加文件
			fileWriter.write(content);
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**  
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true  
     *   
     * @param fileName  
     * @param content  
     */  
    public static void method1(String file, String conent) {   
        BufferedWriter out = null;   
        try {   
            out = new BufferedWriter(new OutputStreamWriter(   
                    new FileOutputStream(file, true)));   
            out.write(conent);   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                out.close();   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
    }
	public static void main(String[] args) {
		FileUtil f = new FileUtil();
		try {
			// System.out.println(new
			// String(f.readFile_TO_Byte("c:/Sunset.jpg")));
			FileOutputStream fout = new FileOutputStream("c:/0.jpg");
			BufferedOutputStream my_out = new BufferedOutputStream(fout);
			my_out.write(f.readFile_TO_Byte("c:/Sunset.jpg"));
			my_out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
