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

package org.distributed.uml.core;


import javax.annotation.PostConstruct;

import org.distributed.uml.services.EtherpadGatewayResources;
import org.distributed.uml.services.PlantUmlResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

/**
 * main daemon
 */
@Component
@PropertySources({
	@PropertySource(value = "classpath:server.properties", ignoreResourceNotFound = true),
	@PropertySource(value = "file://${distributed-uml.user.dir}/config.properties", ignoreResourceNotFound = true)
})
public class DistributedUmlServerDaemon {
	protected Logger logger = LoggerFactory.getLogger(DistributedUmlServerDaemon.class);

	@Autowired
	Environment env;

	@Autowired
	EtherpadGatewayResources etherpadGatewayResources;
	
	@Autowired
	PlantUmlResources plantUmlResources;
	
	protected ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * start component
	 */
	@PostConstruct
	public void server() {
		
		for(String key : ImmutableList.of(
				"distributed-uml.user.dir",
				"distributed-uml.log.dir",
				"distributed-uml.server.url")) {
			logger.info("{} = {}", key, env.getProperty(key));
		}
		
		String iface = env.getProperty("distributed-uml.server.interface");
		int port = Integer.parseInt(env.getProperty("distributed-uml.server.port"));
		spark.Spark.ipAddress(iface);
		spark.Spark.threadPool(Integer.parseInt(env.getProperty("distributed-uml.server.pool.thread","8")));
		
		/**
		 * port
		 */
		spark.Spark.port(port);

		spark.Spark.staticFileLocation("plantuml");
		spark.Spark.staticFiles.expireTime(0);

		/**
		 * mount all resources
		 */
		etherpadGatewayResources.mount();
		plantUmlResources.mount();

		spark.Spark.after((request, response) -> {
		    response.header("Content-Encoding", "gzip");
		});
	}
}
