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
}/*
else{
    Usuario u = (Usuario) session.getAttribute("user");
    if(u.getTipo()==1){
        response.sendRedirect("../?no tienes permisos para esta pagina.");
    }
}*/
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
        var Asignacion, sendVehiculo, sendConductor;
        $(document).ready(function(){
           Asignacion = $("#Asignacion");
           sendVehiculo = $("#sendVehiculo");
           sendConductor = $("#sendConductor");
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
    
    <div style="height: 600px;" ng-controller="ConfEquiposController as ctrl">

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
                            <a href="solicitudes.jsp"><i class="fa fa-fw fa-truck"></i> Solicitudes</a>
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
                                <i class="fa fa-dashboard"></i> Inventario de vehiculos, conductores y asociaciones
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default tabs">
                            <ul class="nav nav-tabs nav-justified">
                                <li><a href="../#tab1" data-toggle="tab">Vehiculos y conductores asociados</a></li>
                                <li class="active"><a href="../#tab2" data-toggle="tab" >Vehiculos</a></li>
                                <li><a href="../#tab3" data-toggle="tab" >Conductores</a></li>
                            </ul>
                            <div class="panel-body tab-content">
                                <div class="tab-pane" id="tab1">
                                    <div class="panel panel-default">
                                        <div class="panel-body">                                                                        
                                            <div class="row">
                                                <table class="table table-striped nowrap table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptionsRegs" id="dataTables-regs">
                                                    <thead>
                                                        <tr>
                                                            <th>Placa</th>
                                                            <th>Conductor</th>
                                                            <th>Fecha asignacion</th>
                                                            <th><button ng-click="ctrl.enviarChanges()" ng-disabled="ctrl.changes.length<1">Cambios</button></th>
                                                            <th><button ng-click="ctrl.enviarDisponibles()" ng-disabled="ctrl.dispos.length<1">Disponibles</button></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr ng-repeat="equipoconductor in ctrl.equiposconductores">
                                                            <td>
                                                                <span ng-bind="equipoconductor.placa"></span>
                                                            </td>
                                                            <td>
                                                                <select ng-disabled="equipoconductor.en_servicio === '1'" class="form-control" 
                                                                        ng-options="conductor.codigo as conductor.n_completo for conductor in ctrl.conductores" 
                                                                        ng-model="equipoconductor.conductor" ng-change="ctrl.cambiarConductor(equipoconductor)">
                                                                    <option value="">Seleccione conductor</option>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <span ng-bind="equipoconductor.fecha_desde"></span>
                                                            </td>
                                                            <td><input type="checkbox" ng-disabled="equipoconductor.en_servicio === '1'" ng-model="equipoconductor.editar" ng-change="ctrl.addChanges(equipoconductor)"/></td>
                                                            <td><input type="checkbox" ng-disabled="equipoconductor.en_servicio === '1'" ng-model="equipoconductor.disponible" ng-change="ctrl.addDisponibles(equipoconductor)"/></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane active" id="tab2">
                                    <div class="panel panel-default">
                                        <div class="panel-body">                                                                        
                                            <div class="row">
                                                <a ng-click="ctrl.nuevoVehiculo()">Nuevo vehiculo...</a>
                                                <table class="table table-striped table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptionsAsign" id="dataTables-asign">
                                                    <thead>
                                                        <tr>
                                                            <th>Placa</th>
                                                            <th>Marca</th>
                                                            <th>Modelo</th>
                                                            <th>Referencia</th>
                                                            <th>T. Trailer</th>
                                                            <th>Trailer</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr ng-repeat="equipo in ctrl.equipos">
                                                            <td>{{equipo.placa}}</td>
                                                            <td>{{equipo.marca}}</td>
                                                            <td>{{equipo.modelo}}</td>
                                                            <td>{{equipo.referencia}}</td>
                                                            <td>{{equipo.remolque}}</td>
                                                            <td>{{equipo.trailer}}</td>
                                                            <td><button class="btn btn-xs btn-info" ng-click="ctrl.verVehiculo(equipo)">Detalles</button></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="tab3">
                                    <div class="panel panel-default">
                                        <div class="panel-body">                                                                        
                                            <div class="row">
                                                <a ng-click="ctrl.nuevoConductor()">Nuevo conductor...</a>
                                                <table class="table table-striped table-bordered dt-responsive compact table-hover" width="100%" cellspacing="0" datatable="ng" dt-options="ctrl.dtOptionsTerm" id="dataTables-term">
                                                    <thead>
                                                        <tr>
                                                            <th>Doc.</th>
                                                            <th>Nombre</th>
                                                            <th>Apellido</th>
                                                            <th>Telefono</th>
                                                            <th>Dirección</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr ng-repeat="conductor in ctrl.conductores">
                                                            <td>{{conductor.doc}}</td>
                                                            <td>{{conductor.nombre}}</td>
                                                            <td>{{conductor.apellido}}</td>
                                                            <td>{{conductor.telefono}}</td>
                                                            <td>{{conductor.direccion}}</td>
                                                            <td><button class="btn btn-xs btn-info" ng-click="ctrl.verConductor(conductor)">Detalles</button></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
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
        
        <div class="modal fade" id="sendVehiculo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog  modal-lg" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
              </div>
              <div class="modal-body">
                  <div class="row">
                        <div class="col-md-12">
                            
                            <form class="form-horizontal">
                                <div class="panel-body">                                                                        
                                    
                                    <div class="row">
                                        
                                        <div class="col-md-6">
                                            
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Placa</label>
                                                <div class="col-md-9">                                            
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.placa"/>
                                                    </div>                                            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Tipo de carga</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <select class="form-control" ng-model="ctrl.equipo.n_tipocarga"
                                                                ng-options="tipo.ID as tipo.Value for tipo in ctrl.TipoCarga">
                                                            <option value="">--- Seleccionar tipo carga ---</option>
                                                        </select>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Tipo de equipo</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <select class="form-control select" ng-model="ctrl.equipo.n_tipoequipo" 
                                                        ng-options="equipo.ID as equipo.Value for equipo in ctrl.TipoEquipo">
                                                            <option value="">--- seleccionar tipo equipo ---</option>
                                                        </select>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Licencia equipo</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control"  ng-model="ctrl.equipo.lic_transito"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Remolque</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.trailer"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Licencia remolque</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control"  ng-model="ctrl.equipo.lic_transito_r"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Tipo remolque</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <select class="form-control select" ng-model="ctrl.equipo.n_remolque"
                                                        ng-options="remolque.ID as remolque.Value for remolque in ctrl.Remolques">
                                                            <option value="">--- Seleccionar tipo remolque ---</option>
                                                        </select>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Capacidad</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control"  ng-model="ctrl.equipo.capacidad"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Tipo unidad</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <select class="form-control select" ng-model="ctrl.equipo.unidad">
                                                            <option value="">--- Seleccionar unidad ---</option>
                                                            <option value="GAL">Galón</option>
                                                            <option value="TON">Tonelada</option>
                                                        </select>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Marca</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control"  ng-model="ctrl.equipo.marca"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Modelo</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control"  ng-model="ctrl.equipo.modelo"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Referencia</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control"  ng-model="ctrl.equipo.referencia"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                        </div>
                                        <div class="col-md-6">
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Poliza seguros</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.poliza"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Compañia</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.compania"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Exp. poliza</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.exp_poliza"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Venc. poliza</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.vence_poliza"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Poliza HC</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.poliza_hc"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Compañia</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.compania_hc"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Exp. poliza</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.exp_poliza_hc"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Venc. poliza</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.vence_poliza_hc"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Soat</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.soat"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Exp. soat</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.exp_soat"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Venc. soat</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.vence_soat"/>
                                                    </div>            
                                                </div>
                                            </div>                                            
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Tecnomecanica</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.equipo.tecno"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Exp. tecnomecanica</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.exp_tecno"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Venc. tecnomecanica</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.equipo.vence_tecno"/>
                                                    </div>            
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>

                                </div>
                            </form>
                            
                        </div>
                    </div>                    
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" ng-click="ctrl.enviarVehiculo()">Save changes</button>
              </div>
            </div>
          </div>
        </div>
        
        
        <div class="modal fade" id="sendConductor" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog  modal-lg" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
              </div>
              <div class="modal-body">
                  <div class="row">
                        <div class="col-md-12">
                            
                            <form class="form-horizontal">
                                <div class="panel-body">                                                                        
                                    
                                    <div class="row">
                                        
                                        <div class="col-md-6">
                                            
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Tipo de documento</label>
                                                <div class="col-md-9">                                            
                                                    <div class="input-group">
                                                        <select class="form-control" ng-model="ctrl.conductor.tipo_doc"
                                                                ng-options="doc.ID as doc.Value for doc in ctrl.TipoDoc">
                                                            <option value="">--- Seleccione tipo doc ---</option>
                                                        </select>
                                                    </div>                                            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Documento</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.conductor.doc"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">N° licencia</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.conductor.lic"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Exp. licencia</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.conductor.exp_lic"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Venc. licencia</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="date" class="form-control" ng-model="ctrl.conductor.vence_lic"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            
                                        </div>
                                        <div class="col-md-6">
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Nombres</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.conductor.nombre"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Apellidos</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.conductor.apellido"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Telefono</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.conductor.telefono"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Direccion</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.conductor.direccion"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Mail</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" ng-model="ctrl.conductor.mail"/>
                                                    </div>            
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">                                        
                                                <label class="col-md-3 control-label">Asignar Contraseña</label>
                                                <div class="col-md-9 col-xs-12">
                                                    <div class="input-group">
                                                        <input type="password" class="form-control" ng-model="ctrl.conductor.contrasena"/>
                                                    </div>            
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>

                                </div>
                            </form>
                            
                        </div>
                    </div>                    
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" ng-click="ctrl.sendConductor()">Enviar cambios</button>
              </div>
            </div>
          </div>
        </div>
        
    </div>
    <!-- /#wrapper -->

    
</body>

</html>
