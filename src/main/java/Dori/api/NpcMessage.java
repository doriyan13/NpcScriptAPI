package Dori.api;

import Dori.message.NpcMessageData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpcMessage {
    private NpcMessageType type;
    private NpcMessageData data;
}
