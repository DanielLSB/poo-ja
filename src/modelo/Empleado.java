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
import javax.swing.DefaultComboBoxModel;

import javax.swing.JComboBox;

/**
 *
 * @author mutsu
 */
public class Empleado extends Persona{
    private int id;
    private String codigo,puesto;
 

   Conexion cn;
    
    public Empleado(){}


    
    public Empleado(int id,String codigo, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento,String puesto) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this. id=id;
        this.codigo = codigo;
        this.puesto = puesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
         try{
             String query;
             cn = new Conexion();
             cn.abrir_conexion();
             query = "select e.id_empleado,e.codigo,e.nombres,e.apellidos,e.direccion,e.telefono,e.fecha_nacimiento,p.puesto from empleados as e inner join puestos as p on e.id_puesto = p.id_puesto;";
             ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
             String encabezado[] = {"id","codigo","Nombres","Apellidos","Direccion","Telefono","Nacimiento","puesto"};
             tabla.setColumnIdentifiers(encabezado);
             
 String datos[] = new String[8]; 
             while(consulta.next()){
                 datos[0] = consulta.getString("e.id_empleado");
                 datos[1] = consulta.getString("e.codigo");
                 datos[2] = consulta.getString("e.nombres");
                 datos[3] = consulta.getString("e.apellidos");
                 datos[4] = consulta.getString("e.direccion");
                 datos[5] = consulta.getString("e.telefono");
                 datos[6] = consulta.getString("e.fecha_nacimiento");
                 datos[7] = consulta.getString("p.puesto");
           
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
            query="INSERT INTO empleados (codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento,id_puesto)VALUES(?,?,?,?,?,?,?);";
            parametro=(PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getPuesto());
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
query="UPDATE empleados SET codigo = ?,nombres = ?,apellidos =?,direccion = ?,telefono = ?,fecha_nacimiento = ?,id_puesto=? WHERE id_empleado = ?;";
parametro=(PreparedStatement) cn.conexionBD.prepareStatement(query);
parametro.setString(1, getCodigo());
parametro.setString(2, getNombres());
parametro.setString(3, getApellidos());
parametro.setString(4, getDireccion());
parametro.setString(5, getTelefono());
parametro.setString(6, getFecha_nacimiento());
parametro.setString(7, getPuesto());
parametro.setInt(8, getId());
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
            query = "delete from empleados WHERE id_empleado = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            int exec = parametro.executeUpdate();
            cn.cerrar_conexion();
             
JOptionPane.showMessageDialog(null,Integer.toString(exec) + " Registros Eliminado", "Mensaje",JOptionPane.INFORMATION_MESSAGE);
             
            
    }catch(HeadlessException | SQLException ex){
    System.out.println("Error" + ex.getMessage());
    }  

}

public DefaultComboBoxModel Obt_puesto()
{
    String idauxpu = null;
         DefaultComboBoxModel ListaModelo = new  DefaultComboBoxModel();
         try {
         String query;
         
             cn = new Conexion();
             cn.abrir_conexion();
         ListaModelo.addElement("seleccione un puesto");
         query="select id_puesto,puesto from puestos;";
         ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
    while(consulta.next()){
        /*
        ListaModelo.addElement(consulta.getString("puesto"));*/
          
                 ListaModelo.addElement(consulta.getString("puesto"));
                 
                 
     
    }
    
    
    
     cn.cerrar_conexion();
         }
         catch(SQLException ex){
         System.out.println("Error:" + ex.getMessage());}
       
         return ListaModelo;
    
    }

  


        }