/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquete.com.Bugtracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author kai
 */
public class ModeloBug {

    
    private static DataSource origenDatos;
    
    public ModeloBug(DataSource origen){
        this.origenDatos = origen;
    }
    
    public List<Bug> getBugs()throws Exception{
        List<Bug> bug = new ArrayList<>();
        //Class.forName("com.mysql.jdbc.Driver");                  
        Connection miConnection = null;
        Statement miStatement = null;
        ResultSet miResultset = null;
        try{
        miConnection = origenDatos.getConnection();

        String sentenciaSql="SELECT * FROM BUG";
        miStatement = miConnection.createStatement();

        miResultset = miStatement.executeQuery(sentenciaSql);
            
            while(miResultset.next()){
                int id = miResultset.getInt(1);
                String name = miResultset.getString(2);
                String type = miResultset.getString(3);
                String descripcion = miResultset.getString(4);
                String status = miResultset.getString(5);
                String user = miResultset.getString(6);
                Date fecha_inicial = miResultset.getDate(7);
                Date fecha_final = miResultset.getDate(8);
                
                Bug temBug = new Bug(id,name,type,descripcion,status,user,fecha_inicial,fecha_final);
                bug.add(temBug);
            }
        }finally{
          miStatement.close();
          miConnection.close();  
        }   
            
       
        return bug;
    }
/////cambie private por static en este metodo y en el origenDatos
   static void addNewBug(Bug newBug) {
       Connection miConnection = null;
       PreparedStatement miStatement = null;
       
        try {
            miConnection = origenDatos.getConnection();
            String instruccionSQL="INSERT INTO BUG (NAME,TYPE,DESCRIPTION,STATUS,USER,INITIALDATE)"+
                    "VALUES(?,?,?,?,?,?)";
            miStatement = miConnection.prepareCall(instruccionSQL);
            
            miStatement.setString(1, newBug.getbName());
            miStatement.setString(2, newBug.getbType());
            miStatement.setString(3, newBug.getbDescription());
            miStatement.setString(4, newBug.getbStatus());
            miStatement.setString(5, newBug.getbUser());
            
            //convirtiendo de fecha Util.Date a sql.Date
            java.util.Date utilDate = newBug.getBinitial_Date();
            long fechaEnlong = utilDate.getTime();
            java.sql.Date fechaConvertida;
            fechaConvertida = new java.sql.Date(fechaEnlong);            
            miStatement.setDate(6, fechaConvertida);
            
            miStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeloBug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
