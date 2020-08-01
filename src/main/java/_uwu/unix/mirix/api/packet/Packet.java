package _uwu.unix.mirix.api.packet;

import _uwu.unix.mirix.api.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Unix on 06.10.2019.
 */
public interface Packet {

    void read(DataInputStream in) throws IOException;

    void write(DataOutputStream out) throws IOException;

    int getId();

    default boolean hasPriority() {
        return false;
    }

    void parse(Player player);

}