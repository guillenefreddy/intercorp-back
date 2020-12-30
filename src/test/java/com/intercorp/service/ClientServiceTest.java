package com.intercorp.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.intercorp.entity.Client;

@SpringBootTest(classes=ClientService.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.intercorp")
class ClientServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceTest.class);
    
	@Autowired
	ClientService clientService;
	
	
	@Test
    public void saveClient() throws Exception {
		
		Client client = new Client("test", "test", "test", new Long(4) , "11/11/2011", "11/11/2011");
		
        String response = clientService.saveClient(client);
 
        logger.info("response: " + response);
    }
	
}
