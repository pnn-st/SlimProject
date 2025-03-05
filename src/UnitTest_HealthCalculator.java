import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class UnitTest_HealthCalculator {

    @Test
    public void test() {
        BMICalculator bmi = new BMICalculator(70, 175, 25, "Male");
        assertEquals(22.86, bmi.calculate(), 0.01);

        BMRCalculator bmr = new BMRCalculator(70, 175, 25, "Male");
        assertEquals(1724.05, bmr.calculate(), 0.01);

        BMRCalculator bmr2 = new BMRCalculator(55, 160, 25, "Female");
        assertEquals(1343.61, bmr2.calculate(), 0.01);

        BodyFatCalculator bodyFat = new BodyFatCalculator(70, 175, 25, "Male");
        assertEquals(16.98, bodyFat.calculate(), 0.01);

        WaterIntakeCalculator waterIntake = new WaterIntakeCalculator(70, 0, 0, "");
        assertEquals(2.31, waterIntake.calculate(), 0.01);
    }
}