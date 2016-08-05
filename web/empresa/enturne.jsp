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
                            <a href="generar.jsp"><i class="fa fa-fw fa-plus"></i> Generación</a>
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
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-money fa-fw"></i> Turnos en asignados y en proceso</h3>
                            </div>
                            <div class="panel-body">
                                <div>
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
                                            <tr dir-paginate="ticketA in ctrl.ticketsA | itemsPerPage:ctrl.itemsPerPageA: 'Asign'" pagination-id="Asign" total-items="ctrl.total_countT">
                                                <td>{{ticketA.turno}}</td>
                                                <td>{{ticketA.operacion}}</td>
                                                <td>{{ticketA.tipo_cargue}}</td>
                                                <td>{{ctrl.formatDate(ticketA.fecha_enturnado) | date:"yyyy/MM/dd hh:mma"}}</td>
                                                <td>{{ticketA.nzona}}</td>
                                                <td>{{ticketA.nbahia}}</td>
                                                <td>{{ticketA.placa}}</td>
                                                <td><button class="btn btn-xs btn-info" ng-click="ctrl.reasignarTickets(ticketA.ticket)">Detalles</button></td>
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
                    
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-money fa-fw"></i> Turnos finalizados</h3>
                            </div>
                            <div class="panel-body">
                                <div>
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
                                            <tr dir-paginate="ticketT in ctrl.ticketsT | itemsPerPage:ctrl.itemsPerPageT: 'Term'" pagination-id="Term" total-items="ctrl.total_countT">
                                                <td>{{ticketT.ticket}}</td>
                                                <td>{{ticketT.operacion}}</td>
                                                <td>{{ticketT.tipo_cargue}}</td>
                                                <td>{{ticketT.placa}}</td>
                                                <td><button class="btn btn-xs btn-info" ng-click="ctrl.asignarTickets(ticketT.ticket)">Detalles</button></td>
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
                    
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-money fa-fw"></i> Turnos en espera</h3>
                            </div>
                            <div class="panel-body">
                                <div>
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
                                            <tr dir-paginate="ticketR in ctrl.ticketsR | itemsPerPage:ctrl.itemsPerPageR: 'Regs'" pagination-id="Regs" total-items="ctrl.total_countR">
                                                <td>{{ticketR.ticket}}</td>
                                                <td>{{ticketR.operacion}}</td>
                                                <td>{{ticketR.tipo_cargue}}</td>
                                                <td>{{ticketR.placa}}</td>
                                                <td><button class="btn btn-xs btn-info" ng-click="ctrl.asignarTickets(ticketR.ticket)">Detalles</button></td>
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
                </div>
                <!-- /.row -->

            </div>
            <!-- /.container-fluid -->

        </div>
        
        
    </div>
    <!-- /#wrapper -->

    
</body>

</html>
