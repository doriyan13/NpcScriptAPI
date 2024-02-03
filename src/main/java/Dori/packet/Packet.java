/*
    This file is part of Desu: MapleStory v62 Server Emulator
    Copyright (C) 2014  Zygon <watchmystarz@hotmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
