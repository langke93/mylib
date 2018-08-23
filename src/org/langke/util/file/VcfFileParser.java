package org.langke.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 对导出rtx通讯录进行去重，导出脚本rtxexp.hta
 * @author langke
 *2014/6/17
 */
public class VcfFileParser
{
    private static String BEGIN = "BEGIN:VCARD";
    private static String END = "END:VCARD";

    /**
     * "VERSION", "N", "FN", "TEL", "PHOTO"
     */
    private static String[] keys = new String[] { "VERSION", "N", "FN", "TEL;TYPE=WORK", "EMAIL;type=INTERNET;type=WORK;type=pref","TEL;TYPE=CELL","ORG","CATEGORIES" };

    public String fileName;
    public List<String> list = new ArrayList<String>();

    public List<Vcard> getVcards()
    {
        return vcards;
    }

    public List<Vcard> vcards = new ArrayList<Vcard>();

    public VcfFileParser(String fileName)
    {
        this.fileName = fileName;
    }

    public void parse() throws Exception
    {
        File f = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String str = null;
        StringBuilder buf = new StringBuilder();
        int i = 0;
        while ((str = reader.readLine()) != null)
        {
            i++;
            if ("".equals(str))
            {
                continue;
            }
            else if (BEGIN.equals(str))
            {
                buf.delete(0, buf.length());
            }
            else if (END.equals(str))
            {
                this.list.add(buf.toString());
            }
            else
            {
                buf.append(str + "\n");
            }
        }
        System.out.println("parse " + i + " lines");
        reader.close();
        for (String s : list)
            vcards.add(parseVcard(s));
    }

    public Vcard parseVcard(String s)
    {
        String[] ss = s.split("\n");
        Vcard v = new Vcard();
        for (String tmp : ss)
        {
            if (tmp.startsWith(keys[0]))
            {
                v.version = tmp.substring(tmp.indexOf(":") + 1);
            }
            else if (tmp.startsWith(keys[1]))
            {
                if (tmp.indexOf("CHARSET") >= 0)
                    v.n = utf8_to_string(tmp.substring(tmp.lastIndexOf(":") + 2, tmp.length() - 3));
                else
                {
                    v.n = tmp.substring(tmp.indexOf(";") + 1, tmp.length() - 3);
                }
            }
            else if (tmp.startsWith(keys[2]))
            {
                if (tmp.indexOf("CHARSET") >= 0)
                    v.fn = utf8_to_string(tmp.substring(tmp.lastIndexOf(":") + 1));
                else
                {
                    v.fn = tmp.substring(tmp.indexOf(":") + 1);
                }
            }
            else if (tmp.startsWith(keys[3]))
            {
                v.telWork =(tmp.substring(tmp.lastIndexOf(":") + 1));
            }
            else if (tmp.startsWith(keys[4]))
            {
                v.email =(tmp.substring(tmp.lastIndexOf(":") + 1));

            }
            else if (tmp.startsWith(keys[5]))
            {
                v.telCel =(tmp.substring(tmp.lastIndexOf(":") + 1));

            }
            else if (tmp.startsWith(keys[6]))
            {
                v.org =(tmp.substring(tmp.lastIndexOf(":") + 1));

            }
            else if (tmp.startsWith(keys[7]))
            {
                v.categories =(tmp.substring(tmp.lastIndexOf(":") + 1));

            }
        }
        return v;
    }

    public void output()
    {
        int i = 1;
        for (Vcard s : vcards)
            System.out.println(i++ + " " + s.toString());
    }

    class Vcard implements Comparable<Object>
    {
        String version;
        String n;
        String fn;
        String telWork;
        String email;
        String telCel;
        String org;
        String categories;

        public String toString()
        {
            return BEGIN+"\nVERSION:" + version + "\nN:" + n + "\nFN:" + fn + "\nTEL;TYPE=WORK:"+telWork+"\nEMAIL;TYPE=HOME;TYPE=pref;TYPE=INTERNET:"+email+"\nTEL;TYPE=CELL:"+telCel+"\nORG:"+org+"\nCATEGORIES:"+categories+"\n"+END+"\n\n"  ;
        }
        
        public boolean equals(Object o)
        {
            if (o instanceof Vcard)
            {
                Vcard tmp = (Vcard) o;
                return this.n.equals(tmp.n) && this.fn.equals(tmp.fn) && this.telCel.equals(tmp.telCel);
            }
            return false;
        }

        @Override
        public int compareTo(Object o)
        {
            return this.n.compareToIgnoreCase(((Vcard) o).n);
        }
    }

    public static String utf8_to_string(String str)
    {
        str = str.replace(";", "");
        if (str.startsWith("="))
            str = str.substring(1);
        try
        {
            String[] ss = str.split("=");
            byte[] bs = new byte[ss.length];
            // {0xE4,0xBD,0x95,0xE4,0xBC,0x9F,0xE9,0x9D,0x99};

            for (int i = 0; i < bs.length; i++)
                bs[i] = (byte) Integer.parseInt(ss[i], 16);
            return new String(bs, "utf-8");
        }
        catch (Exception e)
        {
            System.err.println("Error: " + str);
            return str;
        }
    }

    public static void main(String[] args)
    {
        List<Vcard> l = new ArrayList<Vcard>();
        String filename = "D:/cyou/Dropbox/anywhere/173.vcf";
        System.out.println(filename);
        VcfFileParser parser = new VcfFileParser(filename);
        try
        {
            parser.parse();
            // parser.output();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        l.addAll(parser.getVcards());
        List<Vcard> l2 = new ArrayList<Vcard>();
        for (Vcard s : l)
        {
            if (l2.contains(s))
            {
                //System.out.println("s has equals " + s.toString());
            }
            else
            {
                l2.add(s);
            }
        }
        
        int i = 1;

        System.out.println("l:"+l.size()+" l2:"+l2.size());
        StringBuffer sb = new StringBuffer();
        for (Vcard s : l2)
        {
            System.out.println(i++ + "\n" + s.toString());
            sb.append(s.toString());
        }
        String file = "c:/address173.vcf";
        FileUtil.deleteFile(new File(file));
        FileUtil.FileLog(file, sb.toString());
        
        sb = new StringBuffer();
        for(Vcard s:l2){
            if(s.telCel.length()==0  && s.telWork.length()==0)
                continue;
            sb.append(s.toString());
        }
        file = "c:/address173cell.vcf";
        FileUtil.deleteFile(new File(file));
        FileUtil.FileLog(file, sb.toString());
    }
}
