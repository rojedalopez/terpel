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
	},
        ListaEnturnesEstados: function(){
            return $http.post('../list_enturnes_estados').then(function(response){
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
        },listaVehiculosDispo: function(criterios) {
            return $http.post('../list_vehiculos_disp_e', criterios).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	},listaAllPuntos: function() {
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
	}
    };
}]);


