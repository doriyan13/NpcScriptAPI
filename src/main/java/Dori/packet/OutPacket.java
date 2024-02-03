package Dori.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * This is a watered down OutPacket class, replace this one with your src OutPacket handling!!
 * @author Dori
 */
public class OutPacket extends Packet {
    private final ByteBuf baos;
    private boolean loopback = false;
    private boolean encryptedByShanda = false;
    private short op;

    /**
     * Creates a new OutPacket with a given op. Immediately encodes the op.
     *
     * @param op The opcode of this OutPacket.
     */
    public OutPacket(short op) {
        super(new byte[]{});
        baos = Unpooled.buffer();
        encodeShort(op);
        this.op = op;
    }

    /**
     * Creates a new OutPacket with a given op. Immediately encodes the op.
     *
     * @param op The opcode of this OutPacket.
     */
    public OutPacket(int op) {
        this((short) op);
    }

    /**
     * Creates a new OutPacket, and initializes the data as empty.
     */
    public OutPacket() {
        super(new byte[]{});
        baos = Unpooled.buffer();
    }

    /**
     * Creates a new OutPacket with given data.
     *
     * @param data The data this net.swordie.ms.connection.packet has to be initialized with.
     */
    public OutPacket(byte[] data) {
        super(data);
        baos = Unpooled.buffer();
        encodeArr(data);
    }

    /**
     * Encodes a single byte to this OutPacket.
     *
     * @param b The int to encode as a byte. Will be downcast, so be careful.
     */
    public void encodeByte(int b) {
        encodeByte((byte) b);
    }

    /**
     * Encodes a byte to this OutPacket.
     *
     * @param b The byte to encode.
     */
    public void encodeByte(byte b) {
        baos.writeByte(b);
    }

    public void encodeBool(boolean bNext) {
        encodeByte(bNext ? 1 : 0);
    }

    /**
     * Encodes a byte array to this OutPacket.
     * Named like this to prevent autocompletion of "by" to "byteArray" or similar names.
     *
     * @param bArr The byte array to encode.
     */
    public void encodeArr(byte[] bArr) {
        baos.writeBytes(bArr);
    }

    /**
     * Encodes a character to this OutPacket, UTF-8.
     *
     * @param c The character to encode
     */
    public void encodeChar(char c) {
        baos.writeByte(c);
    }

    /**
     * Encodes a boolean to this OutPacket.
     *
     * @param b The boolean to encode (0/1)
     */
    public void encodeByte(boolean b) {
        baos.writeBoolean(b);
    }

    /**
     * Encodes a short to this OutPacket, in little endian.
     *
     * @param s The short to encode.
     */
    public void encodeShort(short s) {
        baos.writeShortLE(s);
    }

    public void encodeShortBE(short s) {
        baos.writeShort(s);
    }

    public void encodeIntBE(int i) {
        baos.writeInt(i);
    }

    /**
     * Encodes an integer to this OutPacket, in little endian.
     *
     * @param i The integer to encode.
     */
    public void encodeInt(int i) {
        baos.writeIntLE(i);
    }

    /**
     * Encodes a long to this OutPacket, in little endian.
     *
     * @param l The long to encode.
     */
    public void encodeLong(long l) {
        baos.writeLongLE(l);
    }

    /**
     * Encodes a String to this OutPacket.
     * Structure: short(size) + char array of <code>s</code>.
     *
     * @param s The String to encode.
     */
    public void encodeString(String s) {
        if (s == null) {
            s = "";
        }
        if (s.length() > Short.MAX_VALUE) {
            return;
        }
        encodeShort((short) s.length());
        encodeString(s, (short) s.length());
    }

    /**
     * Writes a String as a character array to this OutPacket.
     * If <code>s.length()</code> is smaller than length, the open spots are filled in with zeros.
     *
     * @param s      The String to encode.
     * @param length The maximum length of the buffer.
     */
    public void encodeString(String s, short length) {
        if (s == null) {
            s = "";
        }
        if (!s.isEmpty()) {
            for (char c : s.toCharArray()) {
                encodeChar(c);
            }
        }
        for (int i = s.length(); i < length; i++) {
            encodeByte((byte) 0);
        }
    }

    @Override
    public void setData(byte[] nD) {
        super.setData(nD);
        baos.clear();
        encodeArr(nD);
    }

    @Override
    public byte[] getData() {

        return super.getData();
    }

    @Override
    public Packet clone() {
        return new OutPacket(getData());
    }

    public boolean isLoopback() {
        return loopback;
    }

    public boolean isEncryptedByShanda() {
        return encryptedByShanda;
    }

    @Override
    public String toString() {
        return "";
    }

    public void encodeShort(int value) {
        encodeShort((short) value);
    }

    public void encodeString(String name, int length) {
        encodeString(name, (short) length);
    }
}
