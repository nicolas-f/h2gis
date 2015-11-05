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
package org.h2gis.h2spatialext.function.spatial.processing;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;
import org.h2gis.h2spatialapi.DeterministicScalarFunction;

/**
 *
 * @author Erwan Bocher
 */
public class ST_SimplifyPreserveTopology extends DeterministicScalarFunction {

    public ST_SimplifyPreserveTopology() {
        addProperty(PROP_REMARKS, "Simplifies a geometry and ensures that the result is a valid geometry.");
    }

    @Override
    public String getJavaStaticMethod() {
        return "simplyPreserve";
    }

    /**
     * Simplifies a geometry and ensures that the result is a valid geometry
     * having the same dimension and number of components as the input, and with
     * the components having the same topological relationship.
     *
     * @param geometry
     * @param distance
     * @return
     */
    public static Geometry simplyPreserve(Geometry geometry, double distance) {
        if(geometry == null){
            return null;
        }
        return TopologyPreservingSimplifier.simplify(geometry, distance);
    }
}
