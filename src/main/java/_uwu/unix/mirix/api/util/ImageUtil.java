package _uwu.unix.mirix.api.util;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Unix on 06.10.2019.
 */
public final class ImageUtil {

    private ImageUtil() {}

    public static String imageToString(@NotNull BufferedImage bufferedImage) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage, "png", Base64.getEncoder().wrap(byteArrayOutputStream));
            return "data:image/png;base64," + byteArrayOutputStream.toString(StandardCharsets.ISO_8859_1.name());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static BufferedImage stringToImage(@NotNull String string) {
        try {
            return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(string)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}