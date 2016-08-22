'use strict';

angular.module('myApp',[
    'MyApp.Servicios',
    'MyApp.Camiones',
    'datatables',
    'angularValidator',
    'angularUtils.directives.dirPagination',
    'ngMap',
    'ae-datetimepicker',
    'mgcrea.ngStrap',
    'ui.bootstrap'
]).factory('ServiceTables', ['$http', '$q', function($http, $q){
    return {
        SavePunto: function(punto){
            return $http.post('../savePunto', punto).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando punto ' +errResponse);
                return $q.reject(errResponse);
            });
	},
        SaveZona: function(zona){
            return $http.post('../saveZona', zona).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando zona ' +errResponse);
                return $q.reject(errResponse);
            });
	},
        SaveVehiculos: function(equipo){
            return $http.post('../saveVehiculo', equipo).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando equipo ' +errResponse);
                return $q.reject(errResponse);
            });
	},
        SaveConductores: function(conductor){
            return $http.post('../saveConductor', conductor).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando equipo ' +errResponse);
                return $q.reject(errResponse);
            });
	},
        SaveBahia: function(bahia){
            return $http.post('../saveBahia', bahia).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando bahia ' +errResponse);
                return $q.reject(errResponse);
            });
	},SaveServicioEnturne: function(servicio){
            return $http.post('../saveServicioEnturne', servicio).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando bahia ' +errResponse);
                return $q.reject(errResponse);
            });
	},SaveEquiposConductor: function(equiposconductores){
            return $http.post('../asingEquipoConductor', equiposconductores).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando bahia ' +errResponse);
                return $q.reject(errResponse);
            });
	},SaveEquiposConductorDisp: function(equiposconductores){
            return $http.post('..dispEquipoConductor', equiposconductores).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando bahia ' +errResponse);
                return $q.reject(errResponse);
            });
	},SaveTakeSolicitud: function(datos){
            return $http.post('../takeSolicitud', datos).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error guardando bahia ' +errResponse);
                return $q.reject(errResponse);
            });
	},
        ListaEnturnesEstados: function(busqueda){
            return $http.post('../list_enturnes_estados', busqueda).then(function(response){
                console.log(response);
                return response.data;
            },function(errResponse){
		console.error('Error obteniendo lista de enturnes ' +errResponse);
		return $q.reject(errResponse);
            });
	},
        listPuntos: function(busqueda) {
            return $http.post('../list_puntos', busqueda).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
        },listaVehiculosDispo: function() {
            return $http.post('../list_all_vehiculos').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaVehiculosLogycus: function(mapa) {
            return $http.post('../list_vehiculos_disp', mapa).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaVehiculosActivos: function() {
            return $http.post('../list_act_vehiculos').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},
        listaAllPuntos: function() {
            console.log("entro despues aqui");
            return $http.post('../list_all_puntos').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaCargues: function() {
            return $http.post('../list_cargues').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaEquiposConductores: function() {
            return $http.post('../list_equipoconductor').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaEquipos: function() {
            return $http.post('../list_equipos').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaConductores: function() {
            return $http.post('../list_conductores').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaFletes: function() {
            return $http.post('../list_fletes').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaSolicitudesTransportador: function() {
            return $http.post('../list_solicitudes_transportador').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaConductoresTransportador: function(solicitud) {
            return $http.post('../list_vehiculos_asignar', solicitud).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaServiciosActivos: function() {
            return $http.post('../list_servicios_act').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},loginEmpresa: function(usuario) {
            return $http.post('login_empresa', usuario).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	}
    };
}]).factory('storageService', function () {  
    var listaRetorno = {};  
    return {  
        list: listaRetorno,  
        updatePuntos: function (PuntosArr) {  
            if (window.sessionStorage && PuntosArr) {  
                //Local Storage to add Data  
                sessionStorage.setItem("puntos", angular.toJson(PuntosArr));  
            }  
            listaRetorno = PuntosArr;  

        },  
        getPuntos: function () {  
            //Get data from Local Storage  
            listaRetorno = angular.fromJson(sessionStorage.getItem("puntos"));  
            return listaRetorno ? listaRetorno : [];  
        },
        iniciarPuntos: function (array) {
            if (window.sessionStorage) {
                sessionStorage.setItem("puntos", angular.toJson(array));  
            }
        },  
        updateConductores: function (ConductorArr) {  
            if (window.sessionStorage && ConductorArr) {  
                //Local Storage to add Data  
                sessionStorage.setItem("conductores", angular.toJson(ConductorArr));  
            }  
            listaRetorno = ConductorArr;  

        },
        getConductores: function () {  
            //Get data from Local Storage  
            listaRetorno = angular.fromJson(sessionStorage.getItem("conductores"));  
            return puntosList ? puntosList : [];  
        },
        iniciarConductores: function (array) {
            if (window.sessionStorage) {
                for(var i=0; i < array.length; i++){
                    //Local Storage to add Data  
                    sessionStorage.setItem("conductores", angular.toJson(array));  
                }  
            }
        },  
        updateEquipos: function (EquiposArr) {  
            if (window.sessionStorage && EquiposArr) {  
                //Local Storage to add Data  
                sessionStorage.setItem("equipos", angular.toJson(EquiposArr));  
            }  
            listaRetorno = EquiposArr;  

        },  
        getEquipos: function () {  
            //Get data from Local Storage  
            listaRetorno = angular.fromJson(sessionStorage.getItem("equipos"));  
            return listaRetorno ? listaRetorno : [];  
        },
        iniciarEquipos: function (array) {
            if (window.sessionStorage) {
                for(var i=0; i < array.length; i++){
                    //Local Storage to add Data  
                    sessionStorage.setItem("equipos", angular.toJson(array));  
                }  
            }
        },
        updateFletes: function (FletesArr) {  
            if (window.sessionStorage && FletesArr) {  
                //Local Storage to add Data  
                sessionStorage.setItem("fletes", angular.toJson(FletesArr));  
            }  
            listaRetorno = FletesArr;  

        },  
        getFletes: function () {  
            //Get data from Local Storage  
            listaRetorno = angular.fromJson(sessionStorage.getItem("fletes"));  
            return listaRetorno ? listaRetorno : [];  
        },
        iniciarFletes: function (array) {
            if (window.sessionStorage) {
                    //Local Storage to add Data  
                sessionStorage.setItem("fletes", angular.toJson(array));  
            }
        },
        updateCargues: function (CarguesArr) {  
            if (window.sessionStorage && CarguessArr) {  
                //Local Storage to add Data  
                sessionStorage.setItem("cargues", angular.toJson(CarguesArr));  
            }  
            listaRetorno = CarguesArr;  

        },  
        getCargues: function () {  
            //Get data from Local Storage  
            listaRetorno = angular.fromJson(sessionStorage.getItem("cargues"));  
            return listaRetorno ? listaRetorno : [];  
        },
        iniciarCargues: function (array) {
            if (window.sessionStorage) {
                    //Local Storage to add Data  
                sessionStorage.setItem("cargues", angular.toJson(array));  
            }
        }
    };  

});  


