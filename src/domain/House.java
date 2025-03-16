package domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class House implements Identifiable<UUID>, Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    private UUID houseID;
    private ArrayList<UUID> houseChoresList;
    private ArrayList<UUID> houseMembersList;
    private String houseName;
    private int houseTotalPoints;

    public House(ArrayList<UUID> houseChoresList, ArrayList<UUID> houseMembersList, String houseName) {
        this.houseID = UUID.randomUUID();
        this.houseChoresList = houseChoresList;
        this.houseMembersList = houseMembersList;
        this.houseName = houseName;
        this.houseTotalPoints = 0;
    }

    public House(String houseName, int houseTotalPoints) {
        this.houseID = UUID.randomUUID();
        this.houseName = houseName;
        this.houseTotalPoints = houseTotalPoints;
        this.houseChoresList = new ArrayList<>();
        this.houseMembersList = new ArrayList<>();
    }

    public House(String houseName) {
        this.houseID = UUID.randomUUID();
        this.houseName = houseName;
        this.houseTotalPoints = 0;
        this.houseChoresList = new ArrayList<>();
        this.houseMembersList = new ArrayList<>();
    }

    public House() {
    }

    public ArrayList<UUID> getHouseChoresList() {
        return houseChoresList;
    }

    public void setHouseChoresList(ArrayList<UUID> houseChoresList) {
        this.houseChoresList = houseChoresList;
    }

    public ArrayList<UUID> getHouseMembersList() {
        return houseMembersList;
    }

    public void setHouseMembersList(ArrayList<UUID> houseMembersList) {
        this.houseMembersList = houseMembersList;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getHouseTotalPoints() {
        return houseTotalPoints;
    }

    public void setHouseTotalPoints(int houseTotalPoints) {
        this.houseTotalPoints = houseTotalPoints;
    }

    @Override
    public UUID getID() {
        return houseID;
    }

    @Override
    public void setID(UUID houseID) {
        this.houseID = houseID;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseID=" + houseID +
                ", houseChoresList=" + houseChoresList +
                ", houseMembersList=" + houseMembersList +
                ", houseName='" + houseName + '\'' +
                ", houseTotalPoints=" + houseTotalPoints +
                '}';
    }
}
