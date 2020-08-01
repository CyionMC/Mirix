package _uwu.unix.mirix.core.packet.play.client;

import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Unix on 06.10.2019.
 */
public class StatusQueryPacket implements Packet {
    @Override
    public void read(DataInputStream in) {
    }

    @Override
    public void write(DataOutputStream out) {
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean hasPriority() {
        return true;
    }

    @Override
    public void parse(Player player) {
    }
}