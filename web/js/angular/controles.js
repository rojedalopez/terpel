'use strict';

angular.module('MyApp.Servicios', []).controller('SpotController', ['NgMap', '$http', '$interval', '$templateCache', '$timeout', '$alert', 'ServiceTables', 'storageService',  
    function(NgMap, $http, $interval, $templateCache, $timeout, $alert, ServiceTables, storageService) {
            var vm = this;
            vm.vehiculos = [];
            vm.dateMinCargue=null;
            vm.dateMinDescargue=null;
            vm.dateMaxCargue=null;
            vm.dateMaxDescargue=null;
            vm.mapa = {lng_punto:"", lat_punto:"", solicitud:"", remolques:[], hora:""};
            vm.options = {format: "YYYY/MM/DD HH:mm", allowInputToggle:true, showClose:true};
            vm.shape = {center:[], radius:50000};
            vm.vehiculo = {placa:"", position:[], ult_reporte:"", remolque:"", tipocarga:"", tipoequipo:"", marca:"",
            modelo:"", referencia:"", trailer:"", nombre:"", telefono:"", imagen:"", solicitud:"", servicio:"", 
            origen:"", destino:"", icono:"", id:""};
            vm.servicio={id:"", carga: "", dcarguemin: "", dcarguemax: "", ddescarguemin: "", ddescarguemax: "", equipos: "", orden: "",
            kms: "", time: "", nota_detalle: "", flete: "", vlr_flete: "", enrutado: false, inicio: { }, fin: { }, kms_value: "",
            time_value : "", unidad:"", remolques:[], ccosto:""};
            vm.ccosto = {id:"", desc:""};
            vm.enviado = false;
            vm.Remolques=[];
            vm.Cargas=[];
            vm.puntos = [];
            vm.fletes = [];
            vm.cargues = [];
            vm.ccostos = [];
            vm.alerta = {title: 'Solicitud Enviada', container:'#alerta-submit', duration:5, animation:'am-fade-and-slide-top', show: false};
            vm.date = new Date();
            var stopTime;
            
            vm.selectInicio = function(){
                vm.servicio.addressOrigin = {lat:vm.servicio.inicio.lat, lng:vm.servicio.inicio.lng};
                vm.mapa.lat_punto = vm.servicio.inicio.lat;
                vm.mapa.lng_punto = vm.servicio.inicio.lng;
                vm.placeO = vm.servicio.addressOrigin;
                vm.ReloadVehiculos();
            };
            
            vm.selectFin = function(){
                vm.servicio.addressDestination = {lat:vm.servicio.fin.lat, lng:vm.servicio.fin.lng};
                vm.placeD = vm.servicio.addressDestination;
            };
            
            vm.colocarFecha = function(){
                vm.map.hora = new Date(vm.dateMaxCargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.ReloadVehiculos();
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
            });
            
            
            vm.listaAllPuntos = function(){
                vm.puntos = [];
                vm.puntos = storageService.getPuntos();
                if(vm.puntos.length===0){
                    ServiceTables.listaAllPuntos().then(function(d) {
                        vm.puntos = d;
                        storageService.iniciarPuntos(d);
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
           
            vm.listaCargues = function(){
                vm.cargues = [];
                vm.cargues = storageService.getCargues();
                if(vm.cargues.length===0){
                    ServiceTables.listaCargues().then(function(d) {
                        vm.cargues = d;
                        storageService.iniciarCargues(d);
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
            vm.listaCCosto = function(){
                vm.ccostos = [];
                vm.ccostos = storageService.getCCostos();
                if(vm.ccostos.length===0){
                    ServiceTables.listCCostos().then(function(d) {
                        vm.ccostos = d;
                        storageService.iniciarCCostos(d);
                    },function(errResponse){
                       console.error('Error en CCosto');
                   });
               }
            };
            
            vm.showAlert = function() {
                var myAlert = $alert(vm.alerta);
                myAlert.$promise.then(myAlert.show); // or myAlert.$promise.then(myAlert.show) if you use an external html template
            };
            
            vm.cambiarSeletsMult = function(){
                vm.ReloadVehiculos();
            };
            
            vm.showDetail = function(e, vehiculo) {
                console.log(vehiculo);
                vm.vehiculo = vehiculo;
                vm.map.showInfoWindow('foo-iw', vehiculo.placa);
            };

            
            
            vm.sendServicio = function(){
                vm.servicio.kms = vm.map.directionsRenderers[0].directions.routes[0].legs[0].distance.text;
                vm.servicio.kms_value = vm.map.directionsRenderers[0].directions.routes[0].legs[0].distance.value;
                vm.servicio.time = vm.map.directionsRenderers[0].directions.routes[0].legs[0].duration.text;
                vm.servicio.time_value = vm.map.directionsRenderers[0].directions.routes[0].legs[0].duration.value;
                vm.servicio.dcarguemin = new Date(vm.dateMinCargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.servicio.dcarguemax = new Date(vm.dateMaxCargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.servicio.ddescarguemin = new Date(vm.dateMinDescargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.servicio.ddescarguemax = new Date(vm.dateMaxDescargue).toString("yyyy-MM-dd HH:mm:ss");
                if(vm.mapa.remolques.length===0){
                    for(var i = 0; i < vm.Remolques.length; i++){
                        vm.servicio.remolques.push(vm.Remolques[i].ID);
                    }
                }else{
                    vm.servicio.remolques = vm.mapa.remolques;
                }
                $http.post("../saveServicio", vm.servicio).success(function(d){
                   if(d.mensaje!=="Error"){
                        /*vm.vehiculos = d.lista;
                        vm.map.setCenter(vm.servicio.addressOrigin);*/
                        vm.servicio.id = d.id_solicitud;
                        //vm.mapa.solicitud = d.id_solicitud;
                        vm.enviado = true;
                        vm.alerta.content = "Se ha generado correctamente la solicitud <b>#"+d.id_solicitud+"</b>.";
                        vm.alerta.type = "success";
                        vm.mapa.solicitud = d.id_solicitud;
                        vm.ReloadVehiculos();
                   }else{
                        vm.alerta.content = "Se ha generado un error ejecutando la operación.";
                        vm.alerta.type = "danger";
                   }
                   vm.showAlert();
                });
            };
            
            vm.SendCCosto = function(){
                ServiceTables.saveCCostos(vm.ccosto).then(function(d) {
                    console.log(d);
                        vm.ccostos.push(vm.ccosto);
                        vm.ccosto = {id:"", desc:""};
                        storageService.updateCCostos(vm.ccostos);
                        nuevoCC.modal("hide");
                    //}
                },function(errResponse){
                   console.error('Error en sendPunto');
               });
            };
            
            vm.ReloadVehiculos = function(){
                ServiceTables.listaGeneracionVehiculo(vm.mapa).then(function(d) {
                    vm.vehiculos = d;
                },function(errResponse){
                   console.error('Error en sendPunto');
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
            
            vm.cambiarCargue = function(tipo){
                vm.mapa.remolques=[];
                vm.Remolques=[];
                for(var i = 0; i < vm.cargues.length; i++){
                    if(vm.cargues[i].id===tipo){
                        vm.Remolques = vm.cargues[i].remolques;
                        break;
                    }
                   
                }
                vm.ReloadVehiculos();
            };
            
            
            
            vm.cambiarCarga = function(carga){
                vm.mapa.carga = carga;
                vm.ReloadVehiculos();
            };
            
            vm.listaCCosto();
            vm.listaAllPuntos();
            vm.listaCargues();
            vm.ReloadVehiculos();
            stopTime = $interval(vm.ReloadVehiculos, 30000);

            vm.TipoCarga = [
                {"ID":2,"Value":"Liquida"},
                {"ID":5,"Value":"Refrigerada"},
                {"ID":6,"Value":"Seca"}
            ];
            
            vm.Distancias = [
                {"ID":50000,"Value":"50 Kms", "Zoom":10},
                {"ID":100000,"Value":"100 Kms", "Zoom":9},
                {"ID":150000,"Value":"150 Kms", "Zoom":8},
                {"ID":300000,"Value":"300 Kms", "Zoom":7},
                {"ID":500000,"Value":"500 Kms", "Zoom":6}
            ];
            
            vm.dtOptions = {
            stateSave: true,
            scrollY: 150,
            paging:false,
            language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron registros",
                "info": "",
                "infoEmpty": "No se encontraron registros",
                "infoFiltered": "(filtrado de _MAX_ registros)",
                "search": "Buscar"
            }/*,columnDefs: [ 
                {
                targets: 9,
                orderable: false
                },
                {
                targets: 10,
                orderable: false
                }
            ]
        }*/};
    

}]).controller('ProgramadasController', ['NgMap', '$http', '$interval', '$templateCache', '$timeout', '$alert', 'ServiceTables', 'storageService',  
    function(NgMap, $http, $interval, $templateCache, $timeout, $alert, ServiceTables, storageService) {
            var vm = this;
            vm.dateMinCargue=null;
            vm.dateMinDescargue=null;
            vm.dateMaxCargue=null;
            vm.dateMaxDescargue=null;
            vm.options = {format: "HH:mm", allowInputToggle:true, showClose:true};
            vm.shape = {center:[], radius:50000};
            vm.puntos = [];
            vm.cargues = [];
            vm.alerta = {title: 'Solicitud Enviada', container:'#alerta-submit', duration:5, animation:'am-fade-and-slide-top', show: false};
            vm.date = new Date();
            vm.nuevaSol = {id:"", dia:"", mes:"", inicio:"", fin:"", min_carg:"", max_carg:"", min_desc:"", max_desc:"", cargue:"", equipos:""};
            vm.calendario = [];            
            vm.meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
            vm.mes;
            vm.mes_num;
            NgMap.getMap().then(function(map) {
                vm.map = map;
            });
            
            
            vm.listaAllPuntos = function(){
                vm.puntos = [];
                vm.puntos = storageService.getPuntos();
                if(vm.puntos.length===0){
                    ServiceTables.listaAllPuntos().then(function(d) {
                        vm.puntos = d;
                        storageService.iniciarPuntos(d);
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
           
            vm.listaCargues = function(){
                vm.cargues = [];
                vm.cargues = storageService.getCargues();
                if(vm.cargues.length===0){
                    ServiceTables.listaCargues().then(function(d) {
                        vm.cargues = d;
                        storageService.iniciarCargues(d);
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
            vm.showAlert = function() {
                var myAlert = $alert(vm.alerta);
                myAlert.$promise.then(myAlert.show); // or myAlert.$promise.then(myAlert.show) if you use an external html template
            };
            
            
            vm.showDetail = function(e, vehiculo) {
                console.log(vehiculo);
                vm.vehiculo = vehiculo;
                vm.map.showInfoWindow('foo-iw', vehiculo.placa);
            };
            
            vm.diasDelMes = function(mes, año){
                switch(mes){
                    case 0:  // Enero
                    case 2:  // Marzo
                    case 4:  // Mayo
                    case 6:  // Julio
                    case 7:  // Agosto
                    case 9:  // Octubre
                    case 11: // Diciembre
                    return 31;
                    case 3:  // Abril
                    case 5:  // Junio
                    case 8:  // Septiembre
                    case 10: // Noviembre
                    return 30;
                    case 1:  // Febrero
                    if ( ((año%100 == 0) && (año%400 == 0)) ||
                    ((año%100 != 0) && (año%  4 == 0))   )
                    return 29;  // Año Bisiesto
                    else
                    return 28;
                    default:
                    alert("El mes debe estar entre 0 y 11");
                }
            };
            
            vm.llenarObjetoDias = function(dias){
                for(var i = 0; i < dias; i++){
                    vm.calendario.push({dia:i+1,spots:[]});
                }
            };
            
            vm.abrirVentana = function(dia){
                AgregarSolicitud.modal("show");
                vm.nuevaSol.dia=dia;
                vm.nuevaSol.mes = vm.mes_num;
            };
            
            vm.sendSolicitud = function(){
                vm.nuevaSol.min_carg = new Date(vm.dateMinCargue).toString("HH:mm:ss");
                vm.nuevaSol.max_carg = new Date(vm.dateMaxCargue).toString("HH:mm:ss");
                vm.nuevaSol.min_desc = new Date(vm.dateMinDescargue).toString("HH:mm:ss");
                vm.nuevaSol.max_desc = new Date(vm.dateMaxDescargue).toString("HH:mm:ss");
                ServiceTables.SaveSolicitudProgramada.then(function(d) {
                        if(d.mensaje!=="Error"){
                            for(var i = 0; i < vm.calendario.length; i++){
                                if(vm.calendario[i].dia === vm.nuevaSol.dia){
                                    vm.nuevaSol.id = d.id;
                                    vm.calendario[i].spots.push(vm.nuevaSol);
                                    AgregarSolicitud.modal("hide");
                                    vm.nuevaSol = {id:"",dia:"", mes:"", inicio:"", fin:"", min_carg:"", max_carg:"", min_desc:"", max_desc:"", cargue:"", equipos:""};
                                    vm.dateMinCargue=null;
                                    vm.dateMinDescargue=null;
                                    vm.dateMaxCargue=null;
                                    vm.dateMaxDescargue=null;
                                    break;
                                }
                            }
                            storageService.updateCalendario(vm.calendario);
                        }
                },function(errResponse){
                   console.error('Error en sendPunto');
               });
            };
            
            var date = new Date();
            vm.mes = vm.meses[date.getMonth()+1];
            vm.mes_num = date.getMonth()+1;
            vm.calendario = storageService.getCalendario();
            if(vm.calendario.length===0){
                vm.llenarObjetoDias(vm.diasDelMes(date.getMonth()+1, date.getFullYear()));
                storageService.iniciarCalendario(vm.calendario);
            }
            vm.listaAllPuntos();
            vm.listaCargues();
            

            vm.TipoCarga = [
                {"ID":2,"Value":"Liquida"},
                {"ID":5,"Value":"Refrigerada"},
                {"ID":6,"Value":"Seca"}
            ];
            
            vm.Distancias = [
                {"ID":50000,"Value":"50 Kms", "Zoom":10},
                {"ID":100000,"Value":"100 Kms", "Zoom":9},
                {"ID":150000,"Value":"150 Kms", "Zoom":8},
                {"ID":300000,"Value":"300 Kms", "Zoom":7},
                {"ID":500000,"Value":"500 Kms", "Zoom":6}
            ];
            
            vm.dtOptions = {
            stateSave: true,
            paging:false,
            language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron registros",
                "info": "",
                "infoEmpty": "No se encontraron registros",
                "infoFiltered": "(filtrado de _MAX_ registros)",
                "search": "Buscar"
            }/*,columnDefs: [ 
                {
                targets: 9,
                orderable: false
                },
                {
                targets: 10,
                orderable: false
                }
            ]
        }*/};
    

}]).controller('ServActivosController', ['NgMap', '$http', '$interval', '$templateCache', '$timeout', '$alert', 'ServiceTables', 'storageService',
    function(NgMap, $http, $interval, $templateCache, $timeout, $alert, ServiceTables, storageService) {
            var vm = this;
            vm.cap_cargada = 0;
            vm.servicios = [];
            vm.puntos = [];
            vm.cargues = [];
            vm.servicio;
            vm.mapa = {servicio:"", solicitud:"", placa:"", conductor:"", estados:"", inicio:"", fin:"", carga:"", transportadora:""};
            vm.options = {format: "YYYY/MM/DD hh:mm A", allowInputToggle:true, showClose:true};
            var stopTime;
            
            NgMap.getMap().then(function(map) {
                vm.map = map;
            });
     
            vm.showDetail = function(e, servicio) {
                vm.servicio = servicio;
                vm.map.showInfoWindow('foo-iw', servicio.servicio);
            };
            
            vm.sumarCapacidad = function(){
                vm.cap_cargada = 0;
                for(var i = 0; i< vm.servicios.length; i++){
                    vm.cap_cargada = vm.cap_cargada + vm.servicios[i].cap_carg;
                }
            };
            
            vm.listaAllPuntos = function(){
                vm.puntos = storageService.getPuntos();
                if(vm.puntos.length===0){
                    ServiceTables.listaAllPuntos().then(function(d) {
                        vm.puntos = d;
                        storageService.iniciarPuntos(d);
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
               console.log(vm.puntos);
            };
            
           
            vm.listaCargues = function(){
                vm.cargues = storageService.getCargues();
                if(vm.cargues.length===0){
                    ServiceTables.listaCargues().then(function(d) {
                        vm.cargues = d;
                        storageService.iniciarCargues(d);
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
            vm.listaServicios = function(){
                ServiceTables.listaServiciosActivos(vm.mapa).then(function(d) {
                    vm.servicios = d;
                    vm.sumarCapacidad();
                },function(errResponse){
                   console.error('Error en sendPunto');
               });

            };
            
            vm.buscar = function(){
                vm.listaServicios();
            };
            
            vm.limpiar = function(){
                vm.mapa = {servicio:"", solicitud:"", placa:"", conductor:"", estados:"", inicio:"", fin:"", carga:"", transportadora:""};
                vm.listaServicios();
            };
            
            vm.listaAllPuntos();
            vm.listaCargues();
            vm.listaServicios();
            stopTime = $interval(vm.listaServicios, 30000);

            vm.dtOptions = {
                stateSave: true,
                paging:false,
                scrollY: 180,
                bFilter: false,
                language: {
                    "lengthMenu": "Mostrar _MENU_ registros",
                    "zeroRecords": "No se encontraron registros",
                    "info": "",
                    "infoEmpty": "No se encontraron registros",
                    "infoFiltered": "(filtrado de _MAX_ registros)",
                    "search": "Buscar"
                }
            };
            
            vm.Estados = [
                {ID:2, Value:"Asignada"},
                {ID:6, Value:"Aviso para cargue"},
                {ID:12, Value:"Cancelada"},
                {ID:7, Value:"En cargue"},
                {ID:4, Value:"En geocerca de origen"},
                {ID:9, Value:"En transito a destino"},
                {ID:3, Value:"En transito a origen"},
                {ID:10, Value:"En zona de descargue"},
                {ID:5, Value:"Enturnado"},
                {ID:8, Value:"Finalizo cargue"},
                {ID:1, Value:"Lanzada"},
                {ID:11, Value:"Viaje finalizado"}
            ];

}]).controller('ServiciosController', ['$http', '$templateCache', '$timeout', '$alert' , '$modal', function($http, $templateCache, $timeout, $alert, $modal) {
    var vm = this;
    vm.options = {format:"YYYY/MM/DD", allowInputToggle:true};
    vm.dcargue=null;
    vm.ddescargue=null;
    vm.busqueda={porpage:20, pageno:1, q:"", cargue:"", descargue:"", orden:"", carga:"", estado:""};
    vm.servicios = [];
    vm.foto={id:"", url:"", desc:"", fecha:"", tipo_foto:""};
    vm.fotos=[];
    vm.servicio={origen:"", destino:"", carge:"", descarge:"", id:"-1", nestado:"" ,estado:"",solicitud:0,orden:"", vehiculos:[]};
    vm.vehiculos=[];
    vm.vehiculo = {servicio:"", solicitud:"", placa:"", marca:"", referencia:"", modelo:"", trailer:"", poliza:"", compania:"", 
    exp_poliza:"", vence_poliza:"", soat:"", exp_soat:"", vence_soat:"", tecno:"", exp_tecno:"", vence_tecno:"", 
    nombre:"", tipo_doc:"", doc:"", num_lic:"", exp_lic:"",  vence_lic:"", telefono:"", direccion:"", imagen:"", 
    tipo_carga:"", ntipocarga:"", tipo_remolque:"", ntipo_remolque:"", tipo_equipo:"", ntipo_equipo:"", turno_cargue:"", 
    turno_descargue:"", fotos:[]};
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
        $scope.vehiculos = vm.servicio.vehiculos;
         
        $scope.abrirFotos =  function(id){
            console.log(id);
            for(var i = 0; i < $scope.vehiculos.length; i++){
                if($scope.vehiculos[i].servicio === id) {
                    console.log(id);
                   $scope.vehiculo = angular.copy($scope.vehiculos[i]);
                   $scope.fotos = $scope.vehiculo.fotos;
                   vm.fotos = $scope.vehiculo.fotos;
                   console.log(vm.fotos);
                   vm.showModalFotos();
                   break;
                }
            }
        };
    }
    
    function FotosController($scope) {
        $scope.title = 'Fotos del servicio';
        var slides = $scope.slides = [];
        var currIndex = 0;
        for (var i = 0; i < vm.fotos.length; i++) {
            slides.push({
                image: '../upload/'+vm.fotos[i].url,
                text: vm.fotos[i].desc,
                id: currIndex++
            });
            console.log(vm.fotos[i]);
        }
    }
    
     MyModalController.$inject = ['$scope'];
    var myAlert = $alert({title: 'Holy guacamole!', content: 'Best check yo self, you\'re not looking too good.', placement: 'top', type: 'success', container:'#alerta-busqueda', show: false});
    var detSolicitud = $modal({controller: MyModalController, templateUrl: '../modal/det-solicitud.html', show: false});
    var fotos = $modal({controller: FotosController, templateUrl: '../modal/fotos.html', show: false});
    
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
        
}]).controller('ServiciosSolicitudController', ['$http', '$templateCache', '$timeout', '$alert' , '$modal', '$interval', 'ServiceTables', 
function($http, $templateCache, $timeout, $alert, $modal, $interval, ServiceTables) {
    var vm = this;
    vm.options = {format:"YYYY/MM/DD", allowInputToggle:true};
    vm.dcargue=null;
    vm.solicitud ={};
    vm.ddescargue=null;
    vm.busqueda={porpage:20, pageno:1, q:"", cargue:"", descargue:"", orden:"", carga:"", estado:""};
    vm.servicios = [];
    vm.foto={id:"", url:"", desc:"", fecha:"", tipo_foto:""};
    vm.fotos=[];
    vm.conductores=[];
    vm.datos = {id_servicio:"", equipo_conductor:"", latitud:"", longitud:"", velocidad:""};
    vm.servicio={origen:"", destino:"", carge:"", descarge:"", id:"-1", nestado:"" ,estado:"",solicitud:0,orden:"", vehiculos:[]};
    vm.vehiculos=[];
    vm.solicitudes=[];
    vm.vehiculo = {servicio:"", solicitud:"", placa:"", marca:"", referencia:"", modelo:"", trailer:"", poliza:"", compania:"", 
    exp_poliza:"", vence_poliza:"", soat:"", exp_soat:"", vence_soat:"", tecno:"", exp_tecno:"", vence_tecno:"", 
    nombre:"", tipo_doc:"", doc:"", num_lic:"", exp_lic:"",  vence_lic:"", telefono:"", direccion:"", imagen:"", 
    tipo_carga:"", ntipocarga:"", tipo_remolque:"", ntipo_remolque:"", tipo_equipo:"", ntipo_equipo:"", turno_cargue:"", 
    turno_descargue:"", fotos:[]};
    var stopTime;
    
    
    vm.listaSolicitudes = function(){
      ServiceTables.listaSolicitudesTransportador().then(function(d) {
          console.log(d);
            vm.solicitudes = d;
        },function(errResponse){
           console.error('Error en sendPunto');
       });  
    };
    
    vm.getDataEquiposConductores = function(solicitud){ // This would fetch the data on page change.
        vm.solicitud = solicitud;
        vm.datos.id_servicio = solicitud.id;
        ServiceTables.listaConductoresTransportador(solicitud).then(function(d) {
            if(d!=="sesion"){
                vm.conductores = d;  // data to be displayed on current page.
                console.log(d);
            }else{
                $window.location = '../../';
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.saveTakenSolicitud = function(conductor){ // This would fetch the data on page change.
        vm.datos.latitud = conductor.latitud;
        vm.datos.longitud = conductor.longitud;
        vm.datos.velocidad = conductor.velocidad;
        vm.datos.equipo_conductor = conductor.id;
        ServiceTables.SaveTakeSolicitud(vm.datos).then(function(d) {
            console.log(d);
            if(d.mensaje==="OK"){
                alert("se asigno correctamente con # " + d.servicio);
                vm.datos = {id_servicio:"", equipo_conductor:"", latitud:"", longitud:"", velocidad:""};
                vm.getDataEquiposConductores(vm.solicitud);
                vm.listaSolicitudes();
            }else if(d.mensaje==="nocupo"){
                alert("ya estan asignados todos los cupos");
            }else{
                alert("ocurrio un error");
            }
            
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };

    vm.listaSolicitudes();
    stopTime = $interval(vm.listaSolicitudes, 120000);
    
    vm.verDetalleSol = function(id){
        for(var i = 0; i < vm.servicios.length; i++){
            if(vm.servicios[i].id === id) {
               vm.servicio = angular.copy(vm.servicios[i]);
               console.log(vm.servicio);
               
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
            scrollY: 120,
            paging:false,
            //order: [[ 0, "desc" ]],
            bFilter: false,
            language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron registros",
                "info": "",
                "infoEmpty": "No se encontraron registros",
                "infoFiltered": "(filtrado de _MAX_ registros)",
                "search": "Buscar"
            }
        };
        
}]).controller('EnturneController', ['$http', '$templateCache', '$timeout', '$alert', '$interval', 'ServiceTables', 'storageService',
function($http, $templateCache, $timeout, $alert, $interval, ServiceTables, storageService) {
    var vm = this;
    vm.options = {format:"YYYY/MM/DD HH:mm:ss", allowInputToggle:true};
    vm.dcargue=null;
    vm.ddescargue=null;
    vm.fecha_enturnado = null;
    vm.zonas = []
    vm.zona = {id:"", id_punto:"", desc:"", bahias:[]};
    vm.bahias = [];
    vm.bahia = {id:"", id_zona:"", desc:"", nota:""};
    vm.puntos = [];
    vm.punto = {id:"", desc:"", nota:"", zonas:[]};
    vm.ticketsR = [];
    vm.ticketsA = [];
    vm.ticketsT = [];
    vm.ticket={ticket:"", fecha:"", operacion:"", tipo_cargue:"", fecha_enturne:"", placa:"", remolque:"", 
    tipo_equipo:"", marca:"", modelo:"", referencia:"", trailer:"", poliza:"", soat:"", 
    tecno:"", zona:"", bahia:"", punto :"", npunto:"", nzona:"", nbahia:"", fecha_enturnado:"", fecha_terminada:"", turno:""};
    vm.pagenoR = 1; // initialize page no to 1
    vm.total_countR = 0;
    vm.itemsPerPageR = 20; //this could be a dynamic value from a drop down
    vm.pagenoA = 1; // initialize page no to 1
    vm.total_countA = 0;
    vm.itemsPerPageA = 20; //this could be a dynamic value from a drop down
    vm.pagenoT = 1; // initialize page no to 1
    vm.total_countT = 0;
    vm.itemsPerPageT = 20; //this could be a dynamic value from a drop down
    vm.reasignar = false;
    var stopTime;
    
    vm.llenarZonasBahias = function(){ // This would fetch the data on page change.
        vm.puntos = storageService.getPuntos();
        if(vm.puntos.length===0){
            ServiceTables.listaAllPuntos().then(function(d) {
                vm.puntos = d;
                storageService.iniciarPuntos(d);
            },function(errResponse){
               console.error('Error en sendPunto');
           });
       }
    };

    

    vm.getDataRegistradas = function(page){ // This would fetch the data on page change.
        ServiceTables.ListaEnturnesEstados({porpage:vm.itemsPerPageR, pageno:page,estado:1}).then(function(d) {
            if(d!=="sesion"){
                vm.ticketsR = d.data;  // data to be displayed on current page.
                vm.total_countR = d.total_count; // total data count.
            }else{
                $window.location = '../../';
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.getDataAsignadas = function(page){ // This would fetch the data on page change.
        ServiceTables.ListaEnturnesEstados({porpage:vm.itemsPerPageA, pageno:page,estado:2}).then(function(d) {
            if(d!=="sesion"){
                vm.ticketsA = d.data;  // data to be displayed on current page.
                vm.total_countA = d.total_count; // total data count.
            }else{
                $window.location = '../../';
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.getDataTerminadas = function(page){ // This would fetch the data on page change.
        ServiceTables.ListaEnturnesEstados({porpage:vm.itemsPerPageT, pageno:page,estado:3}).then(function(d) {
            if(d!=="sesion"){
                vm.ticketsT = d.data;  // data to be displayed on current page.
                vm.total_countT = d.total_count; // total data count.
            }else{
                $window.location = '../../';
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.init = function(){
        vm.getDataRegistradas(vm.pagenoR);
    };
    
    vm.stopinit = function() {
      if (angular.isDefined(stopTime)) {
        $interval.cancel(stopTime);
        stopTime = undefined;
      }
    };
    
    vm.changeStopTime = function(tabla){
        vm.stopinit();
        
        if(tabla===1){
            vm.getDataRegistradas(vm.pagenoR);
        }else if(tabla===2){
            vm.getDataAsignadas(vm.pagenoA);
        }else if(tabla===3){
            vm.getDataTerminadas(vm.pagenoT);
        }

        stopTime = $interval(function() {
          if(tabla===1){
              vm.getDataRegistradas(vm.pagenoR);
          }else if(tabla===2){
              vm.getDataAsignadas(vm.pagenoA);
          }else if(tabla===3){
              vm.getDataTerminadas(vm.pagenoT);
          }
        }, 30 * 1000);
    };

    vm.llenarZonasBahias();
    vm.init();
    
    stopTime = $interval(vm.init, 30 * 1000);
    
    
    vm.formatDate = function(date){
        var dateOut = new Date(date);
        return dateOut;
    };
    
    
    
    vm.asignarTickets = function(ticket) {
        vm.ticket = ticket;
        vm.zonas = [];
        for(var i=0; i < vm.puntos.length; i++){
            if(vm.puntos[i].id===vm.ticket.punto){
                vm.zonas = vm.puntos[i].zonas;
                break;
            }
        }
        Asignacion.modal("show");
    };
    
    vm.sendAsignacion = function(){ // This would fetch the data on page change.
            vm.ticket.fecha_enturnado = new Date(vm.fecha_enturnado).toString("yyyy-MM-dd HH:mm:ss");
            $http.post("../asignTurno", vm.ticket).success(function(response){ 
                //ajax request to fetch data into vm.data
                console.log(response);
                if(response!=="false"){
                    vm.init();
                    Asignacion.modal("hide");
                }
            });
        };
        
    vm.selectZonas = function(id){
        vm.bahias = [];
        for(var i = 0; i < vm.zonas.length; i++){
            if(vm.zonas[i].id === id) {
               vm.bahias = angular.copy(vm.zonas[i].bahias);
               break;
            }
        }
    };

    
    vm.reasignarTickets = function(ticket){
        console.log("entro aqui");
        vm.reasignar = true;
        vm.ticket = ticket;
        vm.fecha_enturnado = vm.ticket.fecha_enturnado;
        console.log(vm.ticket);
        vm.zonas = [];
        for(var i=0; i < vm.puntos.length; i++){
            if(vm.puntos[i].id===vm.ticket.punto){
                vm.zonas = vm.puntos[i].zonas;
                break;
            }
        }
        Asignacion.modal("show");

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
        
    vm.dtOptionsAsign = {
        bAutoWidth:true,
        stateSave: true,
        paging:false,
        ordering: false,
        bFilter: false,
        destroy: true,
        info:false
    };
       
    vm.dtOptionsTerm = {
        bAutoWidth:true,
        stateSave: true,
        paging:false,
        ordering: false,
        bFilter: false,
        destroy: true,
        info:false
    };
    
    vm.dtOptionsRegs = {
        bAutoWidth:true,
        stateSave: true,
        paging:false,
        ordering: false,
        bFilter: false,
        destroy: true,
        info:false
    };
        
}])
    .controller('SignUpController', ['ServiceTables','storageService', '$templateCache', '$timeout', '$alert',
    function(ServiceTables, storageService, $templateCache, $timeout, $alert) {
        var vm = this;
        vm.usuario = {nick:"", pass:""};
        vm.alerta = {placement:"top-right", duration:5, animation:'am-fade', container:"#contenedor", show: false};
        
        vm.showAlert = function() {
            var myAlert = $alert(vm.alerta);
            myAlert.$promise.then(myAlert.show); // or myAlert.$promise.then(myAlert.show) if you use an external html template
        };
        
        vm.llenarPuntos = function(){
            console.log("entro aqui");
            ServiceTables.listaAllPuntos().then(function(d) {
                console.log(d);
                storageService.iniciarPuntos(d);
            },function(errResponse){
                console.error('Error en getDataRegistradas');
            });
        };
        
        vm.sendInfo = function(){
            ServiceTables.loginEmpresa(vm.usuario).then(function(d) {
                    if(d.mensaje==="OK"){
                        vm.llenarPuntos();
                        window.location = d.url;
                    }else if(d.mensaje==="badinfo"){
                        vm.alerta.title = "Error en la autenticacion";
                        vm.alerta.content = "Su contraseña es invalida.";
                        vm.alerta.type = "warning";
                    }else if(d.mensaje==="nouser"){
                        vm.alerta.title = "Usuario no existe";
                        vm.alerta.content = "El usuario que intenta ingresar no existe.";
                        vm.alerta.type = "danger";
                    }
                    
                    vm.showAlert();
                    
                },function(errResponse){
                   console.error('Error en getDataRegistradas');
               });
        };
        

}]).controller('ConfPuntosController', ['NgMap', '$http', 'ServiceTables', 'storageService',
    function(NgMap, $http, ServiceTables, storageService) {
            var vm = this;
            vm.markers = [];
            vm.puntos = [];
            vm.zonas = [];
            vm.bahias = [];
            vm.objetoTabla = [];
            vm.vehiculos = [];
            vm.punto = {id:"", lat:"", lng:"", desc:"", nota:"", zonas:[]};
            vm.zona = {id:"-1", id_punto:"", desc:"", nota:"", selectzona:""};
            vm.bahia = {id:"-1", id_zona:"", desc:"", nota:"", selectbahia:""};
            vm.mapa = {radio:10000, carga:"", addressOrigin:[], solicitud:"", todos:false};
            vm.shape = {center:[], radius:10000};
            vm.date = new Date();
            vm.q ="";
            vm.mensaje = "";
            vm.id_selectpunto="";
            vm.id_selectzona="";
            vm.seleccion = false;
            
            vm.placeChanged = function() {
                vm.place = this.getPlace();
                vm.map.setCenter(vm.place.geometry.location);
                vm.map.setZoom(14);
            };
            
            vm.placeMarker = function(e) {
                vm.markers = [];
                vm.markers.push({position: [e.latLng.lat(), e.latLng.lng()]});
                vm.punto.lat = e.latLng.lat();
                vm.punto.lng = e.latLng.lng();
                vm.map.panTo(e.latLng);
            };
            
            vm.listaAllPuntos = function(){
                vm.puntos = [];
                vm.puntos = storageService.getPuntos();
                if(vm.puntos.length===0){
                    ServiceTables.listaAllPuntos().then(function(d) {
                        vm.puntos = d;
                        storageService.iniciarPuntos(d);
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
            vm.listaAllPuntos();

            vm.selectPunto = function(id){
                vm.zonas = [];
                for(var i=0; i < vm.puntos.length; i++){
                    if(vm.puntos[i].id === id){ 
                        vm.zonas = angular.copy(vm.puntos[i].zonas);
                        vm.zona.id_punto = id;
                         break;
                    }
                }
            };
 
            vm.selectZona = function(id){
                vm.bahia.id_zona = id;
                vm.bahias = [];
                for(var i=0; i < vm.zonas.length; i++){
                    console.log(vm.zonas[i]);
                    if(vm.zonas[i].id === id){ 
                        vm.bahias = angular.copy(vm.zonas[i].bahias);
                        break;
                    }
                }
            };
            
            vm.selectPuntoDet = function(id){
                vm.markers = [];
                alert(id);
                if(id!==null){
                    for(var i=0; i < vm.puntos.length; i++){
                        if(vm.puntos[i].id === id){ 
                            vm.punto = angular.copy(vm.puntos[i]);
                            vm.markers.push({position: [vm.punto.lat, vm.punto.lng]});
                            var LatLng = new google.maps.LatLng({lat: vm.punto.lat, lng: vm.punto.lng}); 
                            vm.map.panTo(LatLng);
                            vm.seleccion = true;
                            break;
                        }
                    }
                }else{
                    vm.punto = {id:"", lat:"", lng:"", desc:"", nota:"", zonas:[]};
                    vm.seleccion = false;
                }
            };
            
            vm.selectZonaDet = function(id){
                if(id!==""){
                    for(var i=0; i < vm.zonas.length; i++){
                        if(vm.zonas[i].id === id){ 
                            vm.zona = angular.copy(vm.zonas[i]);
                            break;
                        }
                    }
                }else{
                    vm.zona = {id:"-1", id_punto:"", desc:"", nota:"", selectzona:""};
                    vm.zona.id_punto = id_selectpunto;
                }
            };
            
            vm.selectBahiaDet = function(id){
                if(id!==""){
                    for(var i=0; i < vm.bahias.length; i++){
                        if(vm.bahias[i].id === id){ 
                            vm.bahia = angular.copy(vm.bahias[i]);
                            break;
                        }
                    }
                }else{
                    vm.bahia = {id:"-1", id_zona:"", desc:"", nota:"", selectbahia:""};
                }
            };
            
            vm.sendPunto = function(){
                if(vm.punto.lat ==="" || vm.punto.lng === "" || vm.punto.desc === "" || vm.punto.id === ""){
                    if(vm.punto.id === ""){
                        vm.mensaje = "Es obligatorio asignarle una identificación";
                    }else if(vm.punto.desc === ""){
                        vm.mensaje = "Es obligatorio colocarle algún nombre al punto";
                    }else{
                        vm.mensaje = "Debe seleccionar algún punto en el mapa";
                    }
                }else{
                    ServiceTables.SavePunto(vm.punto).then(function(d) {
                        if(d!==false){
                            vm.eliminarPunto(vm.punto.id);
                            vm.puntos.push(vm.punto);
                            storageService.updatePuntos(vm.puntos);
                            vm.punto = {id:"", lat:"", lng:"", desc:"", nota:"", zonas:[]};
                            vm.mensaje = "Se ejecuto operación con exito";
                        }else{
                            $window.location = '../../';
                        }
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
            vm.eliminarPunto = function(id){
                for(var i = 0; i < vm.puntos.length; i++){
                    if(vm.puntos[i].id === id){
                        vm.puntos.splice(i, 1);
                    }
                }
            };
            
            vm.sendZona = function(){
                if(vm.zona.desc === ""){
                    vm.mensaje = "Es obligatorio colocarle algún nombre a la zona";
                }else{
                    ServiceTables.SaveZona(vm.zona).then(function(d) {
                        if(d!==false){
                            vm.zonas.push(vm.zona);
                            vm.zona = {id:"-1", id_punto:"", desc:"", nota:"", selectzona:""};
                            vm.mensaje = "Se ejecuto mensaje con exito";
                        }else{
                            $window.location = '../../';
                        }
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
            vm.sendBahia = function(){
                if(vm.bahia.desc === ""){
                    vm.mensaje = "Es obligatorio colocarle algún nombre a la bahia";
                }else{
                    ServiceTables.SaveBahia(vm.bahia).then(function(d) {
                        if(d!==false){
                            vm.bahias.push(vm.bahia);
                            vm.bahia = {id:"-1", id_zona:"", desc:"", nota:"", select:""};
                            vm.mensaje = "Se ejecuto mensaje con exito";
                        }else{
                            $window.location = '../../';
                        }
                    },function(errResponse){
                       console.error('Error en sendPunto');
                   });
               }
            };
            
            
            NgMap.getMap().then(function(map) {
              vm.map = map;
            });

    
            vm.showDetail = function(e, vehiculo) {
                console.log(vehiculo);
                vm.vehiculo = vehiculo;
                vm.map.showInfoWindow('foo-iw', vehiculo.cod);
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
            };
            
            vm.cambiarCarga = function(carga){
                vm.mapa.carga = carga;
                vm.ReloadVehiculos();
            };
            
           
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

}]).controller('ConfEquiposController', ['$http', '$templateCache', '$timeout', '$alert', '$interval', 'ServiceTables', 'storageService',
function($http, $templateCache, $timeout, $alert, $interval, ServiceTables, storageService) {
    var vm = this;
    vm.options = {format:"YYYY/MM/DD HH:mm:ss", allowInputToggle:true};
    vm.dcargue=null;
    vm.ddescargue=null;
    vm.fecha_enturnado = null;
    vm.zonas = []
    vm.zona = {id:"", id_punto:"", desc:"", bahias:[]};
    vm.bahias = [];
    vm.bahia = {id:"", id_zona:"", desc:"", nota:""};
    vm.puntos = [];
    vm.punto = {id:"", desc:"", nota:"", zonas:[]};
    vm.equipos = [];
    vm.changes = [];
    vm.dispos = [];
    vm.conductores = [];
    vm.equiposconductores = [];
    vm.Remolques=[];
    vm.Cargas=[];
    vm.ticket={ticket:"", fecha:"", operacion:"", tipo_cargue:"", fecha_enturne:"", placa:"", remolque:"", 
    tipo_equipo:"", marca:"", modelo:"", referencia:"", trailer:"", poliza:"", soat:"", 
    tecno:"", zona:"", bahia:"", punto :"", npunto:"", nzona:"", nbahia:"", fecha_enturnado:"", fecha_terminada:"", turno:""};
    vm.reasignar = false;
    vm.equipo = {placa:"", tipocarga:"", n_tipocarga:"", tipoequipo:"", n_tipoequipo:"", lic_transito:"", trailer:"", lic_transito_r:"", 
    remolque:"", n_remolque:"", capacidad:"", unidad:"", marca:"", modelo:"", referencia:"", poliza:"", compania:"", 
    exp_poliza:"", vence_poliza:"", soat:"", exp_soat:"", vence_soat:"", tecno:"", exp_tecno:"", vence_tecno:"", propietario:"",
    poliza_hc:"", compania_hc:"", exp_poliza_hc:"", vence_poliza_hc:""};
    vm.conductor = {codigo: "", tipo_doc:"", doc:"", lic:"", exp_lic:"", vence_lic:"", nombre:"", 
    apellido:"", telefono:"", direccion:"", mail:"", contrasena:""};
    var stopTime;
    
    vm.llenarZonasBahias = function(){ // This would fetch the data on page change.
        ServiceTables.listaAllPuntos().then(function(d) {
            console.log(d);
            vm.puntos = d;  // data to be displayed on current page.
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };

    
    vm.nuevoVehiculo = function(){
        sendVehiculo.modal("show");
    };
    
    vm.nuevoConductor = function(){
        sendConductor.modal("show");
    };

    vm.getDataEquipos = function(){ // This would fetch the data on page change.
        vm.ticketsR = [];
        ServiceTables.listaEquipos().then(function(d) {
            if(d!=="sesion"){
                vm.equipos = d;  // data to be displayed on current page.
                storageService.iniciarEquipos(d);
            }else{
                $window.location = '../../';
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.verConductor = function(conductor){
      vm.conductor = angular.copy(conductor);  
      
      sendConductor.modal("show");
    };
    
    vm.verVehiculo = function(equipo){
        vm.equipo = angular.copy(equipo);
        vm.equipo.exp_poliza = new Date(vm.equipo.exp_poliza);
        vm.equipo.vence_poliza = new Date(vm.equipo.vence_poliza);
        vm.equipo.exp_poliza_hc = new Date(vm.equipo.exp_poliza_hc);
        vm.equipo.vence_poliza_hc = new Date(vm.equipo.vence_poliza_hc);
        vm.equipo.exp_soat = new Date(vm.equipo.exp_soat);
        vm.equipo.vence_soat =  new Date(vm.equipo.vence_soat);
        vm.equipo.exp_tecno = new Date(vm.equipo.exp_tecno);
        vm.equipo.vence_tecno = new Date(vm.equipo.vence_tecno);
        sendVehiculo.modal("show");
    };
    
    vm.getDataConductores = function(){ // This would fetch the data on page change.
        vm.ticketsA = []; 
        ServiceTables.listaConductores().then(function(d) {
            if(d!=="sesion"){
                console.log(d);
                vm.conductores = d;  // data to be displayed on current page.
                storageService.iniciarConductores(d);
            }else{
                $window.location = '../../';
            }
        },function(errResponse){
           console.error('Error en getDataConductores');
       });
    };
    
    vm.getDataEquiposConductores = function(page){ // This would fetch the data on page change.
        vm.ticketsT = []; 
        ServiceTables.listaEquiposConductores().then(function(d) {
            if(d!=="sesion"){
                vm.equiposconductores = d;  // data to be displayed on current page.
            }else{
                $window.location = '../../';
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.init = function(){
        vm.getDataEquipos();
        vm.getDataConductores();
        vm.getDataEquiposConductores();
    };
    
    vm.stopinit = function() {
      if (angular.isDefined(stopTime)) {
        $interval.cancel(stopTime);
        stopTime = undefined;
      }
    };
    
    vm.cambiarConductor = function(equipoconductor){
        for(var i = 0; i < vm.equiposconductores.length; i++){
            if(vm.equiposconductores[i].conductor === equipoconductor.conductor &&
                vm.equiposconductores[i].id !== equipoconductor.id){
                    var r = confirm("El conductor que desea asignar, esta asignado ya a la placa: " + vm.equiposconductores[i].placa + "\n\
                    ¿Desea segir con la nueva asignación?.");
                    if (r == true) {
                        if(vm.equiposconductores[i].en_servicio === '1'){
                            alert("El conductor que desea asignar esta cumpliendo un servicio en este momento.");
                            equipoconductor.conductor = "";
                        }else{
                            vm.equiposconductores[i].conductor = "";
                            equipoconductor.editar = true;
                        }
                    }else{
                        equipoconductor.conductor = "";
                    } 
                    break;
            }else{
                equipoconductor.editar = true;
            }
        }
        vm.addChanges(equipoconductor);
    };
    
    vm.addChanges = function(equipoconductor){
        console.log(equipoconductor.editar);
        if(!equipoconductor.editar){
            for(var i = 0; i < vm.changes.length; i++){
                if(vm.changes[i].placa === equipoconductor.placa){
                    vm.changes.splice(i, 1);
                    break;
                }
            }
        }else{
            equipoconductor.editar = true;
            for(var i = 0; i < vm.changes.length; i++){
                if(vm.changes[i].placa === equipoconductor.placa){
                    vm.changes.splice(i, 1);
                    break;
                }
            }
            vm.changes.push(equipoconductor);
        }
        console.log(vm.changes);
    };
    
    vm.addDisponibles = function(equipoconductor){
        console.log(equipoconductor.disponible);
        for(var i = 0; i < vm.dispos.length; i++){
            if(vm.dispos[i].placa === equipoconductor.placa){
                vm.dispos.splice(i, 1);
                break;
            }
        }
        vm.dispos.push(equipoconductor);
        console.log(vm.dispos);
    };
    
    vm.enviarChanges = function(){
        ServiceTables.SaveEquiposConductor(vm.changes).then(function(d) {
            if(d){
                alert("Se ejecuto el proceso correctamente");
                
            }else{
                alert("Ocurrio un error y no se ejecuto el proceso completo");
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.enviarDisponibles = function(){
        ServiceTables.SaveEquiposConductorDisp(vm.dispos).then(function(d) {
            if(d){
                alert("Se ejecuto el proceso correctamente");
                
            }else{
                alert("Ocurrio un error y no se ejecuto el proceso completo");
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.enviarVehiculo = function(){
        vm.equipo.exp_poliza = vm.equipo.exp_poliza.toString("yyyy/MM/dd");
        vm.equipo.vence_poliza = vm.equipo.vence_poliza.toString("yyyy/MM/dd");
        vm.equipo.exp_poliza_hc = vm.equipo.exp_poliza_hc.toString("yyyy/MM/dd");
        vm.equipo.vence_poliza_hc = vm.equipo.vence_poliza_hc.toString("yyyy/MM/dd");
        vm.equipo.exp_soat = vm.equipo.exp_soat.toString("yyyy/MM/dd");
        vm.equipo.vence_soat =  vm.equipo.vence_soat.toString("yyyy/MM/dd");
        vm.equipo.exp_tecno = vm.equipo.exp_tecno.toString("yyyy/MM/dd");
        vm.equipo.vence_tecno = vm.equipo.vence_tecno.toString("yyyy/MM/dd");
        ServiceTables.SaveVehiculos(vm.equipo).then(function(d) {
            if(d){
                alert("Se ejecuto el proceso correctamente");
                sendVehiculo.modal("show");
                vm.equipo = {placa:"", tipocarga:"", n_tipocarga:"", tipoequipo:"", n_tipoequipo:"", lic_transito:"", trailer:"", lic_transito_r:"", 
                remolque:"", n_remolque:"", capacidad:"", unidad:"", marca:"", modelo:"", referencia:"", poliza:"", compania:"", 
                exp_poliza:"", vence_poliza:"", soat:"", exp_soat:"", vence_soat:"", tecno:"", exp_tecno:"", vence_tecno:"", propietario:"",
                poliza_hc:"", compania_hc:"", exp_poliza_hc:"", vence_poliza_hc:""};
            }else{
                alert("Ocurrio un error y no se ejecuto el proceso completo");
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.sendConductor = function(){
        vm.conductor.exp_lic = vm.conductor.exp_lic.toString("yyyy/MM/dd");
        vm.conductor.vence_lic = vm.conductor.vence_lic.toString("yyyy/MM/dd");
        console.log(vm.conductor);
        ServiceTables.SaveConductores(vm.conductor).then(function(d) {
            if(d){
                alert("Se ejecuto el proceso correctamente");
                sendConductor.modal("show");
                vm.conductor = {codigo: "", tipo_doc:"", doc:"", lic:"", exp_lic:"", vence_lic:"", nombre:"", 
                apellido:"", telefono:"", direccion:"", mail:"", contrasena:""};
            }else{
                alert("Ocurrio un error y no se ejecuto el proceso completo");
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };    
    
    vm.enviarConductor = function(){
        vm.conductor.exp_lic = vm.conductor.exp_lic.toString("yyyy/MM/dd");
        vm.conductor.vence_lic = vm.conductor.vence_lic.toString("yyyy/MM/dd");
        ServiceTables.SaveConductor(vm.conductor).then(function(d) {
            if(d){
                alert("Se ejecuto el proceso correctamente");
            }else{
                alert("Ocurrio un error y no se ejecuto el proceso completo");
            }
        },function(errResponse){
           console.error('Error en getDataRegistradas');
       });
    };
    
    vm.changeStopTime = function(tabla){
        vm.stopinit();
        
        if(tabla===1){
            vm.getDataRegistradas(vm.pagenoR);
        }else if(tabla===2){
            vm.getDataAsignadas(vm.pagenoA);
        }else if(tabla===3){
            vm.getDataTerminadas(vm.pagenoT);
        }

        stopTime = $interval(function() {
          if(tabla===1){
              vm.getDataRegistradas(vm.pagenoR);
          }else if(tabla===2){
              vm.getDataAsignadas(vm.pagenoA);
          }else if(tabla===3){
              vm.getDataTerminadas(vm.pagenoT);
          }
        }, 30 * 1000);
    };

    vm.llenarZonasBahias();
    vm.init();
    
    vm.formatDate = function(date){
        var dateOut = new Date(date);
        return dateOut;
    };
    
    
    
    vm.asignarTickets = function(ticket) {
        vm.ticket = ticket;
        vm.zonas = [];
        for(var i=0; i < vm.puntos.length; i++){
            if(vm.puntos[i].id===vm.ticket.punto){
                vm.zonas = vm.puntos[i].zonas;
                break;
            }
        }
        Asignacion.modal("show");
    };
    
    vm.sendAsignacion = function(){ // This would fetch the data on page change.
            vm.ticket.fecha_enturnado = new Date(vm.fecha_enturnado).toString("yyyy-MM-dd HH:mm:ss");
            $http.post("../asignTurno", vm.ticket).success(function(response){ 
                //ajax request to fetch data into vm.data
                console.log(response);
                if(response!=="false"){
                    vm.init();
                    Asignacion.modal("hide");
                }
            });
        };
        
    vm.selectZonas = function(id){
        vm.bahias = [];
        for(var i = 0; i < vm.zonas.length; i++){
            if(vm.zonas[i].id === id) {
               vm.bahias = angular.copy(vm.zonas[i].bahias);
               break;
            }
        }
    };

    
    vm.reasignarTickets = function(ticket){
        console.log("entro aqui");
        vm.reasignar = true;
        vm.ticket = ticket;
        vm.fecha_enturnado = vm.ticket.fecha_enturnado;
        console.log(vm.ticket);
        vm.zonas = [];
        for(var i=0; i < vm.puntos.length; i++){
            if(vm.puntos[i].id===vm.ticket.punto){
                vm.zonas = vm.puntos[i].zonas;
                break;
            }
        }
        Asignacion.modal("show");

    };
    
    
    vm.cambiarTipo = function(tipo){
        vm.Remolques=[];
        vm.Cargas=[];
        for(var i = 0; i < vm.TipoRemolque.length; i++){
            if(vm.TipoRemolque[i].Carga===tipo){
                vm.Remolques = vm.TipoRemolque[i].Remolques;
                break;
            }

        }
        for(var i = 0; i < vm.Carga.length; i++){
            if(vm.Carga[i].TipoCarga===tipo){
                vm.Cargas = vm.Carga[i].Cargas;
                break;
            }

        }

    };

    vm.cambiarRemolque = function(){
        var flag = false;
        var flag2 = false;
        console.log(vm.mapa.remolques);
        for(var i = 0; i < vm.mapa.remolques.length; i++){

            if(vm.mapa.remolques[i] === 9){
                flag2 = true;
            }

            if(vm.mapa.remolques[i] === 9 && vm.Cargas.length === 6){
                vm.Cargas.splice(4,1);
                flag = true;
                break;
            }
        }

        if(flag===false && flag2===false){
            vm.Cargas = [];
            for(var i = 0; i < vm.Carga.length; i++){
                if(vm.Carga[i].TipoCarga===vm.mapa.tipocarga){
                    vm.Cargas = vm.Carga[i].Cargas;
                    console.log(vm.Cargas);
                    console.log(vm.Carga[i].Cargas);
                    break;
                }

            }
        }
    };


    vm.TipoCarga = [
        {"ID":2,"Value":"Liquida"},
        {"ID":5,"Value":"Refrigerada"},
        {"ID":6,"Value":"Seca"}
    ];

    
    vm.TipoDoc = [
        {"ID":"CC","Value":"Cedula de ciudadania"},
        {"ID":"CE","Value":"Cedula de extrangeria"},
        {"ID":"PS","Value":"Pasaporte"}
    ];
    
    vm.TipoRemolque = [
        {"Carga":6,
         "Remolques": [
            {"ID":1, Value:"Carbonera"},
            {"ID":2, Value:"Carbonera con varilla"},
            {"ID":3, Value:"Carroceria"},
            {"ID":4, Value:"Plancha"},
            {"ID":5, Value:"Porta contenedor"},
            {"ID":6, Value:"Tolva"},
            {"ID":7, Value:"Van"}
        ]
        },
        {"Carga":2,
         "Remolques" : [
            {"ID":8, Value:"Tanque acero inoxidable"},
            {"ID":9, Value:"Tanque aluminio"},
            {"ID":10, Value:"Tanque lamina"}
         ]
        }
    ];

    vm.TipoEquipo = [
        {"ID": 1,"Value":"Camión 3.5 Ton"},
        {"ID": 2,"Value":"Camión 7 Ton"},
        {"ID": 3,"Value":"Camión 10.5 Ton"},
        {"ID": 4,"Value":"Camión Sencillo S2"},
        {"ID": 5,"Value":"Camión Sencillo S3"},
        {"ID": 6,"Value":"Patineta"},
        {"ID": 7,"Value":"Tractomula 3S2"},
        {"ID": 8,"Value":"Tractomula 3S3"}
    ];

    vm.Carga = [
        {   
            "TipoCarga":2,
            "Cargas":[
                {"ID": 1,"Value":"Aceites y grasas"},
                {"ID": 2,"Value":"ACPM"},
                {"ID": 3,"Value":"Alcohol y derivados"},
                {"ID": 4,"Value":"Gasolinas"},
                {"ID": 5,"Value":"JP-1 (Jet Full)"},
                {"ID": 6,"Value":"Petróleo"}
            ]
        },
        {   
            "TipoCarga":6,
            "Cargas":[
                {"ID": 7,"Value":"Bolsones"},
                {"ID": 8,"Value":"Bolsas"},
                {"ID": 9,"Value":"Contenedor"},
                {"ID": 10,"Value":"Granel"},
                {"ID": 11,"Value":"Pallets"}
            ]
        }
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
        
    vm.dtOptionsAsign = {
        bAutoWidth:true,
        stateSave: true,
        destroy: true,
        DisplayLength: 100
    };
       
    vm.dtOptionsTerm = {
        bAutoWidth:true,
        stateSave: true,
        destroy: true,
        DisplayLength: 100
    };
    
    vm.dtOptionsRegs = {
        bAutoWidth:true,
        stateSave: true,
        destroy: true,
        columnDefs: [ 
            {
            targets: 3,
            orderable: false
            }
        ],
        DisplayLength: 100
    };
        
}]);