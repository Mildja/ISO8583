package me.veeraya.ISO8583.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.veeraya.ISO8583.model.ISO8583Model;
import me.veeraya.ISO8583.repository.ISO8583Repository;
import me.veeraya.ISO8583.service.ISO8583Service;

@RestController
@RequestMapping("/ISO8583")
@CrossOrigin(origins = "*")
public class ISO8583Controller {
	@Autowired
	private ISO8583Service iso8583Service;
	
	@Autowired
	private ISO8583Repository iso8583Repository;
	
	@GetMapping
	public List<ISO8583Model> getAllISO8583(){
		System.out.println("sss");
		return iso8583Service.getAllIso8583Models();
	}
	
	@GetMapping("/{id_device}")
    public ISO8583Model getDeviceById(@PathVariable Integer id_device){
        return iso8583Repository.findById(id_device).get();
    } 

}
