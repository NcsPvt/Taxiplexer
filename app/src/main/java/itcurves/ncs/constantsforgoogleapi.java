package itcurves.ncs;

import java.util.HashMap;
import java.util.Map;

public final class constantsforgoogleapi {

	public static final Map<String, Integer> myMap2 = new HashMap<String, Integer>() {
		{
			put("OK", 1);
			put("NOT_FOUND", 2);
			put("ZERO_RESULTS", 3);
			put("MAX_WAYPOINTS_EXCEEDED", 4);
			put("INVALID_REQUEST", 5);
			put("OVER_QUERY_LIMIT", 6);
			put("REQUEST_DENIED", 7);
			put("UNKNOWN_ERROR", 8);

		}
	};
}
