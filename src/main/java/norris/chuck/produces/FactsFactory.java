package norris.chuck.produces;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import norris.chuck.model.Fact;
import norris.chuck.repository.Facts;
import utils.exception.WebSiteFailConnection;
import utils.parses.ExtractContent;

/**
 * Vai popular os fatos através dos sites ()
 * 
 * @author Allan Borges
 *
 */
@ApplicationScoped
public class FactsFactory {
	
	private static final String URL_CHUCK_NORRIS_FACTS = "http://www.chucknorris.com.br";
	
	@Inject
	private Facts facts;
	
	@Inject
	private ExtractContent extractContent;
	
	//TODO mudar depois para arquivo de configuração
	private static final boolean ENABLE_POPULATE = false;
	
	/**
	 * Na inicialização da applicação as verdades serão populados na base, pois talvez podem ter novas verdades
	 * 
	 * @param init
	 * @throws WebSiteFailConnection
	 */
	@PostConstruct
	public void populateNewFacts(@Observes @Initialized(ApplicationScoped.class) Object init) throws WebSiteFailConnection {
		if (ENABLE_POPULATE) {
			TreeSet<String> pages = getLinksFacts();
			
			for (String page : pages){
	
				extractContent.connect(URL_CHUCK_NORRIS_FACTS + "/" + page);
				
				List<String> textFacts = extractContent.getTextContentByRegex("^[0-9]+((\\s+)?-(\\s+))[a-zA-Z]+");
				
				for(String textFact : textFacts){
					if (StringUtils.isNotBlank(textFact)){
						textFact = StringUtils.trim(textFact);
						Fact fact = new Fact();
						fact.setFactText(textFact);
						facts.saveFact(fact);
					}
				}
			}
		}
	}
	
	/**
	 * Responsável por pegar todas as páginas com as verdades do site
	 * 
	 * 
	 * @return List<String>
	 * @throws WebSiteFailConnection
	 */
	private TreeSet<String> getLinksFacts() throws WebSiteFailConnection{
		extractContent.connect(URL_CHUCK_NORRIS_FACTS);		
		Elements elements = extractContent.getDocumentWebSite().getElementsByAttributeValueMatching("href", "verdades/0?[0-9]+");
		TreeSet<String> pages = new TreeSet<>();
		for(Element element : elements){
			String textElement = element.attr("href");
			pages.add(textElement);
		}
		
		return pages;
	}

}
