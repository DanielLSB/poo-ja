/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.PreparedStatement;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mutsu
 */
public class Cliente extends Persona {
private int id;
    private String nit;

Conexion cn;

      public Cliente(){}
      public Cliente(int id,String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.id=id;
        this.nit = nit;
        
    }
  public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

  
    
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
         try{
             String query;
             cn = new Conexion();
             cn.abrir_conexion();
             query = "SELECT id_cliente as id,nit,nombres,apellidos,direccion,telefono,fecha_nacimiento FROM clientes;";
             ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
             String encabezado[] = {"id","Nit","Nombres","Apellidos","Direccion","Telefono","Nacimiento"};
             tabla.setColumnIdentifiers(encabezado);
             
 String datos[] = new String[7]; 
             while(consulta.next()){
                 datos[0] = consulta.getString("id");
                 datos[1] = consulta.getString("nit");
                 datos[2] = consulta.getString("nombres");
                 datos[3] = consulta.getString("apellidos");
                 datos[4] = consulta.getString("direccion");
                 datos[5] = consulta.getString("telefono");
                 datos[6] = consulta.getString("fecha_nacimiento");
           
 tabla.addRow(datos);
                 
             }
             cn.cerrar_conexion();
         }catch(SQLException ex){
    System.out.println("Error:" + ex.getMessage());
    }
        return tabla;
    
    }
    
@Override
public void agregar(){
    
try{
    PreparedStatement parametro;
    String query;
cn=new Conexion();
cn.abrir_conexion();
query="INSERT INTO clientes(nit,nombres,apellidos,direccion,telefono,fecha_nacimiento)VALUES(?,?,?,?,?,?);";
parametro=(PreparedStatement) cn.conexionBD.prepareStatement(query);
parametro.setString(1, getNit());
parametro.setString(2, getNombres());
parametro.setString(3, getApellidos());
parametro.setString(4, getDireccion());
parametro.setString(5, getTelefono());
parametro.setString(6, getFecha_nacimiento());
int exec= parametro.executeUpdate();

cn.cerrar_conexion();
JOptionPane.showMessageDialog(null,Integer.toString(exec) + " Registros Ingresados", "Mensaje",JOptionPane.INFORMATION_MESSAGE);


}catch(HeadlessException|SQLException ex){
System.out.println("Error" + ex.getMessage());
        }
}

@Override
public void actualizar(){
try{
    PreparedStatement parametro;
    String query;
cn=new Conexion();
cn.abrir_conexion();
query="UPDATE clientes SET nit = ?,nombres = ?,apellidos =?,direccion = ?,telefono = ?,fecha_nacimiento = ?WHERE id_cliente = ?;";
parametro=(PreparedStatement) cn.conexionBD.prepareStatement(query);
parametro.setString(1, getNit());
parametro.setString(2, getNombres());
parametro.setString(3, getApellidos());
parametro.setString(4, getDireccion());
parametro.setString(5, getTelefono());
parametro.setString(6, getFecha_nacimiento());
parametro.setInt(7, getId());
int exec= parametro.executeUpdate();

cn.cerrar_conexion();
JOptionPane.showMessageDialog(null,Integer.toString(exec) + " Registro actualizado", "Mensaje",JOptionPane.INFORMATION_MESSAGE);


}catch(HeadlessException|SQLException ex){
System.out.println("Error" + ex.getMessage());
        }
} 

@Override
public void eliminar(){

 try{
        PreparedStatement parametro;
        String query;
            cn = new Conexion();
            cn.abrir_conexion();
            query = "delete from clientes WHERE id_cliente = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            int exec = parametro.executeUpdate();
            cn.cerrar_conexion();
             
JOptionPane.showMessageDialog(null,Integer.toString(exec) + " Registros Eliminado", "Mensaje",JOptionPane.INFORMATION_MESSAGE);
             
            
    }catch(HeadlessException | SQLException ex){
    System.out.println("Error" + ex.getMessage());
    }  

}

}