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

angular.module('distributed.uml.directives.plantuml', ['distributed.uml.services', 'distributed.uml.services.etherpad'])
.controller('distributedUmlWidgetPlantumlCtrl',
		[ '$scope', '$log', '$stateParams', 'distributedUmlWidgetPlantumlService', 'etherpadService', 'toastService',
	function($scope, $log, $stateParams, distributedUmlWidgetPlantumlService, etherpadService, toastService){
	$scope.starUml = "essai";
    /**
     * watcher
     */
    $scope.starUmlWatcher = function() {
		return $scope.starUml;
    }
    /**
     * submit
     */
    $scope.submit = function(newValue, oldValue, scope) {
		etherpadService.get(function (padValue) {
			distributedUmlWidgetPlantumlService.post(padValue,function(result){
				$log.info('submit', result);
				$( "#starUml" ).html( result );
			}, function() {
				
			});
		});
    }
    /**
     * $watch for uml impact
     */
    $scope.$watch($scope.starUmlWatcher, $scope.submit);
    /**
     * loading
     */
    $scope.load = function() {
		$log.info('plantumlCtrl loaded');
    }
}])
.factory('distributedUmlWidgetPlantumlService', [ 'Restangular', function(Restangular) {
	return {
		post: function(element, handler, errors) {
			Restangular.all('/api').all('/plantuml').post(element).then(handler,function(errors){failure(errors);});
		}
	}
}])
/**
 * widget distributed-uml-widget-plantuml
 */
.directive('distributedUmlWidgetPlantuml', [ '$log', '$stateParams', function ($log, $stateParams) {
  return {
    restrict: 'E',
    controller: 'distributedUmlWidgetPlantumlCtrl',
    templateUrl: '/js/directives/plantuml/distributed-uml-widget-plantuml.html',
    link: function(scope, element, attrs) {
    	$log.info('distributed-uml-widget-plantuml loaded');
    }
  }
}])
;
