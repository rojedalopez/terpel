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
        th, td {
            padding: 5px;
            text-align: left;
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
    
    <div id="wrapper" style="height: 600px;" ng-controller="ServActivosController as ctrl">

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
                            <a href="solicitudes.jsp"><i class="fa fa-fw fa-automobile"></i> Solicitudes</a>
                        </li>
                        <li>
                            <a href="servicios.jsp"><i class="fa fa-fw fa-truck"></i> Servicios en ruta</a>
                        </li>
                        <li>
                            <a href="vehiculos"><i class="fa fa-fw fa-plus"></i> Vehiculos</a>
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
                    <div class="form-group" style="color: white; text-align: center;">
                        <label><u>Generador de carga</u></label>
                    </div>
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
                                                       name="flete" readonly placeholder="$"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Tipo de cargue:</label>
                                            <select class="form-control" ng-model="ctrl.servicio.carga" 
                                            ng-options="Tipo.id as Tipo.desc for Tipo in ctrl.cargues" ng-disabled="ctrl.enviado"
                                            name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                                                <option value="">--- Seleccione Tipo ---</option>
                                            </select>
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
                            <ng-map height="100%" center="10.97978516762394,-74.80676651000977"  zoom="10"
                            map-type-control="true" map-type-control-options="{style:'HORIZONTAL_BAR', position:'BOTTOM_CENTER'}">
                                <custom-control id="home" position="TOP_RIGHT" index="1">
                                    <div style="margin: 7px 7px 0 7px; ">
                                        <table class="table table-bordered compact">
                                            <thead>
                                            <tr>
                                                <td colspan="2" style="background-color: #E8E8E8;">
                                                    <center>
                                                        RESUMEN DE RUTA
                                                    </center>
                                                </td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td style="background-color: #E8E8E8;">Equipos en ruta:</td>
                                                    <td style="background-color: #F5F5F5;">{{ctrl.servicios.length}}</td>
                                                </tr>
                                                <tr>
                                                    <td style="background-color: #E8E8E8;">Capacidad cargada:</td>
                                                    <td style="background-color: #F5F5F5;">{{ctrl.cap_cargada}} GAL</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </custom-control>
                                <marker id='{{servicio.servicio}}' position="{{servicio.lat_actual}}, {{servicio.lng_actual}}" ng-repeat="servicio in ctrl.servicios"
                                on-click="ctrl.showDetail(servicio)" icon="../css/images/ic_truckicon_disp.png"></marker>
                                <info-window id="foo-iw">
                                    <div ng-non-bindable="" class="table compact nowrap table-striped" style="overflow-x: hidden;">
                                        <table style="width:100%;height: 100%;padding: 5px;">
                                            <tr>
                                                <th colspan="2" style="background-color: #E8E8E8;text-align: center;">DATOS DEL SERVICIO</th>
                                            </tr>
                                            
                                            <tr>
                                              <td style="background-color: #E8E8E8;">VIGENCIA LICENCIA</td>
                                              <td>{{ctrl.servicio.exp_lic}} - {{ctrl.servicio.vence_lic}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">VIGENCIA LICENCIA</td>
                                              <td>{{ctrl.servicio.exp_lic}} - {{ctrl.servicio.vence_lic}}</td>
                                            </tr>
                                            <tr>
                                                <th colspan="2" style="background-color: #E8E8E8;text-align: center;">DATOS DEL CONDUCTOR</th>
                                            </tr>
                                            <tr>
                                                <td colspan="2" style="background-color:#000000;padding: 4px;">
                                                     <center><img width="150px" src="{{ctrl.servicio.url_conductor}}"/></center>
                                                </td> 
                                            </tr>
                                            <tr>
                                                <td style="background-color: #E8E8E8;">NOMBRE</td>
                                                <td>{{ctrl.servicio.nombre_completo}}</td>
                                            </tr>
                                            <tr>
                                                <td style="background-color: #E8E8E8;">DOCUMENTO</td>
                                                <td>{{ctrl.servicio.doc}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">NO. LICENCIA</td>
                                              <td>{{ctrl.servicio.licencia}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">VIGENCIA LICENCIA</td>
                                              <td>{{ctrl.servicio.exp_lic}} - {{ctrl.servicio.vence_lic}}</td>
                                            </tr>
                                            <tr>
                                                <td style="background-color: #E8E8E8;">TELEFONO</td>
                                                <td>{{ctrl.servicio.telefono}}</td>
                                            </tr>
                                            <tr>
                                                <td style="background-color: #E8E8E8;">DIRECCION</td>
                                                <td>{{ctrl.servicio.direccion}}</td>
                                            </tr>
                                            <tr>
                                                <th colspan="2" style="background-color: #E8E8E8;text-align: center;">DATOS DEL VEHICULO</th>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">TIPO EQUIPO</td>
                                              <td>{{ctrl.servicio.tipo_equipo}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">PLACA</td>
                                              <td>{{ctrl.servicio.placa}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">LIC. DE TRANSITO</td>
                                              <td>{{ctrl.servicio.lic_transito}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">MARCA</td>
                                              <td>{{ctrl.servicio.marca}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">REFERENCIA</td>
                                              <td>{{ctrl.servicio.referencia}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">MODELO</td>
                                              <td>{{ctrl.servicio.modelo}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">PLACA REMOLQUE</td>
                                              <td>{{ctrl.servicio.placa_rem}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">TIPO REMOLQUE</td>
                                              <td>{{ctrl.servicio.tipo_remolque}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">LIC. TRANSITO REMOLQUE</td>
                                              <td>{{ctrl.servicio.lic_transito_r}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">POLIZA DE SEGURO</td>
                                              <td>{{ctrl.servicio.poliza}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">COMPAÑIA ASEGURADORA</td>
                                              <td>{{ctrl.servicio.comp}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">VIGENCIA POLIZA</td>
                                              <td>{{ctrl.servicio.exp_poliza}} - {{ctrl.servicio.vence_poliza}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">POLIZA DE SEGURO H.C.</td>
                                              <td>{{ctrl.servicio.poliza_hc}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">COMPAÑIA ASEGURADORA</td>
                                              <td>{{ctrl.servicio.comp_hc}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">VIGENCIA POLIZA H.C.</td>
                                              <td>{{ctrl.servicio.exp_poliza_hc}} - {{ctrl.servicio.vence_poliza_hc}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">SOAT</td>
                                              <td>{{ctrl.servicio.soat}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">VIGENCIA SOAT</td>
                                              <td>{{ctrl.servicio.exp_soat}} - {{ctrl.servicio.vence_soat}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">TECNOMECANICA</td>
                                              <td>{{ctrl.servicio.tecno}}</td>
                                            </tr>
                                            <tr>
                                              <td style="background-color: #E8E8E8;">VIGENCIA TECNOMECANICA</td>
                                              <td>{{ctrl.servicio.exp_tecno}} - {{ctrl.servicio.vence_tecno}}</td>
                                            </tr>
                                        </table>
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
        
    </div>
    <!-- /#wrapper -->

    
</body>

</html>
