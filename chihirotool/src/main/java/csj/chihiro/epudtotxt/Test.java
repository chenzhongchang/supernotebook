package csj.chihiro.epudtotxt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import csj.chihiro.txtformat.HexTransformUtil;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;
 
public class Test {

    /*
     * 普通类型的unicode转string
     */
    public static String UnicodeToString(String input) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(input);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            input = input.replace(matcher.group(1), ch + "");
        }
        return input;
    }
    /*
     * string转普通类型的unicode
     */
    public static String StringToUnicode(String input) {
        String str = "";
        for (char c : input.toCharArray()) {
            if ((int) c > 128)
                str += "\\u" + Integer.toHexString((int) c);
            else
                str += c;
        }
        return str;

    }
    /*
     * string转web类型的unicode
     */
    public static String StringToWebUnicode(String input) {
        String str = "";
        for (char c : input.toCharArray()) {
            str += "&#" + (int) c + ";";
        }
        return str;
    }
    /*
     * web类型的unicode转string
     */
    public static String WebUnicodeToString(String input) {
        String str = "";
        String[] y1 = input.split(";");
        for (String c : y1) {
            if(c.startsWith("x"))
            {
//                c=
            }
            if (c.length() > 2) {
                str += (char) Integer.parseInt(c.substring(2));
            }
        }
        return str;
    }

    /**
     *  字符串转16进制
     * @param srcData
     * @return
     */
    static String strToHex(String srcData){
        byte[] bytes = srcData.getBytes(StandardCharsets.UTF_8);
        String result="";
        for (int i=0;i<bytes.length;i++) {
            result =result + Integer.toHexString(bytes[i])+",";
        }
        result=result.substring(0,result.length()-1);
        return result;
    }

  public static void main(String[] args) {
      String str = "&#x6211";//&#x6211;
      int srcnum=HexTransformUtil.specialHexToInt("x6211");
      System.out.println("srcStr:&#x6211;");
      System.out.println("srcnum="+srcnum);
      String webStr="$#"+srcnum;
      System.out.println("srcstr="+WebUnicodeToString(webStr));
//      System.out.println(Test.StringToWebUnicode(str));
      String strHex=Integer.toHexString(25105);
      System.out.println("strHex:"+strHex);
//      String[] hexArr=strHex.split(",");
//      for (int i=0;i<hexArr.length;i++){
//          int hexint=HexTransformUtil.hexToInt(hexArr[i]);
//          System.out.println("hexint:"+hexint);
//      }

//      System.out.println(Test.WebUnicodeToString(str));
//      System.out.println(Test.StringToUnicode(str));
//      System.out.println(Test.UnicodeToString("\u4e2d\u56fd\\uqqqq"));
    }
 
    public static void main1(String[] args) {
        System.out.println("hello world");
         
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
//            Resources resources =  book.getResources();
//            Collection<Resource> res= resources.getAll();
//            for(Resource r:res){
//                InputStream is=r.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
//                StringBuilder strber = new StringBuilder();
//                String line = null;
//                while ((line = reader.readLine()) != null)
//                    strber.append(line + "\n");
//                writer.write(strber.toString());
//                writer.newLine();
//                is.close();
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置epub文件内title.
        //本处修改了toc.ncx文件中的<docTitle>和content.opf中的<dc:title>标签内容.
//        List<String> titlesList = new ArrayList<String>();
//        titlesList.add("test book");
//        book.getMetadata().setTitles(titlesList);
//
//        //write epub
        EpubWriter epubWriter = new EpubWriter();
        try {
            String dstPath = currentPath + "msw-cz1.epub";
            OutputStream ouput = new FileOutputStream(dstPath);
            epubWriter.write(book, ouput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }
 
}