package _uwu.unix.mirix.api.registry;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Unix on 06.10.2019.
 */
public interface Registry<K, V> {

    Collection<V> getValues();

    Optional<V> getValue(K key);

    Optional<K> getKey(V value);

    void register(K key, V value);

}