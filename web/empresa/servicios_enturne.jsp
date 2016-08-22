<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
response.setHeader("Pragma", "No-chache"); 
response.setHeader("Expires", "0"); 
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache", "no-cache"); 
if(session.getAttribute("user") == null){
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
            height: 600px;
        }
        .google-maps ng-map{
            height: 100%;
        }
        .text_valid{
            font-size:10px;
            font-style: italic;
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
        var CrearServicio, VerEnMapa;
        $(document).ready(function(){
            CrearServicio = $("#CrearServicio");
            VerEnMapa = $("#VerEnMapa");
        });
    </script>
    <script type="text/javascript" src="../js/date.js"></script>
    <script type="text/javascript" src="../js/angular/dirPagination.js"></script>
    <script type="text/javascript" src="../js/angular/angular-validator.js"></script>
    <script type="text/javascript" src="../js/app.js"></script>
    <script type="text/javascript" src="../js/angular/ng-map.min.js"></script>
    <script type="text/javascript" src="../js/angular/servicios.js"></script>
    <script type="text/javascript" src="../js/angular/camiones.js"></script>
    
    <!-- Morris Charts JavaScript -->
    <script src="../js/plugins/morris/raphael.min.js"></script>
    <script src="../js/plugins/morris/morris.min.js"></script>
    <script src="../js/plugins/morris/morris-data.js"></script>
    
</head>

<body ng-app="myApp" class="ng-cloak" >
    
    <div style="height: 600px;" ng-controller="ServActEnturneController as ctrl">

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
                            <a href="generar.jsp"><i class="fa fa-fw fa-plus"></i> Generar Solicitud</a>
                        </li>
                        <li>
                            <a href="solicitudes.jsp"><i class="fa fa-fw fa-truck"></i> Solicitudes Activas</a>
                        </li>
                        <li>
                            <a href="servicios_table.jsp"><i class="fa fa-fw fa-truck"></i> Servicios Activos</a>
                        </li>
                        <li>
                            <a href="enturne_table.jsp"><i class="fa fa-fw fa-ticket"></i> Enturnar Veh.</a>
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
                
                <div class="row">
                    <div class="col-lg-12">
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="../Servicios">Servicios</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-edit"></i> Asignar
                            </li>
                        </ol>
                    </div>
                </div>
                
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12" >
                        <table id="vehiculos" datatable="ng" dt-options="ctrl.dtOptions" class="table table-striped table-bordered dt-responsive nowrap compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <td>Placa</td>
                                    <td>Trailer</td>
                                    <td>T. Trailer</td>
                                    <td>Capacidad</td>
                                    <td>Marca</td>
                                    <td>Modelo</td>
                                    <td>Referencia</td>
                                    <td>Conductor</td>
                                    <td>Telefono</td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="vehiculo in ctrl.vehiculos">
                                    <td><span ng-bind="vehiculo.placa"></span></td>
                                    <td><span ng-bind="vehiculo.trailer"></span></td>
                                    <td><span ng-bind="vehiculo.remolque"></span></td>
                                    <td><span ng-bind="vehiculo.capacidad"></span> <span ng-bind="vehiculo.unidad"></span></td>
                                    <td><span ng-bind="vehiculo.marca"></span></td>
                                    <td><span ng-bind="vehiculo.modelo"></span></td>
                                    <td><span ng-bind="vehiculo.referencia"></span></td>
                                    <td><span ng-bind="vehiculo.nombre"></span></td>
                                    <td><span ng-bind="vehiculo.telefono"></span></td>
                                    <td><span style="cursor: pointer;" ng-click="ctrl.showMap(vehiculo)"><i class="fa-globe fa fa-2x fa-align-center"></i></span></td>
                                    <td><span style="cursor: pointer;" ng-click="ctrl.asignarServ(vehiculo)"><i class="fa fa-2x fa-align-center fa-check-square"></i> </span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->
        
        <div id="CrearServicio" class="modal fade" role="dialog">
            <div class="modal-dialog">

              <!-- Modal content-->
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Asignar servicio a vehiculo {{ctrl.vehiculo.placa}}</h4>
                </div>
                <div class="modal-body">
                    <form name="envioAsign">
                        <div class="form-group">
                            <label>Punto de origen</label>
                            <select class="form-control" ng-model="ctrl.servicio.origen" ng-options="punto.id as punto.desc for punto in ctrl.puntos" ng-change="ctrl.selectOrigen(ctrl.servicio.origen)" >
                                <option value="">Seleccione Origen</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Punto de destino</label>
                            <select class="form-control" ng-model="ctrl.servicio.destino" ng-options="punto.id as punto.desc for punto in ctrl.puntos" ng-change="ctrl.selectDestino(ctrl.servicio.destino)">
                                <option value="">Seleccione Destino</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Fecha de cargue:</label>
                            <div class="input-group input-group-sm date" 
                                datetimepicker ng-model="ctrl.dateCargue" options="ctrl.options" ng-disabled="ctrl.enviado">
                                  <input type="text" class="form-control" ng-disabled="ctrl.enviado"/>
                                  <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                  </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Fecha de descargue:</label>
                            <div class="input-group input-group-sm date" 
                                datetimepicker ng-model="ctrl.dateDescargue" options="ctrl.options" ng-disabled="ctrl.enviado">
                                  <input type="text" class="form-control" ng-disabled="ctrl.enviado"/>
                                  <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                  </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Tipo cargue</label>
                            <select class="form-control" ng-model="ctrl.servicio.cargue" ng-options="cargue.id as cargue.desc for cargue in ctrl.cargues">
                                <option value="">Seleccione cargue</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Guia:</label>
                            <input type="text" class="form-control" ng-model="ctrl.servicio.guia" />
                        </div>
                        <div class="form-group">
                            <label>Nota:</label>
                            <textarea class="form-control" ng-model="ctrl.servicio.nota" cols="40" rows="3"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <div id="alerta-submit"></div>
                    <button type="button" class="btn btn-primary" ng-click="ctrl.sendServicio()">Enviar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
              </div>

            </div>
          </div>

        <div id="VerEnMapa" class="modal fade" role="dialog">
            <div class="modal-dialog">

              <!-- Modal content-->
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Posicion del vehiculo {{ctrl.vehiculo.placa}}</h4>
                </div>
                <div class="modal-body">
                    <ng-map id="ViewMap" center="{{ctrl.vehiculo.position[0]}},{{ctrl.vehiculo.position[1]}}">
                        <marker position="{{ctrl.vehiculo.position}}"
                        icon="../css/images/ic_truckicon_disp.png" reload></marker>
                    </ng-map>
                </div>
                <div class="modal-footer">
                    <div id="alerta-submit"></div>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
              </div>

            </div>
          </div>
    </div>
    <!-- /#wrapper -->

    
</body>

</html>
