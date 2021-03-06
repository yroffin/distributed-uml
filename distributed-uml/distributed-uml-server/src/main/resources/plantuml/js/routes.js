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

'use strict';

/* Controllers */

angular.module('distributed.uml.routes',['distributed.uml.config'])
    .config( ['$urlRouterProvider', function($urlRouterProvider) {
        /**
         * default state
         */
        $urlRouterProvider.otherwise('/home');
    }])
    .config(['$stateProvider', function($stateProvider) {
        /**
         * now set up the state
         */
        $stateProvider
        .state('home', {
            url: '/home',
            template: '<distributed-uml-widget-home></distributed-uml-widget-home>'
        })
        .state('pads', {
            url: '/pads',
            template: '<distributed-uml-widget-pads></distributed-uml-widget-pads>'
        })
        .state('pad-by-id', {
            url: '/pads/:id',
            template: '<distributed-uml-widget-pad></distributed-uml-widget-pad>'
        })
        ;
    }]);
