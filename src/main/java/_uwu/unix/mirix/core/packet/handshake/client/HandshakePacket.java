package _uwu.unix.mirix.core.packet.handshake.client;

import _uwu.unix.mirix.api.data.HandshakeIntent;
import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.api.util.IOUtil;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Unix on 06.10.2019.
 */
public class HandshakePacket implements Packet {

    private String hostname;
    private int    protocolVersion, port, intent;

    public HandshakePacket() {}

    public HandshakePacket(int protocolVersion, String hostname, int port, @NotNull HandshakeIntent intent) {
        this.protocolVersion = protocolVersion;
        this.hostname        = hostname;
        this.port            = port;
        this.intent          = intent.getId();
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.protocolVersion = IOUtil.readVarInt(in);
        this.hostname        = IOUtil.readString(in, 255);
        this.port            = in.readUnsignedShort();
        this.intent          = IOUtil.readVarInt(in);
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        IOUtil.writeVarInt(out, this.protocolVersion);
        IOUtil.writeString(out, this.hostname);
        out.writeShort(this.port);
        IOUtil.writeVarInt(out, this.intent);
    }

    @Override
    public boolean hasPriority() {
        return true;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void parse(Player player) {
    }

    public HandshakeIntent getIntent() {
        return switch (this.intent) {
            case 1 -> HandshakeIntent.STATUS;
            case 2 -> HandshakeIntent.LOGIN;
            default -> null;
        };
    }
}