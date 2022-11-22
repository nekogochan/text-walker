package nekogochan.textwalker.charautomate;

import org.junit.jupiter.api.Test;

import static nekogochan.textwalker.util.CharAutomateRules.backEq;
import static nekogochan.textwalker.util.CharAutomateRules.currentEq;
import static nekogochan.textwalker.util.CharAutomateRules.currentIs;
import static nekogochan.textwalker.util.CharAutomateRules.forwardEq;
import static nekogochan.textwalker.util.CharAutomateRules.notOutOfBounds;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("NewClassNamingConvention")
public class CharAutomateTakeSkip_outOfBoundsCases {
  @Test
  void takeUntil() {
    var auto = new CharArrayAutomate("some invalid data");
    assertThrows(ArrayIndexOutOfBoundsException.class,
                 () -> auto.takeUntil(currentEq('$')));
  }

  @Test
  void takeUntil_2() {
    var auto = new CharArrayAutomate("some invalid data");
    assertThrows(ArrayIndexOutOfBoundsException.class,
                 () -> auto.takeUntil(forwardEq("not-existing-word")));
  }

  @Test
  void takeUntil_3() {
    var auto = new CharArrayAutomate("some invalid data");
    assertThrows(ArrayIndexOutOfBoundsException.class,
                 () -> auto.takeUntil(backEq("not-existing-word")));
  }

  @Test
  void takeWhile() {
    var auto = new CharArrayAutomate("          ");
    assertThrows(ArrayIndexOutOfBoundsException.class,
                 () -> auto.takeWhile(currentEq(' ')));
  }

  @Test
  void takeWhile_2() {
    var auto = new CharArrayAutomate("            ");
    auto.takeWhile(forwardEq(" ")); // not throws
  }

  @Test
  void takeWhile_3() {
    var auto = new CharArrayAutomate("              ");
    auto.goForward();
    assertThrows(ArrayIndexOutOfBoundsException.class,
                 () -> auto.takeWhile(backEq(" ")));
  }

  @Test
  void notThrowsExample_wrong() {
    var auto = new CharArrayAutomate("        ");
    assertThrows(ArrayIndexOutOfBoundsException.class,
                 () -> auto.takeWhile(currentEq(' ').and(notOutOfBounds())));
  }

  @Test
  void notThrowsExample_right() {
    var auto = new CharArrayAutomate("           ");
    auto.takeWhile(notOutOfBounds().and(currentEq(' '))); // not throws
  }

  @Test
  void notThrowsExample_right2() {
    var auto = new CharArrayAutomate("           ");
    auto.takeWhile(notOutOfBounds().and(currentEq(' '))); // not throws
  }
}
