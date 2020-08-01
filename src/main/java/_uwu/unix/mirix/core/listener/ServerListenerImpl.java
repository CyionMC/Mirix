package _uwu.unix.mirix.core.listener;

import _uwu.unix.mirix.api.data.GameProfile;
import _uwu.unix.mirix.api.data.HandshakeIntent;
import _uwu.unix.mirix.api.data.SubProtocol;
import _uwu.unix.mirix.api.data.status.PlayerInfo;
import _uwu.unix.mirix.api.data.status.ServerStatusInfo;
import _uwu.unix.mirix.api.data.status.VersionInfo;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.api.server.event.ServerListener;
import _uwu.unix.mirix.core.packet.handshake.client.HandshakePacket;
import _uwu.unix.mirix.core.packet.play.server.StatusResponsePacket;
import _uwu.unix.mirix.core.server.event.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Unix on 06.10.2019.
 */
public class ServerListenerImpl implements ServerListener {

    private final Logger logger;

    public ServerListenerImpl() {
        this.logger = Logger.getLogger("ServerListenerImpl");
    }

    @Override
    public void serverBound(ServerBoundEvent event) {
        this.logger.info("boundPort: " + event.getServer().getPort());
    }

    @Override
    public void serverClosing(ServerClosingEvent event) {
    }

    @Override
    public void serverClosed(ServerClosedEvent event) {
    }

    @Override
    public void onPacketReceived(PacketReceivedEvent event) {
        final Player player = event.getPlayer();

        System.out.println("received: " + event.getPacket().getClass().getSimpleName());

        switch (player.getSubProtocol()) {
            case LOGIN:
                break;
            case STATUS:
                System.out.println("status : ]");
                final BaseComponent message = new TextComponent("Mirix");

                player.send(new StatusResponsePacket(new ServerStatusInfo(new VersionInfo("1.8.8", 47), new PlayerInfo(0, 1), new BaseComponent[] { message }, null)));
                break;
            case HANDSHAKE:
                if (event.getPacket() instanceof HandshakePacket) {
                    final HandshakePacket packet = event.getPacket();
            /*if (p.getProtocolVersion() > 47) {
                        //basic.getSession().disconnect("&7Probujesz polaczyc sie z innej wersji minecraft sprobuj z &c" + MinecraftConstants.GAME_VERSION + "&7.");
                    } else if (p.getProtocolVersion() < 47) {
                        //basic.getSession().disconnect("&7Probujesz polaczyc sie z innej wersji minecraft sprobuj z &c" + MinecraftConstants.GAME_VERSION + "&7.");
                    }*/
                    switch (packet.getIntent()) {
                        case STATUS -> player.setSubProtocol(SubProtocol.STATUS);
                        case LOGIN -> player.setSubProtocol(SubProtocol.LOGIN);
                        default -> throw new UnsupportedOperationException("Invalid client intent: " + packet.getIntent());
                    }

                    if (packet.getIntent() != HandshakeIntent.STATUS && packet.getIntent() != HandshakeIntent.LOGIN) {
                        // Only login and status modes are permitted.
                        try {
                            player.closeConnection();
                        } catch (IOException ignored) {
                        }
                    }
                }
                break;
            case PLAY:
                break;
        }
    }

    @Override
    public void onPacketSent(PacketSentEvent event) {
        System.out.println("sent: " + event.getPacket().getClass().getSimpleName());
    }
}