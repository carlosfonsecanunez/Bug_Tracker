/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package paquete.com.Bugtracker;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author kai
 */
public class ControladorBug extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
     private ModeloBug modeloBug;
    
    @Resource(name="jdbc/BUG")
    private DataSource miPool;
    
    
    @Override
    public void init()throws ServletException{
        super.init();
        
        try{
        modeloBug = new ModeloBug(miPool);
        }catch(Exception e){
            throw new ServletException (e);
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String elcomando = request.getParameter("instruccion");
         
        
        if(elcomando==null) elcomando="listar";
        
        switch(elcomando){
             case "listar" : obtenerBugs(request,response);
                break;
             case "insertar_BBDD" : insertarBugs(request,response);
                break;
             case "cargar" : cargarBugs(request,response);
                break;
             case "actualizar_BBDD" : {
                 try {
                     actualizarBug(request, response);
                 } catch (Exception ex) {
                     Logger.getLogger(ControladorBug.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
                break;

                
             default : obtenerBugs(request,response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void obtenerBugs(HttpServletRequest request, HttpServletResponse response)  {
         List<Bug> bugs;
            
        try  {       
            
            bugs = modeloBug.getBugs();
            
            request.setAttribute("MISBUGS", bugs);
            
            RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaBugs.jsp");
            
            miDispatcher.forward(request, response);
    }catch(Exception e){
            
            e.printStackTrace();
            
        }
        

    }   

    private void insertarBugs(HttpServletRequest request, HttpServletResponse response) {
       // int code = Integer.parseInt(request.getParameter("bug_name"));
        String name = request.getParameter("bug_name");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String user = request.getParameter("user");
        //cambiandole el formato a la fecha 
        SimpleDateFormat formatoDate = new SimpleDateFormat ("yyyy-MM-dd");
        
        Date bugFecha = null;
         try {
             bugFecha = formatoDate.parse(request.getParameter("init_date"));
         } catch (ParseException ex) {
             Logger.getLogger(ControladorBug.class.getName()).log(Level.SEVERE, null, ex);
         }
        //Date bugfechaFinal = null;
        
        Bug newBug = new Bug(name,type,description,status,user,bugFecha);
        
        ModeloBug.addNewBug(newBug);
        
        obtenerBugs(request,response);
    }

    private void cargarBugs(HttpServletRequest request, HttpServletResponse response) {
        String bs = request.getParameter("bugID");
        int bugID = Integer.parseInt(bs) ;
        
        Bug newBug;
        
         try {
             
             newBug = modeloBug.getBug(bugID);
         
             request.setAttribute("BugaActualizar", newBug);
             
             RequestDispatcher miDispatcher = request.getRequestDispatcher("/Actualizar_bugs.jsp");
             
             miDispatcher.forward(request, response);

         
         } catch (Exception ex) {
             Logger.getLogger(ControladorBug.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }

    private void actualizarBug(HttpServletRequest request, HttpServletResponse response)throws Exception {
        int codigoBug = Integer.parseInt(request.getParameter("codigo_bug"));
        
        String name = request.getParameter("name_bug");
        String type = request.getParameter("type_bug");
        String description = request.getParameter("description_bug");
        String status = request.getParameter("status_bug");
        String user = request.getParameter("user_bug");
        
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechainicial = null;
        Date fechafinal = null;
        
         try {
             
             fechainicial = formatofecha.parse(request.getParameter("initial_date"));
             fechafinal = formatofecha.parse(request.getParameter("final_date"));
             
         } catch (ParseException ex) {
             Logger.getLogger(ControladorBug.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        Bug bugaActualizar = new Bug(codigoBug,name,type,description,status,user,fechainicial,fechafinal);
         try {
             modeloBug.actualizarBug(bugaActualizar);
         } catch (Exception ex) {
             Logger.getLogger(ControladorBug.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        obtenerBugs(request,response);
    }

    
}
