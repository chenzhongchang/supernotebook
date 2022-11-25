package csj.chihiro.epudtotxt;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
 
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;
 
public class Test {
 
    public static void main(String[] args) {
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