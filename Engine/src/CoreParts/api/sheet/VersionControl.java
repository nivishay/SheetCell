package CoreParts.api.sheet;

import CoreParts.api.Cell;
import smallParts.CellLocation;
import smallParts.EffectiveValue;

import java.util.Map;

public interface VersionControl {
    Map<Integer, Map<CellLocation, EffectiveValue>> getVersions();
    SheetCell restoreSheetCell(int versionNumber);
    void updateVersions(Cell targetCell);
    void versionControl();
    byte[] saveSheetCellState();
    void clearVersionNumber();
     void updateVersion();
}