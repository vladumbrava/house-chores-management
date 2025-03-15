package validator;

import domain.Chore;

import java.time.LocalDateTime;

public class ChoreValidator {
    public void validateChore(Chore chore) {
        if (chore.getChoreDeadline().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Chore's deadline should be after the present moment.");
        }
    }
}
