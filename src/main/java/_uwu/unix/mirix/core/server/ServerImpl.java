package _uwu.unix.mirix.core.server;

import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.api.server.Server;
import _uwu.unix.mirix.api.server.event.ServerEvent;
import _uwu.unix.mirix.api.server.event.ServerListener;
import _uwu.unix.mirix.core.player.PlayerImpl;
import _uwu.unix.mirix.core.server.event.ServerBoundEvent;
import _uwu.unix.mirix.core.server.event.ServerClosedEvent;
import _uwu.unix.mirix.core.server.event.ServerClosingEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Unix on 06.10.2019.
 */
public class ServerImpl implements Server {

    private final Logger              logger;
    private final Set<ServerListener> listeners;
    private final Map<String, Object> flags;

    private ServerSocket              serverSocket;
    private boolean                   listening;

    public ServerImpl() {
        this.logger    = Logger.getLogger("ServerImpl");
        this.listeners = new HashSet<>();
        this.flags     = new HashMap<>();
    }

    @Override
    public void bind(int port) throws IOException {
        if (this.isListening()) return;

        this.listening = true;
        this.serverSocket = new ServerSocket(port);
        this.callEvent(new ServerBoundEvent(this));

        while (this.isListening()) {
            final Socket socket = this.serverSocket.accept();

            new PlayerImpl(socket).createConnection();
            this.logger.info("Accepting socket: " + socket.getRemoteSocketAddress().toString().split("/")[1]);
        }
    }

    @Override
    public void close() throws IOException {
        if (!this.isListening()) return;

        this.callEvent(new ServerClosingEvent(this));
        this.listening = false;
        this.serverSocket.close();
        this.callEvent(new ServerClosedEvent(this));
    }

    @Override
    public boolean isListening() {
        return this.listening;
    }

    @Override
    public int getPort() {
        return this.serverSocket.getLocalPort();
    }

    @Override
    public void setFlag(String key, Object value) {
        this.flags.put(key, value);
    }

    @Override
    public Object getFlag(String key) {
        return this.flags.get(key);
    }

    @Override
    public Map<String, Object> getFlags() {
        return this.flags;
    }

    @Override
    public void addListener(ServerListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void callEvent(@NotNull ServerEvent event) {
        this.listeners.forEach(event::call);
    }
}