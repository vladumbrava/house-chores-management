package repository;

import domain.Member;

import java.sql.*;
import java.util.UUID;

public class DBMemberRepository extends MemoryRepository<UUID, Member> {
    public String URL;

    public DBMemberRepository(String repoPath) {
        this.URL = "jdbc:sqlite:" + repoPath;

    }

    private void readFromDB() {
        try (Connection connection = DriverManager.getConnection(URL);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Member;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID memberID = UUID.fromString(resultSet.getString(1));
                String memberFirstName = resultSet.getString(2);
                String memberLastName = resultSet.getString(3);
                int memberAge = resultSet.getInt(4);
                int memberPoints = resultSet.getInt(5);
                Member member = new Member(memberFirstName, memberLastName, memberAge, memberPoints);
                member.setID(memberID);
                this.map.put(memberID, member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(UUID id, Member object) {
        super.add(id, object);

        try (Connection connection = DriverManager.getConnection(URL);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Member VALUES (?,?,?,?,?);")) {
            statement.setString(1,object.getID().toString());
            statement.setString(2,object.getMemberFirstName());
            statement.setString(3,object.getMemberLastName());
            statement.setInt(4,object.getMemberAge());
            statement.setInt(5,object.getMemberPoints());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        super.delete(id);

        try (Connection connection = DriverManager.getConnection(URL);
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Member WHERE memberID = ?")) {
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Member findByID(UUID id) {
        return super.findByID(id);
    }
}
