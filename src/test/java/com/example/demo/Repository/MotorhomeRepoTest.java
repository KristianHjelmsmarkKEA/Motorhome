package com.example.demo.Repository;

import com.example.demo.Model.Motorhome;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MotorhomeRepoTest {

    @Test //Intet at Teste
    void fetchAll() {
    }

    @Test //Intet at Teste
    void fetchAllInService() {
    }

    @Test //Intet at Teste
    void addMotorhome() {
    }

    @Test //Intet at Teste
    void findMotorhomeID() {
    }

    @Test //Intet at Teste
    void findMotorhomeBrandAndModel() {
    }

    @Test //Intet at Teste
    void updateMotorhomeInformation() {
    }

    @Test //Intet at Teste
    void unavailableMotorhomes() {
    }

    @Test
    void fetchIntervalMotorhomes() {

        int unavailableMotorhomeID = 2;
        //In Service True
        Motorhome motorhomeOne = new Motorhome(1, "Jayco", 4, 26050, "AA11225", 60.0, true);
        //In Service False
        Motorhome motorhomeTwo = new Motorhome(unavailableMotorhomeID, "Newmar", 6, 152045, "BB22336", 80.0, false);
        //In Service True
        Motorhome motorhomeThree = new Motorhome(3, "Tiffin", 8, 10524, "CC33447", 120.0, true);

        List<Motorhome> allMotorhomes = new ArrayList<>();
        allMotorhomes.add(motorhomeOne);
        allMotorhomes.add(motorhomeTwo);
        allMotorhomes.add(motorhomeThree);

        //INT of Unavailable Motorhome ID's
        List<Integer> unavailableMH = new ArrayList<>();
        unavailableMH.add(unavailableMotorhomeID);

        List<Motorhome> unAvailableMotorhomes = new ArrayList<>();
        for (Motorhome motorhome : allMotorhomes) {
            for (int i = 0; i < unavailableMH.size(); i++) {
                if (motorhome.getMotorhomeID() == unavailableMH.get(i)) {
                    if (!unAvailableMotorhomes.contains(motorhome)) { //Adds Motorhome Object to unAvailableMotorhomes
                        unAvailableMotorhomes.add(motorhome);
                    }
                }
            }
        }
        for (int i = 0; i < unAvailableMotorhomes.size(); i++) { //Removes all unAvailableMotorhome Objects from return value.
            allMotorhomes.remove(unAvailableMotorhomes.get(i));
        }
        List<Motorhome> testAvailableMotorhomes = new ArrayList<>();
        testAvailableMotorhomes.add(motorhomeOne);
        testAvailableMotorhomes.add(motorhomeThree);

        assertEquals(allMotorhomes, testAvailableMotorhomes);
    }

    public List<Motorhome> removeDuplicateBrands(List<Motorhome> withDuplicates) {
        ArrayList<Motorhome> removedDuplicates = new ArrayList<>();

        for (Motorhome motorhome : withDuplicates) {
            if (!removedDuplicates.toString().contains(motorhome.getBrandAndModel())) {
                removedDuplicates.add(motorhome);
            }
        }
        return removedDuplicates;
    }

    @Test
    void removeDuplicateBrands() {

        //Dummies
        Motorhome motorhomeOne = new Motorhome(1, "Jayco", 4, 26050, "AA11225", 60.0, true);
        Motorhome motorhomeTwo = new Motorhome(2, "Newmar", 6, 152045, "BB22336", 80.0, true);
        Motorhome motorhomeThree = new Motorhome(3, "Tiffin", 8, 10524, "CC33447", 120.0, true);
        List<Motorhome> withDuplicates = new ArrayList<>();
        withDuplicates.add(motorhomeOne);
        withDuplicates.add(motorhomeTwo);
        withDuplicates.add(motorhomeThree);
        //Duplicate of Two added:
        withDuplicates.add(motorhomeTwo);
        assertEquals(withDuplicates.size(), 4);
        //Dummies
        List<Motorhome> testWithoutDuplicates = new ArrayList<>();
        testWithoutDuplicates.add(motorhomeOne);
        testWithoutDuplicates.add(motorhomeTwo);
        testWithoutDuplicates.add(motorhomeThree);

        List<Motorhome> removedDuplicates = new ArrayList<>();

        for (Motorhome motorhome : withDuplicates) {
            if (!removedDuplicates.toString().contains(motorhome.getBrandAndModel())) {
                removedDuplicates.add(motorhome);
            }
        }
        assertEquals(removedDuplicates, testWithoutDuplicates);
    }

    @Test //Intet at Teste
    void fetchMotorhomesBrandAndModel() {



    }
}