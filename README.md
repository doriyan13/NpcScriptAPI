# ❤ Dori NPC Script API ❤
This is my take on NPC scripting, i wanted to make fluent low overhead scripting ability that also let you debug in run-time
the code and still have the hotswap ability, in addition wanted to have auto complete ability with a chaining style writing.

Enjoy ~


### Example -
```java
public class Henesys {
    private static MenuOption addTaxiMoveOption(ScriptApi script,
                                        MapleChar chr,
                                        int mapId,
                                        boolean beginner,
                                        int cost) {
        int finalCost = beginner ? cost / 10 : cost;
        String mapName = NpcMessageUtils.mapName(mapId);
        return script.addMenuOption( mapName + "(" + (finalCost) + " mesos)", () -> {
            script.askYesNo("You don't have anything else to do here, huh? Do you really want to go to "
                    + NpcMessageUtils.bold(mapName) + "? It'll cost you " + NpcMessageUtils.bold(finalCost) + " mesos.", response -> {
                if (response) {
                    if (chr.getMeso() - finalCost >= 0) {
                        chr.modifyMeso(-finalCost);
                        chr.warp(mapId);
                    } else {
                        script.sayOK("You don't have enough mesos. Sorry to say this, but without them, you won't be able to ride the cab.");
                    }
                } else {
                    script.sayOK("There's a lot to see in this town, too. Come back and find us when you need to go to a different town.");
                }
            });
        });
    }
    
    // Town ID - 100000000
    @NpcScript(id = 1012000)
    public static ScriptApi handleTaxi(MapleChar chr) {

        ScriptApi script = new ScriptApi();
        boolean beginner = chr.getJob() == Beginner.getId();
        script.sayNext("Hello, I drive the Regular Cab. If you want to go from town to town safely and fast, then ride our cab. We'll glady take you to your destination with an affordable price.");
        if (beginner) {
            script.askMenu("We have a special 90% discount for beginners.",
                            NpcScriptUtils.addTaxiMoveOption(script,chr,104000000,chr.getJob() == 0, 1000),
                            NpcScriptUtils.addTaxiMoveOption(script,chr,102000000,chr.getJob() == 0, 1000),
                            NpcScriptUtils.addTaxiMoveOption(script,chr,101000000,chr.getJob() == 0, 800),
                            NpcScriptUtils.addTaxiMoveOption(script,chr,103000000,chr.getJob() == 0, 1000),
                            NpcScriptUtils.addTaxiMoveOption(script,chr,120000000,chr.getJob() == 0, 800)
                            );
        }
        return script;
    }
}
```