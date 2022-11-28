package csj.chihiro.epudtotxt;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexWebStrUtils {
    public static void main(String[] args) {
        String str = "&#x65F6";//&#x6211;.3
        System.out.println(webUnicodeToString(str));
    }

    public static void main2(String[] args) {
        String str = "&#x65F6";//&#x6211;
        str=str.replace("&#","");
        int srcnum= HexFormartUtils.specialHexToInt(str);
//        System.out.println("srcStr:&#x6211;");
        System.out.println("srcnum="+srcnum);
//        String webStr="$#"+srcnum;.
        System.out.println(webUnicodeToString("&#"+srcnum));
//        System.out.println("srcstr="+webUnicodeToString(str));
        System.out.println(stringToWebUnicode("时"));
        System.out.println(stringToWebUnicode("我"));

    }

    /*
     * 普通类型的unicode转string
     */
    public static String unicodeToString(String input) {
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
    public static String stringToUnicode(String input) {
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
    public static String stringToWebUnicode(String input) {
        String str = "";
        for (char c : input.toCharArray()) {
            str += "&#" + (int) c + ";";
        }
        return str;
    }
    /*
     * web类型的unicode转string
     */
    public static String webUnicodeToString(String input) {
        String str = "";
        String[] y1 = input.split(";");
        for (String c : y1) {
            if(c.startsWith("&#"))
                c=c.substring(2);
            if(c.startsWith("x"))
            {
                int srcnum= HexFormartUtils.specialHexToInt(c);
                c=srcnum+"";
                if (c.length() > 2) {
                   String temp = (char) Integer.parseInt(srcnum+"")+"";
                   str += temp.trim();
                }
            }else if (c.length() > 2) {
                String temp = (char) Integer.parseInt(c)+"";
                str += temp.trim();
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
}
