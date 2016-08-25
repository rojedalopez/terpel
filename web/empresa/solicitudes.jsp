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

    <!-- load Galleria -->

    <!-- Custom Fonts -->
    <link href="../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        .google-maps {
            height: 600px;
        }
        .google-maps ng-map{
            height: 100%;
        }
        .noticia-content{
            padding: 15px;
            margin-top: 10px;
            display: flex;
            flex-wrap: wrap;
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
            width:20%;
            height:200px;
        }
        
        .titulo{
            margin-left: 2%;
            width: 68%;
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
    
    <div id="wrapper" style="height: 600px;" ng-controller="SolicitudesActivasController as ctrl">

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
                            <a href="programada.jsp"><i class="fa fa-fw fa-truck"></i> Calendario cont.</a>
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
                <form role="form" class="nav navbar-nav side-nav form-group-sm" style="padding: 5px; color: gray;">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                              PANEL DE BUSQUEDAS
                                        </h4>
                                    </div>
                                <div id="collapse1">
                                    <div class="form-group" style="padding: 10px;">
                                            <div class="form-group">
                                                <label>Estado de solicitud:</label>
                                                <div class='input-group date'>
                                                    <span class="input-group-addon">
                                                        <span class="fa fa-truck"></span>
                                                    </span>
                                                    <select class="form-control" ng-model="ctrl.busqueda.estado" 
                                                    ng-options="Estado.ID as Estado.Value for Estado in ctrl.EstadoSolicitud">
                                                        <option value="">--- Seleccione Estado ---</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Tipo de cargue</label>
                                                <div class='input-group date'>
                                                    <span class="input-group-addon">
                                                        <span class="fa fa-truck"></span>
                                                    </span>
                                                    <select class="form-control" ng-model="ctrl.busqueda.carga" 
                                                    ng-options="cargue.id as cargue.desc for cargue in ctrl.cargues">
                                                        <option value="">--- Seleccione Tipo ---</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Puntos:</label>
                                                <div class='input-group date'>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-map-marker"></span>
                                                    </span>
                                                    <input type="text"
                                                    ng-model="ctrl.busqueda.q"
                                                    class="form-control"
                                                    placeholder="Ingrese Origen o Destino"/>
                                                </div>                        
                                            </div>
                                            <div class="form-group">
                                                <label>Fecha de inicio:</label>
                                                <div class="input-group input-group-sm date" 
                                                    datetimepicker ng-model="ctrl.dcargue" options="ctrl.options">
                                                      <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                      </span>
                                                      <input type="text" class="form-control" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label>Fecha de fin:</label>
                                                <div class="input-group input-group-sm date" 
                                                    datetimepicker ng-model="ctrl.ddescargue" options="ctrl.options">
                                                      <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                      </span>
                                                      <input type="text" class="form-control" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Orden de servicio:</label>
                                                <div class='input-group date'>
                                                    <span class="input-group-addon">
                                                        <span class="fa fa-file-text"></span>
                                                    </span>
                                                    <input type='text' class="form-control" ng-model="ctrl.busqueda.orden"/>
                                                </div>
                                            </div>
                                            <div id="alerta-busqueda"></div>
                                            <div class="form-group">
                                                <button type="button" class="btn btn-success" ng-click="ctrl.getData(1)">Buscar</button>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                </form>
                
            </div>
            <!-- /.navbar-collapse -->

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row" >
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            SOLICITUDES ACTIVAS
                        </h1>
                    </div>

                    <div class="col-lg-12">
                        <table class="table table-striped table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptions" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Estado</th>
                                        <th>Orden de servicio</th>
                                        <th>Cargue</th>
                                        <th>Fecha Cargue</th>
                                        <th>Fecha Descargue</th>
                                        <th>Origen</th>
                                        <th>Destino</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr dir-paginate="servicio in ctrl.servicios|itemsPerPage:ctrl.itemsPerPage" total-items="ctrl.total_count">
                                        <td>{{servicio.id}}</td>
                                        <td>{{servicio.nestado}}</td>
                                        <td>{{servicio.orden}}</td>
                                        <td>{{servicio.n_cargue}}</td>
                                        <td>{{ctrl.formatDate(servicio.cargue) | date:"yyyy/MM/dd hh:mma"}}</td>
                                        <td>{{ctrl.formatDate(servicio.descargue) | date:"yyyy/MM/dd hh:mma"}}</td>
                                        <td>{{servicio.origen}}</td>
                                        <td>{{servicio.destino}}</td>
                                        <td><button class="btn btn-xs btn-info" ng-click="ctrl.verDetalleSol(servicio.id)">Detalles</button></td>
                                    </tr>
                                </tbody>
                            </table>
                            <dir-pagination-controls
                                max-size="8"
                                direction-links="true"
                                boundary-links="true" 
                                on-page-change="ctrl.getData(newPageNumber)">
                            </dir-pagination-controls>
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
