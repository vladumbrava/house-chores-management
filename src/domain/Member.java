package domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Member implements Identifiable<UUID>, Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    private UUID memberID;
    private String memberFirstName;
    private String memberLastName;
    private int memberAge;
    private int memberPoints;

    public Member(String memberFirstName, String memberLastName, int memberAge) {
        this.memberID = UUID.randomUUID();
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberAge = memberAge;
        this.memberPoints = 0;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }

    public int getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(int memberAge) {
        this.memberAge = memberAge;
    }

    public int getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(int memberPoints) {
        this.memberPoints = memberPoints;
    }

    @Override
    public UUID getID() {
        return memberID;
    }

    @Override
    public void setID(UUID memberID) {
        this.memberID = memberID;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberID=" + memberID +
                ", firstName='" + memberFirstName + '\'' +
                ", lastName='" + memberLastName + '\'' +
                ", age=" + memberAge +
                ", points=" + memberPoints +
                '}';
    }
}
