package _uwu.unix.mirix.api.server;

import _uwu.unix.mirix.api.server.event.ServerEvent;
import _uwu.unix.mirix.api.server.event.ServerListener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * @author Unix on 06.10.2019.
 */
public interface Server {

    void bind(int port) throws IOException;

    void close() throws IOException;

    boolean isListening();

    int getPort();

    void setFlag(String key, Object value);

    Object getFlag(String key);

    Map<String, Object> getFlags();

    void addListener(ServerListener listener);

    void callEvent(@NotNull ServerEvent event);

}