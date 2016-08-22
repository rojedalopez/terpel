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
        var CrearServicio;
        $(document).ready(function(){
            CrearServicio = $("#CrearServicio");
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
    
    <div id="wrapper" style="height: 600px;" ng-controller="ServEnturneController as ctrl">

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
        
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <form role="form" class="nav navbar-nav side-nav form-group-sm" style="padding: 5px; color: gray;">
                    <div class="col-sm-12">
                        <div class="list-group">
                            <input type="text" class="form-control" placeholder="Buscar..." ng-model="filterValue"><br/>
                            <a class="list-group-item" ng-repeat="vehiculo in ctrl.vehiculos | filter:filterValue" style="cursor: pointer;">
                                <h4 class="list-group-item-heading">{{vehiculo.placa}}</h4>
                                <p class="list-group-item-text">
                                        Conductor: {{vehiculo.nombre}}<br/>
                                        Telefono: {{vehiculo.telefono}}<br/>
                                        Tipo Remolque: {{vehiculo.remolque}}<br/>
                                        Placa Remolque: {{vehiculo.trailer}}<br/>
                                        Tipo Equipo: {{vehiculo.tipoequipo}}<br/>
                                        Tipo Carga: {{vehiculo.tipocarga}}<br/>
                                        Marca: {{vehiculo.marca}}<br/>
                                        Modelo: {{vehiculo.modelo}}<br/>
                                        Referencia: {{vehiculo.referencia}}<br/>
                                        Ult Actualización: {{vehiculo.ult_reporte}}<br/></p>
                                        <button ng-click="ctrl.showOnMap(vehiculo)"> Ver en mapa </button>
                                        <button ng-click="ctrl.asignarServMap(vehiculo)"> Asignar Servicio </button>
                            </a>
                        </div>
                    </div>  
                </form>
                
            </div>
            <!-- /.navbar-collapse -->

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12" >
                        <div class="google-maps">
                            <ng-map height="100%" center="10.97978516762394,-74.80676651000977"  zoom="10"
                            map-type-control="true" map-type-control-options="{style:'HORIZONTAL_BAR', position:'BOTTOM_CENTER'}">
                                <custom-control id="home" position="TOP_RIGHT" index="1">
                                    <div style="margin: 7px 7px 0 7px; ">
                                        <select class="form-control" ng-model="ctrl.mapa.radio" 
                                            ng-options="Tipo.ID as Tipo.Value for Tipo in ctrl.Distancias" ng-change="ctrl.cambiarDistancia(ctrl.mapa.radio)">
                                            <option value="">--- Radio ---</option>
                                        </select>
                                    </div>
                                    <div style="margin: 7px;">
                                        <select class="form-control" ng-model="ctrl.mapa.tipocarga" 
                                        ng-options="Tipo.ID as Tipo.Value for Tipo in ctrl.TipoCarga" ng-change="ctrl.cambiarTipo(ctrl.mapa.tipocarga)">
                                            <option value="">--- Tipo Carga ---</option>
                                        </select>
                                    </div>
                                    <div style="margin: 7px;">
                                        <button type="button" class="btn btn-default form-control" ng-disabled="(ctrl.Remolques.length==0)?true:false" ng-model="ctrl.mapa.remolques" 
                                                max-length-html="Seleccionados" ng-change="ctrl.cambiarSeletsMult()" data-multiple="1" max-length="1" placeholder="--- Tipo Remolque ---" all-none-buttons="true"
                                                bs-options="Remolque.ID as Remolque.Value for Remolque in ctrl.Remolques" bs-select>
                                            Action <span class="caret"></span>
                                        </button>
                                    </div>
                                    <div style="margin: 7px;">
                                        <button type="button" class="btn btn-default form-control" ng-model="ctrl.mapa.equipos" 
                                                max-length-html="Seleccionados" data-multiple="1" 
                                                max-length="1" placeholder="--- Tipo Equipos ---" all-none-buttons="true"
                                                bs-options="TipoEquipo.ID as TipoEquipo.Value for TipoEquipo in ctrl.TipoEquipos" bs-select>
                                            Action <span class="caret"></span>
                                        </button>
                                    </div>
                                    <div style="margin: 7px;">
                                        <select class="form-control" ng-model="ctrl.mapa.cargue" 
                                        ng-options="Tipo.ID as Tipo.Value for Tipo in ctrl.Cargas" >
                                            <option value="">--- Producto ---</option>
                                        </select>
                                    </div>
                                </custom-control>
                                <directions
                                draggable="false"
                                travel-mode="DRIVING"
                                origin="{{ctrl.placeO.geometry.location}}"
                                destination="{{ctrl.placeD.geometry.location}}" suppressMarkers='true'>
                                <marker id='{{vehiculo.placa}}' position="{{vehiculo.position}}" ng-repeat="vehiculo in ctrl.vehiculos"
                                on-click="ctrl.showDetail(vehiculo)" icon="../css/images/ic_truckicon_disp.png" reload></marker>
                                <info-window id="foo-iw">
                                      <div ng-non-bindable="">
                                        <img width="64" src="{{ctrl.vehiculo.imagen}}" align="left" /> 
                                        Conductor: {{ctrl.vehiculo.nombre}}<br/>
                                        Telefono: {{ctrl.vehiculo.telefono}}<br/>
                                        Placa: {{ctrl.vehiculo.placa}}<br/>
                                        Tipo Remolque: {{ctrl.vehiculo.remolque}}<br/>
                                        Placa Remolque: {{ctrl.vehiculo.trailer}}<br/>
                                        Tipo Equipo: {{ctrl.vehiculo.tipoequipo}}<br/>
                                        Tipo Carga: {{ctrl.vehiculo.tipocarga}}<br/>
                                        Marca: {{ctrl.vehiculo.marca}}<br/>
                                        Modelo: {{ctrl.vehiculo.modelo}}<br/>
                                        Referencia: {{ctrl.vehiculo.referencia}}<br/>
                                        Ult Actualización: {{ctrl.vehiculo.ult_reporte}}<br/>
                                        
                                    </div>
                                </info-window>
                                <shape id="circle" name="circle" stroke-color="#01DF3A" stroke-opacity="0.5" 
                                center="{{ctrl.shape.center}}" radius="{{ctrl.shape.radius}}" stroke-weight="1"></shape>
                            </ng-map>
                        </div>
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
                                <option value="">Seleccione Destino</option>
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

    </div>
    <!-- /#wrapper -->

    
</body>

</html>
