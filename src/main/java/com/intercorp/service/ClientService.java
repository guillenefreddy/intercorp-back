package com.intercorp.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.intercorp.entity.Client;
import com.intercorp.entity.Kpi;

@Service
public class ClientService {

	@Autowired
	private Firestore firestore;
	
	private CollectionReference getClientTable(){
	
		return firestore.collection("client");
	}
	
	public String saveClient(Client client) throws InterruptedException, ExecutionException {
		
		ApiFuture<WriteResult> aFuture = getClientTable().document().set(client);
				
		return aFuture.get().getUpdateTime().toString();
	}
	
	public Kpi getKpi() throws InterruptedException, ExecutionException {
		
		ApiFuture<QuerySnapshot> query = getClientTable().get();

		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		
		double avg = documents.stream().map(f -> f.getLong("age")).mapToDouble(Long::doubleValue).average().orElse(0.0);
			
		double var = 1.0;
				
		double sd = Math.sqrt(var);
		
		return new Kpi( formatearDecimales(avg,2), formatearDecimales(sd,2));
	}
	
	public List<Client> getListClient() throws InterruptedException, ExecutionException {
		
		ApiFuture<QuerySnapshot> query = getClientTable().get();

		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
				
		List<Client> clients = documents.stream()
	       .map(f -> new Client(f.getId(),f.getString("name"),f.getString("lastname"),f.getLong("age"),f.getString("birthday"),f.getString("birthday")))
	       .collect(Collectors.toList());
	       
		return clients;
		
	}
	
	public Client getClient(String id) throws InterruptedException, ExecutionException {
		
		Client client = null;
		DocumentReference docRef = getClientTable().document(id);
		ApiFuture<DocumentSnapshot> future = docRef.get();

		
		DocumentSnapshot f = future.get();
		if (f.exists()) {
			
			client = new Client(f.getId(),f.getString("name"),f.getString("lastname"),f.getLong("age"),f.getString("birthday"),f.getString("birthday"));

		} 
	       
		return client;
		
	}
	
	
	private Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
}
