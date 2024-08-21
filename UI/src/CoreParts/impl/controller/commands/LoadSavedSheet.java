package CoreParts.impl.controller.commands;

import CoreParts.api.Engine;

public class LoadSavedSheet extends SheetEngineCommand {
    public LoadSavedSheet(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() throws Exception {
        String path = inputHandler.getFilePathInput();
        if (path == null) return;
        engine.load(path);
    }
}
