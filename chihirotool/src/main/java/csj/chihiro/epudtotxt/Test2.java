package csj.chihiro.epudtotxt;

import csj.chihiro.util.StringUtils;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Test2 {

    static Map<String,StringBuilder> content=new HashMap<>();
    private static String bookName="msw-cz";
    private static String bookPath="D:\\MyBooks\\";

    public static void main(String[] args) {
        epubToText();
    }

    public static void epubToText(){
        EpubReader epubReader = new EpubReader();
        //处理io流路径
        String epubPath = bookPath + bookName+".epub";
        System.out.println(epubPath);
        String dstPath = bookPath + bookName+".txt";
        //读epub文件
        Book book = null;
        try {
            File outFile=new File(dstPath);
            if(outFile.exists())
                outFile.delete();
            BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));

            InputStream inputStr = new FileInputStream(epubPath);
            book = epubReader.readEpub(inputStr);
            Resources resources =  book.getResources();
            Collection<Resource> res= resources.getAll();
            for(Resource r:res){
              extractContent(r);
            }
            saveContent(writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int seq=0;
    static void setMaxSeq(int num){
        if(seq<num)
            seq=num;
    }

    static void extractContent(Resource r) throws IOException {
        String rid=r.getId();
        String key=System.currentTimeMillis()+"";
        if(rid.indexOf(".html")!=-1){
            key=rid.replace("x","").replace(".html","");
            int num=Integer.parseInt(key);
            key=num+"";
            setMaxSeq(num);
        }else if(rid.indexOf("html")!=-1){
            key=rid.replace("html","");
            int num=Integer.parseInt(key);
            key=num+"";
            setMaxSeq(num);
        }else if(rid.indexOf("id")!=-1){
            key=rid.replace("id","");
            int num=Integer.parseInt(key);
            key=num+"";
            setMaxSeq(num);
        }
        InputStream is=r.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        StringBuilder strber = new StringBuilder();
        String line = null;
        String title="";
        while ((line = reader.readLine()) != null){
            if(line==null||"".equals(line))
                continue;
            if(StringUtils.errCode(line)){
                String delstr=StringUtils.filterErrCode(line);
                if(delstr==null&&"".equals(delstr.trim()))
                    continue;
                getStringContent(delstr,strber);
                continue;
            }
            if(line.indexOf("<title>")!=-1){
                String temp=line.replace("<title>","").replace("</title>","");
                title=temp.trim();
//                strber.append(temp.trim());
//                strber.append("\n");
            }else  if(line.indexOf("&#")!=-1){
                String temp=line.replaceAll("<br>","");
                int index=temp.indexOf("<a href");
                if(index!=-1)
                    temp=temp.substring(0,index+1);
                String str=hexWebToStr(temp);
                strber.append(str.trim());
                strber.append("\n");
            }else if(StringUtils.checkChinese(line)){
                getStringContent(line,strber);
            }
        }
        is.close();
        if(!"".equals(title.trim())&&null!=title){
           int index=strber.toString().indexOf(title);
           if(index==-1)
               strber.insert(0,title);
        }
        content.put(key,strber);
    }

    static void getStringContent(String srcstr,StringBuilder strber){
        int index=srcstr.indexOf("<");
        if (index!=-1){
            String[] strarr=new String[2];
            strarr[0]=srcstr.substring(0,index);
            strarr[1]=srcstr.substring(index+1);
            if(strarr[0]!=null&&!"".equals(strarr[0].trim())){
                strber.append(strarr[0].trim());
                strber.append("\n");
            }
            index=strarr[1].indexOf(">");
            if(index!=-1){
                String temp=strarr[1].substring(index+1);
                getStringContent(temp,strber);
            }
        }else {
            if(srcstr==null||"".equals(srcstr.trim()))
                return;
            strber.append(srcstr.trim());
            strber.append("\n");
        }
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


    static void saveContent(BufferedWriter writer) throws IOException {
        for (int i=1;i<=seq;i++){
            StringBuffer sb=new StringBuffer();
            sb.append(content.get(i+""));
            String content=sb.toString();
            content=content.replaceAll("&#13;\n","").replaceAll("<br/>","");
            if("null".equals(content)||"".equals(content.trim()))
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

}
