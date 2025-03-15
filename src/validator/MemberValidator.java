package validator;

import domain.Member;

public class MemberValidator {
    public void validateMember(Member member) {
        if (member.getMemberAge() < 0 || member.getMemberAge() > 125) {
            throw new IllegalArgumentException("Member's age should be between 0 and 125.");
        }
        if (member.getMemberPoints() < 0) {
            member.setMemberPoints(0);
        }
    }
}
