package Dori.message;

import Dori.api.NpcMessageType;
import Dori.packet.OutPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SayMsg implements NpcMessageData {

    private String msg;
    private NpcMessageType type;
    private byte speakerType;
    private int speakerTemplateID;

    @Override
    public void encode(OutPacket outPacket) {
        if ((speakerType & 4) != 0) {
            outPacket.encodeInt(speakerTemplateID); // speakerTemplateID
        }
        outPacket.encodeString(msg); // sMsg
        outPacket.encodeBool(type.isPrevPossible());
        outPacket.encodeBool(type.isNextPossible());
    }
}
