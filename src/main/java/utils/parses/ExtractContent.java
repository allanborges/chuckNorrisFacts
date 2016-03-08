package utils.parses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.exception.WebSiteFailConnection;

/**
 * Responsável por connectar ao Web Site 
 * e extrair o elementos ( textos, elementos html)
 * utilizando o JSoup
 * 
 * @author Allan Borges
 *
 */
public class ExtractContent {

	private Document documentWebSite;
	
	public ExtractContent(){
		
	}
	
	public void connect(final String url) throws WebSiteFailConnection {
		connectWebSite(url);
	}
		
	public ExtractContent(final String url) throws WebSiteFailConnection{
		connectWebSite(url);
	}

	private void connectWebSite(final String url) throws WebSiteFailConnection {
		try {
			this.setDocumentWebSite(Jsoup.connect(url).get());
		} catch (IOException e) {
			throw new WebSiteFailConnection("Fail to connect WebSite");
		}
	}
	
	public List<String> getTextContentByRegex(final String pattern){		
		List<String> listContents = new ArrayList<String>();
		Element elementBody = documentWebSite.body();
		if (elementBody != null ) {
			populateListContent(elementBody, pattern, listContents);
		} else{
			throw new NoSuchElementException("Body no such in HTML");
		}
		
		return listContents;
	}
	
	
	
	private void populateListContent(Element elementFather, String pattern, List <String> listContents){
		Elements elementos = elementFather.getElementsMatchingOwnText(pattern);	
		for (Element element : elementos){
			listContents.add(element.text());
		}
	}

	public Document getDocumentWebSite() {
		return documentWebSite;
	}

	public void setDocumentWebSite(Document documentWebSite) {
		this.documentWebSite = documentWebSite;
	}

	
}
