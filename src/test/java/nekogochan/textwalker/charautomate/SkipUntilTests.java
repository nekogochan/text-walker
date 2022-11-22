package nekogochan.textwalker.charautomate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nekogochan.textwalker.util.CharAutomateRules.backEq;
import static nekogochan.textwalker.util.CharAutomateRules.currentEq;
import static nekogochan.textwalker.util.CharAutomateRules.forwardEq;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkipUntilTests {

  CharAutomate auto;

  @BeforeEach
  void init() {
    auto = new CharArrayAutomate(
      "         many spaces $ many $"
    );
  }

  @Test
  void currentEqOne() {
    auto.skipUntil(currentEq('$'));
    assertEquals(21, auto.currentPos());
    assertEquals('$', auto.current());
  }

  @Test
  void currentEqAny() {
    auto.skipUntil(currentEq('$', 'y'));
    assertEquals(12, auto.currentPos());
    assertEquals('y', auto.current());

    auto.goForward();
    auto.skipUntil(currentEq('$', 'y'));
    assertEquals(21, auto.currentPos());
    assertEquals('$', auto.current());
  }

  @Test
  void forwardEqOne() {
    auto.skipUntil(forwardEq("many"));
    assertEquals(8, auto.currentPos());
    assertEquals(' ', auto.current());
  }

  @Test
  void forwardEqAny() {
    auto.skipUntil(forwardEq("spaces", "many"));
    assertEquals(8, auto.currentPos());
    assertEquals(' ', auto.current());

    auto.goForward();
    auto.skipUntil(forwardEq("spaces", "many"));
    assertEquals(13, auto.currentPos());
    assertEquals(' ', auto.current());
  }

  @Test
  void backEqOne() {
    auto.goForward();
    auto.skipUntil(backEq("many"));
    assertEquals(13, auto.currentPos());
    assertEquals(' ', auto.current());
  }

  @Test
  void backEqAny() {
    auto.goForward();
    auto.skipUntil(backEq("many", "many "));
    assertEquals(13, auto.currentPos());
    assertEquals(' ', auto.current());
  }

  @Test
  void forwardAndBackEq() {
    auto.skipUntil(backEq("many").and(forwardEq("$")));
    assertEquals(27, auto.currentPos());
    assertEquals(' ', auto.current());
  }
}
