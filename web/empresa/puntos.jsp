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

    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
    <link href="../css/dataTables.bootstrap.min.css" rel="stylesheet" />
    <!-- Custom CSS -->
    <link href="../css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../css/plugins/morris.css" rel="stylesheet">
    

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
    <script type="text/javascript" src="../js/angular/controles.js"></script>
    <script type="text/javascript" src="../js/angular/camiones.js"></script>
    
    <!-- Morris Charts JavaScript -->
    <script src="../js/plugins/morris/raphael.min.js"></script>
    <script src="../js/plugins/morris/morris.min.js"></script>
    <script src="../js/plugins/morris/morris-data.js"></script>


</head>

<body ng-app="myApp" class="ng-cloak">

    <div ng-controller="ConfPuntosController as ctrl">

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
                <a class="navbar-brand" href="../index.html">SB Admin</a>
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
                            <a href="servicios.jsp"><i class="fa fa-fw fa-truck"></i> Servicios Activos</a>
                        </li>
                        <li>
                            <a href="programada.jsp"><i class="fa fa-fw fa-calendar"></i> Calendario Cont.</a>
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
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="../index.html">Dashboard</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-edit"></i> Forms
                            </li>
                        </ol>
                    </div>
                </div>
                
                <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-default tabs">
                                <ul class="nav nav-tabs nav-justified">
                                    <li class="active"><a href="../#tab1" data-toggle="tab">Puntos</a></li>
                                    <li><a href="../#tab2" data-toggle="tab" ng-click="ctrl.listaAllPuntos()">Zonas</a></li>
                                    <li><a href="../#tab3" data-toggle="tab" ng-click="ctrl.listaAllPuntos()">Bahias</a></li>
                                </ul>
                                <div class="panel-body tab-content">
                                    <div class="tab-pane active" id="tab1">
                                        <form class="form-horizontal">
                                            <div class="panel panel-default">
                                                
                                                <div class="panel-body">                                                                        

                                                    <div class="row">

                                                        <div class="col-md-6">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <h3 class="panel-title">Puntos</h3>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div class="form-group" >
                                                                        <label class="col-md-3 control-label">Lista de puntos:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group" style="z-index: 9999;">
                                                                                <span class="input-group-addon"><span class="fa fa-home"></span></span>
                                                                                <select class="form-control select" ng-model="ctrl.punto.id" ng-change="ctrl.selectPuntoDet(ctrl.punto.id)"
                                                                                        ng-options="punto.id as punto.desc for punto in ctrl.puntos" >
                                                                                    <option value="">Crear punto</option>
                                                                                </select>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>
                                                                    
                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Codigo:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group">
                                                                                <span class="input-group-addon"><span class="fa fa-pencil"></span></span>
                                                                                <input type="text" class="form-control" ng-model="ctrl.punto.id" ng-disabled="ctrl.seleccion"/>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>
                                                                    
                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Descripcion:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group">
                                                                                <span class="input-group-addon"><span class="fa fa-pencil"></span></span>
                                                                                <input type="text" class="form-control" ng-model="ctrl.punto.desc"/>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Nota</label>
                                                                        <div class="col-md-9 col-xs-12">                                            
                                                                            <textarea class="form-control" rows="3" ng-model="ctrl.punto.nota"></textarea>
                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">
                                                                            <span style="color: red;"><i>
                                                                                    {{ctrl.mensaje}}
                                                                            </i></span>
                                                                        <button class="btn btn-primary pull-right" ng-click="ctrl.sendPunto()">Submit</button>
                                                                    </div>
                                                                    
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">                        
                                                            <!-- START GOOGLE WORLD MAP -->
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <h3 class="panel-title">Google World Map</h3>
                                                                </div>
                                                                <div class="panel-body panel-body-map">
                                                                    <div id="google_world_map" style="width: 100%; height: 300px;">
                                                                        <ng-map center="10.97978516762394,-74.80676651000977" zoom="10" default-style="true" on-click="ctrl.placeMarker()">
                                                                            <custom-control id="go-home" position="TOP_RIGHT" index="1">
                                                                              <div style="margin: 7px 7px 0 7px; ">
                                                                                  <input places-auto-complete component-restrictions="{country:'co'}" 
                                                                                    on-place-changed="ctrl.placeChanged()"
                                                                                    type="text" class="form-control" placeholder="Escriba lugar del punto..."/>
                                                                                </div>
                                                                            </custom-control>
                                                                            <marker position="{{marker.position}}" ng-repeat="marker in ctrl.markers"></marker>
                                                                          </ng-map>                                                                      
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <!-- END GOOGLE WORLD MAP-->
                                                        </div> 

                                                    </div>

                                                </div>
                                                
                                            </div>
                                        </form>
                                    </div>
                                    <div class="tab-pane" id="tab2">
                                        <form class="form-horizontal">
                                            <div class="panel panel-default">
                                                
                                                <div class="panel-body">                                                                        

                                                    <div class="row">

                                                        <div class="col-md-6">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <h3 class="panel-title">Lista de puntos</h3>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div class="list-group border-bottom">
                                                                        <input type="text" ng-model="ctrl.q" class="form-control"/>
                                                                        <div class="col-lg-12" style="cursor: pointer;"  ng-repeat="punto in ctrl.puntos | filter:ctrl.q">
                                                                            <div class="panel panel-primary2 alert alert-success" ng-click="ctrl.selectPunto(punto.id)">
                                                                                {{punto.desc}}
                                                                            </div>
                                                                        </div>
                                                                    </div>                              
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <h3 class="panel-title">Puntos</h3>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Zonas:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group" style="z-index: 9999;">
                                                                                <span class="input-group-addon"><span class="fa fa-home"></span></span>
                                                                                <select class="form-control select" ng-model="ctrl.id_selectzona" ng-change="ctrl.selectZonaDet(ctrl.id_selectzona)"
                                                                                        ng-options="zona.id as zona.desc for zona in ctrl.zonas">
                                                                                    <option value="">Crear zona</option>
                                                                                </select>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Descripcion:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group">
                                                                                <span class="input-group-addon"><span class="fa fa-pencil"></span></span>
                                                                                <input type="text" class="form-control" ng-model="ctrl.zona.desc"/>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Nota</label>
                                                                        <div class="col-md-9 col-xs-12">                                            
                                                                            <textarea class="form-control" rows="3" ng-model="ctrl.zona.nota"></textarea>
                                                                        </div>
                                                                    </div>


                                                                    <div class="footer">
                                                                        <button class="btn btn-primary pull-right" ng-click="ctrl.sendZona()">Submit</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div> 

                                                    </div>

                                                </div>
                                                
                                            </div>
                                        </form>
                                    </div>
                                    <div class="tab-pane" id="tab3">
                                        <form class="form-horizontal">
                                            <div class="panel panel-default">
                                                
                                                <div class="panel-body">                                                                        

                                                    <div class="row">

                                                        <div class="col-md-6">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <h3 class="panel-title">Lista de puntos</h3>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div class="list-group border-bottom">
                                                                        <input type="text" ng-model="ctrl.q" class="form-control"/>
                                                                        <div class="col-lg-12" style="cursor: pointer;" dir-paginate="punto in ctrl.puntos|itemsPerPage:ctrl.itemsPerPage" total-items="ctrl.total_count">
                                                                            <div class="panel panel-primary2 alert alert-success" ng-click="ctrl.selectPunto(punto.id)">
                                                                                {{punto.desc}}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <h3 class="panel-title">Puntos</h3>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Zonas:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group" style="z-index: 9999;">
                                                                                <span class="input-group-addon"><span class="fa fa-home"></span></span>
                                                                                <select class="form-control select" ng-model="ctrl.selectzona" ng-change="ctrl.selectZona(ctrl.selectzona)"
                                                                                        ng-options="zona.id as zona.desc for zona in ctrl.zonas">
                                                                                    <option value="">Seleccione zona</option>
                                                                                </select>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Bahias:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group" style="z-index: 9999;">
                                                                                <span class="input-group-addon"><span class="fa fa-home"></span></span>
                                                                                <select class="form-control select" ng-model="ctrl.bahia.selectbahia" ng-change="ctrl.selectBahiaDet(ctrl.bahia.selectbahia)"
                                                                                        ng-options="bahia.id as bahia.desc for bahia in ctrl.bahias">
                                                                                    <option value="">Crear bahia</option>
                                                                                </select>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Descripcion:</label>
                                                                        <div class="col-md-9">                                            
                                                                            <div class="input-group">
                                                                                <span class="input-group-addon"><span class="fa fa-pencil"></span></span>
                                                                                <input type="text" class="form-control" ng-model="ctrl.bahia.desc"/>
                                                                            </div>                                            
                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label class="col-md-3 control-label">Nota</label>
                                                                        <div class="col-md-9 col-xs-12">                                            
                                                                            <textarea class="form-control" rows="3" ng-model="ctrl.bahia.nota"></textarea>
                                                                        </div>
                                                                    </div>


                                                                    <div class="footer">
                                                                        <button class="btn btn-primary pull-right" ng-click="ctrl.sendBahia()">Submit</button>
                                                                    </div>
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
