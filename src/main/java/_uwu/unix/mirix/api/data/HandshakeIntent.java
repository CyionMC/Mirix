package _uwu.unix.mirix.api.data;

/**
 * @author Unix on 06.10.2019.
 */
public enum HandshakeIntent {

    STATUS(1),
    LOGIN(2);

    private final int id;

    HandshakeIntent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}