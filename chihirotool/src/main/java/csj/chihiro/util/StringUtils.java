package csj.chihiro.util;

import java.io.UnsupportedEncodingException;

public class StringUtils {

    public static void main(String[] args) {
        StringBuilder sb=new StringBuilder();
        String str="中国ABVC";
        sb.append(str);
        String str1="ABVC";
        sb.append(str1);
        sb.insert(0,"第一");
        System.out.println(sb.toString());
        System.out.println(checkChinese(str1));
        System.out.println(checkChinese(str.charAt(0)));
        String errstr="���4�E��M�`\u0015�";
        System.out.println(errCode(errstr));
        String delStr="";
        if(errCode(errstr)){
            String filterstr="`\u0015";
            for(int i=0;i<errstr.length();i++){
                String temp=errstr.charAt(i)+"";
                if(filterstr.indexOf(temp)!=-1&&!errCode(temp)){
                    delStr += temp;
                }
            }
        }
        System.out.println(delStr);
    }

    public static boolean checkChinese(String str){
        int srcleng=str.length();
        int byteleng=str.getBytes().length;
        return srcleng!=byteleng;
    }

    public static boolean checkChinese(char ch){
        return String.valueOf(ch).matches("[\u4e00-\u9fa5]");
    }


    public static boolean errCode(String str){
        return !(java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(str));
    }

    public static String filterErrCode(String errstr){
        String delStr="";
        if(errCode(errstr)){
            String filterstr="`\u0015";
            for(int i=0;i<errstr.length();i++){
                String temp=errstr.charAt(i)+"";
                if(filterstr.indexOf(temp)==-1&&!errCode(temp)){
                    delStr += temp;
                }
            }
        }
        return delStr;
    }

}
