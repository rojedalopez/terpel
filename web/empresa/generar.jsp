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
    <link rel="stylesheet" href="http://mgcrea.github.io/angular-strap/styles/libs.min.css">
    <link rel="stylesheet" href="http://mgcrea.github.io/angular-strap/styles/docs.min.css">
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
    
    <div id="wrapper" style="height: 600px;" ng-controller="SolicitudController as ctrl">

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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu message-dropdown">
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>John Smith</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>John Smith</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>John Smith</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-footer">
                            <a href="#">Read All New Messages</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu alert-dropdown">
                        <li>
                            <a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">View All</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="servicios.jsp"><i class="fa fa-fw fa-plus"></i> Servicios</a>
                        </li>
                        <li>
                            <a href="camiones.jsp"><i class="fa fa-fw fa-truck"></i> En ruta</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
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
                <form role="form" class="nav navbar-nav side-nav form-group-sm" angular-validator-submit="ctrl.sendServicio()" name="solicitud" 
                      angular-validator novalidate style="padding: 5px; color: gray;">
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
                                            
                                            <input places-auto-complete
                                            ng-model="ctrl.servicio.nameOrigin"
                                            component-restrictions="{country:'co'}"
                                            on-place-changed="ctrl.placeChangedOrigin()" 
                                            class="form-control"
                                            placeholder="Ingrese Origen" ng-disabled="ctrl.enviado"
                                            type="text" name="nameOrigin"
                                            clase="text_valid"
                                            validator = "ctrl.lengthValidator(ctrl.servicio.nameOrigin, 8) === true"
                                            invalid-message = "ctrl.lengthValidator(ctrl.servicio.nameOrigin, 8)"
                                            required-message="'El campo no puede estar vacio'" 
                                            required/>
                                                
                                        </div>
                                        <div class="form-group">
                                            <label>Punto de descargue:</label>
                                            <input places-auto-complete 
                                            ng-model="ctrl.servicio.nameDestination"
                                            component-restrictions="{country:'co'}"
                                            on-place-changed="ctrl.placeChangedDestination()" 
                                            class="form-control"
                                            placeholder="Ingrese Destino" ng-disabled="ctrl.enviado"
                                            type="text" name="nameDestination"
                                            clase="text_valid"
                                            validator = "ctrl.lengthValidator(ctrl.servicio.nameDestination, 8) === true"
                                            invalid-message = "ctrl.lengthValidator(ctrl.servicio.nameDestination, 8)"
                                            required-message="'El campo no puede estar vacio'" 
                                            required/>
                                        </div>
                                        <div class="form-group">
                                            <label>Tipo de carga</label>
                                            <select class="form-control" ng-model="ctrl.servicio.carga" 
                                            ng-options="Tipo.ID as Tipo.Value for Tipo in ctrl.TipoCarga" ng-disabled="ctrl.enviado" ng-change="ctrl.cambiarCarga(ctrl.servicio.carga)"
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
                                            <div class="input-group input-group-sm date" 
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
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse4" style="text-decoration: none; color: #808080;">
                                      <h4 class="panel-title">
                                            Pago
                                      </h4>
                                    </a>
                                </div>
                                <div id="collapse4" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Valor del flete:</label>
                                            <div class='date'>
                                                <input type='number' class="form-control" ng-model="ctrl.servicio.flete" ng-disabled="ctrl.enviado"
                                                    name="flete" clase="text_valid" placeholder="$"
                                                    invalid-message = "'Debe ingresar un numero valido'"
                                                    required-message="'El campo no puede estar vacio'" 
                                                    required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Notas:</label>
                                            <textarea class="form-control"  rows="3" ng-model="ctrl.servicio.nota_pago" style="resize:false;" ng-disabled="ctrl.enviado"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <div id="alerta-submit"></div>
                    <div style="text-align: right;">
                        <button type="submit" class="btn btn-success" ng-disabled="ctrl.enviado">Enviar Solicitud</button>
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
                                                max-length-html="Seleccionados" ng-change="ctrl.cambiarRemolque()" data-multiple="1" max-length="1" placeholder="--- Tipo Remolque ---" all-none-buttons="true"
                                                bs-options="Remolque.ID as Remolque.Value for Remolque in ctrl.Remolques" bs-select>
                                            Action <span class="caret"></span>
                                          </button>
                                    </div>
                                    <div style="margin: 7px;">
                                        <select class="form-control" ng-model="ctrl.mapa.carga" ng-disabled="(ctrl.Cargas.length==0)?true:false"
                                        ng-options="Carga.ID as Carga.Value for Carga in ctrl.Cargas" >
                                            <option value="">--- Carga ---</option>
                                        </select>
                                    </div>
                                </custom-control>
                                <directions
                                draggable="false"
                                travel-mode="DRIVING"
                                origin="{{ctrl.placeO.geometry.location}}"
                                destination="{{ctrl.placeD.geometry.location}}" suppressMarkers='true'>
                                <marker id='{{vehiculo.cod}}' position="{{vehiculo.position}}" ng-repeat="vehiculo in ctrl.vehiculos"
                                on-click="ctrl.showDetail(vehiculo)" icon="../{{vehiculo.icono}}" reload></marker>
                                <info-window id="foo-iw">
                                    <div ng-non-bindable="">
                                      placa: {{ctrl.vehiculo.placa}}<br/>
                                      ult reporte : {{ctrl.vehiculo.ult_reporte}}<br/>
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
