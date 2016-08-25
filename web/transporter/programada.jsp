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

    <!-- Bootstrap Core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
    <link href="../css/dataTables.bootstrap.min.css" rel="stylesheet" />
    <!-- Custom CSS -->
    <link href="../css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../css/plugins/morris.css" rel="stylesheet">
    

    <!-- Custom Fonts -->
    <link href="../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        .google-maps {
            height: 300px;
        }
        .google-maps ng-map{
            height: 100%;
        }
        .text_valid{
            font-size:10px;
            font-style: italic;
        }

        .dropdown-menu {
            width:100%;
          }
    </style>
    <script src="../js/jquery.js"></script>
    <script src="https://maps.google.com/maps/api/js?libraries=placeses,visualization,drawing,geometry,places&key=AIzaSyCqUEyO3rTumxb0G-oRsyBnZLn4O9VKtiM"></script>
    <script src="../js/angular.min.js"></script>
    <script src="../js/angular-strap.min.js"></script>
    <script src="../js/angular-strap.tpl.min.js"></script>
    <script src="../js/ui-bootstrap-tpls-2.0.0.js"></script>

    <script src="../js/bootstrap.min.js"></script>  
    <script type="text/javascript" src="../js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="../js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/dataTables.responsive.min.js"></script>
    <script src="../js/moment.min.js"></script>
    <script src="../js/bootstrap-datetimepicker.min.js"></script>
    <script src="../js/angular-eonasdan-datetimepicker.min.js"></script>
    <script src="../js/dist/angular-datatables.min.js"></script>
    
    <script>
        var AgregarSolicitud, nuevoCC;
        $(document).ready(function(){
            AgregarSolicitud = $("#AgregarSolicitud");
            nuevoCC = $("#nuevoCC");
        });
    </script>
    
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
    
    <div style="height: 600px;" ng-controller="ProgramadasController as ctrl">

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
                <a class="navbar-brand" href="index.html">SB Admin</a>
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
                            <a href="solicitudes.jsp"><i class="fa fa-fw fa-truck"></i> Solicitudes Activas</a>
                        </li>
                        <li>
                            <a href="servicios.jsp"><i class="fa fa-fw fa-truck"></i> Servicios Activos</a>
                        </li>
                        <li>
                            <a href="puntos.jsp"><i class="fa fa-fw fa-map-marker"></i> Puntos</a>
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
                    <div class="col-lg-4" ng-repeat="dias in ctrl.calendario">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div  class="text-right">
                                    <div style="float: left;">
                                        <i class="fa fa-calendar fa-fw"></i> {{ctrl.mes}} {{dias.dia}}
                                    </div>
                                    <br/>
                                </div>
                            </div>
                            <div class="panel-body">                                
                                <div class="list-group" style="height: 300px; overflow: scroll; overflow-x: hidden;">
                                    <a href="#" class="list-group-item" ng-repeat="spot in dias.spots">
                                        <p class="list-group-item-text"><b>Punto de origen:</b> {{spot.desc_inicio}}</p>
                                        <p class="list-group-item-text"><b>Punto de destino:</b> {{spot.desc_fin}}</p>
                                        <p class="list-group-item-text"><b>Tipo de cargue:</b> {{spot.desc_tipocargue}}</p>
                                        <p class="list-group-item-text"><b>Intervalo de servicio:</b> {{spot.min_carg}} - {{spot.max_desc}}</p>
                                        <p class="list-group-item-text"><b>Cantidad equipos:</b> {{spot.equipos}}</p>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="AgregarSolicitud" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title" id="gridSystemModalLabel">Nueva Solicitud para {{ctrl.mes}} {{ctrl.nuevaSol.dia}} </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Punto de cargue:</label>
                        <select ng-disabled="ctrl.enviado" ng-model="ctrl.nuevaSol.inicio"
                        type="text" name="nameOrigin"
                        clase="text_valid" class="form-control"
                        required-message="'El campo no puede estar vacio'" 
                        required ng-options="punto as punto.desc for punto in ctrl.puntos"
                        ng-change="ctrl.selectInicio()">
                            <option value="">--- Seleccione inicio ---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Punto de descargue:</label>
                        <select ng-disabled="ctrl.enviado" ng-model="ctrl.nuevaSol.fin"
                        type="text" name="nameDestination"
                        clase="text_valid" class="form-control"
                        required-message="'El campo no puede estar vacio'" 
                        required ng-options="punto as punto.desc for punto in ctrl.puntos"
                        ng-change="ctrl.selectFin()">
                            <option value="">--- Seleccione fin ---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Centro de costos:</label>
                        <div class="input-group">
                            <select class="form-control" ng-model="ctrl.nuevaSol.ccosto"
                            ng-options="ccosto.id as ccosto.desc for ccosto in ctrl.ccostos" ng-disabled="ctrl.enviado"
                            name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                                <option value="">--- Seleccione Tipo ---</option>
                            </select>
                            <span class="input-group-addon" data-toggle="modal" data-target="#nuevoCC">
                                <span class="glyphicon glyphicon-plus"></span>
                              </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Tipo de cargue:</label>
                        <select class="form-control" ng-model="ctrl.nuevaSol.carga" ng-change="ctrl.cambiarCargue(ctrl.servicio.carga)"
                        ng-options="Tipo as Tipo.desc for Tipo in ctrl.cargues" ng-disabled="ctrl.enviado"
                        name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                            <option value="">--- Seleccione Tipo ---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>No. de equipos:</label>
                        <input ng-disabled="ctrl.enviado" ng-model="ctrl.nuevaSol.equipos"
                        type="number" name="nameOrigin" clase="text_valid" class="form-control"
                        required-message="'El campo no puede estar vacio'" 
                        required />
                    </div>
                    <div class="form-group">
                        <label>Fecha min de cargue:</label>
                        <div class="input-group input-group-sm date" 
                            datetimepicker ng-model="ctrl.dateMinCargue" options="ctrl.options" ng-disabled="ctrl.enviado">
                              <input type="text" class="form-control" ng-disabled="ctrl.enviado"/>
                              <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                              </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Fecha max de cargue:</label>
                        <div class="input-group input-group-sm date" ng-change="ctrl.colocarFecha()"
                            datetimepicker ng-model="ctrl.dateMaxCargue" options="ctrl.options" ng-disabled="ctrl.enviado">
                            <input type="text" class="form-control" ng-disabled="ctrl.enviado"/>
                              <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                              </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Fecha min de descargue:</label>
                        <div class="input-group input-group-sm date" 
                            datetimepicker ng-model="ctrl.dateMinDescargue" options="ctrl.options" ng-disabled="ctrl.enviado">
                              <input type="text" class="form-control" ng-disabled="ctrl.enviado"/>
                              <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                              </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Fecha max de descargue:</label>
                        <div class="input-group input-group-sm date" 
                            datetimepicker ng-model="ctrl.dateMaxDescargue" options="ctrl.options" ng-disabled="ctrl.enviado">
                              <input type="text" class="form-control" ng-disabled="ctrl.enviado"/>
                              <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                              </span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" ng-click="ctrl.sendSolicitud()">Enviar</button>
                </div>
            </div>    
          </div>
        </div>  
                    
        <div class="modal fade" id="nuevoCC" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title" id="gridSystemModalLabel">Agregar Centro de Costo</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>ID:</label>
                        <div class='date'>
                            <input type='text' class="form-control" ng-model="ctrl.ccosto.id" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Descripción:</label>
                        <div class='date'>
                            <input type='text' class="form-control" ng-model="ctrl.ccosto.desc"/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" ng-click="ctrl.SendCCosto()">Enviar</button>
                </div>
            </div>    
          </div>
        </div>
        </div>
        <!-- /#page-wrapper -->
        
    
    
</body>

</html>
