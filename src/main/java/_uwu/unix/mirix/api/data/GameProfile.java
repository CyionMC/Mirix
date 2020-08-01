package _uwu.unix.mirix.api.data;

import java.util.UUID;

/**
 * @author Unix on 06.10.2019.
 */
public class GameProfile {

    private final UUID    uuid;
    private final String  name;
    private final boolean legacy;

    public GameProfile(UUID uuid, String name, boolean legacy) {
        this.uuid   = uuid;
        this.name   = name;
        this.legacy = legacy;
    }

    public GameProfile(UUID uuid, String name) {
        this(uuid, name, false);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public boolean isLegacy() {
        return this.legacy;
    }
}