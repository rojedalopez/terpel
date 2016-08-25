<%@page import="bean.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
response.setHeader("Pragma", "No-chache"); 
response.setHeader("Expires", "0"); 
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache", "no-cache"); 
if(session.getAttribute("user") != null){
    Usuario u = (Usuario)session.getAttribute("user");
    if(u.getRol()==2||u.getRol()==3){
        if(u.getTipo()==2){
            response.sendRedirect("/transporter/servicios.jsp");
        }

    }
}else{
   //redirijo al login
   response.sendRedirect("../?mensaje=Acabo su sesion.");
}
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="../js/jquery.js"></script>
    <script src="../js/angular.min.js"></script>
    <script src="../js/angular-strap.min.js"></script>
    <script src="../js/angular-strap.tpl.min.js"></script>
    <script src="../js/angular-animate.js"></script>
    <script src="../js/ui-bootstrap-tpls-2.0.0.js"></script>

    <script src="../js/bootstrap.min.js"></script>  
    <script type="text/javascript" src="../js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="../js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/dataTables.responsive.min.js"></script>
    <script src="../js/moment.min.js"></script>
    <script src="../js/bootstrap-datetimepicker.min.js"></script>
    <script src="../js/angular-eonasdan-datetimepicker.min.js"></script>
    <script src="../js/dist/angular-datatables.min.js"></script>
    <script src="../js/angular-material.min.js"></script>
    
    <script type="text/javascript" src="../js/date.js"></script>
    <script type="text/javascript" src="../js/angular/dirPagination.js"></script>
    <script type="text/javascript" src="../js/angular/angular-validator.js"></script>
    <script type="text/javascript" src="../js/app.js"></script>
    <script type="text/javascript" src="../js/angular/ng-map.min.js"></script>
    <script type="text/javascript" src="../js/angular/controles.js"></script>
    <script type="text/javascript" src="../js/angular/camiones.js"></script>
    <title>SB Admin - Bootstrap Admin Template</title>

    <style type="text/css">
        body, html{
            width: 100%;
            height: 100%;
            overflow-x: hidden;
            overflow-y: hidden;
            color: black;
        }
        .principal{
            position: absolute;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color: #fff;
        }
        
        .turnoactivo{
            position: absolute;
            border: 5px solid black;
            width: 40%;
            height: 20%;
            top: 5%;
            left: 50%;
            margin-left: -20%;
            text-align: center;
            font-size: 50px;
            color: white;
            background-color: #2E2EFE;
        }
        
        .botonsiguiente{
            position: absolute;
            top: 12%;
            left: 80%;
            width: 200px;
            height: 35px;
            border: 2px solid black;
            background-color: green;
            font-size: 30px;
            text-align: center;
        }
        
        .tablaturno{
            position: absolute;
            width: 90%;
            margin-left: 5%;
            height: 65%;
            top: 30%;
            left: 50%;
            margin-left: -45%;
            font-size: 50px;
            color: black;
        }
        
        table, th, td {
            border: 3px solid black;
        }
    </style>

</head>

<body ng-app="myApp" class="ng-cloak">
    <div ng-controller="TicketsController as ctrl"> 
    <div class="principal">
        <a href="../logout">..</a>
        <div class="turnoactivo" >
            PROXIMO TURNO:
            <br/>
            <b>{{ctrl.activo.placa}}</b>
        </div>
        <div class="botonsiguiente" ng-click="ctrl.siguiente()">
        SIGUIENTE
    </div>
    </div>
    
    <div class="tablaturno">
        <table style="width: 100%; text-align: center; border-collapse: collapse;">
            <thead>
                <tr><th colspan="3" style="background-color: blue; color: white;">SISTEMA DE ENTURNE</th></tr>
                <tr style="background-color: #2E2EFE; color: white;"><th>TURNO</th><th>PLACA</th><th></th></tr>
            </thead>
            <tbody>
                <tr ng-repeat="ticket in ctrl.tickets">
                    <td>{{ticket.turno}}</td><td>{{ticket.placa}}</td><td><div style="border: solid black 2px; padding: 10px;background: #2E2EFE; color: white; cursor: pointer; " ng-click="ctrl.terminar(ticket)">Terminar</div></td>
                </tr>
            </tbody>
        </table>
    </div>
    </div>
</body>

</html>
