package org.h2gis.network.graph_creator;

/**
 * A helper class for parsing String arguments to h2network graph functions.
 *
 * @author Adam Gouge
 */
public class GraphFunctionParser {

    private String weightColumn;
    private Orientation globalOrientation;
    private String edgeOrientation;

    public static final String SEPARATOR = "-";
    public static final String DIRECTED = "directed";
    public static final String REVERSED = "reversed";
    public static final String UNDIRECTED = "undirected";

    public enum Orientation {
        DIRECTED, REVERSED, UNDIRECTED
    }

    public static final String EDGE_ORIENTATION_COLUMN = "edge_orientation_column";
    public static final String POSSIBLE_ORIENTATIONS =
            "'" + DIRECTED + " - " + EDGE_ORIENTATION_COLUMN + "' "
                    + "| '" + REVERSED + " - " + EDGE_ORIENTATION_COLUMN + "' "
                    + "| '" + UNDIRECTED + "'";
    public static final String ORIENTATION_ERROR =
            "Bad orientation format. Enter " + POSSIBLE_ORIENTATIONS + ".";

    /**
     * Recovers the weight column name from a string.
     *
     * @param v String
     *
     * @return the weight column name
     */
    protected String parseWeight(String v) {
        if (v == null) {
            return null;
        }
        return v.trim();
    }

    /**
     * Recovers the global orientation from a string.
     *
     *
     * @param v String
     * @return The global orientation
     */
    protected static Orientation parseGlobalOrientation(String v) {
        if (v == null) {
            return null;
        }
        if (isDirectedString(v)) {
            return Orientation.DIRECTED;
        } else if (isReversedString(v)) {
            return Orientation.REVERSED;
        } else if (isUndirectedString(v)) {
            return Orientation.UNDIRECTED;
        } else {
            throw new IllegalArgumentException(ORIENTATION_ERROR);
        }
    }

    private static boolean isDirectedString(String s) {
        if (s == null) {
            return false;
        }
        if (s.toLowerCase().contains(DIRECTED)
                && !isUndirectedString(s)) {
            return true;
        }
        return false;
    }

    private static boolean isReversedString(String s) {
        if (s == null) {
            return false;
        }
        if (s.toLowerCase().contains(REVERSED)) {
            return true;
        }
        return false;
    }

    private static boolean isUndirectedString(String s) {
        if (s == null) {
            return false;
        }
        if (s.toLowerCase().contains(UNDIRECTED)) {
            return true;
        }
        return false;
    }

    private boolean isOrientationString(String s) {
       return isDirectedString(s) || isReversedString(s) || isUndirectedString(s);
    }

    private boolean isWeightString(String s) {
        if (s == null) {
            return false;
        }
        return !isOrientationString(s);
    }

    /**
     * Recovers the edge orientation from a string.
     *
     * @param v String
     * @return The edge orientation
     */
    protected String parseEdgeOrientation(String v) {
        if (v == null) {
            return null;
        }
        if (!v.contains(SEPARATOR)) {
            throw new IllegalArgumentException(ORIENTATION_ERROR);
        }
        // Extract the global and edge orientations.
        String[] s = v.split(SEPARATOR);
        if (s.length == 2) {
            // Return just the edge orientation column name and not the
            // global orientation.
            return s[1].trim();
        } else {
            throw new IllegalArgumentException(ORIENTATION_ERROR);
        }
    }

    /**
     * Parse the weight and orientation(s) from two strings, given in arbitrary
     * order.
     *
     * @param arg1 Weight or orientation
     * @param arg2 Weight or orientation
     */
    public void parseWeightAndOrientation(String arg1, String arg2) {
        if ((arg1 == null && arg2 == null)
                || (isWeightString(arg1) && arg2 == null)
                || (arg1 == null && isWeightString(arg2))) {
            // Disable default orientations (D and WD).
            throw new IllegalArgumentException("You must specify the orientation.");
        }
        if (isWeightString(arg1) && isWeightString(arg2)) {
            throw new IllegalArgumentException("Cannot specify the weight column twice.");
        }
        if (isOrientationString(arg1) && isOrientationString(arg2)) {
            throw new IllegalArgumentException("Cannot specify the orientation twice.");
        }
        if (isWeightString(arg1) || isOrientationString(arg2)) {
            setWeightAndOrientation(arg1, arg2);
        }
        if (isOrientationString(arg1) || isWeightString(arg2)) {
            setWeightAndOrientation(arg2, arg1);
        }
    }

    private void setWeightAndOrientation(String weight, String orient) {
        weightColumn = parseWeight(weight);
        globalOrientation = parseGlobalOrientation(orient);
        if (globalOrientation != null) {
            if (!globalOrientation.equals(Orientation.UNDIRECTED)) {
                edgeOrientation = parseEdgeOrientation(orient);
            }
        }
    }

    /**
     * Get the weight column name.
     *
     * @return weight column name
     */
    public String getWeightColumn() {
        return weightColumn;
    }

    /**
     * Get the global orientation string.
     *
     * @return global orientation string
     */
    public Orientation getGlobalOrientation() {
        return globalOrientation;
    }

    /**
     * Get the edge orientation column name.
     *
     * @return edge orientation column name
     */
    public String getEdgeOrientation() {
        return edgeOrientation;
    }

    /**
     * Returns an array of destination ids from a comma-separated list of
     * destinations.
     *
     * @param s Comma-separated list of destinations
     *
     * @return An array of destination ids
     */
    public static int[] parseDestinationsString(String s) {
        String[] array = s.split(",");
        int[] destinations = new int[array.length];
        for (int i = 0; i < destinations.length; i++) {
            final String stringWithNoWhiteSpaces = array[i].replaceAll("\\s", "");
            if (stringWithNoWhiteSpaces.isEmpty()) {
                throw new IllegalArgumentException("Empty destination. Too many commas?");
            }
            destinations[i] = Integer.valueOf(stringWithNoWhiteSpaces);
        }
        return destinations;
    }
}