package _uwu.unix.mirix.core.server.event;

import _uwu.unix.mirix.api.server.Server;
import _uwu.unix.mirix.api.server.event.ServerEvent;
import _uwu.unix.mirix.api.server.event.ServerListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Unix on 06.10.2019.
 */
public class ServerBoundEvent implements ServerEvent {

    private final Server server;

    public ServerBoundEvent(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return this.server;
    }

    @Override
    public void call(@NotNull ServerListener listener) {
        listener.serverBound(this);
    }
}