package _uwu.unix.mirix.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Unix on 06.10.2019.
 */
public class ChatUtil {

    private ChatUtil() {
    }

    @NotNull
    public static String fixColor(@NotNull String text) {
        return text.replace("&", "§")
                .replace(">>", "»")
                .replace("<<", "«");
    }

    @NotNull
    public static List<String> fixColor(@NotNull Collection<String> strings) {
        return strings
                .stream()
                .map(ChatUtil::fixColor)
                .collect(Collectors.toList());
    }
}