package csj.chihiro.epudtotxt;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;

import java.io.*;
import java.util.*;

public class Test1 {
    private static List<String> noContent=new ArrayList<>();
    static Map<String,StringBuilder> content=new HashMap<>();

    static{
        noContent.add("<?xml ");
        noContent.add("<!DOCTYPE");
        noContent.add("<!DOCTYPE");
    }

    public static void main(String[] args) {
//        String str="&#xA0;&#xA0;&#xA0;&#xA0;&#x6211;&#x6700;&#x8FD1;&#x78B0;&#x5DE7;&#x53D1;&#x73B0;&#x4E00;&#x4E9B;&#x6709;&#x540D;&#x6709;&#x9B45;&#x529B;&#x7537;&#x4EBA;&#x7684;&#x8EAB;&#x9AD8;&#xFF1A;";
//        System.out.println(HexWebStrUtils.webUnicodeToString(str));
        long starTime=System.currentTimeMillis();
        epubToText();
        String temp="100&#x4E2A";
        System.out.println(temp.startsWith("&#"));
        long useTime=System.currentTimeMillis()-starTime;
        System.out.println("共耗时："+useTime/1000+"s");
    }

    static String hexWebToStr(String hexweb){
        String str = "";
        String[] y1 = hexweb.split(";");
        for (String c : y1) {
            if(c.indexOf("&#")!=-1){
                if(c.startsWith("&#")){
                    str += HexWebStrUtils.webUnicodeToString(c);
                }else{
                    String[] carr=c.split("&#");
                    str +=carr[0];
                    str += HexWebStrUtils.webUnicodeToString(carr[1]);
                }
            }
        }
        return str;
    }


     static void epubToText(){
        EpubReader epubReader = new EpubReader();
        //处理io流路径
        String currentPath = "D:\\MyBooks\\";
        String epubPath = currentPath + "msw-cz.epub";
        System.out.println(epubPath);
        //读epub文件
        Book book = null;
        try {
            String dstPath = currentPath + "msw-cz.txt";
            File outFile=new File(dstPath);
            if(outFile.exists())
                outFile.delete();
            BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));

            InputStream inputStr = new FileInputStream(epubPath);
            book = epubReader.readEpub(inputStr);
            Resources resources =  book.getResources();
            Collection<Resource> res= resources.getAll();
            for(Resource r:res){
                String rid=r.getId();
                String key=System.currentTimeMillis()+"";
                if(rid.indexOf(".html")!=-1){
                    key=rid.replace("x","").replace(".html","");
                }
                InputStream is=r.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    if(line.indexOf("<title>")!=-1){
                        String temp=line.replace("<title>","").replace("</title>","");
                        strber.append(temp.trim());
                        strber.append("\n");
                    }else  if(line.indexOf("&#")!=-1){
                        String temp=line.replaceAll("<br>","");
                        int index=temp.indexOf("<a href");
                        if(index!=-1)
                            temp=temp.substring(0,index+1);
//                        System.out.println("temp="+temp);
                        String str=hexWebToStr(temp);
                        strber.append(str.trim());
                        strber.append("\n");

                    }
                }
                is.close();
                content.put(key,strber);
            }
            saveContent(writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void saveContent(BufferedWriter writer) throws IOException {
        for (int i=1;i<=content.size();i++){
            StringBuffer sb=new StringBuffer();
            sb.append(content.get(i+""));
            String content=sb.toString();
            if(null==content)
                continue;
            if(content.length()>20){
                String jj=content.substring(0,20);
                System.out.println("简介："+jj);
            }else System.out.println("简介："+content);
            writer.write(content);
            writer.newLine();
            writer.flush();
        }
    }

    void getEpubInfo(){
        EpubReader epubReader = new EpubReader();

        //处理io流路径
        String currentPath = "D:\\MyBooks\\";
        String epubPath = currentPath + "msw-cz.epub";
        System.out.println(epubPath);

        //读epub文件
        Book book = null;
        try {
            String dstPath = currentPath + "msw-cz.txt";
            File outFile=new File(dstPath);
            BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));

            InputStream inputStr = new FileInputStream(epubPath);
            book = epubReader.readEpub(inputStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置epub文件内title.
        //本处修改了toc.ncx文件中的<docTitle>和content.opf中的<dc:title>标签内容.
        List<String> titlesList = new ArrayList<>();
        titlesList.add("test book");
        book.getMetadata().setTitles(titlesList);
        //write epub  将html页面转成epud格式文档
//        EpubWriter epubWriter = new EpubWriter();
//        try {
//            String dstPath = currentPath + "msw-cz1.epub";
//            OutputStream ouput = new FileOutputStream(dstPath);
//            epubWriter.write(book, ouput);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
