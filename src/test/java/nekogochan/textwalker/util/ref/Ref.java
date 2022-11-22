package nekogochan.textwalker.util.ref;

public class Ref<T> {
  T val;

  public Ref(T val) {
    this.val = val;
  }

  public Ref() {
  }

  public T get() {
    return val;
  }

  public void set(T val) {
    this.val = val;
  }
}
