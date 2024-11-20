
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC2 {

    // Conexión activa con la base de datos MySQL para realizar operaciones SQL.
    Connection conexion;

    // Tabla sobre la que se realizará las operaciones
    String tabla;

    // Constructor para conectar con la base de datos
    public JDBC2(String url, String usuario, String contraseña, String tabla) {
        try {
            // Se carga el driver de MySQL
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver cargado exitosamente.");

            // Se establece la conexión con la base de datos
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión establecida con la base de datos.");

            // Se almacena el nombre de la tabla
            this.tabla = tabla;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método para obtener un campo específico de una tabla de la base de datos
    public String selectCampo(int numRegistro, String nombreColumna) {
        // Variable para almacenar el resultado del query
        String resultado = "";

        // Sentencia SQL para seleccionar el campo específico de la tabla
        String sql = "SELECT " + nombreColumna + " FROM " + tabla + " WHERE id = ?";

        // Se crea un PreparedStatement para ejecutar la consulta
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Se establece el valor para el parámetro de la consulta
            ps.setInt(1, numRegistro);

            // Se ejecuta la consulta y obtiene el resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Se obtiene el resultado y se almacena en la variable resultado
                if (rs.next()) {
                    resultado = rs.getString(nombreColumna);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultado; // Se devuelve el resultado del query
    }

    // Método para obtener una lista con todos los valores de un campo específico
    public List<String> selectColumna(String nombreColumna) {
        // Lista para almacenar los resultados del query
        List<String> resultados = new ArrayList<>();

        // Sentencia SQL para seleccionar todos los valores del campo específico de la tabla
        String sql = "SELECT " + nombreColumna + " FROM " + tabla;

        // Se crea un PreparedStatement para ejecutar la consulta
        try (PreparedStatement ps = conexion.prepareStatement(sql);) {

            // Se ejecuta la consulta y obtiene el resultado
            try (ResultSet rs = ps.executeQuery(sql)) {
                // Se obtiene todos los resultados y se almacenan en la lista
                while (rs.next()) {
                    resultados.add(rs.getString(nombreColumna));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultados; // Se devuelve la lista de resultados del query
    }

    // Método para obtener los datos de una registro específico	
    public List<String> selectRowList(int numRegistro) {
        // Lista para almacenar los resultados del query
        List<String> resultados = new ArrayList<>();

        // Sentencia SQL para seleccionar todos los valores de la tabla
        String sql = "SELECT * FROM " + tabla + " WHERE id = ?";

        // Se crea un PreparedStatement para ejecutar la consulta
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Se establece el valor para el parámetro de la consulta
            ps.setInt(1, numRegistro);

            // Se ejecuta la consulta y obtiene el resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Se obtiene todos los resultados y se almacenan en la lista
                if (rs.next()) {
                    // Se obtiene información sobre las columnas del ResultSet
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount(); // Número de columnas en el resultado

                    // Se recorre todas las columnas y se agregan sus valores a la lista
                    for (int i = 1; i <= columnCount; i++) {
                        resultados.add(rs.getString(i));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        return resultados; // Se devuelve la lista de resultados del query
    }

    // Método para obtener los datos de una registro específico	en un mapa
    public Map<String, String> selectRowMap(int numRegistro) {
        // Mapa para almacenar los resultados del query
        Map<String, String> resultados = new HashMap<>();

        // Sentencia SQL para seleccionar todos los valores de la tabla
        String sql = "SELECT * FROM " + tabla + " WHERE id = ?";

        // Se crea un PreparedStatement para ejecutar la consulta
        try (PreparedStatement ps = conexion.prepareStatement(sql);) {

            // Se establece el valor para el parámetro de la consulta
            ps.setInt(1, numRegistro);

            // Se ejecuta la consulta y obtiene el resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Se obtiene todos los resultados y se almacenan en la lista
                if (rs.next()) {
                    // Se obtiene información sobre las columnas del ResultSet
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount(); // Número de columnas en el resultado

                    // Se recorre todas las columnas y se agregan sus valores a la lista
                    for (int i = 1; i <= columnCount; i++) {
                        // Se obtiene el nombre de la columna para añadirla al mapa como clave
                        String nombreColumna = metaData.getColumnName(i);

                        resultados.put(nombreColumna, rs.getString(i));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        return resultados; // Se devuelve la lista de resultados del query
    }

    // Método para actualizar un registro de la tabla en la base de datos
    public void update(int row, Map<String, String> datos) {
        // Construcción de la sentencia SQL para actualizar un registro de la tabla
        StringBuilder sql = new StringBuilder("UPDATE " + tabla + " SET ");

        // Se recorre el mapa de datos para completar la sentencia SQL
        for (Map.Entry<String, String> entry : datos.entrySet()) {
            sql.append(entry.getKey()).append(" = ?,");
        }

        // Se elimina la última coma
        sql.setLength(sql.length() - 1);

        // Se agrega el WHERE para filtrar la fila al actualizar
        sql.append(" WHERE id = ?");

        try {
            // Se establece el autocommit a false para evitar que se guarde la transacción automáticamente
            conexion.setAutoCommit(false);

            // Se crea un PreparedStatement para ejecutar la consulta
            try (PreparedStatement ps = conexion.prepareStatement(sql.toString())) {

                // Contador para establecer los valores de los parámetros
                int index = 1;

                // Se reemplaza los valores de los parámetros con los valores de los datos
                for (Map.Entry<String, String> entry : datos.entrySet()) {
                    ps.setString(index++, entry.getValue());
                }

                // Se reemplaza el valor del parámetro para el WHERE
                ps.setInt(index, row);

                // Se ejecuta la consulta
                int filasActualizadas = ps.executeUpdate();

                // Se confirma la transacción
                conexion.commit();

                System.out.println("Filas actualizadas: " + filasActualizadas);

            }
        } catch (SQLException e) {
            try {
                // Se devuelve la transacción al estado anterior
                conexion.rollback();

                System.out.println("Transacción revertida por un error");
            } catch (SQLException rollBackEx) {
                System.err.println("Error al deshacer la transacción: " + rollBackEx.getMessage());
            }
            System.out.println("Error al actualizar el registro: " + e.getMessage());
        } finally {
            try {
                // Se restaura el autocommit al estado anterior
                conexion.setAutoCommit(true);
            } catch (SQLException setAutoCommitEx) {
                System.err.println("Error al restaurar el autocommit: " + setAutoCommitEx.getMessage());
            }
        }
    }

    // Método para actualizar un campo específico de un registro de la tabla en la base de datos
    public void update(int row, String nombreColumna, String valor) {
        // Sentencia SQL para actualizar un campo específico de un registro de la tabla
        String sql = "UPDATE " + tabla + " SET " + nombreColumna + " = ? WHERE id = ?";

        try {
            // Se establece el autocommit a false para evitar que se guarde la transacción automáticamente
            conexion.setAutoCommit(false);

            // Se crea un PreparedStatement para ejecutar la consulta
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {

                // Se establece los valores para los parámetros de la consulta
                ps.setString(1, valor);
                ps.setInt(2, row);

                // Se ejecuta la consulta
                ps.executeUpdate();

                // Se confirma la transacción
                conexion.commit();

                System.out.println("Campo actualizado");

            }
        } catch (SQLException e) {
            try {
                // Se devuelve la transacción al estado anterior
                conexion.rollback();

                System.out.println("Transacción revertida por un error");
            } catch (SQLException rollBackEx) {
                System.err.println("Error al deshacer la transacción: " + rollBackEx.getMessage());
            }

            System.out.println("Error al actualizar el campo: " + e.getMessage());
        } finally {
            try {
                // Se establece el autocommit a true para volver a guardar la transacción automáticamente
                conexion.setAutoCommit(true);
            } catch (SQLException setAutoCommitEx) {
                System.err.println("Error al restaurar el autocommit: " + setAutoCommitEx.getMessage());
            }
        }
    }

    // Método para eliminar un registro de la tabla en la base de datos
    public void delete(int row) {
        // Sentencia SQL para eliminar un registro de la tabla
        String sql = "DELETE FROM " + tabla + " WHERE id = ?";

        try {
            // Se establece el autocommit a false para evitar que se guarde la transacción automáticamente
            conexion.setAutoCommit(false);

            // Se crea un PreparedStatement para ejecutar la consulta
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                // Se establece el valor para el parámetro de la consulta
                ps.setInt(1, row);

                // Se ejecuta la consulta
                int filasEliminadas = ps.executeUpdate();

                // Se confirma la transacción
                conexion.commit();

                System.out.println("Filas eliminadas: " + filasEliminadas);
            }

        } catch (SQLException e) {
            try {
                // Se devuelve la transacción al estado anterior
                conexion.rollback();

                System.out.println("Transacción revertida por un error");

            } catch (SQLException rollBackEx) {
                System.err.println("Error al deshacer la transacción: " + rollBackEx.getMessage());
            }

            System.out.println("Error al eliminar la fila: " + e.getMessage());
        } finally {
            try {
                // Se restablece el autocommit a true para volver a guardar las transacciones automáticamente
                conexion.setAutoCommit(true);
            } catch (SQLException setAutoCommitEx) {
                System.err.println("Error al restaurar el autocommit: " + setAutoCommitEx.getMessage());
            }
        }
    }

}
