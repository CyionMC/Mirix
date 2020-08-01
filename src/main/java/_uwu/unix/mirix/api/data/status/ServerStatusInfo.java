package _uwu.unix.mirix.api.data.status;

import net.md_5.bungee.api.chat.BaseComponent;

import java.awt.image.BufferedImage;

/**
 * @author Unix on 06.10.2019.
 */
public class ServerStatusInfo {

    private VersionInfo     version;
    private PlayerInfo      playerInfo;
    private BaseComponent[] description;
    private BufferedImage   icon;

    public ServerStatusInfo(VersionInfo version, PlayerInfo players, BaseComponent[] description, BufferedImage icon) {
        this.version     = version;
        this.playerInfo  = players;
        this.description = description;
        this.icon        = icon;
    }

    public VersionInfo getVersion() {
        return this.version;
    }

    public PlayerInfo getPlayerInfo() {
        return this.playerInfo;
    }

    public BaseComponent[] getDescription() {
        return this.description;
    }

    public BufferedImage getIcon() {
        return this.icon;
    }
}