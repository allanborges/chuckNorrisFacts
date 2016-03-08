package norris.chuck.services.api;

import java.util.List;

import norris.chuck.model.Fact;

/**
 * interface que define os métodos de obtenção dos fatos do Chuck
 * 
 * @author Allan Borges
 *
 */
public interface IFactsService {

	/**
	 * retorna uma lista dos fatos de Chuck Norris 
	 * 
	 * @return List<Fact>
	 */
	public List<Fact> findFacts(int qtFacts, int pageNumber, String search);

	public Long countFacts(String search);
	
	public Fact saveFact(Fact fact);

}
