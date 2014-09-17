package com.spiker.test1;

import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class TestUrl {

	public static void main(String[] args) {
		/*
		 * URL url = null; InputStream stream = null; try { url = new
		 * URL("http://www.baidu.com"); stream = url.openStream(); byte[] s =
		 * new byte[1024*1024]; stream.read(s, 0, 1024*1024);
		 * System.out.println(s); } catch (MalformedURLException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 */
		/*CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("http://www.baidu.com/s?wd=%E4%B8%AD%E5%9B%BD%E7%94%B5%E4%BF%A1&ie=utf-8&tn=19045005_17_pg");
			System.out.println("Executing request " + httpGet.getRequestLine());
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpGet, responseHandler);
			System.out.println("----------------------------------------");
			//System.out.println(responseBody);
			HtmlParserTool.extracLinks(responseBody,null);
		} catch (ClientProtocolException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/

		/*MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{"http://www.lietu.com"});*/

        com.spiker.util.SimpleBloomFilter.main(args);

	}

}
