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

angular.module('distributed.uml.directives.home', ['distributed.uml.services','distributed.uml.services.etherpad'])
.controller('homeWidgetCtrl',
		[ '$scope', '$log', '$stateParams', 'distributedUmlWidgetHomeService', 'toastService',
	function($scope, $log, $stateParams, distributedUmlWidgetHomeService, toastService){
    /**
     * loading
     */
    $scope.load = function() {
		$log.info('homeWidgetCtrl loaded');
    }
}])
.factory('distributedUmlWidgetHomeService', [ function() {
	return {
	}
}])
/**
 * widget distributed-uml-widget-home
 */
.directive('distributedUmlWidgetHome', [ '$log', '$stateParams', function ($log, $stateParams) {
  return {
    restrict: 'E',
    templateUrl: '/js/directives/home/distributed-uml-widget-home.html',
    link: function(scope, element, attrs) {
		$log.info('distributed-uml-widget-home loaded');
    }
  }
}])
;
