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

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.distributed.uml.exception.TechnicalException;
import org.distributed.uml.model.PadBean;
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
	 * get pad
	 * @param id
	 * @return PadBean
	 * @throws TechnicalException
	 */
	public PadBean get(String id) throws TechnicalException {
		EPLiteClient client = new EPLiteClient("http://192.168.1.12:9001", "a7d14330f5704ecb95fad7716d1d51e60ede7dc2272ce1c91bbbed0a30e6746f");

		PadBean pad = new PadBean(id);
		
		// Get pad text
		pad.text = client.getText(id).get("text").toString();
		try {
			pad.base64 = encode(pad.text);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}

		return pad;
	}

	/**
	 * internal method
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private static String encode(String value) throws Exception {
	      return Base64.getEncoder().encodeToString(value.getBytes());
	}

	/**
	 * find all
	 * @return List<PadBean>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<PadBean> findAll() throws TechnicalException {
		EPLiteClient client = new EPLiteClient("http://192.168.1.12:9001", "a7d14330f5704ecb95fad7716d1d51e60ede7dc2272ce1c91bbbed0a30e6746f");

		// init result
		List<PadBean> result = new ArrayList<PadBean>();
		
		// Get list of all pad ids
		@SuppressWarnings("rawtypes")
		Map pads = client.listAllPads();
		for(String item : (List<String>) pads.get("padIDs")) {
			PadBean bean = new PadBean(item);
			result.add(bean);
			bean.text = client.getText(item).get("text").toString();
			try {
				bean.base64 = encode(bean.text);
			} catch (Exception e) {
				throw new TechnicalException(e);
			}
		}
		
		return result;
	}
}
