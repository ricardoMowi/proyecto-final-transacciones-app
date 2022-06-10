package com.nttdata.transactionmanagement.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.transactionmanagement.Model.MasterValues;
import com.nttdata.transactionmanagement.Util.AppUtils;
import com.nttdata.transactionmanagement.config.MasterValuesApiProperties;
import com.thoughtworks.xstream.mapper.Mapper;

import io.vavr.collection.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
@Slf4j
public class MasterValuesApiClient {
    private final WebClient webClient;
	private final MasterValuesApiProperties mvProperties;
	
	/**public List<MasterValuesResponse> getList() throws InterruptedException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		List<MasterValuesResponse> result=new ArrayList<>();
		//cambiar url
		webClient.get().uri(mvProperties.getBaseUrl()+"/mastervalues/all")
		.accept(MediaType.TEXT_EVENT_STREAM)
		.retrieve()
		.bodyToFlux(MasterValuesResponse.class)
		.publishOn(Schedulers.fromExecutor(executor))
		.subscribe(response->result.add(response));
		executor.awaitTermination(1, TimeUnit.SECONDS);
		log.info("Master Values list"+ result);
		return result;
	}
	**/
	/**
	public List<MasterValues> getList() throws InterruptedException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		List<MasterValues> result=new ArrayList<>();
		//cambiar url
		webClient.get().uri(mvProperties.getBaseUrl()+"/mastervalues/all")
		.accept(MediaType.TEXT_EVENT_STREAM)
		.retrieve()
		.bodyToFlux(MasterValues.class)
		.publishOn(Schedulers.fromExecutor(executor))
		.subscribe(response->result.add(response));
		executor.awaitTermination(1, TimeUnit.SECONDS);
		log.info("Master Values list"+ result);
		return result;
	}
	*/

	public List<MasterValuesResponse> getList() throws InterruptedException{


		ObjectMapper mapper = new ObjectMapper();

		ExecutorService executor = Executors.newSingleThreadExecutor();
		String all =  webClient.get().uri(mvProperties.getBaseUrl() +"/mastervalues/all").exchange().block().bodyToMono(String.class).block();
		List<MasterValuesResponse> test = new ArrayList<>();


		Mono<MasterValues[]> response = webClient.get().uri(mvProperties.getBaseUrl() +"/mastervalues/all").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(MasterValues[].class).log();
		MasterValues[] objects = response.block();

		Mono<List<MasterValues>> response_1 = webClient.get()
		.uri(mvProperties.getBaseUrl() +"/mastervalues/all")
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(new ParameterizedTypeReference<List<MasterValues>>() {});
	  	List<MasterValues> readers = response_1.block();


		//List<String> ids =  readers.stream()
		//.map(MasterValues::getCode)
		//.collect(Collectors.toList()); 

		List<MasterValuesResponse> ids =  new ArrayList<>();
		//readers.stream()
		//.map(AppUtils::masterValuesToMasterValuesR)
		//.collect(Collectors.toList()); 


		



		// try {
		// 	test = mapper.readValue(all, new TypeReference<List<MasterValuesResponse>>(){});
		// } catch (JsonMappingException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// } catch (JsonProcessingException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }


		executor.awaitTermination(1, TimeUnit.SECONDS);
		log.info("Master Values String " + all);
		log.info("Master Values test " + test);
		log.info("Master Values readers " + readers);

		MasterValuesResponse nuevo = new MasterValuesResponse();
		nuevo.setCode("PURCHASE_RATE");
		nuevo.setValue(2.3);
		ids.add(nuevo);

		MasterValuesResponse nuevo_2 = new MasterValuesResponse();
		nuevo.setCode("SELLING_RATE");
		nuevo.setValue(2.0);
		ids.add(nuevo_2);

		log.info("Master Values ids " + ids);


		return ids;



	
		

	}



	public List<MasterValuesResponse> getListResponse() throws InterruptedException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		List<MasterValuesResponse> result = new ArrayList<>();

		webClient.get().uri(mvProperties.getBaseUrl() +"/mastervalues/all")
		  .accept(MediaType.TEXT_EVENT_STREAM)
		  .retrieve()
		  .bodyToFlux(MasterValuesResponse.class)
		  .publishOn(Schedulers.fromExecutor(executor))
		  .subscribe(obj -> result.add(obj));
	
		executor.awaitTermination(1, TimeUnit.SECONDS);
		log.info("Master Values list " + result);

		return result;
	}
}
