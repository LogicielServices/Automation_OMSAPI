package IdentityServer_API_Admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.annotations.Test;


public class Dumm {

		
	@Test 
	public void AdminLoginAuthentications( )
	{
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = localDateTime.format(formatter);
        System.out.println("formattedDateTime :"+formattedDateTime);
        
	}
	
		
}
