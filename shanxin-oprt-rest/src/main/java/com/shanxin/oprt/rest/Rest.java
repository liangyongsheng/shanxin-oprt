package com.shanxin.oprt.rest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanxin.core.api.ApiException;
import com.shanxin.core.api.ApiRest;
import com.shanxin.core.api.Response;
import com.shanxin.core.context.MyContext;

@RestController
@RequestMapping(path = "/api")
public class Rest extends ApiRest {

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String desc() {
		String content = "";
		try {
			String fileName = MyContext.getServletContext().getRealPath("/") + "/WEB-INF/index.html";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedReader rd = new BufferedReader(new InputStreamReader(fis, Charset.forName("utf-8")));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = rd.readLine()) != null)
				sb.append(line);

			rd.close();
			fis.close();
			content = new String(sb);
		} catch (Throwable e) {
		}

		return content;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response<?> api(HttpServletRequest request) throws ApiException {
		return super.api(request);
	}
}
