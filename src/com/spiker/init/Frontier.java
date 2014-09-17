package com.spiker.init;

import com.spiker.type.CrawlUrl;

public interface Frontier {

	public CrawlUrl getNext() throws Exception;
	public boolean putUrl(CrawlUrl url) throws Exception;
	//public boolean visited(CrawlUrl url);
}