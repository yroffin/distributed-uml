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

angular.module('distributed.uml.directives.pad', ['distributed.uml.services', 'distributed.uml.services.etherpad'])
.controller('distributedUmlWidgetPadCtrl',
		[ '$scope', '$sce', '$log', '$stateParams', 'distributedUmlWidgetPadService', 'etherpadService', 'toastService',
	function($scope, $sce, $log, $stateParams, distributedUmlWidgetPadService, etherpadService, toastService){
    /**
     * loading
     */
    $scope.load = function(id) {
    	distributedUmlWidgetPadService.get(id, function(pad) {
    		$scope.pad=pad;
    		$scope.trusted=$sce.trustAsResourceUrl("http://192.168.1.12:9001/p/" + pad.name + "?showControls=true&showChat=true&showLineNumbers=true&useMonospaceFont=false");
    	});
		$log.info('distributedUmlCtrl loaded');
    }
}])
.factory('distributedUmlWidgetPadService', [ 'Restangular', function(Restangular) {
	return {
		get: function(id, handler, errors) {
			Restangular.all('/api').one('/etherpad', id).get().then(handler,function(errors){failure(errors);});
		}
	}
}])
/**
 * widget distributed-uml-widget-Pad
 */
.directive('distributedUmlWidgetPad', [ '$log', '$stateParams', function ($log, $stateParams) {
  return {
    restrict: 'E',
    controller: 'distributedUmlWidgetPadCtrl',
    templateUrl: '/js/directives/Pad/distributed-uml-widget-pad.html',
    link: function(scope, element, attrs) {
    	$log.info('distributed-uml-widget-pad loaded', $stateParams);
    	scope.load($stateParams.id);
    }
  }
}])
;
