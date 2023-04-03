package me.veeraya.ISO8583.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.veeraya.ISO8583.model.ISO8583Model;

@Repository
public interface ISO8583Repository extends JpaRepository<ISO8583Model, Integer>{
	
}
