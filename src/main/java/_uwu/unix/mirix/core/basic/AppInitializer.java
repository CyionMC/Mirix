package _uwu.unix.mirix.core.basic;

import java.io.IOException;

/**
 * @author Unix on 06.10.2019.
 */
public class AppInitializer {

    public static void main(String... args) throws IOException {
        new MirixImpl().onLoad();
    }
}