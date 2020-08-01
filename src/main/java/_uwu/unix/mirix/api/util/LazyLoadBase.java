package _uwu.unix.mirix.api.util;

/**
 * @author Unix on 06.10.2019.
 */
public abstract class LazyLoadBase<T> {

    private T value;
    private boolean loaded;

    public T getValue() {
        if (!this.loaded) {
            this.loaded = true;
            this.value = this.load();
        }

        return this.value;
    }

    protected abstract T load();

}