package _uwu.unix.mirix.core.registry;

import _uwu.unix.mirix.api.packet.Packet;
import _uwu.unix.mirix.api.registry.Registry;
import _uwu.unix.mirix.api.util.LazyLoadBase;
import _uwu.unix.mirix.api.util.SafeUtil;
import com.google.common.collect.HashBiMap;

import java.util.*;

/**
 * @author Unix on 06.10.2019.
 */
public class PacketRegistry implements Registry<Integer, Packet> {

    private final Map<Integer, Class<?>> packetMap;
    private Map<Class<?>, Integer>       inversedMap;

    public PacketRegistry() {
        this.packetMap = new HashMap<>();
    }

    @Override
    public Collection<Packet> getValues() {
        throw new IllegalArgumentException("unsupported method");
    }

    @Override
    public Optional<Integer> getKey(Packet value) {
        if (Objects.isNull(this.inversedMap)) this.inversedMap = HashBiMap.create(this.packetMap).inverse();

        return Optional.ofNullable(this.inversedMap.get(value.getClass()));
    }

    @Override
    public Optional<Packet> getValue(Integer key) {
        final Class<?> optionalClass = packetMap.get(key);
        final LazyLoadBase<Packet> lazyLoadBase = new LazyLoadBase<>() {
            @Override
            protected Packet load() {
                return Objects.nonNull(optionalClass) ? (Packet) SafeUtil.safeExecute(optionalClass::newInstance) : null;
            }
        };

        return Optional.ofNullable(lazyLoadBase.getValue());
    }

    @Override
    public void register(Integer key, Packet value) {
        packetMap.put(key, value.getClass());
    }
}