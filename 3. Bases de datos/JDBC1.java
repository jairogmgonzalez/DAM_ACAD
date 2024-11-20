
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC1 {

    // Conexión activa con la base de datos MySQL para realizar operaciones SQL.
    Connection conexion;

    // Tabla sobre la que se realizará las operaciones
    String tabla;

    // Constructor para conectar con la base de datos
    public JDBC1(String url, String usuario, String contraseña, String tabla) {
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
        String sql = "SELECT " + nombreColumna + " FROM " + tabla + " WHERE id = " + numRegistro;

        try {
            // Se crea un statement para ejecutar la consulta
            Statement s = conexion.createStatement();

            // Se ejecuta la consulta y obtiene el resultado
            ResultSet rs = s.executeQuery(sql);

            // Se obtiene el resultado y se almacena en la variable resultado
            if (rs.next()) {
                resultado = rs.getString(nombreColumna);
            }

            // Se cierra el ResultSet y el Statement
            rs.close();
            s.close();

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

        try {
            // Se crea un statement para ejecutar la consulta
            Statement s = conexion.createStatement();

            // Se ejecuta la consulta y obtiene el resultado
            ResultSet rs = s.executeQuery(sql);

            // Se obtiene todos los resultados y se almacenan en la lista
            while (rs.next()) {
                resultados.add(rs.getString(nombreColumna));
            }

            // Se cierra el ResultSet y el Statement
            rs.close();
            s.close();

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
        String sql = "SELECT * FROM " + tabla + " WHERE id = " + numRegistro;

        try {
            // Se crea un statement para ejecutar la consulta
            Statement s = conexion.createStatement();

            // Se ejecuta la consulta y obtiene el resultado
            ResultSet rs = s.executeQuery(sql);

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
        String sql = "SELECT * FROM " + tabla + " WHERE id = " + numRegistro;

        try {
            // Se crea un statement para ejecutar la consulta
            Statement s = conexion.createStatement();

            // Se ejecuta la consulta y obtiene el resultado
            ResultSet rs = s.executeQuery(sql);

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
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        return resultados; // Se devuelve la lista de resultados del query
    }

    // Método para actualizar un registro de la tabla en la base de datos
    public void update(int row, Map<String, String> datos) {
        // Sentencia SQL para actualizar un registro de la tabla
        StringBuilder sql = new StringBuilder("UPDATE " + tabla + " SET ");

        // Se recorre el mapa de datos para completar la sentencia SQL
        for (Map.Entry<String, String> entry : datos.entrySet()) {
            sql.append(entry.getKey())
                    .append(" = '")
                    .append(entry.getValue())
                    .append("',");
        }

        // Se elimina la última coma
        sql.setLength(sql.length() - 1);

        // Se agrega el WHERE para filtrar la fila al actualizar
        sql.append(" WHERE id = ").append(row);

        try {
            // Se crea un statement para ejecutar la consulta
            Statement s = conexion.createStatement();

            // Se ejecuta la consulta
            int filasActualizadas = s.executeUpdate(sql.toString());

            System.out.println("Filas actualizadas: " + filasActualizadas);

            // Se cierra el Statement
            s.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método para actualizar un campo específico de un registro de la tabla en la base de datos
    public void update(int row, String nombreColumna, String valor) {
        // Sentencia SQL para actualizar un campo específico de un registro de la tabla
        String sql = "UPDATE " + tabla + " SET " + nombreColumna + " = '" + valor + "' WHERE id = " + row;

        try {
            // Se crea un statement para ejecutar la consulta
            Statement s = conexion.createStatement();

            // Se ejecuta la consulta
            s.executeUpdate(sql);

            System.out.println("Campo actualizado");

            // Se cierra el statement
            s.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método para eliminar un registro de la tabla en la base de datos
    public void delete(int row) {
        // Sentencia SQL para eliminar un registro de la tabla
        String sql = "DELETE FROM " + tabla + " WHERE id = " + row;

        try {
            // Se crea un statement para ejecutar la consulta
            Statement s = conexion.createStatement();

            // Se ejecuta la consulta
            s.executeUpdate(sql);

            System.out.println("Fila eliminada");

            // Se cierra el statement
            s.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
