package _uwu.unix.mirix.core.server.event;

import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.api.server.event.ServerEvent;
import _uwu.unix.mirix.api.server.event.ServerListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Unix on 06.10.2019.
 */
public class PacketSentEvent implements ServerEvent {

    private final Player player;
    private final Packet packet;

    public PacketSentEvent(Player player, Packet packet) {
        this.player = player;
        this.packet = packet;
    }

    public Player getPlayer() {
        return this.player;
    }

    public <T extends Packet> T getPacket() {
        try {
            return (T) this.packet;
        } catch(ClassCastException e) {
            throw new IllegalStateException("Tried to get packet as the wrong type. Actual type: " + this.packet.getClass().getName());
        }
    }

    @Override
    public void call(@NotNull ServerListener listener) {
        listener.onPacketSent(this);
    }
}