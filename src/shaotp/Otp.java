package shaotp;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

public class Otp {
	public static String encode(String input) throws NoSuchAlgorithmException, IOException
	{  

		byte[] encodedMessage = MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8));  //Converting the input string into SHA 256 encoding
		StringBuilder sb = new StringBuilder();
        for (byte b : encodedMessage) {
            sb.append(String.format("%02x", b));  //Converting the byte information into hexadecimal format
        }
        File file=new File("hashFile.txt");		//Reading the file
		if (file.exists() && file.isFile())		//Deleting the file if already exists
		  {
		  file.delete();
		  }
		file.createNewFile();
		//Write hash Code into the file
		if(file.length()==0) {
			FileWriter writer=new FileWriter("hashFile.txt");
			writer.write(sb.toString());
			writer.close();
		}
        return sb.toString();
	} 

	//In this encoding from 64 bit hash code to 4 digit OTP we are taking 8 consecutive character from front and 8 from back
	public static String F(String hashString) {
		String otp="";
		int i,j,front=0,back=0;
		for(i=0,j=hashString.length()-1;i<=j;i=i+8,j=j-8)
		{
			int iter = i+8;
			//take 8 consecutive elements from the left hand side
			while(iter>i)
			{
				front = front^(hashString.charAt(iter)+hashString.charAt(iter-1));
				iter=iter-2;
			}
			iter = j-8;
			//take 8 consecutive elements from the right hand side
			while(iter<j)
			{
				back = back^(hashString.charAt(iter)+hashString.charAt(iter+1));
				iter=iter+2;
			}
			//generate OTP
			otp = otp + ((front^back)%10);
		}
		return otp;

	}
	
	//Function to verify the OTP with user entry
	public static boolean Verify(String hashCode, String otp) 
	{	return F(hashCode).equals(otp)?true:false;	}

	public static void main(String[] args) throws InterruptedException {
		try{
			Scanner sc = new Scanner(System.in);
			SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ssddMMyyyy");
			Date date=new Date();
			String stringDate=dateFormat.format(date);
			//Generating the OTP from given input
			System.out.print("Following is your OTP :"+F(encode(stringDate))+" Press enter to proceed");
			System.in.read();
			//Taking user input for validation
			String H = new String(Files.readAllBytes(Paths.get("hashFile.txt")));
			int attempt = 3;
			//Giving user three attempts to make for correct OTP
			while(attempt>0)
			{
				System.out.print("Please enter the OTP :");
				String otp = sc.next();
				if(Verify(H, otp)==true) {
					System.out.print("OTP successfully verified");	
					PrintWriter writer=new PrintWriter("hashFile.txt"); //Make the text file clear after the use
					writer.print("");
					writer.close();
					break;
			}
			else {
				System.out.print("Sorry Wrong OTP");
				System.out.println();
			}
			attempt--;
			}
			if(attempt==0)
				System.err.println("You have exceeded the limit Please request for new OTP");
			sc.close();
		}
		catch (NoSuchAlgorithmException e) {  
			System.err.println("No such algorithm exists: " + e);  
		} catch (IOException e) {
			System.err.println("Input Output exception :"+e);

		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

}