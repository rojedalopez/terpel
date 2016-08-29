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

    <title>SB Admin - Bootstrap Admin Template</title>

    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
    <link href="../css/dataTables.bootstrap.min.css" rel="stylesheet" />
    <!-- Custom CSS -->
    <link href="../css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../css/plugins/morris.css" rel="stylesheet">
    

    <!-- Custom Fonts -->
    <link href="../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <script src="../js/jquery.js"></script>
    <script src="https://maps.google.com/maps/api/js?libraries=placeses,visualization,drawing,geometry,places&key=AIzaSyCqUEyO3rTumxb0G-oRsyBnZLn4O9VKtiM"></script>
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
    
    <script type="text/javascript" src="../js/date.js"></script>
    <script type="text/javascript" src="../js/angular/dirPagination.js"></script>
    <script type="text/javascript" src="../js/angular/angular-validator.js"></script>
    <script type="text/javascript" src="../js/app.js"></script>
    <script type="text/javascript" src="../js/angular/ng-map.min.js"></script>
    <script type="text/javascript" src="../js/angular/controles.js"></script>
    <script type="text/javascript" src="../js/angular/camiones.js"></script>
    
    <!-- Morris Charts JavaScript -->
    <script src="../js/plugins/morris/raphael.min.js"></script>
    <script src="../js/plugins/morris/morris.min.js"></script>
    <script src="../js/plugins/morris/morris-data.js"></script>


</head>

<body ng-app="myApp" class="ng-cloak">

    <div ng-controller="NotificacionesController as ctrl">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="../index.html">SB Admin</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${sessionScope.usr}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="spot.jsp"><i class="fa fa-fw fa-truck"></i> Generar Spot</a>
                        </li>
                        <li>
                            <a href="programada.jsp"><i class="fa fa-fw fa-truck"></i> Calendario cont.</a>
                        </li>
                        <li>
                            <a href="solicitudes.jsp"><i class="fa fa-fw fa-truck"></i> Solicitudes Activas</a>
                        </li>
                        <li>
                            <a href="solicitudes_hist.jsp"><i class="fa fa-fw fa-truck"></i> Solicitudes Historicas</a>
                        </li>
                        <li>
                            <a href="servicios.jsp"><i class="fa fa-fw fa-truck"></i> Servicios Activos</a>
                        </li>
                        <li>
                            <a href="servicios_hist.jsp"><i class="fa fa-fw fa-truck"></i> Servicios Historicos</a>
                        </li>
                        <li>
                            <a href="vehiculos.jsp"><i class="fa fa-fw fa-map-marker"></i> Vehiculos</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="../logout"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h2>NOTIFICACIONES</h2>
                    </div>
                </div>
                
                <div class="row">
                        <div class="col-md-12">
                            <label>Buscar: <input ng-model="searchText" class="form-control"></label>
                            <ul class="list-group" ng-repeat="notificacion in ctrl.notificaciones | filter:searchText">
                                <li class="list-group-item" >{{notificacion.nota}} <span class="badge">{{notificacion.desde}}</span></li>
                            </ul>
                        </div>
                    </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

</body>

</html>
