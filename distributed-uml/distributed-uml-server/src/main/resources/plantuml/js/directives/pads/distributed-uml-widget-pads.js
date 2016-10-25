/* 
 * Copyright 2014 Yannick Roffin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* Directives */

angular.module('distributed.uml.directives.pads', ['distributed.uml.services', 'distributed.uml.services.etherpad'])
.controller('distributedUmlWidgetPadsCtrl',
		[ '$scope', '$log', '$stateParams', 'distributedUmlWidgetPadsService', 'etherpadService', 'toastService',
	function($scope, $log, $stateParams, distributedUmlWidgetPadsService, etherpadService, toastService){
    /**
     * loading
     */
    $scope.load = function() {
    	$scope.pads=[];
    	distributedUmlWidgetPadsService.findAll(function(pad) {
    		$scope.pads=pad.plain();
    	});
		$log.info('distributedUmlCtrl loaded');
    }
}])
.factory('distributedUmlWidgetPadsService', [ 'Restangular', function(Restangular) {
	return {
		findAll: function(handler, errors) {
			Restangular.all('/api').all('/etherpad').getList().then(handler,function(errors){failure(errors);});
		}
	}
}])
/**
 * widget distributed-uml-widget-pads
 */
.directive('distributedUmlWidgetPads', [ '$log', '$stateParams', function ($log, $stateParams) {
  return {
    restrict: 'E',
    controller: 'distributedUmlWidgetPadsCtrl',
    templateUrl: '/js/directives/pads/distributed-uml-widget-pads.html',
    link: function(scope, element, attrs) {
    	$log.info('distributed-uml-widget-pads loaded');
    	scope.load();
    }
  }
}])
;
