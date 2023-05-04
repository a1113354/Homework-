import java.util.Scanner;

public class A1113354_0421_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("請輸入小寫的電子郵件信箱:");
        String input = scanner.nextLine().trim();
        scanner.close();
        
        if (isValidEmail(input)) {
            System.out.println("輸入格式正確");
        } else {
            System.out.println("輸入格式錯誤");
        }
    }
    
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        
        String username = parts[0];
        String domain = parts[1];
        
        if (username.isEmpty() || domain.isEmpty()) {
            return false;
        }
        
        String[] domainParts = domain.split("\\.");
        if (domainParts.length != 2 || !domainParts[1].equals("com")) {
            return false;
        }
        
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        
        return true;
    }
}
