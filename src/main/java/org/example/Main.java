package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection con = null;

        try {
            // Cargar el driver de SQLITE
            Class.forName("org.sqlite.JDBC");

            // Cadena de conexión para conectar con chinook.db
            String connectionUrl = "jdbc:sqlite:C:\\sqlite\\db\\chinook.db" ;

            // Obtener la conexión
            con = DriverManager.getConnection(connectionUrl);

            // La clase Statement contiene los métodos executeQuery y
            //executeUpdate para realizar consultas y actualizaciones
            Statement stmt = con.createStatement();

            //El método executeQuery devuelve un objeto ResultSet para poder
            // recorrer el resultado de la consulta utilizando un cursor.
            // Esta consulta obtiene todos los datos, todos los campos, )debido
            // al *), almacenados en la tabla medicamentos.
            ResultSet rs = stmt.executeQuery("SELECT * from Customer;");

            // Mientras queden datos
            while (rs.next()) {
                // Imprimir en la consola
                int customerID = rs.getInt("CustomerID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String company = rs.getString("Company");
                String country = rs.getString("country");
                System.out.println(customerID + "---" + firstName + "--" +lastName+ "--" +company+ "--" +country);
            }
            rs.close();

            // Sentencias con parametros
            PreparedStatement psmt = con.prepareStatement("select * from Invoice WHERE CustomerID = ? ;");

            // Cargamos el parametro
            psmt.setInt(1, 40);

            System.out.println();
            System.out.println("-------------------------------------------------------");
            System.out.println();
            //Ejecutamos la consulta
            rs = psmt.executeQuery();
            while (rs.next()){
                int InvoiceID = rs.getInt("InvoiceID");
                int costomerId = rs.getInt("CustomerID");
                Date invoiceDate = rs.getDate("InvoiceDate");
                String billingAddress = rs.getString("BillingAddress");
                double total = rs.getDouble("Total");
                System.out.println(InvoiceID + "\t" + costomerId + "\t" +invoiceDate+ "\t" +billingAddress+ "\t" +total);

            }

            System.out.println("\nENTENCIAS CUD");
            int n = 0;
            n = stmt.executeUpdate("INSERT INTO Artist (ArtistId, Name) VALUES (280, 'Santiago Auserón');");
            n = stmt.executeUpdate("INSERT INTO Album (AlbumId, Title, ArtistId) VALUES (350, 'Veneno en la piel', 280);");
            n = stmt.executeUpdate("INSERT INTO Artist (ArtistId, Name) VALUES (281, 'Manolo Escobar');");

            //Actualizamos un registro nuevo
            n = stmt.executeUpdate("UPDATE Artist SET Name='Juan Perro' WHERE ArtistId=280;");

            //Borramos los datos
            n = stmt.executeUpdate("DELETE FROM Artist WHERE ArtistId=281;");
            n = stmt.executeUpdate("DELETE FROM Artist WHERE ArtistId=280;");
            n = stmt.executeUpdate("DELETE FROM Album WHERE ArtistId=280;");

            System.out.println(n);
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println("Excepción: "+ e.toString());
        }
    }


}