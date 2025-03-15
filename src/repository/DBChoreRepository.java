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
    }

    private void readFromDB() {
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


}
