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
package org.h2gis.drivers.dbf;

import org.h2gis.h2spatialapi.AbstractFunction;
import org.h2gis.h2spatialapi.EmptyProgressVisitor;
import org.h2gis.h2spatialapi.ScalarFunction;
import org.h2gis.utilities.URIUtility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Nicolas Fortin
 */
public class DBFRead  extends AbstractFunction implements ScalarFunction {
    public DBFRead() {
        addProperty(PROP_REMARKS, "Read a DBase III file and copy the content into a new table in the database");
    }

    @Override
    public String getJavaStaticMethod() {
        return "read";
    }

    public static void read(Connection connection, String fileName, String tableReference) throws IOException, SQLException {
        DBFDriverFunction dbfDriverFunction = new DBFDriverFunction();
        dbfDriverFunction.importFile(connection, tableReference, URIUtility.fileFromString(fileName), new EmptyProgressVisitor());
    }

    public static void read(Connection connection, String fileName, String tableReference, String fileEncoding) throws IOException, SQLException {
        DBFDriverFunction dbfDriverFunction = new DBFDriverFunction();
        dbfDriverFunction.importFile(connection, tableReference, URIUtility.fileFromString(fileName), new EmptyProgressVisitor(), fileEncoding);
    }
}
