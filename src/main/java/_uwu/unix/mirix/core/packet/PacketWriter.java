package _uwu.unix.mirix.core.packet;

import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.core.basic.MirixImpl;
import _uwu.unix.mirix.core.server.event.PacketReceivedEvent;
import _uwu.unix.mirix.core.server.event.PacketSentEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Unix on 06.10.2019.
 */
public class PacketWriter extends Thread {

    private final Player player;

    public PacketWriter(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        while (this.player.isConnected()) {
            if (this.player.getPackets().size() == 0) {
                continue;
            }

            System.out.println("packetSize > 0");
            final Packet packet = this.player.getPackets().poll();

            try {
                System.out.println("sent!");
                this.player.getPacketOutput().write(packet.getId());
                packet.write(this.player.getPacketOutput());
                this.player.getPacketOutput().flush();
                MirixImpl.getInstance().getServer().callEvent(new PacketSentEvent(this.player, packet));
            } catch (Exception e) {
                Logger.getLogger("PacketWriter").log(Level.SEVERE, "Error while writing packet \"" + packet.getId() + "\"!");
                this.player.disconnect("Error while writing packet.");
            }
        }
    }
}