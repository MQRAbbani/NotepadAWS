package com.notepad.aws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class Helper {	
	
	//YOUR AMAZON ACCESS KEY ID
	public static final String ACCESS_KEY_ID = "AKIAJ5T6VPEJF2VPFDIQ";
	
	//YOUR AMAZON SECRET KEY ID
	public static final String SECRET_KEY = "HiuFCerSJp7gEv+CyyQpmjDuyTNED8kc8VoeJQyY";
	
	
	public static final String BUCKET_NAME = "faizaniqbal";
	

	// convert InputStream to String
		public static String getStringFromInputStream(InputStream is) {
	 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}
	 
	
	
}
