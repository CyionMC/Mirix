package _uwu.unix.mirix.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Unix on 06.10.2019.
 */
public final class IOUtil {

    private IOUtil() {
    }

    public static void writeString(DataOutputStream out, String string) throws IOException {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        if (bytes.length > 32767) {
            throw new RuntimeException("String too big (was " + bytes.length + " bytes encoded, max 32767)");
        }

        writeVarInt(out, bytes.length);
        writeBytes(out, bytes);
    }

    public static void writeBytes(DataOutputStream out, byte[] bytes) throws IOException {
        out.write(bytes);
    }

    public static int readBytes(DataInputStream in, byte[] bytes) throws IOException {
        return readBytes(in, bytes, 0, bytes.length);
    }

    public static int readBytes(DataInputStream in, byte[] bytes, int offset, int length) throws IOException {
        return in.read(bytes, offset, length);
    }

    public static byte[] readBytes(DataInputStream in, int length) throws IOException {
        final byte[] bytes = new byte[length];

        while (length > 0) {
            final int n = readBytes(in, bytes, bytes.length - length, length);

            if (n < 1) {
                throw new EOFException();
            }

            length -= n;
        }

        return bytes;
    }

    public static String readString(DataInputStream in, int maxLength) throws IOException {
        final int length = readVarInt(in);

        if (length > maxLength * 4) {
            throw new IOException("The received encoded string buffer length is longer than maximum allowed (" + length + " > " + maxLength * 4 + ")");
        }

        if (length < 0) {
            throw new IOException("The received encoded string buffer length is less than zero! Weird string!");
        }

        final String str = new String(readBytes(in, length), StandardCharsets.UTF_8);

        if (str.length() > maxLength) {
            throw new IOException("The received string length is longer than maximum allowed (" + length + " > " + maxLength + ")");
        }

        return str;
    }

    public static int readVarInt(@NotNull DataInputStream in) throws IOException {
        int value = 0;
        int size = 0;
        int b;

        while (((b = in.readByte()) & 0x80) == 0x80) {
            value |= (b & 0x7F) << (size++ * 7);
            if (size > 5) {
                throw new IOException("VarInt too long (length must be <= 5)");
            }
        }

        return value | ((b & 0x7F) << (size * 7));
    }

    public static void writeVarInt(DataOutputStream out, int value) throws IOException {
        while ((value & 0xFFFFFF80) != 0) {
            out.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }

        out.writeByte(value);
    }
}