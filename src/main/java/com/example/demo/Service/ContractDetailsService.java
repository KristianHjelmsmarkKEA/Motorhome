package com.example.demo.Service;

import com.example.demo.Model.ContractDetails;
import com.example.demo.Repository.ContractDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractDetailsService {

    @Autowired
    ContractDetailsRepo contractDetailsRepo;

    public List<ContractDetails> fetchAll() {return contractDetailsRepo.fetchAll();}
}
