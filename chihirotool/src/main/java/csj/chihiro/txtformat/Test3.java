package csj.chihiro.txtformat;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Test3 {

    private static String bookName="wb陷阱";
    private static String bookPath="D:\\MyBooks\\";

    public static void main(String[] args) throws IOException {
//        String srcData="天天上班，让我的精神百倍，生命得到了延续，拥有了意义，还让我变的更加长生。";
//        String hexStr=strToHex(srcData);
//        System.out.println("--hexStr:"+hexStr);
//        String jmStr=getJmStr(hexStr);
//        String srcStr=hexTostr(jmStr);
//        System.out.println("srcStr:"+srcStr);
//        readFileContent("D:\\MyBooks\\射雕,陶华.txt","D:\\MyBooks\\射雕,陶华2.txt");
        String srcBook=bookName+".txt";
        String jmBook=bookName+"cc.txt";
        String dstBook=bookName+"dst.txt";
        String srcPath=bookPath+srcBook;
        String jmPath=bookPath+jmBook;
        String dstPath=bookPath+dstBook;

        readFileContent(srcPath,jmPath);
        getFileContent(jmPath,dstPath);
    }

    public static void readFileContent(String fileName,String fileName2) throws IOException {
        File file=new File(fileName);
        File outFile=new File(fileName2);

        if(!file.exists()){
            return;
        }else if(!outFile.exists()){
            if(!outFile.createNewFile()){
                return;
            }
        }
        //读取文本,用字符流.如果是其他二进制文件，可使用字节流，在try中创建可自动关闭
        try (BufferedReader reader=new BufferedReader(new FileReader(file));
             BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));){
            String readStr;
            //读一行写一行
            int i=1;
            while((readStr=reader.readLine())!=null){
                if(readStr.equals(""))
                    continue;
                String hexStr=strToHex(i,readStr);
                String jmStr=getJmStr(hexStr);
//                if(i==4){
//                    System.out.println("--readStr:"+readStr);
//                    System.out.println("--hexStr:"+hexStr);
//                    System.out.println("--jmStr:"+jmStr);
//                }
                writer.write(jmStr);
                writer.newLine();
            }
        }
    }
    public static void getFileContent(String fileName,String fileName2) throws IOException {
        File file=new File(fileName);
        File outFile=new File(fileName2);

        if(!file.exists()){
            return;
        }else if(!outFile.exists()){
            if(!outFile.createNewFile()){
                return;
            }
        }

        //读取文本,用字符流.如果是其他二进制文件，可使用字节流，在try中创建可自动关闭
        try (BufferedReader reader=new BufferedReader(new FileReader(file));
             BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));){
            String readStr;
            //读一行写一行
            int i=1;
            while((readStr=reader.readLine())!=null){
                if(readStr.equals(""))
                    continue;
                System.out.println("readStr:"+readStr);
                String srcStr=hexTostr(readStr);
                System.out.println("srcStr:"+srcStr);
                writer.write(srcStr);
                writer.newLine();
            }
        }
    }

    /**
     *  字符串转16进制
     * @param srcData
     * @return
     */
    static String strToHex(int a,String srcData){
        byte[] bytes = srcData.getBytes(StandardCharsets.UTF_8);
        if(a==4){
            System.out.println("bytes="+new Gson().toJson(bytes));
        }
        String result="";
        for (int i=0;i<bytes.length;i++) {
            result =result + Integer.toHexString(bytes[i])+",";
        }
        result=result.substring(0,result.length()-1);
        return result;
    }


    /**
     * 16进制转字符串
     * @param srcData
     * @return
     */
    static String hexTostr(String srcData){
        String[] srcArr=srcData.split(",");
        byte[] bytes=new byte[srcArr.length];
        for(int i=0;i<srcArr.length;i++){
            bytes[i]= (byte) -HexTransformUtil.hexGetInt1(srcArr[i]);
        }
        return new String(bytes,StandardCharsets.UTF_8);
    }


    /**
     * 16进制转byte数组
     * @param str
     * @return
     */
    static byte[] hexToBytes(String str){
        String[] arrStr=str.split(",");
        byte[] dstByte=new byte[arrStr.length];
        for(int i=0;i<arrStr.length;i++){
            dstByte[i]= (byte) HexTransformUtil.hexToInt(arrStr[i]);
        }
        return dstByte;
    }

    /**
     * 16进制字符串 获取去前缀f的16进制字符
     * @param str
     * @return
     */
    static String getJmStr(String str){
        StringBuilder sb=new StringBuilder();
        String[] arrStr=str.split(",");
        for(int i=0;i<arrStr.length;i++){
            sb.append(HexTransformUtil.getHexstr(arrStr[i])+",");
        }
        String sbstr=sb.toString();
        sbstr=sbstr.substring(0,sbstr.length()-1);
        return sbstr;
    }




}
