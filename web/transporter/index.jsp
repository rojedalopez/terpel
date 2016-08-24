<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
response.setHeader("Pragma", "No-chache"); 
response.setHeader("Expires", "0"); 
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache", "no-cache"); 
if(session.getAttribute("user") != null){
   response.sendRedirect("/transporter/servicios.jsp");
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

    <title>Terpel 360</title>

    <!-- Bootstrap Core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
    <link href="../css/dataTables.bootstrap.min.css" rel="stylesheet" />
    <!-- Custom CSS -->
    <link href="../css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../css/plugins/morris.css" rel="stylesheet">
    

    <!-- Custom Fonts -->
    <link href="../css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
    <script src="../js/angular-material.min.js"></script>
    
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

<body class="body-Login-back" ng-app="myApp" >
    <div ng-controller="SignUpController as ctrl">
    <div class="container">
       
        <div class="row">
            <div class="col-md-4 col-md-offset-4 text-center logo-margin ">
                <label style="font-size: 48px; color: white;">Terpel 360</label>
                </div>
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">                  
                    <div class="panel-heading">
                        <center><h3 class="panel-title">Formulario de ingreso para transpotadoras</h3></center>
                    </div>
                    <div class="panel-body" >
                        
                        <form role="form" angular-validator-submit="ctrl.sendInfo()" name="informacion_login" 
                            angular-validator novalidate>
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="E-mail" name="nick" ng-model="ctrl.usuario.nick"
                                    type="email" value="<%out.print((request.getParameter("email")!=null)?request.getParameter("email"):"");%>" 
                                    autofocus clase='text_valid'
                                    invalid-message = "'Debe ingresar un email correcto'"
                                    required-message="'El campo no puede estar vacio'" 
                                    required>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Contraseña" 
                                        name="pass" type="password" clase='text_valid' ng-model="ctrl.usuario.pass"
                                        required-message="'El campo no puede estar vacio'" 
                                        required>
                                    </div>
                                    <!-- Change this to a button or input when using this as a form -->
                                    <button type="submit" class="btn btn-lg btn-primary btn-block" style="margin-top: 3%;">Iniciar sesion</button>

                            </fieldset>
                        </form>
                    </div>                                
                </div>
                <div id='contenedor' class="alert">

                </div>  
                
                
            </div>
        </div>
    </div>
    </div>
</body>

</html>
