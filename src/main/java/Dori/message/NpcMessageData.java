package Dori.message;


import Dori.packet.OutPacket;

public interface NpcMessageData {
    void encode(OutPacket outPacket);
    String getMsg();

    void setMsg(String updatedMsg);
}
