package _uwu.unix.mirix.api.data.status;

import _uwu.unix.mirix.api.data.GameProfile;

/**
 * @author Unix on 06.10.2019.
 */
public class PlayerInfo {

    private int           onlinePlayers, maxPlayers;
    private GameProfile[] players;

    public PlayerInfo(int onlinePlayers, int maxPlayers, GameProfile... players) {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers    = maxPlayers;
        this.players       = players;
    }

    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public GameProfile[] getPlayers() {
        return this.players;
    }
}