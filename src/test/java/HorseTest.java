
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class HorseTest {

    @Test
    void constructor_ShouldThrowException_WhenNameIsNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 1.0, 1.0),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Name cannot be null."));
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void constructor_ShouldThrowException_WhenNameIsBlank(String name) {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 1.0, 1.0),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Name cannot be blank."));
    }

    @Test
    void constructor_ShouldThrowException_WhenSpeedIsNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Test", -1.0, 1.0),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Speed cannot be negative."));
    }

    @Test
    void constructor_ShouldThrowException_WhenDistanceIsNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Test", 1.0, -1.0),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Distance cannot be negative."));
    }

    @Test
    void checkParametersConstructorGetName() {
        Horse horse = new Horse("TestName", 1.0, 1.0);
        assertEquals("TestName", horse.getName());
    }
    @Test
    void checkParametersConstructorGetSpeed() {
        Horse horse = new Horse("TestName", 1.0, 1.0);
        assertEquals(1.0, horse.getSpeed());
    }

    @Test
    void checkParametersConstructorGetDistance() {
        Horse horse = new Horse("TestName", 1.0, 1.0);
        assertEquals(1.0, horse.getDistance());
    }

    @Test
    void checkCallsMoveGetRandomDoubleWithParameters() {
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("TestName", 1.0, 1.0);
            horse.move();
            mockStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1,0.5,12})
    void checkMoveDistance(double fakeDouble) {
        double expectedDistance = 1.0 + 1.0 * fakeDouble;
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("TestName", 1.0, 1.0);
            mockStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeDouble);
            horse.move();
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

    //----------------


    /*ParameterizedTest
    @MethodSource("checkGaiters")
    void checkParametersConstructorGeters(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse(input, 1.0, 1.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }
    private static Stream<String> checkGaiters() {
        return Stream.of("", " ", "\t", "\n", null);
    }*/

    @ParameterizedTest
    @CsvSource({
            "'TestName', -10.0, 5.0",
            "'Another Name', -15.0, 8.0"
    })
    void testHorseConstructorWithParametersSpeed(String name, double speed, double distance) {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, speed, distance),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Speed cannot be negative."));

    }

    @ParameterizedTest
    @CsvSource({
            "'TestName', 10.0, -5.0",
            "'Another Name', 15.0, -8.0"
    })
    void testHorseConstructorWithParametersDistance(String name, double speed, double distance) {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, speed, distance),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Distance cannot be negative."));
    }

    @Test
    void move_ShouldUseRandomDouble_WithinRange() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Test", 5.0, 10.0);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5); // Приклад замоканого значення

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
            assertEquals(12.5, horse.getDistance(), "Distance should be updated correctly after move.");
        }
    }

}