package com.intercorp.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service 
public class FirebaseInitializer {

          
	@PostConstruct
	public void initialize() throws IOException, URISyntaxException{
		
		InputStream serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();
				
		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		  .setDatabaseUrl("https://retointercorp-8abe5-default-rtdb.firebaseio.com")
		  .build();

		FirebaseApp.initializeApp(options);
		
	}
     
}