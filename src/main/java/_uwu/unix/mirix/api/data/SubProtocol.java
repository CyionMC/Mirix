package _uwu.unix.mirix.api.data;

import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.registry.Registry;
import _uwu.unix.mirix.core.packet.handshake.client.HandshakePacket;
import _uwu.unix.mirix.core.packet.play.client.StatusPingPacket;
import _uwu.unix.mirix.core.packet.play.client.StatusQueryPacket;
import _uwu.unix.mirix.core.packet.play.server.StatusPongPacket;
import _uwu.unix.mirix.core.packet.play.server.StatusResponsePacket;
import _uwu.unix.mirix.core.registry.PacketRegistry;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Unix on 06.10.2019.
 */
public enum SubProtocol {
    HANDSHAKE(-1) {
        {
            serverBound.register(0, new HandshakePacket());
        }
    },
    PLAY(0) {
        {
//            serverBound.register(0, new ClientKeepAlivePacket());
//            serverBound.register(1, new ClientChatPacket());
//
//            clientBound.register(0, new ServerKeepAlivePacket());
//            clientBound.register(1, new ServerJoinGamePacket());
//            clientBound.register(2, new ServerChatPacket());
//            clientBound.register(7, new ServerRespawnPacket());
//            clientBound.register(8, new ServerPlayerPositionRotationPacket());
//            clientBound.register(63, new ServerPluginMessagePacket());
//            clientBound.register(64, new ServerDisconnectPacket());
//            clientBound.register(69, new ServerTitlePacket());
//            clientBound.register(71, new ServerPlayerListDataPacket());
        }
    },
    STATUS(1) {
        {
            serverBound.register(0, new StatusQueryPacket());
            serverBound.register(1, new StatusPingPacket());

            clientBound.register(0, new StatusResponsePacket());
            clientBound.register(1, new StatusPongPacket());
        }
    },
    LOGIN(2) {
        {
//            serverBound.register(0, new ClientLoginStartPacket());
//            // serverBound.register(1, new ClientEncryptionResponsePacket());
//
//            clientBound.register(0, new ServerLoginDisconnectPacket());
//            //   clientBound.register(1, new ServerEncryptionRequestPacket());
//            clientBound.register(2, new ServerLoginSuccessPacket());
//            clientBound.register(3, new ServerSetCompressionPacket());
        }
    };

    protected final Registry<Integer, Packet> clientBound = new PacketRegistry();
    protected final Registry<Integer, Packet> serverBound = new PacketRegistry();

    private final int protocolId;

    SubProtocol(int protocolId) {
        this.protocolId = protocolId;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public Optional<Integer> getPacketId(PacketDirection direction, Packet packet) {
        return direction == PacketDirection.CLIENTBOUND ? clientBound.getKey(packet) : serverBound.getKey(packet);
    }

    public Optional<Packet> getPacket(PacketDirection direction, int id) {
        return direction == PacketDirection.CLIENTBOUND ? clientBound.getValue(id) : serverBound.getValue(id);
    }

    public static SubProtocol getById(int protocolId) {
        return Arrays.stream(values())
                .filter(connectionState -> connectionState.protocolId == protocolId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Don't found connection state with id " + protocolId));
    }
}