/***
 * Copyright (c) 2013 Cenobit Technologies Inc. - http://cenobit.es/
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.caelum.vraptor.mustache.example.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.mustache.example.domain.Item;
import br.com.caelum.vraptor.mustache.interceptor.download.MustacheDownload;

@Resource
public class TemplateController {

	private final Result result;
	private ServletContext servletContext;

	public TemplateController(Result result, ServletContext servletContext) {
		this.result = result;
		this.servletContext = servletContext;
	}

	@Path("/download")
	public Download download() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(1L, "VRaptor", "http://vraptor.caelum.com.br/", 22.50, true));
		items.add(new Item(2L, "Mustache", "http://mustache.github.io/", 34.40, true));
		items.add(new Item(3L, "Cenobit.es", "http://cenobit.es/", 6.66, true));
		
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("items", items);

		File file = new File(this.servletContext.getRealPath("/WEB-INF/classes/templates/template.mustache"));
		return new MustacheDownload(file, scope, "application/vnd.ms-excel", "template.cvs");
	}
}
