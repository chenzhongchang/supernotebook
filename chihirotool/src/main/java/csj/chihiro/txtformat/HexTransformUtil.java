package csj.chihiro.txtformat;


public class HexTransformUtil {
    private static String hx="0123456789abcdef";


    public static void main(String[] args) {
        String xs="5929";//22825
        if(xs.startsWith("f")){
            getHexstr(xs);
            System.out.println("xs:");
            int hexx=-hexGetInt1(getHexstr(xs));
            System.out.println("hexx:"+hexx);
        }else{
            int hexx=hexGetInt(xs);
            System.out.println("hexx:"+hexx);
        }
    }

    public static int specialHexToInt(String hex){
        if(hex.startsWith("x")){
            hex=hex.substring(1);
            return hexToInt(hex);
        }else{
            return hexGetInt(hex);
        }
    }
    public static int hexToInt(String hex){
        if(hex.startsWith("f")){
            String val=getHexstr(hex);
            return -hexGetInt1(val);
        }else{
            return hexGetInt(hex);
        }
    }
    public static String getHexstr(String hex){
        String result="";
        boolean b=false;
        for(int i=0;i<hex.length();i++){
            char c=hex.charAt(i);
            if(b||!"f".equals((c+""))){
                result=result+c;
                b=true;
            }
        }
        return result;
    }
    public static String getHexstr1(String hex){
        String result="";
        boolean b=false;
        for(int i=0;i<hex.length();i++){
            char c=hex.charAt(i);
            if(b||!"f".equals((c+""))){
                result=result+c;
                b=true;
            }
        }
        return result;
    }
    public static int hexGetInt1(String hex){
        hex=hex.toLowerCase();
        int leng=hex.length();
        int val=0;
        for(int i=1;i<=leng;i++){
            String h=hex.charAt(i-1)+"";
            if(leng>i){
                int mul=getMultiple(leng,i);
                val=val+mul*(hx.length()-hx.indexOf(h)-1);
            }else if(leng==i){
                val=val+(hx.length()-hx.indexOf(h));
            }
        }
        return val;
    }
    public static int hexGetInt(String hex){
        hex=hex.toLowerCase();
        int leng=hex.length();
        int val=0;
        for(int i=1;i<=leng;i++){
            String h=hex.charAt(i-1)+"";
            if(leng>i){
                int mul=getMultiple(leng,i);
                val=val+mul*hx.indexOf(h);
            }else if(leng==i){
                val=val+hx.indexOf(h);
            }
        }
        return val;
    }
    public static int getMultiple(int leng,int i){
        int zy=(leng-i-1)*4;
        int mul=16<<zy;
        return mul;
    }

}
