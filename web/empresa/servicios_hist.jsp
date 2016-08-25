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
        #resumen th, #resumen td {
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

    <script>
        var indicadores, infoServicio;
        $(document).ready(function(){
            if (Notification) {
                if (Notification.permission !== "granted") {
                    Notification.requestPermission()
                }
            }
            indicadores = $("#indicadores");
            infoServicio = $("#infoServicio");
        });
    </script>
    
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

<body ng-app="myApp" class="ng-cloak" >
    
    <div id="wrapper" style="height: 600px;" ng-controller="ServHistoricosController as ctrl">

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
                            <a href="programada.jsp"><i class="fa fa-fw fa-calendar"></i> Calendario Cont.</a>
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
                <form role="form" class="nav navbar-nav side-nav form-group-sm" name="solicitud" 
                      novalidate style="padding: 5px; color: gray;">
                        <div class="panel-group" >
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" style="text-decoration: none; color: #808080;">
                                    <h4 class="panel-title">
                                          FILTROS
                                    </h4>
                                  </a>
                                </div>
                                <div id="collapse1" class="panel-collapse collapse in">
                                    <div class="form-group" style="padding: 10px;">
                                        <div class="form-group">
                                            <label>Por Solicitud, Servicio, Placa o Transp.</label>
                                            <div class='date'>
                                                <input type='text' class="form-control" ng-model="ctrl.mapa.q"
                                                       name="flete"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Por Conductor</label>
                                            <div class='date'>
                                                <input type='text' class="form-control" ng-model="ctrl.mapa.conductor"
                                                       name="flete" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Por Punto de cargue:</label>
                                            
                                            <select ng-model="ctrl.mapa.inicio" class="form-control" ng-options="inicio.id as inicio.desc for inicio in ctrl.puntos">
                                                <option value="">--- Seleccione inicio ---</option>
                                            </select>
                                                
                                        </div>
                                        <div class="form-group">
                                            <label>Por Punto de descargue:</label>
                                            <select ng-model="ctrl.mapa.fin" class="form-control" ng-options="fin.id as fin.desc for fin in ctrl.puntos">
                                                <option value="">--- Seleccione fin ---</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Por Tipo de cargue:</label>
                                            <select class="form-control" ng-model="ctrl.mapa.carga" 
                                            ng-options="Tipo.id as Tipo.desc for Tipo in ctrl.cargues" 
                                            name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                                                <option value="">--- Seleccione Tipo ---</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Fecha de inicio:</label>
                                            <div class="input-group input-group-sm date" 
                                                datetimepicker ng-model="ctrl.desde" options="ctrl.options">
                                                  <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                  </span>
                                                  <input type="text" class="form-control" />
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label>Fecha de fin:</label>
                                            <div class="input-group input-group-sm date" 
                                                datetimepicker ng-model="ctrl.hasta" options="ctrl.options">
                                                  <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                  </span>
                                                  <input type="text" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <div id="alerta-submit"></div>
                    <div style="text-align: right;">
                        <button type="button" class="btn btn-success" ng-click="ctrl.buscar()">Buscar</button>
                        <button type="button" class="btn btn-default" ng-click="ctrl.resetForm()">Limpiar</button>
                    </div>
                </form>
                
            </div>
            <!-- /.navbar-collapse -->

        <div id="page-wrapper">

            <div class="container-fluid">
               
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            SERVICIOS FINALIZADOS
                        </h1>
                    </div>
                    <div class="col-lg-12" >
                        <table datatable="ng" dt-options="ctrl.dtOptions" class="table table-striped table-bordered compact" cellspacing="0" width="100%" dt-options="ctrl.dtOptions">
                            <thead>
                                <tr>
                                    <td>Servicio</td>
                                    <td>Cargue</td>
                                    <td>Placa</td>
                                    <td>Placa R.</td>
                                    <td>Conductor</td>
                                    <td>Telefono</td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="servicio in ctrl.servicios">
                                    <td><span ng-bind="servicio.servicio"></span></td>
                                    <td><span ng-bind="servicio.tipo_cargue"></span></td>
                                    <td><span ng-bind="servicio.placa"></span></td>
                                    <td><span ng-bind="servicio.placa_rem"></span></td>
                                    <td><span ng-bind="servicio.nombre_completo"></span></td>
                                    <td><span ng-bind="servicio.telefono"></span></td>
                                    <td><button ng-click="ctrl.abrirModal(servicio)">ind</button></td>
                                    <td><button ng-click="ctrl.abrirModalInfo(servicio)">inf</button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->
        
        <div class="modal fade" id="indicadores" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title">INDICADORES DEL SERVICIO</h4>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered compact">
                        <tr>
                            <th colspan="2" style="background-color: #E8E8E8;text-align: center;">INDICADORES DE DEMORA</th>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">ASIGNACIÓN</td>
                            <td>{{ctrl.servicio.delta_1}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">LLEGADA a CARGUE</td>
                            <td>{{ctrl.servicio.delta_2}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">ENTURNADO</td>
                            <td>{{ctrl.servicio.delta_3}}</td>
                        </tr><tr>
                            <td style="background-color: #E8E8E8;width: 40%;">AVISADO</td>
                            <td>{{ctrl.servicio.delta_4}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">CARGUE</td>
                            <td>{{ctrl.servicio.delta_5}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FIN DE CARGUE</td>
                            <td>{{ctrl.servicio.delta_6}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FIN DE SERVICIO</td>
                            <td>{{ctrl.servicio.delta_7}}</td>
                        </tr>
                    </table>
                    
                    <table class="table table-bordered compact">
                        <tr>
                            <th colspan="2" style="background-color: #E8E8E8;text-align: center;">CALCULAR INDICADORES</th>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA INICIO</td>
                            <td>
                                <select class="form-control" ng-model="ctrl.servicio.f1" 
                                ng-options="Tipo.fecha as Tipo.desc for Tipo in ctrl.servicio.fechas" >
                                    <option value="">--- Seleccione Fecha ---</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA FIN</td>
                            <td>
                                <select class="form-control" ng-model="ctrl.servicio.f2" 
                                ng-options="Tipo.fecha as Tipo.desc for Tipo in ctrl.servicio.fechas" >
                                    <option value="">--- Seleccione Fecha ---</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">RESULTADO</td>
                            <td>
                                <span ng-bind="ctrl.calcularTiempo(ctrl.servicio.f1,ctrl.servicio.f2)"></span>
                            </td>
                        </tr>
                    </table>
                </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                  </div>
              </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
          </div><!-- /.modal -->
        
          <div class="modal fade" id="infoServicio" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title">INFORMACIÓN DEL SERVICIO</h4>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered compact">
                        <tr>
                            <th colspan="2" style="background-color: #E8E8E8;text-align: center;">RESUMEN DE SOLICITUD CUBIERTA</th>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">NO. DE SOLICITUD</td>
                            <td>{{ctrl.servicio.solicitud}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">RUTA</td>
                            <td>{{ctrl.servicio.desc_inicio}} - {{ctrl.servicio.desc_fin}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA MIN DE CARGUE</td>
                            <td>{{ctrl.servicio.min_carg}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA MAX DE DESCARGUE</td>
                            <td>{{ctrl.servicio.max_desc}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA ASIGNACIÓN</td>
                            <td>{{ctrl.servicio.fecha_asignacion}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA LLEGADA A PUNTO DE CARGUE</td>
                            <td>{{ctrl.servicio.fecha_geo}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA ENTURNADO</td>
                            <td>{{ctrl.servicio.fecha_enturnado}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA AVISADO PARA CARGAR</td>
                            <td>{{ctrl.servicio.fecha_avisado}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA INICIO DE CARGUE</td>
                            <td>{{ctrl.servicio.fecha_cargue}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA FIN DE CARGUE</td>
                            <td>{{ctrl.servicio.fecha_fin_cargue}}</td>
                        </tr>
                        <tr>
                            <td style="background-color: #E8E8E8;width: 40%;">FECHA/HORA LLEGADA A PUNTO DE DESCARGUE</td>
                            <td>{{ctrl.servicio.fecha_fin}}</td>
                        </tr>
                    </table>
                    
                    <table class="table table-bordered compact">
                        <tr>
                            <th colspan="2" style="background-color: #E8E8E8;text-align: center;">INFORMACION DE CONDUCTOR</th>
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
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                  </div>
              </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
          </div><!-- /.modal -->
    </div>
    <!-- /#wrapper -->

    
</body>

</html>
