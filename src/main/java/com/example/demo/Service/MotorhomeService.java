package com.example.demo.Service;

import com.example.demo.Model.Motorhome;
import com.example.demo.Repository.MotorhomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MotorhomeService {

    @Autowired
    MotorhomeRepo motorhomeRepo;

    public List<Motorhome> fetchAll(){
        return motorhomeRepo.fetchAll();
    }

    public void addMotorhome(Motorhome motorhome){
        motorhomeRepo.addMotorhome(motorhome);
    }

    public List<Motorhome> fetchIntervalMotorhomes(LocalDate startDate, LocalDate endDate) {
        return motorhomeRepo.fetchIntervalMotorhomes(startDate, endDate);
    }

    public Motorhome findMotorhome(int motorhomeID) { return motorhomeRepo.findMotorhomeID(motorhomeID); }

    public void updateMotorhomeInformation(Motorhome m) {
        motorhomeRepo.updateMotorhomeInformation(m);
    }

    public List<Motorhome> removeDuplicateBrands(List<Motorhome> withDuplicates) {
        return motorhomeRepo.removeDuplicateBrands(withDuplicates);
    }

    public List<Motorhome> fetchMotorhomesBrandAndModel(String brandAndModel, LocalDate startDate, LocalDate endDate) {
        return motorhomeRepo.fetchMotorhomesBrandAndModel(brandAndModel, startDate, endDate);
    }

    public Motorhome findMotorhomeBrandAndModel(int motorhomeID) { return motorhomeRepo.findMotorhomeBrandAndModel(motorhomeID); }

}
