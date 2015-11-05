/**
 * H2GIS is a library that brings spatial support to the H2 Database Engine
 * <http://www.h2database.com>.
 *
 * H2GIS is distributed under GPL 3 license. It is produced by CNRS
 * <http://www.cnrs.fr/>.
 *
 * H2GIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * H2GIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * H2GIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.h2gis.org/>
 * or contact directly: info_at_h2gis.org
 */
package org.h2gis.h2spatial.internal.function.spatial.properties;

import com.vividsolutions.jts.geom.Geometry;
import org.h2gis.h2spatialapi.DeterministicScalarFunction;

/**
 * Get the first Y coordinate
 * @author Nicolas Fortin
 */
public class ST_Y extends DeterministicScalarFunction {

    /**
     * Default constructor
     */
    public ST_Y() {
        addProperty(PROP_REMARKS, "Get the first Y coordinate.");
    }

    @Override
    public String getJavaStaticMethod() {
        return "getY";
    }

    /**
     * @param geometry Geometry instance
     * @return A y coordinate or null if null or empty geometry.
     */
    public static Double getY(Geometry geometry) {
        if(geometry==null) {
            return null;
        }
        return geometry.getCoordinate().y;
    }
}
