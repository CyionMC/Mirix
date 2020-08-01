package _uwu.unix.mirix.core.player;

import _uwu.unix.mirix.api.data.SubProtocol;
import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.core.packet.PacketReader;
import _uwu.unix.mirix.core.packet.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Unix on 06.10.2019.
 */
public class PlayerImpl implements Player {

    private final Socket           socket;
    private final DataInputStream  packetInput;
    private final DataOutputStream packetOutput;
    private final Queue<Packet>    packets;

    private String                 name;
    private boolean                connected;
    private SubProtocol            subProtocol;

    public PlayerImpl(@NotNull Socket socket) throws IOException {
        this.socket       = socket;
        this.packetInput  = new DataInputStream(socket.getInputStream());
        this.packetOutput = new DataOutputStream(socket.getOutputStream());
        this.packets      = new ConcurrentLinkedQueue<>();
        this.subProtocol  = SubProtocol.HANDSHAKE;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void createConnection() throws IOException {
        this.connected = true;
        new PacketReader(this).start();
        new PacketWriter(this).start();
    }

    @Override
    public void closeConnection() throws IOException {
        this.connected = false;
        this.socket.close();
    }

    @Override
    public DataInputStream getPacketInput() {
        return this.packetInput;
    }

    @Override
    public DataOutputStream getPacketOutput() {
        return this.packetOutput;
    }

    @Override
    public Queue<Packet> getPackets() {
        return this.packets;
    }

    @Override
    public void send(Packet packet) {
        this.packets.add(packet);
    }

    @Override
    public void disconnect(String reason) {
        //send disconnect packet
        //		this.call(new DisconnectEvent(this, reason));
    }

    @Override
    public void setSubProtocol(SubProtocol subProtocol) {
        this.subProtocol = subProtocol;
    }

    @Override
    public SubProtocol getSubProtocol() {
        return this.subProtocol;
    }

    @Override
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}