package _uwu.unix.mirix.core.packet;

import _uwu.unix.mirix.api.data.PacketDirection;
import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.core.basic.MirixImpl;
import _uwu.unix.mirix.core.server.event.PacketReceivedEvent;

import java.io.EOFException;
import java.lang.reflect.Constructor;

/**
 * @author Unix on 06.10.2019.
 */
public class PacketReader extends Thread {

    private final Player player;

    public PacketReader(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        while (this.player.isConnected()) {
            try {
                final int packetId = this.player.getPacketInput().readUnsignedByte();

                if (packetId < 0) {
                    continue;
                }

                this.player.getSubProtocol().getPacket(PacketDirection.SERVERBOUND, packetId).ifPresent(p -> {
                    try {
                        final Constructor<? extends Packet> constructor = p.getClass().getDeclaredConstructor();
                        if (!constructor.isAccessible()) {
                            constructor.setAccessible(true);
                        }

                        final Packet packet = constructor.newInstance();

                        packet.read(this.player.getPacketInput());
                        packet.parse(this.player);
                        MirixImpl.getInstance().getServer().callEvent(new PacketReceivedEvent(this.player, packet));
                    } catch (Exception ignored) {
                    }
                });
            } catch (EOFException e) {
                this.player.disconnect("End of Stream");
            } catch (Exception e) { ;
                this.player.disconnect("Error while listening to connection!");
            }
        }
    }
}