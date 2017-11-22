import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
//		tThread t1=new tThread();
//		tThread t2=new tThread();
//		try {
//			t1.start();
//			t1.join();
//			t2.start();
//			t2.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//			
		String aString="ÎÒ";
			System.out.println(chinaToUnicode(aString.charAt(0)));
			Scanner in=new Scanner(System.in);
			while(true){
				int a=in.nextInt();
				int b=in.nextInt();
				if(a==0&&b==0)
					break;
				System.out.println(a+b);
				
			}
	}
	public static String chinaToUnicode(char c) {  
            if (c >= 19968 && c <= 171941) // ºº×Ö·¶Î§ \u4e00-\u9fa5 (ÖÐÎÄ)  
                return "\\u" + Integer.toHexString(c);  
            return "";
	}
	
}
