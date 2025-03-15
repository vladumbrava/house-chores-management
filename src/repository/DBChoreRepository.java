package repository;

import domain.Chore;
import domain.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class DBChoreRepository extends MemoryRepository<UUID, Chore>{
    public String URL;

    public DBChoreRepository(String repoPath) {
        this.URL = "jdbc:sqlite:" + repoPath;
        readFromDB();
    }

    public void readFromDB() {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Chore;")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID choreID = UUID.fromString(resultSet.getString(1));
                UUID choreAssignedMemberID = UUID.fromString(resultSet.getString(2));
                String choreTitle = resultSet.getString(3);
                String choreDescription = resultSet.getString(4);
                LocalDateTime choreDeadline = LocalDateTime.parse(resultSet.getString(5));
                int choreAssignedPoints = resultSet.getInt(6);
                Status choreStatus = Status.valueOf(resultSet.getString(7));
                Chore chore = new Chore(choreAssignedMemberID, choreTitle, choreDescription, choreDeadline, choreAssignedPoints, choreStatus);
                chore.setID(choreID);
                this.map.put(choreID, chore);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(UUID id, Chore object) {
        super.add(id, object);

        try (Connection connection = DriverManager.getConnection(URL);
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO Chore VALUES (?,?,?,?,?,?,?);")) {
            statement.setString(1,object.getID().toString());
            statement.setString(2,object.getChoreAssignedMemberID().toString());
            statement.setString(3,object.getChoreTitle());
            statement.setString(4,object.getChoreDescription());
            statement.setString(5,object.getChoreDeadline().toString());
            statement.setInt(6,object.getChoreAssignedPoints());
            statement.setString(7,object.getChoreStatus().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        super.delete(id);

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Chore WHERE choreID = ?")) {
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Chore findByID(UUID id) {
        return super.findByID(id);
    }
}
