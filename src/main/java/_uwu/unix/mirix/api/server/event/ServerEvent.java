package _uwu.unix.mirix.api.server.event;

/**
 * @author Unix on 06.10.2019.
 */
public interface ServerEvent {

    void call(ServerListener listener);

}