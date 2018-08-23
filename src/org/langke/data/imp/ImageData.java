package org.langke.data.imp;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
public class ImageData {
    public int[][] data;
    public int w;
    public int h;
    public char code;
    
    public ImageData() {
    }
    
    public ImageData(BufferedImage bi) {
        this(bi,new WhiteFilter());
    }
    public ImageData(BufferedImage bi,Filter filter) {
        h = bi.getHeight();
        w = bi.getWidth();
        data = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int p = bi.getRGB(j, i);
                data[i][j] = p;
            }
        }
        filter.doFilter(data);
    }
    public ImageData[] split() {
        ArrayList list = new ArrayList();
        ImageIterator ite = new ImageIterator(this);
        while (ite.hasNext()) {
            list.add(ite.next());
        }
        return (ImageData[]) list.toArray(new ImageData[0]);
    }
    int skipEmpty(int begin, boolean isX, int value) {
        if (isX) {
            for (int i = begin; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if (data[j][i] != value) {
                        return i;
                    }
                }
            }
            return -1;
        } else {
            for (int i = begin; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (data[i][j] != value) {
                        return i;
                    }
                }
            }
            return -1;
        }
    }
    int skipEntity(int begin, boolean isX, int value) {
        if (isX) {
            for (int i = begin; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if (data[j][i] == value) {
                        break;
                    }
                    if (j == h - 1)
                        return i;
                }
            }
            return -1;
        } else {
            for (int i = begin; i < h; i++) {

      
      
                for (int j = 0; j < w; j++) {
                    if (data[i][j] == value) {
                        break;
                    }
                    if (j == w - 1)
                        return i;
                }
            }
            return -1;
        }
    }
    class ImageIterator implements Iterator {
        int x;
        ImageData ia;
        ImageData next;
        public ImageIterator(ImageData ia) {
            this.ia = ia;
        }
        public boolean hasNext() {
            if (next != null)
                return true;
            next = getNext();
            return next != null;
        }
        ImageData getNext() {
            int x1 = skipEmpty(x, true, 0);
            if (x1 == -1) {
                return null;
            }
            int x2 = skipEntity(x1, true, 1);
            if (x2 == -1) {
                x2 = w;
            }
            x = x2;
            int y1 = skipEmpty(0, false, 0);
            if (y1 == -1)
                return null;
            int y2 = skipEntity(y1, false, 1);
            if (y2 == -1)
                y2 = h;
            return ia.clone(x1, y1, x2 - x1, y2 - y1);
        }
        public Object next() {
            ImageData temp = next;
            next = null;
            return temp;
        }
        public void remove() {
        }
    }
    ImageData clone(int x, int y, int w0, int h0) {
        ImageData ia = new ImageData();
        ia.w = w0;
        ia.h = h0;
        ia.data = new int[ia.h][ia.w];
        for (int i = 0; i < h0; i++) {
            for (int j = 0; j < w0; j++) {
                ia.data[i][j] = data[i + y][j + x];
            }
        }
        return ia;
    }
    public void show() {
        System.out.println();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print((data[i][j] == 1 ? "1" : " ") + "");
            }
            System.out.println();
        }
        System.out.println();
    }
    public int hashCode() {
        int code = w ^ h;
        int count = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (data[i][j] == 1)
                    count++;
            }
        }
        code ^= count;
        return code;
    }
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ImageData) {
            ImageData o = (ImageData) obj;
            if (o.h != h)
                return false;
            if (o.w != w)
                return false;
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (o.data[i][j] != data[i][j])
                        return false;
                }
            }
            return true;
        } else {
            return false;

      
      
        }
    }
    public static ImageData[] decodeFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(
                new File(path)));
        String line;
        ArrayList list = new ArrayList();
        while ((line = reader.readLine()) != null) {
            ImageData ia = decode(line);
            if (ia != null) {
                list.add(ia);
            }
        }
        return (ImageData[]) list.toArray(new ImageData[0]);
    }
    public static ImageData decode(String s) {
        String[] ss = s.split("\\,", 4);
        if (ss.length != 4)
            return null;
        if (ss[0].length() != 1)
            return null;
        ImageData ia = new ImageData();
        ia.code = ss[0].charAt(0);
        ia.w = Integer.parseInt(ss[1]);
        ia.h = Integer.parseInt(ss[2]);
        if (ss[3].length() != ia.w * ia.h) {
            return null;
        }
        ia.data = new int[ia.h][ia.w];
        for (int i = 0; i < ia.h; i++) {
            for (int j = 0; j < ia.w; j++) {
                if (ss[3].charAt(i * ia.w + j) =='1') {
                    ia.data[i][j] = 1;
                } else {
                    ia.data[i][j] = 0;
                }
            }
        }
        return ia;
    }
    public String encode() {
        StringBuffer sb = new StringBuffer();
        sb.append(code).append(",");
        sb.append(w).append(",");
        sb.append(h).append(",");
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (data[i][j] == 1) {
                    sb.append('1');
                } else {
                    sb.append('0');
                }
            }
        }
        return sb.toString();
    }
}
