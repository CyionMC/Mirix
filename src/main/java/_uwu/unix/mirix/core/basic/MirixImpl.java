package _uwu.unix.mirix.core.basic;

import _uwu.unix.mirix.api.basic.Mirix;
import _uwu.unix.mirix.api.server.Server;
import _uwu.unix.mirix.core.listener.ServerListenerImpl;
import _uwu.unix.mirix.core.server.ServerImpl;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Unix on 06.10.2019.
 */
public class MirixImpl implements Mirix {

    private static Mirix instance;

    private final Logger logger;
    private final Server server;

    MirixImpl() {
        instance    = this;
        this.logger = Logger.getLogger("MirixImpl");
        this.server = new ServerImpl();
    }

    @Override
    public void onLoad() throws IOException {
        this.logger.info("Loading...");

        this.server.addListener(new ServerListenerImpl());
        this.server.bind(1337);
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    public static Mirix getInstance() {
        return instance;
    }
}