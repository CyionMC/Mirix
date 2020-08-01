package _uwu.unix.mirix.core.packet.play.server;

import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Unix on 06.10.2019.
 */
public class StatusPongPacket implements Packet {

    private long time;

    @Override
    public void read(DataInputStream in) throws IOException {
        this.time = in.readLong();
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeLong(this.time);
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void parse(Player player) {
    }
}