package CoreParts.smallParts;

import java.util.HashMap;
import java.util.Map;

public class CellLocationFactory {
    private static Map<String,CellLocation> cachedCoordinates = new HashMap<>();

    public static CellLocation fromCellId(String key) {

        if (cachedCoordinates.containsKey(key)) {
            return cachedCoordinates.get(key);
        }


        CellLocation coordinate = new CellLocation(key.charAt(0), key.charAt(0));
        cachedCoordinates.put(key, coordinate);

        return coordinate;
    }
}
