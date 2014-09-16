package com.spiker.test1;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.filters.*;
import org.htmlparser.util.*;
import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.visitors.TextExtractingVisitor;

@SuppressWarnings("unused")
public class HtmlParserTool {
	// 获取一个网站上的链接,filter 用来过滤链接
	public static Set<String> extracLinks(String url, NodeFilter filter) {
		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("UTF-8");

			@SuppressWarnings("serial")
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				}
			};

			OrFilter linkFilter = new OrFilter(new NodeClassFilter(
					LinkTag.class), frameFilter);

			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			
			System.out.println("length=" + list.size());

			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);

				if (tag instanceof LinkTag) {// <a> 标签
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// URL

					/*
					 * if (filter.accept(linkUrl)) { links.add(linkUrl); }
					 */

					System.out.println("linkUrl=" + linkUrl);

					if (filter.accept(tag)) {
						links.add(linkUrl);
					}
				} else {// <frame> 标签
					// 提取 frame 里 src 属性的链接,如 <frame src="test.html"/>
					String frame = tag.getText();
					int start = frame.indexOf("src=");
					frame = frame.substring(start);
					int end = frame.indexOf(" ");

					if (end == -1) {
						end = frame.indexOf(">");
					}

					String frameUrl = frame.substring(5, end - 1);
					// if (filter.accept(frameUrl)) {
					// links.add(frameUrl);
					// }

					System.out.println("frameUrl=" + frameUrl);

					if (filter.accept(tag)) {
						links.add(frameUrl);
					}
				}
			}

			/*
			 * NodeFilter filter = new TagNameFilter("DIV"); NodeList nodes =
			 * parser.extractAllNodesThatMatch(filter); if(nodes!=null) { for
			 * (int i = 0; i < nodes.size(); i++) { Node textnode = (Node)
			 * nodes.elementAt(i);
			 * System.out.println("getText:"+textnode.getText());
			 * System.out.println
			 * ("================================================="); } }
			 */
			/*
			 * for(NodeIterator i = parser.elements (); i.hasMoreNodes(); ) {
			 * Node node = i.nextNode();
			 * System.out.println("getText:"+node.getText());
			 * System.out.println("getPlainText:"+node.toPlainTextString());
			 * System.out.println("toHtml:"+node.toHtml());
			 * System.out.println("toHtml(true):"+node.toHtml(true));
			 * System.out.println("toHtml(false):"+node.toHtml(false));
			 * System.out.println("toString:"+node.toString());
			 * System.out.println
			 * ("================================================="); }
			 */

			/*
			 * TextExtractingVisitor visitor = new TextExtractingVisitor();
			 * parser.visitAllNodesWith(visitor); String textInPage =
			 * visitor.getExtractedText(); System.out.println(textInPage);
			 */

		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}

}
