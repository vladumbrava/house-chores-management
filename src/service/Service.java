package service;

import domain.House;
import domain.Member;
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
        for (UUID memberID : houseRepo.findByID(houseID).getHouseMembersList()){
            if (memberRepo.findByID(memberID).getMemberFirstName().equals(memberFirstName) &&
            memberRepo.findByID(memberID).getMemberLastName().equals(memberLastName)){
                memberIDFound = memberID;
            }
        }
        return memberIDFound;
    }

    public UUID findChoreIDByTitle(UUID houseID, String choreTitle) {
        UUID choreIDFound = null;
        for (UUID choreID : houseRepo.findByID(houseID).getHouseChoresList()){
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

    public void updateHouseName(UUID houseID, String newHouseName) {
        houseRepo.findByID(houseID).setHouseName(newHouseName);
    }

    public void updateHouseTotalPoints(UUID houseID, int newTotalPoints) {
        houseRepo.findByID(houseID).setHouseTotalPoints(newTotalPoints);

    }

    public void addMemberToHouse(UUID houseID, String memberFirstName, String memberLastName, int memberAge) {
        Member member = new Member(memberFirstName, memberLastName, memberAge);
        memberRepo.add(member.getID(), member);
        House updatedHouse = houseRepo.findByID(houseID);
        updatedHouse.getHouseMembersList().add(member.getID());
        houseRepo.delete(houseID);
        houseRepo.add(houseID, updatedHouse);
    }

    public void removeMemberFromHouse(UUID houseID, UUID memberID) {
        memberRepo.delete(memberID);
        House updatedHouse = houseRepo.findByID(houseID);
        houseRepo.delete(houseID);
        updatedHouse.getHouseMembersList().remove(memberID);
        houseRepo.add(houseID, updatedHouse);
    }

    public void updateMemberFirstName(UUID memberID, String newMemberFirstName) {
        Member updatedMember = memberRepo.findByID(memberID);
        memberRepo.delete(memberID);
        updatedMember.setMemberFirstName(newMemberFirstName);
        memberRepo.add(memberID, updatedMember);
    }

    public void updateMemberLastName() {

    }

    public void updateMemberAge() {

    }

    public void updateMemberPoints() {

    }
}
