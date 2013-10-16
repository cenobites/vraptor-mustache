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
package br.com.caelum.vraptor.mustache.interceptor.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

/**
 * Handles download by reading from a mustache template.
 * 
 * @author nycholas Nycholas de Oliveira e Oliveira <nycholas@gmail.com>
 * 
 * @see Mustache
 * @see FileDownload
 */
public class MustacheDownload implements Download {

	private final File template;
	private final Object scope;
	private final String contentType;
	private final String fileName;
	private final boolean doDownload;
	private final long size;

	public MustacheDownload(File template, Object scope, String contentType, String fileName) {
		this(template, scope, contentType, fileName, false, 0);
	}

	public MustacheDownload(File template, Object scope, String contentType, String fileName, boolean doDownload,
			long size) {
		this.template = template;
		this.scope = scope;
		this.size = size;
		this.contentType = contentType;
		this.fileName = fileName;
		this.doDownload = doDownload;
	}

	public void write(HttpServletResponse response) throws IOException {
		this.writeDetails(response);

		String fileContent = this.fileContent(this.template);
		Reader reader = new StringReader(fileContent);
		Mustache compile = new DefaultMustacheFactory().compile(reader, this.fileName);

		OutputStream out = response.getOutputStream();
		Writer writer = new OutputStreamWriter(out);

		compile.execute(writer, this.scope).flush();
	}

	private void writeDetails(HttpServletResponse response) {
		if (this.contentType != null) {
			String contentDisposition = String.format("%s; filename=%s", this.doDownload ? "attachment" : "inline",
					this.fileName);
			response.setHeader("Content-disposition", contentDisposition);
			response.setHeader("Content-type", this.contentType);
		}
		if (this.size > 0) {
			response.setHeader("Content-Length", Long.toString(this.size));
		}
	}

	private String fileContent(File filename) throws FileNotFoundException, IOException {
		return IOUtils.toString(new FileInputStream(filename));
	}
}
