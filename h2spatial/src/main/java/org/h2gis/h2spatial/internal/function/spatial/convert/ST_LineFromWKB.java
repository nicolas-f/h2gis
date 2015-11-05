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
package org.h2gis.h2spatial.internal.function.spatial.convert;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import org.h2gis.h2spatialapi.DeterministicScalarFunction;
import org.h2gis.utilities.GeometryTypeCodes;
import org.h2gis.utilities.jts_utils.GeometryMetaData;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Convert Well Known Binary into a LINESTRING.
 * @author Nicolas Fortin
 */
public class ST_LineFromWKB extends DeterministicScalarFunction {

    /**
     * Default constructor
     */
    public ST_LineFromWKB() {
        addProperty(PROP_REMARKS, "Convert Well Known Binary into a LINESTRING.\n If an SRID is not specified, it defaults to 0.");
    }

    @Override
    public String getJavaStaticMethod() {
        return "toLineString";
    }
    
    /**
     * Convert WKT into a LinearRing
     * @param bytes Byte array
     * @return LineString instance of null if bytes null
     * @throws SQLException WKB Parse error
     * @throws java.io.IOException
     */
    public static Geometry toLineString(byte[] bytes) throws SQLException, IOException {
        return toLineString(bytes, 0);
    }
    

    /**
     * Convert WKT into a LinearRing
     * @param bytes Byte array
     * @param srid SRID
     * @return LineString instance of null if bytes null
     * @throws SQLException WKB Parse error
     * @throws java.io.IOException
     */
    public static Geometry toLineString(byte[] bytes, int srid) throws SQLException, IOException {
        if(bytes==null) {
            return null;
        }
        WKBReader wkbReader = new WKBReader();
        try {
            if(GeometryMetaData.getMetaDataFromWKB(bytes).geometryType != GeometryTypeCodes.LINESTRING) {
                throw new SQLException("Provided WKB is not a LINESTRING.");
            }
            Geometry geometry = wkbReader.read(bytes);
            geometry.setSRID(srid);
            return geometry;
        } catch (ParseException ex) {
            throw new SQLException("ParseException while evaluating ST_LineFromWKB",ex);
        }
    }
}
