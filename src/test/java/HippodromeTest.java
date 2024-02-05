import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HippodromeTest {

    @Test
    void constructor_ThrowException_WhenParameterNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Horses cannot be null."));
    }

    @Test
    void constructor_ThrowException_WhenParameterEmpty() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()),
                "Expected constructor to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Horses cannot be empty."));
    }
    @Test
    void checkGetHorses_ParameterListConstructor() {
        ArrayList<Horse> listMockHorses = new ArrayList<>();
        for (int i = 0; i <30; i++) {
            listMockHorses.add(new Horse("TestName"+i, 1.0, 1.0));
        }
        ArrayList<Horse> listMockHorses1;
        listMockHorses1 = listMockHorses;
        Hippodrome hippodrome = new Hippodrome(listMockHorses);
        //listMockHorses1.add(new Horse("TestName", 1.0, 1.0));
        assertEquals(listMockHorses1, hippodrome.getHorses());
    }
    @Test
    void checkCalledMoveOnAllHorses() {
        ArrayList<Horse> listMockHorses = new ArrayList<>();
        for (int i = 0; i <50; i++) {
            listMockHorses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(listMockHorses);
        hippodrome.move();
        for (Horse horse : listMockHorses){
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }

    @Test
    void CheckReturnsCorrectWinner() {

        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("TestName1", 1.0, 1.0),
                new Horse("TestName2", 2.0, 2.0),
                new Horse("TestName3", 3.0, 3.0)
        ));
        assertEquals("TestName3", hippodrome.getWinner().getName());
    }
}