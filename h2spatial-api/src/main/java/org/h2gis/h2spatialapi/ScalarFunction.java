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
package org.h2gis.h2spatialapi;

/**
 * Scalar function interface.
 * Scalar function in H2 can be defined through CREATE ALIAS, but in an OSGi context the class java name is not sufficient.
 * The full declaration of java name in H2 through osgi is BundleSymbolicName:BundleVersion:BinaryJavaName
 * Registering this interface as an OSGi service will add this function in h2spatial linked with a DataSource service.
 * @author Nicolas Fortin
 */
public interface ScalarFunction extends Function {
    /** Boolean, Deterministic functions must always return the same value for the same parameters.
     *  The result of such functions is cached if possible. */
    static final String PROP_DETERMINISTIC = "deterministic";
    /** Boolean, if nobuffer is true then this function will be called more often but will not cache the results in
     *  memory nor files */
    static final String PROP_NOBUFFER = "nobuffer";

    /**
     * Returns Java name of static methods in this class to expose in database,
     * theses methods are under the same alias but with different number of arguments.
     * @return The Java name of static methods or null if it has not be loaded
     */
    String getJavaStaticMethod();
}
