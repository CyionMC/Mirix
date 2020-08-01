package _uwu.unix.mirix.api.player;

import _uwu.unix.mirix.api.data.SubProtocol;
import _uwu.unix.mirix.api.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

/**
 * @author Unix on 06.10.2019.
 */
public interface Player {

    Socket getSocket();

    void createConnection() throws IOException;

    void closeConnection() throws IOException;

    DataInputStream getPacketInput();

    DataOutputStream getPacketOutput();

    Queue<Packet> getPackets();

    void send(Packet packet);

    void disconnect(String reason);

    void setSubProtocol(SubProtocol subProtocol);

    SubProtocol getSubProtocol();

    void setConnected(boolean connected);

    boolean isConnected();

    void setName(String name);

    String getName();

}