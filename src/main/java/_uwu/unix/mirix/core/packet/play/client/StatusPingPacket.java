package _uwu.unix.mirix.core.packet.play.client;

import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.core.packet.play.server.StatusPongPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Unix on 06.10.2019.
 */
public class StatusPingPacket implements Packet {

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
        player.send(new StatusPongPacket());
    }
}