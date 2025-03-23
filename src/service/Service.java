package service;

import domain.House;
import repository.DBChoreRepository;
import repository.DBHouseRepository;
import repository.DBMemberRepository;

import java.util.Iterator;
import java.util.UUID;

public class Service {

    private final DBMemberRepository memberRepo;
    private final DBChoreRepository choreRepo;
    private final DBHouseRepository houseRepo;

    public Service(DBMemberRepository memberRepo, DBChoreRepository choreRepo, DBHouseRepository houseRepo) {
        this.memberRepo = memberRepo;
        this.choreRepo = choreRepo;
        this.houseRepo = houseRepo;
    }

    public DBMemberRepository getMemberRepo() {
        return memberRepo;
    }

    public DBChoreRepository getChoreRepo() {
        return choreRepo;
    }

    public DBHouseRepository getHouseRepo() {
        return houseRepo;
    }

    public UUID findHouseIDByHouseName(String houseName) {
        UUID houseID = null;
        Iterator<House> houseIterator = houseRepo.iterator();
        while (houseIterator.hasNext()){
            House house = houseIterator.next();
            if (house.getHouseName().equals(houseName)){
                houseID = house.getID();
            }
        }
        return houseID;
    }

    public UUID findMemberIDByMemberName(UUID houseID, String memberFirstName, String memberLastName) {
        UUID memberIDFound = null;
        House house = houseRepo.findByID(houseID);
        for (UUID memberID : house.getHouseMembersList()){
            if (memberRepo.findByID(memberID).getMemberFirstName().equals(memberFirstName) &&
            memberRepo.findByID(memberID).getMemberLastName().equals(memberLastName)){
                memberIDFound = memberID;
            }
        }
        return memberIDFound;
    }

    public UUID findChoreIDByTitle(UUID houseID, String choreTitle) {
        UUID choreIDFound = null;
        House house = houseRepo.findByID(houseID);
        for (UUID choreID : house.getHouseChoresList()){
            if (choreRepo.findByID(choreID).getChoreTitle().equals(choreTitle)){
                choreIDFound = choreID;
            }
        }
        return choreIDFound;
    }

    public void createHouse(String houseName) {
        House house = new House(houseName);
        houseRepo.add(house.getID(), house);
    }

    public void deleteHouse(UUID houseID) {
        House house = houseRepo.findByID(houseID);
        for (UUID memberID : house.getHouseMembersList()){
            memberRepo.delete(memberID);
        }
        for (UUID choreID : house.getHouseChoresList()){
            choreRepo.delete(choreID);
        }
        houseRepo.delete(houseID);
    }


}
