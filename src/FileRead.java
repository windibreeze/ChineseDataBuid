import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileRead {
	public static List<String> getStringData(File file){
		List<String> data=new ArrayList<>();
		try(Scanner scanner=new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")))){
			while(scanner.hasNext()){
				String line=scanner.nextLine();
				String ch[]=line.split(" ");
				for(String temp : ch){
					if(!temp.equals(" "))
						data.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
