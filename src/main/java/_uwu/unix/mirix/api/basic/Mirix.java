package _uwu.unix.mirix.api.basic;

import _uwu.unix.mirix.api.server.Server;

import java.io.IOException;

/**
 * @author Unix on 06.10.2019.
 */
public interface Mirix {

    void onLoad() throws IOException;

    Server getServer();

}