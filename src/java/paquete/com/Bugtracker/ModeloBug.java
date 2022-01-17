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

    Bug getBug(int bugID) throws Exception {
        Bug elBug = null;
        Connection miConnection = null;
        PreparedStatement miStatement = null;
        ResultSet miResulset = null;
        int bugid = bugID;
        
        try {
            
            miConnection = origenDatos.getConnection();
            
            String sql = "SELECT * FROM BUG WHERE ID=?";
            
            miStatement = miConnection.prepareStatement(sql);
            
            miStatement.setInt(1, bugid);
            
            miResulset = miStatement.executeQuery();
            
            if (miResulset.next()){
                int id = miResulset.getInt(1);
                String name = miResulset.getString(2);
                String type = miResulset.getString(3);
                String description = miResulset.getString(4);
                String status = miResulset.getString(5);
                String user = miResulset.getString(6);
                Date initfecha = miResulset.getDate(7);
                Date finfecha = miResulset.getDate(8);
                
                elBug = new Bug(id,name,type,description,status,user,initfecha,finfecha);
            } else {
                throw new Exception("theres not Bug whit that ID = "+bugid);
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(ModeloBug.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return elBug;
        
    }

    void actualizarBug(Bug bugaActualizar) throws Exception{
       Connection miConnection = null;
       PreparedStatement miStatement = null;
       
        try {
            miConnection = origenDatos.getConnection();
            String instruccionSQL="UPDATE BUG SET NAME=?, TYPE=?, DESCRIPTION=?, STATUS=?, USER=?, INITIALDATE=?, FINALDATE=?"+
                    "WHERE ID=?";
            miStatement = miConnection.prepareStatement(instruccionSQL);
            
            miStatement.setString(1, bugaActualizar.getbName());
            miStatement.setString(2, bugaActualizar.getbType());
            miStatement.setString(3, bugaActualizar.getbDescription());
            miStatement.setString(4, bugaActualizar.getbStatus());
            miStatement.setString(5, bugaActualizar.getbUser());
            
            //convirtiendo de fecha Util.Date a sql.Date
            java.util.Date utilDate = bugaActualizar.getBinitial_Date();
            long fechaEnlong = utilDate.getTime();
            java.sql.Date fechaConvertida;
            fechaConvertida = new java.sql.Date(fechaEnlong);            
            miStatement.setDate(6, fechaConvertida);
            
            java.util.Date utilDate2 = bugaActualizar.getBfinal_Date();
            long fechaEnlong2 = utilDate2.getTime();
            java.sql.Date fechaConvertida2;
            fechaConvertida2 = new java.sql.Date(fechaEnlong2);            
            miStatement.setDate(7, fechaConvertida2);
            
            miStatement.setInt(8, bugaActualizar.getbCode());
            
            miStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeloBug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
