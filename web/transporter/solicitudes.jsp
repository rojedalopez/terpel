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
        if(u.getTipo()==1){
            response.sendRedirect("/empresa/servicios.jsp");
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

    <!-- load Galleria -->

    <!-- Custom Fonts -->
    <link href="../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        .google-maps {
            height: 200px;
        }
        .cont {
            height: 200px;
            overflow: scroll;
            overflow-x: hidden;
        }        
        .contenedor {
            padding: 5px;
            margin-top: 3px;
            display: flex;
            flex-wrap: wrap;
        }
        .google-maps ng-map{
            height: 100%;
        }
        .box{
            border: 1px solid #c6c6c6;
            -webkit-box-shadow: 1px 1px 1px 1px #C7C7C7;
            box-shadow: 1px 1px 1px 1px #C7C7C7;
            -webkit-border-radius: 1px 1px 1px 1px;
            border-radius: 1px 1px 1px 1px;
        }
        .foto{
            order:1;
            border:2px black solid; 
            margin:auto;
            position: relative;
            width:18%;
            height:100px;
        }

        .titulo{
            margin-left: 2%;
            width: 80%;
            order:2;
        }
        
    </style>
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
    <style>
        .modal-backdrop.am-fade {
          opacity: .5;
          transition: opacity .15s linear;
          &.ng-enter {
            opacity: 0;
            &.ng-enter-active {
              opacity: .5;
            }
          }
          &.ng-leave {
            opacity: .5;
            &.ng-leave-active {
              opacity: 0;
            }
          }
        }
    </style>
</head>

