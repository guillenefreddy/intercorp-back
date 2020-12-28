package com.intercorp.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service 
public class FirebaseInitializer {

          
	@PostConstruct
	public void initialize() throws IOException, URISyntaxException{
		
		System.out.println("PASO");
		URL serviceAccount = getClass().getResource("/serviceAccountKey.json");
		System.out.println("VALOR"+ serviceAccount);
		File file = new File(serviceAccount.toURI());
		FileInputStream input = new FileInputStream(file);
		
		System.out.println("input");

		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(input))
		  .setDatabaseUrl("https://retointercorp-8abe5-default-rtdb.firebaseio.com")
		  .build();

		FirebaseApp.initializeApp(options);
		
	}
     
}