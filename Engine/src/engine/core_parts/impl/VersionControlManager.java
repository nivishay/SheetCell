package engine.core_parts.impl;

import engine.core_parts.api.Cell;
import dto.small_parts.CellLocation;
import dto.small_parts.EffectiveValue;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class VersionControlManager implements Serializable {
    private Map<Integer, Map<CellLocation, EffectiveValue>> versionToCellsChanges;
    private SheetCellImp sheetCell;

    public VersionControlManager(Map<Integer, Map<CellLocation, EffectiveValue>> versionToCellsChanges, SheetCellImp sheetCell) {
        this.versionToCellsChanges = versionToCellsChanges;
        this.sheetCell = sheetCell;
    }

    public void versionControl() {
        int sheetCellLatestVersion = sheetCell.getLatestVersion();
        versionToCellsChanges.put(sheetCellLatestVersion, new HashMap<>());

        Map<CellLocation, EffectiveValue> changedCells = versionToCellsChanges.get(sheetCellLatestVersion);

        for (Map.Entry<CellLocation, Cell> entry : sheetCell.getSheetCell().entrySet()) {
            CellLocation location = entry.getKey();
            Cell cell = entry.getValue();
            // Check if the cell's latest version matches the sheet's latest version
            if (cell.getLatestVersion() == sheetCellLatestVersion) {   // Assuming Cell has a getVersion() method// Replace with your logic to calculate the effective value
                changedCells.put(location, cell.getEffectiveValue().evaluate(sheetCell));
            }
        }
    }

    public void updateVersions(Cell targetCell) {
        sheetCell.updateVersion();
        targetCell.updateVersion(sheetCell.getLatestVersion());
    }

    public Map<Integer, Map<CellLocation, EffectiveValue>> getVersions() {
        return versionToCellsChanges;
    }
}