<body ng-app="myApp" class="ng-cloak" >
    
    <div style="height: 600px;" ng-controller="ServiciosSolicitudController as ctrl">

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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${sessionScope.usr} <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-calendar"></i> Calendario Cont.</a>
                        </li>
                        <li>
                            <a href="servicios.jsp"><i class="fa fa-fw fa-truck"></i> Servicios en ruta</a>
                        </li>
                        <li>
                            <a href="vehiculos.jsp"><i class="fa fa-fw fa-automobile"></i> Vehiculos</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="../logout"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </nav>
        
        
            <!-- /.navbar-collapse -->

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row" >
                    
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><i class="fa fa-money fa-fw"></i> Solicitudes</h3>
                                </div>
                                <div class="panel-body">
                                    <div>
                                        <table class="table table-striped table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptions">
                                            <thead>
                                                <tr>
                                                    <th>No. Solicitud</th>
                                                    <th>Empresa Generadora</th>
                                                    <th>Ruta</th>
                                                    <th>Min de cargue</th>
                                                    <th>Max de descargue</th>
                                                    <th>Producto</th>
                                                    <th>Cupos disponibles</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr ng-repeat="solicitud in ctrl.solicitudes">
                                                    <td><span ng-bind="solicitud.id"></span></td>
                                                    <td><span ng-bind="solicitud.empresa"></span></td>
                                                    <td>{{solicitud.origen}} - {{solicitud.destino}}</td>
                                                    <td><span ng-bind="solicitud.cargue"></span></td>
                                                    <td><span ng-bind="solicitud.descargue"></span></td>
                                                    <td><span ng-bind="solicitud.producto"></span></td>
                                                    <td><span ng-bind="solicitud.cupos"></span></td>
                                                    <td><a ng-click="ctrl.getDataEquiposConductores(solicitud)">Rango de {{solicitud.rango}} KM</a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>                    
                        
                    </div>

                    <div class="row">
                        <div class="col-lg-4">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><i class="fa fa-money fa-fw"></i> Mis conductores</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="cont">
                                        <div ng-repeat="conductor in ctrl.conductores" class="contenedor box">
                                            <div class="foto">
                                                <div style="background-image: url('{{conductor.imagen}}');background-size: cover !important;background-repeat: no-repeat;position: absolute;top: 0;left: 0;right: 0;bottom: 0;"> 
                                                </div>
                                            </div>
                                            <div class="titulo">
                                                <p class="list-group-item-text">
                                                    Nombre: {{conductor.nombre}}<br/>
                                                    Celular: {{conductor.telefono}}<br/>
                                                    Ultimo reporte: {{conductor.ult_reporte}}</p>
                                                <p style="text-align: right;">
                                                    <button type="button" class="btn btn-sm btn-success" ng-click="ctrl.saveTakenSolicitud(conductor)">Asignar</button> 
                                                    <button type="button" class="btn btn-sm btn-info">Mas info</button>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><i class="fa fa-money fa-fw"></i> Mis conductores</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="google-maps">
                                        <ng-map height="100%" center="10.97978516762394,-74.80676651000977"  zoom="10">
                                            <marker id='{{conductor.id}}' position="{{conductor.latitud}},{{conductor.longitud}}" ng-repeat="conductor in ctrl.conductores"
                                            on-click="ctrl.showDetail(vehiculo)" icon="../css/images/ic_truckicon.png" reload></marker>
                                            <info-window id="foo-iw">
                                                  <div ng-non-bindable="">
                                                     <table style="width:100%;height: 100%;padding: 5px;">
                                                        <tr>
                                                            <th colspan="2" style="background-color: #E8E8E8;text-align: center;">DATOS DEL CONDUCTOR</th>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2" style="background-color:#000000;padding: 4px;">
                                                                 <center><img width="150px" src="{{ctrl.vehiculo.img_conductor}}"/></center>
                                                            </td> 
                                                        </tr>
                                                        <tr>
                                                            <td style="background-color: #E8E8E8;">NOMBRE</td>
                                                            <td>{{ctrl.vehiculo.nombre}}</td>
                                                        </tr>
                                                        <tr>
                                                            <td style="background-color: #E8E8E8;">DOCUMENTO</td>
                                                            <td>{{ctrl.vehiculo.doc}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">NO. LICENCIA</td>
                                                          <td>{{ctrl.vehiculo.lic}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">VENCIMIENTO DE LICENCIA</td>
                                                          <td>{{ctrl.vehiculo.vence_lic}}</td>
                                                        </tr>
                                                        <tr>
                                                            <td style="background-color: #E8E8E8;">TELEFONO</td>
                                                            <td>{{ctrl.vehiculo.telefono}}</td>
                                                        </tr>
                                                        <tr>
                                                            <td style="background-color: #E8E8E8;">DIRECCION</td>
                                                            <td>{{ctrl.vehiculo.direccion}}</td>
                                                        </tr>
                                                        <tr>
                                                            <th colspan="2" style="background-color: #E8E8E8;text-align: center;">DATOS DEL VEHICULO</th>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">TIPO EQUIPO</td>
                                                          <td>{{ctrl.vehiculo.tipo_equipo}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">PLACA</td>
                                                          <td>{{ctrl.vehiculo.placa}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">LIC. DE TRANSITO</td>
                                                          <td>{{ctrl.vehiculo.lic_transito}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">MARCA</td>
                                                          <td>{{ctrl.vehiculo.marca}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">REFERENCIA</td>
                                                          <td>{{ctrl.vehiculo.referencia}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">MODELO</td>
                                                          <td>{{ctrl.vehiculo.modelo}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">PLACA REMOLQUE</td>
                                                          <td>{{ctrl.vehiculo.placa_rem}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">TIPO REMOLQUE</td>
                                                          <td>{{ctrl.vehiculo.tipo_remolque}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">LIC. TRANSITO REMOLQUE</td>
                                                          <td>{{ctrl.vehiculo.lic_transito_r}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">POLIZA DE SEGURO</td>
                                                          <td>{{ctrl.vehiculo.poliza}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">COMPAÑIA ASEGURADORA</td>
                                                          <td>{{ctrl.vehiculo.comp}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">VENCIMIENTO DE POLIZA</td>
                                                          <td>{{ctrl.vehiculo.vence_poliza}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">POLIZA DE SEGURO H.C.</td>
                                                          <td>{{ctrl.vehiculo.poliza_hc}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">COMPAÑIA ASEGURADORA</td>
                                                          <td>{{ctrl.vehiculo.comp_hc}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">VENCIMIENTO DE POLIZA H.C.</td>
                                                          <td>{{ctrl.vehiculo.vence_poliza_hc}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">SOAT</td>
                                                          <td>{{ctrl.vehiculo.soat}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">VENCIMIENTO DE SOAT</td>
                                                          <td>{{ctrl.vehiculo.vence_soat}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">TECNOMECANICA</td>
                                                          <td>{{ctrl.vehiculo.tecno}}</td>
                                                        </tr>
                                                        <tr>
                                                          <td style="background-color: #E8E8E8;">VENCIMIENTO DE TECNOMECANICA</td>
                                                          <td>{{ctrl.vehiculo.vence_tecno}}</td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </info-window>
                                        </ng-map>
                                    </div>
                                </div>
                            </div>
                        </div>
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
