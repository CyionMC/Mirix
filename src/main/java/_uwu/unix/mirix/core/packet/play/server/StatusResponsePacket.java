package _uwu.unix.mirix.core.packet.play.server;

import _uwu.unix.mirix.api.data.GameProfile;
import _uwu.unix.mirix.api.data.status.PlayerInfo;
import _uwu.unix.mirix.api.data.status.ServerStatusInfo;
import _uwu.unix.mirix.api.data.status.VersionInfo;
import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.player.Player;
import _uwu.unix.mirix.api.util.IOUtil;
import _uwu.unix.mirix.api.util.ImageUtil;
import _uwu.unix.mirix.api.util.JsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Unix on 06.10.2019.
 */
public class StatusResponsePacket implements Packet {

    private ServerStatusInfo serverStatusInfo;

    public StatusResponsePacket() {}

    public StatusResponsePacket(ServerStatusInfo serverStatusInfo) {
        this.serverStatusInfo = serverStatusInfo;
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        final JsonObject jsonObject = JsonUtil.getGson().fromJson(IOUtil.readString(in,32767), JsonObject.class);
        final JsonObject version = jsonObject.get("version").getAsJsonObject();
        final VersionInfo versionInfo = new VersionInfo(version.get("name").getAsString(), version.get("protocol").getAsInt());
        final JsonObject players = jsonObject.get("players").getAsJsonObject();
        final List<GameProfile> gameProfiles = new ArrayList<>();

        if (players.has("sample")) {
            final JsonArray profiles = players.get("sample").getAsJsonArray();
            profiles.forEach(profileElement -> {
                final JsonObject profileObject = profileElement.getAsJsonObject();
                gameProfiles.add(new GameProfile(UUID.fromString(profileObject.get("id").getAsString()), profileObject.get("name").getAsString()));
            });
        }

        final PlayerInfo playerInfo = new PlayerInfo(players.get("max").getAsInt(), players.get("online").getAsInt(), gameProfiles.toArray(new GameProfile[0]));
        final BaseComponent[] description = ComponentSerializer.parse(jsonObject.get("description").getAsString());

        BufferedImage icon = null;

        if (jsonObject.has("favicon")) {
            icon = ImageUtil.stringToImage(jsonObject.get("favicon").getAsString());
        }

        this.serverStatusInfo = new ServerStatusInfo(versionInfo, playerInfo, description, icon);
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        final JsonObject jsonObject = new JsonObject();
        final JsonObject version = new JsonObject();
        version.addProperty("name", this.serverStatusInfo.getVersion().getName());
        version.addProperty("protocol", this.serverStatusInfo.getVersion().getProtocol());

        final JsonObject players = new JsonObject();
        players.addProperty("max", this.serverStatusInfo.getPlayerInfo().getMaxPlayers());
        players.addProperty("online", this.serverStatusInfo.getPlayerInfo().getOnlinePlayers());

        if (this.serverStatusInfo.getPlayerInfo().getPlayers().length > 0) {
            final JsonArray array = new JsonArray();

            Arrays.stream(this.serverStatusInfo.getPlayerInfo().getPlayers()).forEach(gameProfile -> {
                final JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("name", gameProfile.getName());
                jsonObject1.addProperty("id", gameProfile.getUuid().toString());
                array.add(jsonObject1);
            });

            players.add("sample", array);
        }
        jsonObject.add("version", version);
        jsonObject.add("players", players);
        jsonObject.add("description", JsonUtil.getJsonParser().parse(ComponentSerializer.toString(this.serverStatusInfo.getDescription())));

        if (this.serverStatusInfo.getIcon() != null) {
            jsonObject.addProperty("favicon", ImageUtil.imageToString(this.serverStatusInfo.getIcon()));
        }

        IOUtil.writeString(out, jsonObject.toString());
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void parse(Player player) {
    }
}