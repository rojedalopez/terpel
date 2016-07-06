'use strict';

angular.module('MyApp.Servicios', []).controller('ServiciosController', ['NgMap', '$http', function(NgMap, $http) {
            var vm = this;
            vm.vehiculos = [];
            vm.vehiculo = {cod:"", ult_reporte:"", placa:"", position:[]};
            vm.servicio={addressOrigin:[], nameOrigin:"",addressDestination:[],nameDestination:"",carga:"",
            dateCargue:"", dcargue:"", dateDescargue:"", ddescargue:"",equipos:0,orden:"", kms:"", time:""};
            vm.enviado = false;
            vm.date = new Date();
    
            vm.placeChangedOrigin = function() {
                vm.servicio.addressOrigin = this.getPlace().geometry.location;
                vm.placeO = this.getPlace();
            };
            
            vm.placeChangedDestination = function() {
                vm.servicio.addressDestination = this.getPlace().geometry.location;
                vm.placeD = this.getPlace();
            };
            
            NgMap.getMap().then(function(map) {
              vm.map = map;
            });
            
            vm.showDetail = function(e, vehiculo) {
                console.log(vehiculo);
                vm.vehiculo = vehiculo;
                vm.map.showInfoWindow('foo-iw', vehiculo.cod);
            };

            vm.sendServicio = function(){
                vm.servicio.dcargue = new Date(vm.servicio.dateCargue).toString("yyyy-MM-dd HH:mm:ss");
                vm.servicio.ddescargue = new Date(vm.servicio.dateDescargue).toString("yyyy-MM-dd HH:mm:ss");
                $http.post("saveServicio", vm.servicio).success(function(d){
                   if(d!=="false"){
                        vm.vehiculos = d;
                        vm.map.setCenter(vm.servicio.addressOrigin);
                        vm.map.setZoom(18);
                        vm.enviado = true;
                   }
                });
            };
            
            vm.ReloadVehiculos = function(){
                $http.post("list_vehiculos", vm.servicio).success(function(d){
                    vm.vehiculos = [];
                    if(d!=="false"){
                        vm.vehiculos = d;
                        vm.map.setCenter(vm.servicio.addressOrigin);
                        vm.map.setZoom(18);
                   }
                });
            };
            
            vm.TipoCarga = [
                {"ID":1,"Value":"Carga Refrigerada"},
                {"ID":2,"Value":"Liquidos Inflamables"},
                {"ID":3,"Value":"Cama Baja"},
                {"ID":4,"Value":"Container"}
            ];
}]).directive('datetimepicker', [
            '$timeout',
            function($timeout) {
              return {
                require: '?ngModel',
                restrict: 'EA',
                scope: {
                  datetimepickerOptions: '@',
                  onDateChangeFunction: '&',
                  onDateClickFunction: '&'
                },
                link: function($scope, $element, $attrs, controller) {
                  $element.on('dp.change', function() {
                    $timeout(function() {
                      var dtp = $element.data('DateTimePicker');
                      controller.$setViewValue(dtp.date());
                      $scope.onDateChangeFunction();
                    });
                  });

                  $element.on('click', function() {
                    $scope.onDateClickFunction();
                  });

                  controller.$render = function() {
                    if (!!controller && !!controller.$viewValue) {
                      var result = controller.$viewValue;
                      $element.data('DateTimePicker').date(result);
                    }
                  };

                  $element.datetimepicker($scope.$eval($attrs.datetimepickerOptions));
                }
              };
            }
   ]).directive('reload', ['$interval',
      function($interval) {
        // return the directive link function. (compile function not needed)
        return function(scope, element, attrs) {
          var stopTime; // so that we can cancel the time updates
          
          function updateTime() {
                if(scope.ctrl.enviado){
                    scope.ctrl.ReloadVehiculos(); 
                }
          }  
            
          // watch the expression, and update the UI on change.
          scope.$watch(attrs.myCurrentTime, function() {
              updateTime();
          });

          stopTime = $interval(updateTime, 3000);

          // listen on DOM destroy (removal) event, and cancel the next UI update
          // to prevent updating time after the DOM element was removed.
          element.on('$destroy', function() {
            $interval.cancel(stopTime);
          });
        };
}]);