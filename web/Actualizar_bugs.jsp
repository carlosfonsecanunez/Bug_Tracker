<%-- 
    Document   : Actualizar_bugs
    Created on : 4 ene. 2022, 11:12:43
    Author     : kai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Bug</title>
    </head>
    <body>
        <h1>Update Bug</h1>
        <form name="form1" method="get" action="ControladorBug">
        <imput type="hidden" name="instruccion" value="actualizar_BBDD">
        <imput type="hidden" name="codigo_bug"  value="${BugaActualizar.bCode}">     
               
            <table>
                <tr>
                    <td><label for="name_bug">Name Bug</label></td>
                    <td><input type="text" name="name_bug" id="name_bug" value="${BugaActualizar.bName}"></td>
                </tr>  
                <tr>
                    <td><label for="type_bug">Type of Bug</label></td>
                    <td><input type="text" name="type_bug" id="type_bug" value="${BugaActualizar.bType}"></td>
                </tr>
                <tr>
                    <td><label for="description_bug">Description</label></td>
                    <td><input type="text" name="description_bug" id="description_bug" value="${BugaActualizar.bDescription}"></td>
                </tr>
                <tr>
                    <td><label for="status_bug">Status</label></td>
                    <td><input type="text" name="status_bug" id="status_bug" value="${BugaActualizar.bStatus}"></td>
                </tr>
                <tr>
                    <td><label for="user_bug">User</label></td>
                    <td><input type="text" name="user_bug" id="user_bug" value="${BugaActualizar.bUser}"></td>
                </tr>
                <tr>
                    <td><label for="initial_date">Initial Date</label></td>
                    <td><input type="text" name="initial_date" id="initial_date" value="${BugaActualizar.binitial_Date}"></td>
                </tr>
                <tr>
                    <td><label for="final_date">Final Date</label></td>
                    <td><input type="text" name="final_date" id="final_date" value="${BugaActualizar.bfinal_Date}"></td>
                </tr>
                <tr>
                    <td><input type="submit" name="buton_actualizar" value="Update"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
