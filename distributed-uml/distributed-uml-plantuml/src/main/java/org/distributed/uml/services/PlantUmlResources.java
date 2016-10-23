/**
 *  Copyright 2015 Yannick Roffin
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.distributed.uml.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * main daemon
 */
@Component
@PropertySources({
	@PropertySource(value = "classpath:server.properties", ignoreResourceNotFound = true),
	@PropertySource(value = "file://${distributed-uml.user.dir}/config.properties", ignoreResourceNotFound = true)
})
public class PlantUmlResources {
	protected Logger logger = LoggerFactory.getLogger(PlantUmlResources.class);

	@Autowired
	Environment env;

	@Autowired
	PlantUmlService plantUmlService;
	
	protected ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * mount all resources
	 */
	public void mount() {
		/**
		 * Build plantuml uml api
		 */
		spark.Spark.post("/api/plantuml", (req, res) -> {
			return plantUmlService.svg(req.body());
		});
	}
}
