'use strict';

angular.module('MyApp.Servicios', []).controller('ServiciosController', function(NgMap) {
            var vm = this;
            vm.servicio={addressOrigin:[], nameOrigin:"",addressDestination:[],nameDestination:"",carga:"",dateCargue:"", dateDescargue:"",equipos:0,orden:""};
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
            
        vm.TipoCarga = [
            {"ID":1,"Value":"Carga Refrigerada"},
            {"ID":2,"Value":"Liquidos Inflamables"},
            {"ID":3,"Value":"Cama Baja"},
            {"ID":4,"Value":"Container"}
        ];
}).directive('datetimepicker', [
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
   ]);