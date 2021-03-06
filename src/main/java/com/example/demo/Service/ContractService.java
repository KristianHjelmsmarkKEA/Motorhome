package com.example.demo.Service;

import com.example.demo.Model.Contract;
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

    public int addContract(Contract contract){
        return contractRepo.addContract(contract);
    }

    public List<Contract> fetchOngoingContracts() {
        return contractRepo.fetchOngoingContracts();
    }

    public Contract findContractByContractID(int contractID) {
        return contractRepo.findContractByContractID(contractID);
    }

    public void saveContractInformation(Contract c, boolean trueFinalFalseCancel) { contractRepo.saveContractInformation(c, trueFinalFalseCancel); }


}
