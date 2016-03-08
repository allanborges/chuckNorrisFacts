import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.exception.WebSiteFailConnection;
import utils.parses.ExtractContent;

public class TesteJsoup {

	public static void main(String[] args)  {
		ExtractContent extrac = null;
		try {
			extrac = new ExtractContent("http://www.chucknorris.com.br");
		} catch (WebSiteFailConnection e) {
			e.getMessage();
		}
		
		String regex = "[0-9]+";
		
		List<String> elementos = extrac.getTextContentByRegex(regex);
		
		Elements elements = extrac.getDocumentWebSite().getElementsByAttributeValueMatching("href", "verdades/0?[0-9]+");
		
		
		
		for (Element element : elements){
			System.out.println(element.attr("href"));
		}
		
	}

}
