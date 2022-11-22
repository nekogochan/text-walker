package nekogochan.textwalker.charautomate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nekogochan.textwalker.util.CharAutomateRules.backEq;
import static nekogochan.textwalker.util.CharAutomateRules.currentEq;
import static nekogochan.textwalker.util.CharAutomateRules.forwardEq;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkipWhileTests {

  CharAutomate auto;

  @BeforeEach
  void init() {
    auto = new CharArrayAutomate(
      "         many spaces $ money $"
    );
  }

  @Test
  void currentEqOne() {
    auto.skipWhile(currentEq(' '));
    assertEquals(9, auto.currentPos());
    assertEquals('m', auto.current());
  }

  @Test
  void currentEqAny() {
    auto.skipWhile(currentEq(' ', 'm', 'a'));
    assertEquals(11, auto.currentPos());
    assertEquals('n', auto.current());
  }

  @Test
  void forwardEqOne() {
    auto.skipWhile(forwardEq("    ")); // four spaces
    assertEquals(5, auto.currentPos());
    assertEquals(' ', auto.current());
  }

  @Test
  void forwardEqAny() {
    auto.skipWhile(forwardEq(" ", "many"));
    assertEquals(9, auto.currentPos());
    assertEquals('m', auto.current());
  }

  @Test
  void backEqOne() {
    auto.goForward();
    auto.skipWhile(backEq(" "));
    assertEquals(10, auto.currentPos());
    assertEquals('a', auto.current());
  }

  @Test
  void backEqAny() {
    auto.goForward();
    auto.skipWhile(backEq(" ", "m", "ma", "man", "many"));
    assertEquals(15, auto.currentPos());
    assertEquals('p', auto.current());
  }

  @Test
  void backAndCurEq() {
    auto.skipWhile(currentEq(' ', 'm', 'a', 'n', 'y').or(backEq("many")));
    assertEquals(14, auto.currentPos());
    assertEquals('s', auto.current());
  }
}
