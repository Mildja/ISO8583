package me.veeraya.ISO8583.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import me.veeraya.ISO8583.model.ISO8583Model;
import me.veeraya.ISO8583.repository.ISO8583Repository;


@Service
public class ISO8583Service {
	@Autowired
	private ISO8583Repository iso8583Repository;
	
	public List<ISO8583Model> getAllIso8583Models(){
		System.out.println("TT");
		//System.out.println(iso8583Repository.findById(0).get());
		return  iso8583Repository.findAll();
	}
	
	

	
	
}
