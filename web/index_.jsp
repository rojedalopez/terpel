<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Usuario"%>
<% 
response.setHeader("Pragma", "No-chache"); 
response.setHeader("Expires", "0"); 
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache", "no-cache"); 
if(session.getAttribute("user") != null){
    Usuario u = (Usuario)session.getAttribute("user");
    if(u.getRol()==2||u.getRol()==3){
        response.sendRedirect("/empresa/servicios.jsp");
    }
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
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">
    <link href="css/dataTables.bootstrap.min.css" rel="stylesheet" />
    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="css/plugins/morris.css" rel="stylesheet">
    

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
    <script src="js/jquery.js"></script>
    <script src="https://maps.google.com/maps/api/js?libraries=placeses,visualization,drawing,geometry,places&key=AIzaSyCqUEyO3rTumxb0G-oRsyBnZLn4O9VKtiM"></script>
    <script src="js/angular.min.js"></script>
    <script src="js/angular-strap.min.js"></script>
    <script src="js/angular-strap.tpl.min.js"></script>
    <script src="js/angular-animate.js"></script>
    <script src="js/ui-bootstrap-tpls-2.0.0.js"></script>

    <script src="js/bootstrap.min.js"></script>  
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="js/dataTables.responsive.min.js"></script>
    <script src="js/moment.min.js"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <script src="js/angular-eonasdan-datetimepicker.min.js"></script>
    <script src="js/dist/angular-datatables.min.js"></script>
    
    <script type="text/javascript" src="js/date.js"></script>
    <script type="text/javascript" src="js/angular/dirPagination.js"></script>
    <script type="text/javascript" src="js/angular/angular-validator.js"></script>
    <script type="text/javascript" src="js/app.js"></script>
    <script type="text/javascript" src="js/angular/ng-map.min.js"></script>
    <script type="text/javascript" src="js/angular/servicios.js"></script>
    <script type="text/javascript" src="js/angular/camiones.js"></script>
    
    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script>
    
</head>

<body >
    <div >
    <div class="container">
       
        <div class="row">
            <div class="col-md-4 col-md-offset-4 text-center logo-margin ">
                <label style="font-size: 48px; color: white;">Logycus360</label>
                </div>
            <div class="col-md-8 col-md-offset-2">
                <div class="login-panel panel panel-default">                  
                    <div class="panel-heading">
                        <h3 class="panel-title">Selecciona tu modo de ingreso</h3>
                    </div>
                    <div class="panel-body" >
                        
                        <div class="col-lg-6" style="cursor: pointer;" onclick="location.href='conductor/';">
                            <div class="panel panel-primary text-center no-boder">
                                <div class="panel-body green">
                                    <img src="css/images/conductor_icon.png" />
                                    <h3>Conductor</h3>
                                </div>
                                <div class="panel-footer">
                                    <span class="panel-eyecandy-title">Ingresa como conductor
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6" style="cursor: pointer;" onclick="location.href='empresa/';">
                            <div class="panel panel-primary text-center no-boder">
                                <div class="panel-body green">
                                    <img src="css/images/empresa_icon.png" />
                                    <h3>Empresa</h3>
                                </div>
                                <div class="panel-footer">
                                    <span class="panel-eyecandy-title">Ingresa como empresa
                                    </span>
                                </div>
                            </div> 
                        </div>
                    </div>                                
                </div>
                
                
            </div>
        </div>
    </div>
    </div>
</body>

</html>
