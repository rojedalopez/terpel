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
            height: 320px;
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
        var nuevoCC;
        $(document).ready(function(){
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

<body ng-app="myApp" class="ng-cloak" >
    
    <div id="wrapper" style="height: 600px;" ng-controller="SpotController as ctrl">

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
                            <a href="puntos.jsp"><i class="fa fa-fw fa-map-marker"></i> Puntos</a>
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
        
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <form role="form" class="nav navbar-nav side-nav form-group-sm" name="solicitud" 
                      novalidate style="padding: 5px; color: gray;">
                        <div class="panel-group" id="accordion">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" style="text-decoration: none; color: #808080;">
                                    <h4 class="panel-title">
                                          Servicio
                                    </h4>
                                  </a>
                                </div>
                                <div id="collapse1" class="panel-collapse collapse in">
                                    <div class="form-group" style="padding: 10px;">
                                        <div class="form-group">
                                            <label>Punto de cargue:</label>
                                            
                                            <select ng-disabled="ctrl.enviado" ng-model="ctrl.servicio.inicio"
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
                                            <select ng-disabled="ctrl.enviado" ng-model="ctrl.servicio.fin"
                                            type="text" name="nameDestination"
                                            clase="text_valid" class="form-control"
                                            required-message="'El campo no puede estar vacio'" 
                                            required ng-options="punto as punto.desc for punto in ctrl.puntos"
                                            ng-change="ctrl.selectFin()">
                                                <option value="">--- Seleccione fin ---</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Valor del flete:</label>
                                            <div class='date'>
                                                <input type='text' class="form-control" ng-model="ctrl.servicio.flete"
                                                       name="flete" placeholder="$"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Tipo de cargue:</label>
                                            <select class="form-control" ng-model="ctrl.servicio.carga" ng-change="ctrl.cambiarCargue(ctrl.servicio.carga)"
                                            ng-options="Tipo.id as Tipo.desc for Tipo in ctrl.cargues" ng-disabled="ctrl.enviado"
                                            name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                                                <option value="">--- Seleccione Tipo ---</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Tipo Remolque:</label>
                                            <button type="button" class="btn btn-default form-control" ng-disabled="(ctrl.Remolques.length==0)?true:false" ng-model="ctrl.mapa.remolques" 
                                                    max-length-html="Seleccionados" ng-change="ctrl.cambiarSeletsMult()" data-multiple="1" max-length="1" placeholder="--- Tipo Remolque ---" all-none-buttons="true"
                                                    bs-options="Remolque.ID as Remolque.Value for Remolque in ctrl.Remolques" bs-select>
                                                Action <span class="caret"></span>
                                            </button>
                                        </div>
                                        <div class="form-group">
                                            <label>N° equipos:</label>
                                            <input type='number' class="form-control" min="1" 
                                            ng-model="ctrl.servicio.equipos" ng-disabled="ctrl.enviado"
                                            name="equipos" clase="text_valid"
                                            invalid-message = "'Debe ingresar un numero valido'"
                                            required-message="'El campo no puede estar vacio'" 
                                            required/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse2" style="text-decoration: none; color: #808080;">
                                      <h4 class="panel-title">
                                            Fecha
                                      </h4>
                                    </a>
                                </div>
                                <div id="collapse2" class="panel-collapse collapse">
                                    <div class="panel-body">
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
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse3" style="text-decoration: none; color: #808080;">
                                  <h4 class="panel-title">
                                        Detalle
                                  </h4>
                                </a>
                                </div>
                                <div id="collapse3" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Orden de servicio:</label>
                                            <div class='date'>
                                                <input type='text' class="form-control" ng-model="ctrl.servicio.orden" ng-disabled="ctrl.enviado"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Notas:</label>
                                            <textarea class="form-control"  rows="3" ng-model="ctrl.servicio.nota_detalle" style="resize:false;" ng-disabled="ctrl.enviado"></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>Centro de costos:</label>
                                            <div class="input-group">
                                                <select class="form-control" ng-model="ctrl.servicio.ccosto"
                                                ng-options="ccosto.id as ccosto.desc for ccosto in ctrl.ccostos" ng-disabled="ctrl.enviado"
                                                name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                                                    <option value="">--- Seleccione Tipo ---</option>
                                                </select>
                                                <span class="input-group-addon" data-toggle="modal" data-target="#nuevoCC">
                                                    <span class="glyphicon glyphicon-plus"></span>
                                                  </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <div id="alerta-submit"></div>
                    <div style="text-align: right;">
                        <button type="button" class="btn btn-success" ng-click="ctrl.sendServicio()">Enviar Solicitud</button>
                        <button type="button" class="btn btn-default" ng-click="ctrl.resetForm()">Limpiar</button>
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
                            <ng-map height="100%" center="10.97978516762394,-74.80676651000977"  zoom="10">
                                <directions
                                draggable="false"
                                travel-mode="DRIVING"
                                origin="{{ctrl.placeO}}"
                                destination="{{ctrl.placeD}}" suppressMarkers='true'>
                                <marker id='{{vehiculo.placa}}' position="{{vehiculo.position}}" ng-repeat="vehiculo in ctrl.vehiculos"
                                on-click="ctrl.showDetail(vehiculo)" icon="{{vehiculo.icono}}" reload></marker>
                                <info-window id="foo-iw">
                                      <div ng-non-bindable="">
                                         <table style="width:100%;height: 100%;padding: 5px;">
                                            <tr>
                                                <th colspan="2" style="background-color: #E8E8E8;text-align: center;">DATOS DEL CONDUCTOR</th>
                                            </tr>
                                            <tr>
                                                <td colspan="2" style="background-color:#000000;padding: 4px;">
                                                     <center><img width="150px" src="{{ctrl.vehiculo.img_cond}}"/></center>
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
                <div class="row" style="margin-top: 15px;">
                    <div class="col-lg-12" >
                        <table id="vehiculos" datatable="ng" dt-options="ctrl.dtOptions" class="table table-striped table-bordered dt-responsive compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <td>Transportadora</td>
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
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="vehiculo in ctrl.vehiculos">
                                    <td><span ng-bind="vehiculo.empresa"></span></td>
                                    <td><span ng-bind="vehiculo.placa"></span></td>
                                    <td><span ng-bind="vehiculo.placa_rem"></span></td>
                                    <td><span ng-bind="vehiculo.tipo_remolque"></span></td>
                                    <td><span ng-bind="vehiculo.capacidad"></span> <span ng-bind="vehiculo.unidad"></span></td>
                                    <td><span ng-bind="vehiculo.marca"></span></td>
                                    <td><span ng-bind="vehiculo.modelo"></span></td>
                                    <td><span ng-bind="vehiculo.referencia"></span></td>
                                    <td><span ng-bind="vehiculo.nombre"></span></td>
                                    <td><span ng-bind="vehiculo.telefono"></span></td>
                                    <td><span style="cursor: pointer;" ng-click="ctrl.showDetail(vehiculo)"><i class="fa-globe fa fa-2x fa-align-center"></i></span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

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
    <!-- /#wrapper -->

    
</body>

</html>
