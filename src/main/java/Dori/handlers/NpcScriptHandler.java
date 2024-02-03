package Dori.handlers;

import Dori.api.NpcScript;
import lombok.NoArgsConstructor;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

@NoArgsConstructor
public class NpcScriptHandler {
    private static NpcScriptHandler instance;
    private static final Map<Integer, Method> npcScripts = new HashMap<>();

    public static NpcScriptHandler getInstance() {
        if (instance == null) {
            instance = new NpcScriptHandler();
        }
        return instance;
    }

    /**
     * Searches through the given directory recursively to find all files
     * @param toAdd the set to add the files to
     * @param dir the directory to start in
     */
    public static void findAllFilesInDirectory(Set<File> toAdd, File dir) {
        // depth first search
        if (dir != null) {
            if (dir.isDirectory()) {
                for (File file : Objects.requireNonNull(dir.listFiles())) {
                    if (file.isDirectory()) {
                        findAllFilesInDirectory(toAdd, file);
                    } else {
                        toAdd.add(file);
                    }
                }
            }
        }
    }

    public static void initHandlers() {
        long start = System.currentTimeMillis();
        String handlersDir = "YOR NPC SCRIPTS DIR";
        Set<File> files = new HashSet<>();
        findAllFilesInDirectory(files, new File(handlersDir));
        for (File file : files) {
            try {
                // grab all files in the NPCs scripts dir, strip them to their package name, and remove .java extension -
                String className = file.getPath()
                        .replaceAll("[\\\\|/]", ".")
                        .split("src\\.main\\.java\\.")[1]
                        .replaceAll("\\.java", "");
                Class<?> clazz = Class.forName(className);
                for (Method method : clazz.getMethods()) {
                    NpcScript npcScript = method.getAnnotation(NpcScript.class);
                    if (npcScript != null) {
                        int npcID = npcScript.id();
                        if (npcID == -1) {
                            System.err.println("Found unMarked script! in the class - " + className);
                        } else {
                            npcScripts.put(npcID, method);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Initialized " + npcScripts.size() + " NPC scripts in " + (System.currentTimeMillis() - start) + "ms.");
    }

// This is an optional handling for utilizing the annotation ability -

//    public void handleNpcScript(@NotNull MapleChar chr,
//                                @NotNull Npc npc) {
//        Method method = npcScripts.get(npc.getTemplateId());
//        ScriptApi script = null;
//        if (method == null) {
//            script = new ScriptApi();
//            script.sayOK("The Npc ");
//            script.red(npc.getTemplateId())
//                    .addMsg("wasn't handled!");
//        } else {
//            try {
//                script = (ScriptApi) method.invoke(this, chr);
//            } catch (IllegalAccessException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//        if (script != null) {
//            chr.boundScript(script, npc.getTemplateId());
//            NpcMessage msg = script.getCurrentMsg();
//            chr.write(CScriptMan.scriptMessage((byte) 0, npc.getTemplateId(), msg.getType(), msg.getData()));
//        }
//    }
}
