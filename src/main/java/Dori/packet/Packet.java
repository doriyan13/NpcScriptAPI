package Dori.packet;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a packet to be sent over a TCP socket for MapleStory.
 * Very simply, it is an abstraction of raw data that applies some extra 
 * functionality because it is a MapleStory packet.
 *
 */
@Setter
@Getter
public class Packet implements Cloneable {

    private byte[] data;

    public Packet(byte[] data) {
        this.data = new byte[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    public static String readableByteArray(byte[] arr) {
        StringBuilder res = new StringBuilder();
        for(byte b : arr) {
            res.append(String.format("%02X ",b));
        }
        return res.toString();
    }
    
    @Override
    public String toString() {
        if (data == null) return "";
        return "[Pck] | " + readableByteArray(data);
    }
    
    @Override
    public Packet clone() {
        return new Packet(data);
    }

}
