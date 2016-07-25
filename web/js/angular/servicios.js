'use strict';

angular.module('MyApp.Servicios', []).controller('SolicitudController', ['NgMap', '$http', '$interval', '$templateCache', '$timeout', '$alert',  
    function(NgMap, $http, $interval, $templateCache, $timeout, $alert) {
            var vm = this;
            vm.vehiculos = [];
            vm.dateMinCargue=null;
            vm.dateMinDescargue=null;
            vm.dateMaxCargue=null;
            vm.dateMaxDescargue=null;
            vm.mapa = {radio:10000, carga:"", addressOrigin:[], solicitud:"", todos:false};
            vm.options = {format: "YYYY/MM/DD hh:mm A", allowInputToggle:true, showClose:true};
            vm.shape = {center:[], radius:10000};
            vm.vehiculo = {cod:"", ult_reporte:"", placa:"", position:[], icono:"", imagen:"", tipo_doc:"",
            doc:"", tipo_lic:"", lic:"", telefono:"",propietario:"",carga:"",poliza:""};
            vm.servicio={id:"",addressOrigin:[], nameOrigin:"",addressDestination:[],nameDestination:"",carga:"",
            dcarguemin:"", dcarguemax:"", ddescarguemin:"", ddescarguemax:"",equipos:"",orden:"", kms:"", time:"",
            nota_detalle:"", nota_pago:"", flete:"", radio:10000};
            vm.enviado = false;
            vm.alerta = {title: 'Solicitud Enviada', container:'#alerta-submit', duration:5, animation:'am-fade-and-slide-top', show: false};
            vm.date = new Date();
            var stopTime;
            
            vm.placeChangedOrigin = function() {
                vm.servicio.addressOrigin = this.getPlace().geometry.location;
                vm.shape = {center:vm.servicio.addressOrigin};
                vm.shape.center = vm.servicio.addressOrigin;
                vm.placeO = this.getPlace();
            };
            
            vm.placeChangedDestination = function() {
                vm.servicio.addressDestination = this.getPlace().geometry.location;
                vm.placeD = this.getPlace();
            };
            
            vm.lengthValidator = function(texto, length) {
                if(!texto){return;}
                if (texto.length < length) {
                        return "El campo debe tener como minimo " + length + " caracteres de largo";
                }
                return true;
            };
    
            NgMap.getMap().then(function(map) {
              vm.map = map;
              vm.mapa.addressOrigin = vm.map.getCenter();
              vm.shape.center = vm.map.getCenter();
              vm.ReloadVehiculos();
            });
            
            vm.showAlert = function() {
                var myAlert = $alert(vm.alerta);
                myAlert.$promise.then(myAlert.show); // or myAlert.$promise.then(myAlert.show) if you use an external html template
            };

            
            vm.showDetail = function(e, vehiculo) {
                console.log(vehiculo);
                vm.vehiculo = vehiculo;
                vm.map.showInfoWindow('foo-iw', vehiculo.cod);
            };

            vm.sendServicio = function(){
                vm.servicio.kms = vm.map.directionsRenderers[0].directions.routes[0].legs[0].distance.text;
                vm.servicio.time = vm.map.directionsRenderers[0].directions.routes[0].legs[0].duration.text;
                vm.servicio.dcarguemin = new Date(vm.dateMinCargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.servicio.dcarguemax = new Date(vm.dateMaxCargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.servicio.ddescarguemin = new Date(vm.dateMinDescargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.servicio.ddescarguemax = new Date(vm.dateMaxDescargue).toString("yyyy-MM-dd HH:mm:ss");
                $http.post("../saveServicio", vm.servicio).success(function(d){
                   if(d.mensaje!=="error"){
                        vm.vehiculos = d.lista;
                        vm.map.setCenter(vm.servicio.addressOrigin);
                        vm.servicio.id = d.id_solicitud;
                        vm.mapa.solicitud = d.id_solicitud;
                        vm.enviado = true;
                        vm.alerta.content = "Se ha generado correctamente la solicitud <b>#"+d.id_solicitud+"</b>.";
                        vm.alerta.type = "success";
                   }else{
                        vm.alerta.content = "Se ha generado un error ejecutando la operación.";
                        vm.alerta.type = "danger";
                   }
                   vm.showAlert();
                });
            };
            
            vm.ReloadVehiculos = function(){
                $http.post("../list_all_vehiculos", vm.mapa).success(function(d){
                vm.vehiculos = [];
                    if(d!=="false"){
                        vm.vehiculos = d;
                    }
                });
            };
            
            vm.resetForm = function(){
                location.reload(); 
            };
            
            vm.cambiarDistancia = function(distancia){
                vm.shape.radius = distancia;
                for(var i = 0; i < vm.Distancias.length; i++){
                    //vm.map.setZoom();
                    if(vm.Distancias[i].ID===distancia){
                        if(vm.servicio.addressOrigin.length==0){
                            vm.map.setCenter(vm.servicio.addressOrigin);
                        }else{
                            vm.map.setCenter(vm.map.getCenter());
                        }
                        vm.map.setZoom(vm.Distancias[i].Zoom);
                        break;
                    }
                   
                }
                vm.ReloadVehiculos();
            };
            
            vm.cambiarCarga = function(carga){
                vm.mapa.carga = carga;
                vm.ReloadVehiculos();
            };
            
            stopTime = $interval(vm.ReloadVehiculos, 30000);

            vm.TipoCarga = [
                {"ID":1,"Value":"Gases"},
                {"ID":2,"Value":"Liquida"},
                {"ID":3,"Value":"Liquida Inflamable"},
                {"ID":4,"Value":"Reciduo Peligroso"},
                {"ID":5,"Value":"Refrigerada"},
                {"ID":6,"Value":"Seca"}
            ];
            
            vm.Distancias = [
                {"ID":10000,"Value":"10 Kms", "Zoom":12},
                {"ID":50000,"Value":"50 Kms", "Zoom":10},
                {"ID":100000,"Value":"100 Kms", "Zoom":9},
                {"ID":150000,"Value":"150 Kms", "Zoom":8}
            ];

}]).controller('ServiciosController', ['$http', '$templateCache', '$timeout', '$alert' , '$modal', function($http, $templateCache, $timeout, $alert, $modal) {
    var vm = this;
    vm.options = {format:"YYYY/MM/DD", allowInputToggle:true};
    vm.dcargue=null;
    vm.ddescargue=null;
    vm.busqueda={porpage:20, pageno:1, q:"", cargue:"", descargue:"", orden:"", carga:"", estado:""};
    vm.servicios = [];
    vm.foto={id:"", url:"", desc:"", fecha:"", tipo_foto:""};
    vm.servicio={origen:"", destino:"", carge:"", descarge:"", id:"-1", nestado:"" ,estado:"",solicitud:0,orden:"", vehiculos:[]};
    vm.vehiculo = {cod:"", ult_reporte:"", placa:"", position:[], icono:"", imagen:"", tipo_doc:"",
    doc:"", tipo_lic:"", lic:"", telefono:"",propietario:"",carga:"",poliza:"", turno_cargue:"", turno_descargue:"", fotos:[]};
    vm.date = new Date();
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 20; //this could be a dynamic value from a drop down

    vm.getData = function(pageno){ // This would fetch the data on page change.
        //In practice this should be in a factory.
        vm.busqueda.porpage = vm.itemsPerPage;
        vm.busqueda.pageno = pageno;
        vm.servicios = []; 
        if(vm.dcargue!=="" && vm.dcargue!==null){
            vm.busqueda.cargue = new Date(vm.dcargue).toString("yyyy/MM/dd");
        }
        if(vm.ddescargue!=="" && vm.ddescargue!==null){
            vm.busqueda.descargue = new Date(vm.ddescargue).toString("yyyy/MM/dd");
        }
        $http.post("../list_solicitudes", vm.busqueda).success(function(response){ 
            //ajax request to fetch data into vm.data
            vm.servicios = response.data;  // data to be displayed on current page.
            vm.total_count = response.total_count; // total data count.
            //Modal_filter.modal('hide');
        });
    };
    
    vm.getData(vm.pageno);
    
    
    function MyModalController($scope) {
        $scope.title = 'Detalle de servicio';
        $scope.servicio = vm.servicio;
    }
     MyModalController.$inject = ['$scope'];
    var myAlert = $alert({title: 'Holy guacamole!', content: 'Best check yo self, you\'re not looking too good.', placement: 'top', type: 'success', container:'#alerta-busqueda', show: false});
    var detSolicitud = $modal({controller: MyModalController, templateUrl: '../modal/det-solicitud.html', show: false});
    var fotos = $modal({controller: MyModalController, templateUrl: '../modal/fotos.html', show: true});
    
    vm.showAlert = function() {
      myAlert.show(); // or myAlert.$promise.then(myAlert.show) if you use an external html template
    };
    
    
    vm.showModalSol = function() {
      detSolicitud.show();
    };
    
    
    vm.showModalFotos = function() {
      fotos.show();
    };
    
    vm.verDetalleSol = function(id){
        for(var i = 0; i < vm.servicios.length; i++){
            if(vm.servicios[i].id === id) {
               vm.servicio = angular.copy(vm.servicios[i]);
               console.log(vm.servicio);
               vm.showModalSol();
               break;
            }
        }
    };
    
    vm.verFotos = function(id){
        for(var i = 0; i < vm.servicios.length; i++){
            if(vm.servicios[i].id === id) {
               vm.servicio = angular.copy(vm.servicios[i]);
               console.log(vm.servicio);
               vm.showModalFotos();
               break;
            }
        }
    };
    
    vm.formatDate = function(date){
        var dateOut = new Date(date);
        return dateOut;
    };
    
    vm.limpiar = function(){
        //vm.showModal();
        vm.dcargue=null;
        vm.ddescargue=null;
        vm.busqueda={porpage:20, pageno:1, q:"", cargue:"", descargue:"", orden:"", carga:"", estado:""};
        console.log(vm.busqueda);
        vm.getData(1);
    };
    
    vm.TipoCarga = [
        {"ID":1,"Value":"Gases"},
        {"ID":2,"Value":"Liquida"},
        {"ID":3,"Value":"Liquida Inflamable"},
        {"ID":4,"Value":"Reciduo Peligroso"},
        {"ID":5,"Value":"Refrigerada"},
        {"ID":6,"Value":"Seca"}
    ];
    
    vm.EstadoSolicitud = [
        {"ID":1, "Value": "Lanzada"},
        {"ID":2, "Value": "Asignada"},
        {"ID":3, "Value": "Enturnada para cargue"},
        {"ID":4, "Value": "Cargando"},
        {"ID":5, "Value": "Cargada"},
        {"ID":6, "Value": "En ruta"},
        {"ID":7, "Value": "En destino"},
        {"ID":8, "Value": "Enturnada para Descargue"},
        {"ID":9, "Value": "Descargando"},
        {"ID":10, "Value": "Entregada"},
        {"ID":11, "Value": "Terminada"},
        {"ID":12, "Value": "Cerrada"}
    ];
        
    vm.dtOptions = {
            bAutoWidth:true,
            stateSave: true,
            paging:false,
            //order: [[ 0, "desc" ]],
            bFilter: false,
            columnDefs: [ 
                {
                targets: 7,
                orderable: false
                }
            ],
            language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron registros",
                "info": "",
                "infoEmpty": "No se encontraron registros",
                "infoFiltered": "(filtrado de _MAX_ registros)",
                "search": "Buscar"
            }
        };
        
}]).controller('CamionesController', ['NgMap', '$http', '$interval',
    function(NgMap, $http, $interval) {
            var vm = this;
            vm.vehiculos = [];
            vm.mapa = {radio:10000, carga:"", addressOrigin:[], solicitud:"", todos:false};
            vm.shape = {center:[], radius:10000};
            vm.vehiculo = {cod:"", ult_reporte:"", placa:"", position:[], icono:"", imagen:"", tipo_doc:"",
            doc:"", tipo_lic:"", lic:"", telefono:"",propietario:"",carga:"",poliza:""};
            vm.date = new Date();
            var stopTime;
            
            vm.placeChangedOrigin = function() {
                vm.mapa.addressOrigin = this.getPlace().geometry.location;
                vm.shape = {center:vm.mapa.addressOrigin};
                vm.map.setCenter(vm.mapa.addressOrigin);
                vm.placeO = this.getPlace();
                vm.ReloadVehiculos();
            };
            
            NgMap.getMap().then(function(map) {
              vm.map = map;
              vm.mapa.addressOrigin = vm.map.getCenter();
              vm.shape.center = vm.map.getCenter();
              vm.ReloadVehiculos();
            });

    
            vm.showDetail = function(e, vehiculo) {
                console.log(vehiculo);
                vm.vehiculo = vehiculo;
                vm.map.showInfoWindow('foo-iw', vehiculo.cod);
            };
            
            vm.ReloadVehiculos = function(){
                $http.post("../list_all_vehiculos", vm.mapa).success(function(d){
                vm.vehiculos = [];
                    if(d!=="false"){
                        vm.vehiculos = d;
                    }
                });
            };
            
            vm.resetForm = function(){
                location.reload(); 
            };
            
            vm.cambiarDistancia = function(distancia){
                vm.shape.radius = distancia;
                for(var i = 0; i < vm.Distancias.length; i++){
                    //vm.map.setZoom();
                    if(vm.Distancias[i].ID===distancia){
                        if(vm.mapa.addressOrigin.length==0){
                            vm.map.setCenter(vm.mapa.addressOrigin);
                        }else{
                            vm.map.setCenter(vm.map.getCenter());
                        }
                        vm.map.setZoom(vm.Distancias[i].Zoom);
                        break;
                    }
                   
                }
                vm.ReloadVehiculos();
            };
            
            vm.cambiarCarga = function(carga){
                vm.mapa.carga = carga;
                vm.ReloadVehiculos();
            };
            
           
            stopTime = $interval(vm.ReloadVehiculos, 30000);

            vm.TipoCarga = [
                {"ID":1,"Value":"Gases"},
                {"ID":2,"Value":"Liquida"},
                {"ID":3,"Value":"Liquida Inflamable"},
                {"ID":4,"Value":"Reciduo Peligroso"},
                {"ID":5,"Value":"Refrigerada"},
                {"ID":6,"Value":"Seca"}
            ];
            
            vm.Distancias = [
                {"ID":10000,"Value":"10 Kms", "Zoom":12},
                {"ID":50000,"Value":"50 Kms", "Zoom":10},
                {"ID":100000,"Value":"100 Kms", "Zoom":9},
                {"ID":150000,"Value":"150 Kms", "Zoom":8}
            ];

}]).controller('SignUpController', ['NgMap', '$http', '$interval',
    function(NgMap, $http, $interval) {
            var vm = this;
            vm.vehiculos = [];
            vm.mapa = {radio:10000, carga:"", addressOrigin:[], solicitud:"", todos:false};
            vm.shape = {center:[], radius:10000};
            vm.vehiculo = {cod:"", ult_reporte:"", placa:"", position:[], icono:"", imagen:"", tipo_doc:"",
            doc:"", tipo_lic:"", lic:"", telefono:"",propietario:"",carga:"",poliza:""};
            vm.date = new Date();
            var stopTime;
            
            vm.placeChangedOrigin = function() {
                vm.mapa.addressOrigin = this.getPlace().geometry.location;
                vm.shape = {center:vm.mapa.addressOrigin};
                vm.map.setCenter(vm.mapa.addressOrigin);
                vm.placeO = this.getPlace();
                vm.ReloadVehiculos();
            };
            
            NgMap.getMap().then(function(map) {
              vm.map = map;
              vm.mapa.addressOrigin = vm.map.getCenter();
              vm.shape.center = vm.map.getCenter();
              vm.ReloadVehiculos();
            });

    
            vm.showDetail = function(e, vehiculo) {
                console.log(vehiculo);
                vm.vehiculo = vehiculo;
                vm.map.showInfoWindow('foo-iw', vehiculo.cod);
            };
            
            vm.ReloadVehiculos = function(){
                $http.post("../list_all_vehiculos", vm.mapa).success(function(d){
                vm.vehiculos = [];
                    if(d!=="false"){
                        vm.vehiculos = d;
                    }
                });
            };
            
            vm.resetForm = function(){
                location.reload(); 
            };
            
            vm.cambiarDistancia = function(distancia){
                vm.shape.radius = distancia;
                for(var i = 0; i < vm.Distancias.length; i++){
                    //vm.map.setZoom();
                    if(vm.Distancias[i].ID===distancia){
                        if(vm.mapa.addressOrigin.length==0){
                            vm.map.setCenter(vm.mapa.addressOrigin);
                        }else{
                            vm.map.setCenter(vm.map.getCenter());
                        }
                        vm.map.setZoom(vm.Distancias[i].Zoom);
                        break;
                    }
                   
                }
                vm.ReloadVehiculos();
            };
            
            vm.cambiarCarga = function(carga){
                vm.mapa.carga = carga;
                vm.ReloadVehiculos();
            };
            
           
            stopTime = $interval(vm.ReloadVehiculos, 30000);

            vm.TipoCarga = [
                {"ID":1,"Value":"Gases"},
                {"ID":2,"Value":"Liquida"},
                {"ID":3,"Value":"Liquida Inflamable"},
                {"ID":4,"Value":"Reciduo Peligroso"},
                {"ID":5,"Value":"Refrigerada"},
                {"ID":6,"Value":"Seca"}
            ];
            
            vm.Distancias = [
                {"ID":10000,"Value":"10 Kms", "Zoom":12},
                {"ID":50000,"Value":"50 Kms", "Zoom":10},
                {"ID":100000,"Value":"100 Kms", "Zoom":9},
                {"ID":150000,"Value":"150 Kms", "Zoom":8}
            ];

}]);