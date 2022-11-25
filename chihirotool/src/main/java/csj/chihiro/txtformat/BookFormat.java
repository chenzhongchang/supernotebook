package csj.chihiro.txtformat;

import java.io.*;

public class BookFormat {
    private static String []dn={"一","二","三","四","五","六","七","八","九","十"};
    private static String []dnum;


    private static String bookName="jmhs-ljtd";
    private static String bookPath="D:\\MyBooks\\";

    public static void main(String[] args) {
        try {
            long startTime=System.currentTimeMillis();
            String srcBook=bookName+".txt";
            String dstBook=bookName+"1.txt";
            String srcPath=bookPath+srcBook;
            String dstPath=bookPath+dstBook;
            readFileContent(srcPath,dstPath);
            long currentTime=System.currentTimeMillis();
            long useTime=startTime-currentTime;
            System.out.println("format success Total time 耗时："+(useTime/1000)+"s");
        }catch (IOException e){
            e.printStackTrace();
        }
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
                    readStr=readStr.replaceAll("  ","");
                    readStr=readStr.replaceAll("。","");
//                    if(readStr!=null&&!"".equals(readStr)){
//                        if(!readStr.matches("[\u4e00-\u9fa5]+")){
//                            String str="";
//                            for(int x=0;x<readStr.length();x++){
//                                String tmp=readStr.charAt(x)+"";
//                                if(!tmp.matches("[\u4e00-\u9fa5]+")&&!tmp.matches("\\w+")&&!tmp.matches("[\\pP\\p{Punct}]")){
//                                    continue;
//                                }
//                                str=str+tmp;
//                            }
//                            readStr=str;
//                        }
//                        String zs=dn.toString();
//                        int index=readStr.indexOf("章");
//                        String num=readStr.substring(index,index+1);
//
//                        String seq="章"+i;
//                        seq=seq.replaceAll(i+"",dnum[i-1]);
//                        boolean fault=false;
//                        if(readStr.indexOf(seq+" ")!=-1||fault) {
//                            if (fault) {
//                                readStr = readStr.replace(seq, "第" + seq + "章");
//                                System.out.println(readStr);
//                                i++;
//                            }
//                        }
//                        readStr=readStr.replace("\n\r","");
//                    }
                    writer.write(readStr);
                    writer.newLine();
                    //输出流会在close之前自动执行flush,也可以根据情况手动执行
//                    writer.flush();
                }
        }

    }
}
