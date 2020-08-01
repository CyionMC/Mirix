package _uwu.unix.mirix.api.util;

import org.jetbrains.annotations.Nullable;

/**
 * @author Unix on 06.10.2019.
 */
public final class SafeUtil {

    private SafeUtil() {
    }

    public interface SafeExecutor<T> {

        T execute() throws Throwable;

    }

    @Nullable
    public static <T> T safeExecute(SafeExecutor<T> executor) {
        try {
            return executor.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }
}