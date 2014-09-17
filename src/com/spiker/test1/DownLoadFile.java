package com.spiker.test1;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DownLoadFile {

	/**
	 * 根据 URL 和网页类型生成需要保存的网页的文件名,去除 URL 中的非文件名字符
	 */
	public String getFileNameByUrl(String url, String contentType) {
		// 移除 http:
		url = url.substring(7);
		// text/html 类型
		if (contentType.indexOf("html") != -1) {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
			return url;
		}
		// 如 application/pdf 类型
		else {
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "."
					+ contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	/**
	 * 保存网页字节数组到本地文件,filePath 为要保存的文件的相对地址
	 */
	private void saveToLocal(byte[] data, String filePath) {
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					new File(filePath)));
			for (int i = 0; i < data.length; i++)
				out.write(data[i]);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 下载 URL 指向的网页
	public String downloadFile(String url) throws HttpException {
		String filePath = null;
		// 1.生成 HttpClinet 对象并设置参数
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 设置 HTTP 连接超时 5s
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(5000).setConnectTimeout(5000)
				.setSocketTimeout(5000).build();
		// 2.生成 GetMethod 对象并设置参数
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		System.out.println("Executing request " + httpGet.getRequestLine());
		// 设置 get 请求超时 5s
		// getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
		// 5000);
		// 设置请求重试处理
		// getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		// new DefaultHttpMethodRetryHandler());
		// 3.执行 HTTP GET 请求
		try {		
			CloseableHttpResponse response = httpclient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String context = entity != null ? EntityUtils.toString(entity)
						: null;
				filePath = "temp/"
						+ getFileNameByUrl(url, response.getHeaders(
						"Content-Type")[0].getValue());
				saveToLocal(context.getBytes(), filePath);
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}

		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}
}
