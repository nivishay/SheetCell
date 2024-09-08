package Utility.Exception;

import CoreParts.smallParts.CellLocation;

import java.util.Set;
import java.util.stream.Collectors;

public class RangeCantBeDeleted extends RuntimeException {

    String rangeName;
    Set<CellLocation> cellsThatThisRangeAffects;

    public RangeCantBeDeleted(String RangeName, Set<CellLocation> cellsThatThisRangeAffects) {
        this.rangeName = RangeName;
        this.cellsThatThisRangeAffects = cellsThatThisRangeAffects;
    }

    @Override
    public String getMessage() {
        String cells = cellsThatThisRangeAffects.stream()
                .map(CellLocation::getCellId)
                .collect(Collectors.joining(", "));
        return "Range: '" + rangeName + "' can't be deleted because it is being used by cells: " + cells;
    }
}
