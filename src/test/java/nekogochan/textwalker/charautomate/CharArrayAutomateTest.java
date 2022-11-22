package nekogochan.textwalker.charautomate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharArrayAutomateTest {

  private CharAutomate automateFrom(String s) {
    return new CharArrayAutomate(s.toCharArray());
  }

  @Test
  @DisplayName("Simple usage of automate should work fine")
  void simpleAutomateTest() {
    // Given
    var auto = automateFrom("I love cats!");

    // Then
    assertEquals('I', auto.current());

    // When
    auto.goForward();
    auto.goForward();
    auto.goForward();
    auto.goForward();

    // Then
    assertEquals('v', auto.current());

    // When
    auto.goBack();

    // Then
    assertEquals('o', auto.current());
  }

  @Test
  @DisplayName("Automate pos can go out of bounds, but call of current() should throw an exception and call of " +
               "outOfBounds() should be true")
  void outOfBoundsAutomateTest() {
    // Given
    var auto = automateFrom("I love cats!");

    // When
    auto.goBack();

    // Then
    assertThrows(Exception.class, auto::current);
    assertEquals(-1, auto.currentPos());
    assertTrue(auto.outOfBounds());

    // When
    for (int i = 0; i < 100; i++) {
      auto.goForward();
    }

    // Then
    assertThrows(Exception.class, auto::current);
    assertEquals(99, auto.currentPos());
    assertTrue(auto.outOfBounds());

    // When
    auto.setPos(999);

    // Then
    assertTrue(auto.outOfBounds());
  }

  @Test
  @DisplayName("Usage of next() method")
  void next() {
    // Given
    var auto = automateFrom("I love cats!");

    // When
    var a = auto.next();
    var b = auto.next();

    // Then
    assertEquals(0, auto.currentPos());
    assertEquals(' ', a);
    assertEquals(' ', b);
  }
}