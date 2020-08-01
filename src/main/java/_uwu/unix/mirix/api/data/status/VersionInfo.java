package _uwu.unix.mirix.api.data.status;

/**
 * @author Unix on 06.10.2019.
 */
public class VersionInfo {

    private final String name;
    private final int    protocol;

    public VersionInfo(String name, int protocol) {
        this.name     = name;
        this.protocol = protocol;
    }

    public String getName() {
        return this.name;
    }

    public int getProtocol() {
        return this.protocol;
    }
}