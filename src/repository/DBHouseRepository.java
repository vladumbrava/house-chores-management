package repository;

import domain.House;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DBHouseRepository extends MemoryRepository<UUID, House> {
    public String URL;

    public DBHouseRepository(String repoPath) {
        this.URL = "jdbc:sqlite:" + repoPath;
    }

//    public void readFromDB() {
//        // i could initialize the houses with no chores and members
//        // and then create another try in which i try for each houseID to find
//        // the chores and the members
//        try (Connection connection = DriverManager.getConnection(URL);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM House")) {
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                UUID houseID = UUID.fromString(resultSet.getString(1));
//                String houseName = resultSet.getString(2);
//                int houseTotalPoints = resultSet.getInt(3);
//                House house = new House(houseName,houseTotalPoints);
//                house.setID(houseID);
//                this.map.put(houseID, house);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        UUID[] houseIDs = this.map.keySet().toArray(new UUID[0]);
//
//        for (UUID houseID : houseIDs) {
//            try (Connection connection = DriverManager.getConnection(URL);
//            PreparedStatement statement =
//                    connection.prepareStatement("SELECT memberID FROM HouseMembers WHERE houseID = ?")) {
//                statement.setString(1, houseID.toString());
//                ResultSet resultSet = statement.executeQuery();
//                ArrayList<UUID> houseMembersList = new ArrayList<>();
//                while (resultSet.next()) {
//                    UUID memberID = UUID.fromString(resultSet.getString(1));
//                    houseMembersList.add(memberID);
//                }
//                House oldHouse = this.map.get(houseID);
//                House newHouse = new House();
//                newHouse.setID(houseID);
//                newHouse.setHouseName(oldHouse.getHouseName());
//                newHouse.setHouseTotalPoints(oldHouse.getHouseTotalPoints());
//                newHouse.setHouseChoresList(oldHouse.getHouseChoresList());
//                newHouse.setHouseMembersList(houseMembersList);
//                this.map.replace(houseID,oldHouse,newHouse);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        for (UUID houseID : houseIDs) {
//            try (Connection connection = DriverManager.getConnection(URL);
//                 PreparedStatement statement =
//                         connection.prepareStatement("SELECT choreID FROM HouseChores WHERE houseID = ?")) {
//                statement.setString(1, houseID.toString());
//                ResultSet resultSet = statement.executeQuery();
//                ArrayList<UUID> houseChoresList = new ArrayList<>();
//                while (resultSet.next()) {
//                    UUID choreID = UUID.fromString(resultSet.getString(1));
//                    houseChoresList.add(choreID);
//                }
//                House oldHouse = this.map.get(houseID);
//                House newHouse = new House();
//                newHouse.setID(houseID);
//                newHouse.setHouseName(oldHouse.getHouseName());
//                newHouse.setHouseTotalPoints(oldHouse.getHouseTotalPoints());
//                newHouse.setHouseMembersList(oldHouse.getHouseMembersList());
//                newHouse.setHouseChoresList(houseChoresList);
//                this.map.replace(houseID,oldHouse,newHouse);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void readFromDB() {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM House")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID houseID = UUID.fromString(resultSet.getString(1));
                String houseName = resultSet.getString(2);
                int houseTotalPoints = resultSet.getInt(3);
                House house = new House(houseName, houseTotalPoints);
                house.setID(houseID);
                this.map.put(houseID, house);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UUID[] houseIDs = this.map.keySet().toArray(new UUID[0]);

        for (UUID houseID : houseIDs) {
            ArrayList<UUID> houseMembersList = getHouseMembers(houseID);
            ArrayList<UUID> houseChoresList = getHouseChores(houseID);

            House oldHouse = this.map.get(houseID);
            House newHouse = new House();
            newHouse.setID(houseID);
            newHouse.setHouseName(oldHouse.getHouseName());
            newHouse.setHouseTotalPoints(oldHouse.getHouseTotalPoints());
            newHouse.setHouseMembersList(houseMembersList);
            newHouse.setHouseChoresList(houseChoresList);
            this.map.replace(houseID, oldHouse, newHouse);
        }
    }

    private ArrayList<UUID> getHouseMembers(UUID houseID) {
        ArrayList<UUID> houseMembersList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("SELECT memberID FROM HouseMembers WHERE houseID = ?")) {
            statement.setString(1, houseID.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID memberID = UUID.fromString(resultSet.getString(1));
                houseMembersList.add(memberID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return houseMembersList;
    }

    private ArrayList<UUID> getHouseChores(UUID houseID) {
        ArrayList<UUID> houseChoresList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("SELECT choreID FROM HouseChores WHERE houseID = ?")) {
            statement.setString(1, houseID.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID choreID = UUID.fromString(resultSet.getString(1));
                houseChoresList.add(choreID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return houseChoresList;
    }

    @Override
    public void add(UUID id, House object) {
        super.add(id, object);

        try (Connection connection = DriverManager.getConnection(URL);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO House VALUES (?,?,?)")) {
            statement.setString(1, object.getID().toString());
            statement.setString(2, object.getHouseName());
            statement.setInt(3,object.getHouseTotalPoints());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO HouseChores VALUES (?,?)")) {
            UUID[] choresIDs = object.getHouseChoresList().toArray(new UUID[0]);
            for (UUID choreID : choresIDs) {
                statement.setString(1, object.getID().toString());
                statement.setString(2, choreID.toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO HouseMembers VALUES (?,?)")) {
            UUID[] membersIDs = object.getHouseMembersList().toArray(new UUID[0]);
            for (UUID memberID : membersIDs) {
                statement.setString(1, object.getID().toString());
                statement.setString(2, memberID.toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        super.delete(id);

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM House WHERE houseID = ?")) {
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM HouseMembers WHERE houseID = ?")) {
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM HouseChores WHERE houseID = ?")) {
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public House findByID(UUID id) {
        return super.findByID(id);
    }
}
