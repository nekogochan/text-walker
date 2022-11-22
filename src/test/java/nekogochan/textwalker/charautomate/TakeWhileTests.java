package nekogochan.textwalker.charautomate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nekogochan.textwalker.util.CharAutomateRules.backEq;
import static nekogochan.textwalker.util.CharAutomateRules.currentEq;
import static nekogochan.textwalker.util.CharAutomateRules.forwardEq;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TakeWhileTests {

  CharAutomate auto;

  @BeforeEach
  void init() {
    auto = new CharArrayAutomate(
      "         many spaces $ money $"
    );
  }

  @Test
  void currentEqOne() {
    var sb = auto.takeWhile(currentEq(' '));
    assertEquals("         ", sb.toString());
    assertEquals('m', auto.current());
  }

  @Test
  void currentEqAny() {
    var sb = auto.takeWhile(currentEq(' ', 'm', 'a'));
    assertEquals("         ma", sb.toString());
    assertEquals('n', auto.current());
  }

  @Test
  void forwardEqOne() {
    var sb = auto.takeWhile(forwardEq("    ")); // four spaces
    assertEquals("     ", sb.toString());
    assertEquals(' ', auto.current());
  }

  @Test
  void forwardEqAny() {
    var sb = auto.takeWhile(forwardEq(" ", "many"));
    assertEquals("         ", sb.toString());
    assertEquals('m', auto.current());
  }

  @Test
  void backEqOne() {
    auto.goForward();
    var sb = auto.takeWhile(backEq(" "));
    assertEquals("        m", sb.toString());
    assertEquals('a', auto.current());
  }

  @Test
  void backEqAny() {
    auto.goForward();
    var sb = auto.takeWhile(backEq(" ", "m", "ma", "man", "many"));
    assertEquals("        many s", sb.toString());
    assertEquals('p', auto.current());
  }

  @Test
  void backAndCurEq() {
    var sb = auto.takeWhile(currentEq(' ', 'm', 'a', 'n', 'y').or(backEq("many")));
    assertEquals("         many ", sb.toString());
    assertEquals('s', auto.current());
  }
}
