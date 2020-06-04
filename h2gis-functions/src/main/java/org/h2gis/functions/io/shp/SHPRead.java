/**
 * H2GIS is a library that brings spatial support to the H2 Database Engine
 * <http://www.h2database.com>. H2GIS is developed by CNRS
 * <http://www.cnrs.fr/>.
 *
 * This code is part of the H2GIS project. H2GIS is free software; 
 * you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * version 3.0 of the License.
 *
 * H2GIS is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details <http://www.gnu.org/licenses/>.
 *
 *
 * For more information, please consult: <http://www.h2gis.org/>
 * or contact directly: info_at_h2gis.org
 */

package org.h2gis.functions.io.shp;

import org.h2gis.api.AbstractFunction;
import org.h2gis.api.EmptyProgressVisitor;
import org.h2gis.api.ScalarFunction;
import org.h2gis.functions.io.utility.FileUtil;
import org.h2gis.utilities.TableLocation;
import org.h2gis.utilities.URIUtilities;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQL Function to copy Shape File data into a Table.
 * @author Nicolas Fortin
 * @author Erwan Bocher, CNRS
 */
public class SHPRead  extends AbstractFunction implements ScalarFunction {
    public SHPRead() {
        addProperty(PROP_REMARKS, "Read a shape file and copy the content in the specified table.");
    }

    @Override
    public String getJavaStaticMethod() {
        return "readShape";
    }

    /**
     * Copy data from Shape File into a new table in specified connection.
     * @param connection Active connection
     * @param tableReference [[catalog.]schema.]table reference
     * @param forceEncoding Use this encoding instead of DBF file header encoding property.
     * @param fileName File path of the SHP file or URI
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static void readShape(Connection connection, String fileName, String tableReference,String forceEncoding) throws IOException, SQLException {
        readShape(connection, fileName, tableReference, forceEncoding, false);
    }

    /**
     * Copy data from Shape File into a new table in specified connection.
     * @param connection Active connection
     * @param tableReference [[catalog.]schema.]table reference
     * @param forceEncoding Use this encoding instead of DBF file header encoding property.
     * @param fileName File path of the SHP file or URI
     * @param deleteTables delete existing tables
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static void readShape(Connection connection, String fileName, String tableReference,String forceEncoding, boolean deleteTables) throws IOException, SQLException {
        File file = URIUtilities.fileFromString(fileName);
        SHPDriverFunction shpDriverFunction = new SHPDriverFunction();
        shpDriverFunction.importFile(connection, TableLocation.parse(tableReference, true).toString(true),
                file,  forceEncoding,deleteTables, new EmptyProgressVisitor());
    }

    /**
     * Copy data from Shape File into a new table in specified connection.
     * @param connection Active connection
     * @param tableReference [[catalog.]schema.]table reference
     * @param fileName File path of the SHP file or URI
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static void readShape(Connection connection, String fileName, String tableReference) throws IOException, SQLException {
        readShape(connection, fileName, tableReference, null, false);
    }

    public static void readShape(Connection connection, String fileName, String tableReference, boolean deleteTable) throws IOException, SQLException {
        readShape(connection, fileName, tableReference, null, deleteTable);
    }

    public static void readShape(Connection connection, String fileName, boolean deleteTable) throws IOException, SQLException {
        final String name = URIUtilities.fileFromString(fileName).getName();
        String tableName = name.substring(0, name.lastIndexOf(".")).toUpperCase();
        if (tableName.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
            readShape(connection, fileName, tableName, null, deleteTable);
        } else {
            throw new SQLException("The file name contains unsupported characters");
        }
    }

    /**
     * Copy data from Shape File into a new table in specified connection.
     * The newly created table is given the same name as the filename
     * without the ".shp" extension. If such a table already exists, an
     * exception is thrown.
     *
     * @param connection Active connection
     * @param fileName   File path of the SHP file or URI
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static void readShape(Connection connection, String fileName) throws IOException, SQLException {
        final String name = URIUtilities.fileFromString(fileName).getName();
        String tableName = name.substring(0, name.lastIndexOf(".")).toUpperCase();
        if (tableName.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
            readShape(connection, fileName, tableName, null, false);
        } else {
            throw new SQLException("The file name contains unsupported characters");
        }
    }
}
