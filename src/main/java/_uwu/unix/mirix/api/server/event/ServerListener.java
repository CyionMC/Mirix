package _uwu.unix.mirix.api.server.event;

import _uwu.unix.mirix.core.server.event.*;

/**
 * @author Unix on 06.10.2019.
 */
public interface ServerListener {

    void serverBound(ServerBoundEvent event);

    void serverClosing(ServerClosingEvent event);

    void serverClosed(ServerClosedEvent event);

    void onPacketReceived(PacketReceivedEvent event);

    void onPacketSent(PacketSentEvent event);

//    void sessionAdded(SessionAddedEvent event);
//
//    void sessionRemoved(SessionRemovedEvent event);

}