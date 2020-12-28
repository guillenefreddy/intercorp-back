package com.intercorp.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intercorp.entity.Client;
import com.intercorp.entity.Kpi;
import com.intercorp.service.ClientService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST})
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@PostMapping("/creacliente")
	@ApiOperation(value = "Crear Clientes", notes = "Retorna la hora que fue creado el documento")
	public String createClient(@RequestBody Client client) throws InterruptedException, ExecutionException {
		return clientService.saveClient(client);
	}
	
	@GetMapping("/kpideclientes")
	@ApiOperation(value = "Kpi de clientes", notes = "Retorna indicadores de las edades de los clientes")
	public Kpi kpiClient() throws InterruptedException, ExecutionException {
		return clientService.getKpi();
	}
	
	@GetMapping("/listclientes")
	@ApiOperation(value = "Lista de clientes", notes = "Retorna la lista de todos los clientes")
	public List<Client> listClient() throws InterruptedException, ExecutionException {
		return clientService.getListClient();
	}
}
