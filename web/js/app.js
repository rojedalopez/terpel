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
        SaveHV: function(hoja_vida){
            var fd = new FormData();
            fd.append("file", hoja_vida.file);
            
            return $http.post('../upload_hv', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function(response){
                console.log(response);
                return response.data;
            },function(errResponse){
                console.error('Error while updating paper ' +errResponse);
                return $q.reject(errResponse);
            });
	},
        getDatosUser: function(){
            return $http.post('../usuario_info').then(function(response){
                console.log(response);
                return response.data;
            },function(errResponse){
		console.error('Error while updating paper ' +errResponse);
		return $q.reject(errResponse);
            });
	},
        verOferta: function(id) {
            return $http.post('../vista_oferta', {"id":id}).then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
        },listaDptosCiudad: function() {
            return $http.post('../assets/colombia.json').then(function(response){
                return response.data;
            },function(errResponse){
                console.error('Error while fetching expenses');
                return $q.reject(errResponse);
            });
	}
    };
}]);


