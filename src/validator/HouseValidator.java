package validator;

import domain.House;

public class HouseValidator {
    public void validateHouse(House house) {
        if (house.getHouseTotalPoints() < 0) {
            house.setHouseTotalPoints(0);
        }
    }
}
