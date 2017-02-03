import java.io.*;
import java.util.*;
/**
 * 此类用于加密大文件，采用亦或加密算法加密。
 */
public class SplitAndMergeDemo{

    private static int SPLIT_SIZE = 1024 * 1024 * 100;
    private static String originFileName = "Rct494 Free Asian Porn Video - Mobile.mp4";
    private static String newFileName = "newFile.mp4";
    private static String partName = ".mp4";
    private static String DIST = "dist";
    private static int key = 0x99;
    public static void main(String[] args) throws IOException{
        split();
        merge();
        // delete();
    }
    public static void split() throws IOException{
        System.out.println("开始切割文件");
        FileInputStream in = new FileInputStream(originFileName);
        FileOutputStream out = null;
        byte[] buff = new byte[SPLIT_SIZE];
        int len = 0;
        int count = 1;
        File dist = new File(DIST);
        if(!dist.exists()){
            dist.mkdir();
        }
        while((len=in.read(buff)) != -1){
            String f = DIST + "\\" + (count++) + partName;
            out = new FileOutputStream(f);
            for(int i=0;i<buff.length;i++){
                buff[i] = (byte)((int)buff[i] ^ key);
            }
            out.write(buff, 0, len);
            out.close();
            System.out.println("切割成文件..  " + f);
        }
        in.close();
        System.out.println("切割完成");
    }
    public static void merge() throws IOException{
        System.out.println("开始合并文件");
        ArrayList<FileInputStream> list = new ArrayList<FileInputStream>();
        File[] files = new File(DIST).listFiles();
        for(File f: files){
            list.add(new FileInputStream(f));
            System.out.println("讲文件 " + f.getName() + "  放入合并流中");
        }
        System.out.println("正在合并文件");
        Iterator<FileInputStream> it = list.iterator();
        final Enumeration<FileInputStream> en = new Enumeration<FileInputStream>(){
            @Override
            public boolean hasMoreElements(){
                return it.hasNext();
            }
            @Override
            public FileInputStream nextElement(){
                return it.next();
            }
        };
        SequenceInputStream sis = new SequenceInputStream(en);
        FileOutputStream out = new FileOutputStream(newFileName);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=sis.read(buff)) != -1){
            for(int i=0;i<buff.length;i++){
                buff[i] = (byte)((int)buff[i] ^ key);
            }
            out.write(buff, 0, len);
        }
        out.close();
        sis.close();
        System.out.println("合并完成");
    }
    public static void delete(){
        System.out.println("开始清理文件");
        File dist = new File(DIST);
        File[] files = dist.listFiles();
        for(File f: files){
            f.delete();
        }
        dist.delete();
        System.out.println("清理完成");
    }
}