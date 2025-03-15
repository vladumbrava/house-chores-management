package domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Chore implements Identifiable<UUID>, Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    private UUID choreID;
    private UUID choreAssignedMemberID;
    private String choreTitle;
    private String choreDescription;
    private LocalDateTime choreDeadline;
    private int choreAssignedPoints;
    private Status choreStatus;

    public Chore(UUID choreAssignedMemberID, String choreTitle, String choreDescription, LocalDateTime choreDeadline, int choreAssignedPoints, Status choreStatus) {
        this.choreID = UUID.randomUUID();
        this.choreAssignedMemberID = choreAssignedMemberID;
        this.choreTitle = choreTitle;
        this.choreDescription = choreDescription;
        this.choreDeadline = choreDeadline;
        this.choreAssignedPoints = choreAssignedPoints;
        this.choreStatus = choreStatus;
    }

    public UUID getChoreAssignedMemberID() {
        return choreAssignedMemberID;
    }

    public void setChoreAssignedMemberID(UUID choreAssignedMemberID) {
        this.choreAssignedMemberID = choreAssignedMemberID;
    }

    public String getChoreTitle() {
        return choreTitle;
    }

    public void setChoreTitle(String choreTitle) {
        this.choreTitle = choreTitle;
    }

    public String getChoreDescription() {
        return choreDescription;
    }

    public void setChoreDescription(String choreDescription) {
        this.choreDescription = choreDescription;
    }

    public LocalDateTime getChoreDeadline() {
        return choreDeadline;
    }

    public void setChoreDeadline(LocalDateTime choreDeadline) {
        this.choreDeadline = choreDeadline;
    }

    public int getChoreAssignedPoints() {
        return choreAssignedPoints;
    }

    public void setChoreAssignedPoints(int choreAssignedPoints) {
        this.choreAssignedPoints = choreAssignedPoints;
    }

    public Status getChoreStatus() {
        return choreStatus;
    }

    public void setChoreStatus(Status choreStatus) {
        this.choreStatus = choreStatus;
    }

    @Override
    public UUID getID() {
        return choreID;
    }

    @Override
    public void setID(UUID choreID) {
        this.choreID = choreID;
    }

    @Override
    public String toString() {
        return "Chore{" +
                "choreID=" + choreID +
                ", choreAssignedMemberID=" + choreAssignedMemberID +
                ", choreTitle='" + choreTitle + '\'' +
                ", choreDescription='" + choreDescription + '\'' +
                ", choreDeadline=" + choreDeadline +
                ", choreAssignedPoints=" + choreAssignedPoints +
                ", choreStatus=" + choreStatus +
                '}';
    }
}
