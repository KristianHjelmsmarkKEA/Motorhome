package com.example.demo.Service;

import com.example.demo.Model.CancelContract;
import com.example.demo.Model.Contract;
import com.example.demo.Model.FinalContract;
import com.example.demo.Model.OngoingContract;
import com.example.demo.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    ContractRepo contractRepo;

    public List<Contract> fetchAll(){
        return contractRepo.fetchAll();
    }

    public List<FinalContract> fetchAllFinalContracts(){
        return contractRepo.fetchAllFinalContracts();
    }

    public List<CancelContract> fetchAllCancelledContracts(){
        return contractRepo.fetchAllCancelledContracts();
    }

    public List<OngoingContract> fetchAllOngoingContracts(){
        return contractRepo.fetchAllOngoingContracts();
    }

    public Contract addContract(Contract contract){
        return contractRepo.addContract(contract);
    }

    public Boolean deleteContract(int contractID) { return contractRepo.deleteContract(contractID); }



}
