import java.util.*;

public class A1113354_0421_2{
    public static void main (String [] argv){
        Scanner scanner = new Scanner(System.in);
        String x;
        System.out.print("請輸入日期");
        x=scanner.nextLine();
        if(x.matches("[0-9]{4}[/]{1}[0-1]{1}[0-9]{1}[/]{1}[0-3]{1}[0-9]{1}") == true || x.matches("[0-1]{1}[0-9]{1}[/]{1}[0-3]{1}[0-9]{1}[/]{1}[0-9]{4}") == true){
            System.out.println("輸入格式正確");
        }else{
            System.out.println("輸入格式錯誤");
        }
    }
}
