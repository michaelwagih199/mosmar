package helper;

public class UsefulCalculas {

    public float getUnitsFromHoleWeight(float weight_all_units, int weight_of_unit) {
        float result = 0;
        float weight_all_units_byGM = weight_all_units * 1000;
        result = (weight_all_units_byGM / weight_of_unit);
        return result;
    }

}
