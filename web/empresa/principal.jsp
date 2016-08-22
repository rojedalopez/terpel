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

    <!-- load Galleria -->

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
    
    <script>
        var Asignacion;
        $(document).ready(function(){
           Asignacion = $("#Asignacion"); 
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
    
    <div style="height: 600px;" ng-controller="EnturneController as ctrl">

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

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Sistema de enturnamiento
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default tabs">
                            <ul class="nav nav-tabs nav-justified">
                                <li class="active"><a href="../#tab1" data-toggle="tab" ng-click="ctrl.changeStopTime(1)">Tickets sin asignación</a></li>
                                <li><a href="../#tab2" data-toggle="tab" ng-click="ctrl.changeStopTime(2)">Tickets Asignados y Activos</a></li>
                                <li><a href="../#tab3" data-toggle="tab" ng-click="ctrl.changeStopTime(3)">Tickets Finalizados</a></li>
                            </ul>
                            <div class="panel-body tab-content">
                                <div class="tab-pane active" id="tab1">
                                    <div class="panel panel-default">
                                        <div class="panel-body">                                                                        
                                            <div class="row">
                                                <table class="table table-striped table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptionsRegs" id="dataTables-regs">
                                                    <thead>
                                                        <tr>
                                                            <th>Ticket</th>
                                                            <th>Operacion</th>
                                                            <th>Tipo carga</th>
                                                            <th>Placa veh.</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr dir-paginate="ticket in ctrl.ticketsR | itemsPerPage:ctrl.itemsPerPageR: 'Regs'" pagination-id="Regs" total-items="ctrl.total_countR">
                                                            <td>{{ticket.ticket}}</td>
                                                            <td>{{ticket.operacion}}</td>
                                                            <td>{{ticket.tipo_cargue}}</td>
                                                            <td>{{ticket.placa}}</td>
                                                            <td><button class="btn btn-xs btn-info" ng-click="ctrl.asignarTickets(ticket)">Detalles</button></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <dir-pagination-controls
                                                    max-size="8"
                                                    direction-links="true"
                                                    boundary-links="true" 
                                                    on-page-change="ctrl.getDataRegistradas(newPageNumber)"
                                                    pagination-id="Regs">
                                                </dir-pagination-controls>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="tab2">
                                    <div class="panel panel-default">
                                        <div class="panel-body">                                                                        
                                            <div class="row">
                                                <table class="table table-striped table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptionsAsign" id="dataTables-asign">
                                                    <thead>
                                                        <tr>
                                                            <th>Turno</th>
                                                            <th>Operacion</th>
                                                            <th>Tipo carga</th>
                                                            <th>Fecha asignada</th>
                                                            <th>Zona</th>
                                                            <th>Bahia</th>
                                                            <th>Placa veh.</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr dir-paginate="ticket in ctrl.ticketsA | itemsPerPage:ctrl.itemsPerPageA: 'Asign'" pagination-id="Asign" total-items="ctrl.total_countT">
                                                            <td>{{ticket.turno}}</td>
                                                            <td>{{ticket.operacion}}</td>
                                                            <td>{{ticket.tipo_cargue}}</td>
                                                            <td>{{ctrl.formatDate(ticket.fecha_enturnado) | date:"yyyy/MM/dd hh:mma"}}</td>
                                                            <td>{{ticket.nzona}}</td>
                                                            <td>{{ticket.nbahia}}</td>
                                                            <td>{{ticket.placa}}</td>
                                                            <td><button class="btn btn-xs btn-info" ng-click="ctrl.reasignarTickets(ticket)">Detalles</button></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <dir-pagination-controls
                                                    max-size="8"
                                                    direction-links="true"
                                                    boundary-links="true" 
                                                    on-page-change="ctrl.getDataAsignadas(newPageNumber)"
                                                    pagination-id="Asign">
                                                </dir-pagination-controls>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="tab3">
                                    <div class="panel panel-default">
                                        <div class="panel-body">                                                                        
                                            <div class="row">
                                                <table class="table table-striped table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptionsTerm" id="dataTables-term">
                                                    <thead>
                                                        <tr>
                                                            <th>Ticket</th>
                                                            <th>Operacion</th>
                                                            <th>Tipo carga</th>
                                                            <th>Placa veh.</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr dir-paginate="ticket in ctrl.ticketsT | itemsPerPage:ctrl.itemsPerPageT: 'Term'" pagination-id="Term" total-items="ctrl.total_countT">
                                                            <td>{{ticket.ticket}}</td>
                                                            <td>{{ticket.operacion}}</td>
                                                            <td>{{ticket.tipo_cargue}}</td>
                                                            <td>{{ticket.placa}}</td>
                                                            <td><button class="btn btn-xs btn-info" ng-click="ctrl.asignarTickets(ticket)">Detalles</button></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <dir-pagination-controls
                                                    max-size="8"
                                                    direction-links="true"
                                                    boundary-links="true" 
                                                    on-page-change="ctrl.getDataTerminadas(newPageNumber)"
                                                    pagination-id="Term">
                                                </dir-pagination-controls>
                                            </div>
                                        </div>
                                    </div>
                                </div>                        
                            </div>
                        </div>
                    </div>
                </div>
               
                <!-- /.row -->

            </div>
            <!-- /.container-fluid -->

        </div>
        
        <div id="Asignacion" class="modal fade" role="dialog">
            <div class="modal-dialog">

              <!-- Modal content-->
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Posicion del vehiculo {{ctrl.ticket.ticket}}</h4>
                </div>
                <div class="modal-body">
                    <form role="form" angular-validator-submit="ctrl.sendAsignacion()" name="solicitud" 
                  angular-validator novalidate>
                <div class="form-group">
                    <div class="form-group">
                        <label>Zona para operación:</label>
                        <select class="form-control" ng-model="ctrl.ticket.zona" 
                        ng-options="zona.id as zona.desc for zona in ctrl.zonas" ng-change="ctrl.selectZonas(ctrl.ticket.zona)"
                        name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                            <option value="">--- Seleccione Zona ---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Bahia de operación:</label>
                        <select class="form-control" ng-model="ctrl.ticket.bahia" 
                        ng-options="bahia.id as bahia.desc for bahia in ctrl.bahias" 
                        name="carga" clase="text_valid" required-message="'Debe seleccionar una opcion'" required>
                            <option value="">--- Seleccione Bahia ---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Fecha asignada:</label>
                        <div class="input-group input-group-sm date" 
                             datetimepicker ng-model="ctrl.fecha_enturnado" options="ctrl.options" ng-disabled="ctrl.reasignar">
                              <input type="text" class="form-control" ng-disabled="ctrl.reasignar"/>
                              <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                              </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Nota</label>
                        <div class='input-group date'>
                            <textarea rows="3" cols="80" class="form-control" ng-model="ctrl.ticket.nota" ></textarea>
                        </div>
                    </div>
                    <div style="text-align: right;">
                        <button type="submit" class="btn btn-success">Enviar Asignacion</button>
                        <button type="button" class="btn btn-default">Limpiar</button>
                    </div>
                </div>
            </form>
                </div>
            </div>
          </div>
            </div>
        
    </div>
    <!-- /#wrapper -->

    
</body>

</html>
