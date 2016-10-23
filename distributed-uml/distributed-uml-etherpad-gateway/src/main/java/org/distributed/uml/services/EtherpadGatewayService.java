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

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.distributed.uml.exception.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import net.gjerull.etherpad.client.EPLiteClient;

/**
 * main daemon
 */
@Service
public class EtherpadGatewayService {
	protected Logger logger = LoggerFactory.getLogger(EtherpadGatewayService.class);

	@Autowired
	Environment env;
	
	/**
	 * init
	 */
	@PostConstruct
	public void init() {
	}

	/**
	 * render as svg
	 * @param payload
	 * @return String
	 * @throws TechnicalException
	 */
	public String create(String payload) throws TechnicalException {
		EPLiteClient client = new EPLiteClient("http://localhost:9001", "a7d14330f5704ecb95fad7716d1d51e60ede7dc2272ce1c91bbbed0a30e6746f");

		// Create pad and set text
		client.createPad("my_pad");
		client.setText("my_pad", "foo!!");
		return "";
	}

	/**
	 * render as svg
	 * @param payload
	 * @return String
	 * @throws TechnicalException
	 */
	public String get(String payload) throws TechnicalException {
		EPLiteClient client = new EPLiteClient("http://192.168.1.12:9001", "a7d14330f5704ecb95fad7716d1d51e60ede7dc2272ce1c91bbbed0a30e6746f");

		// Get pad text
		String text = client.getText("my_pad").get("text").toString();
		return text;
	}

	/**
	 * render as svg
	 * @param payload
	 * @return String
	 * @throws TechnicalException
	 */
	public String findAll(String payload) throws TechnicalException {
		EPLiteClient client = new EPLiteClient("http://192.168.1.12:9001", "a7d14330f5704ecb95fad7716d1d51e60ede7dc2272ce1c91bbbed0a30e6746f");

		// Get list of all pad ids
		Map result = client.listAllPads();
		List padIds = (List) result.get("padIDs");
		
		return result.toString();
	}
}
