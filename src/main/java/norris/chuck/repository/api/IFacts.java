package norris.chuck.repository.api;

import java.util.List;

import norris.chuck.model.Fact;

public interface IFacts {

	public Fact saveFact(Fact fact);

	public List<Fact> getFacts(int qtFacts, int pageNumber, String search);

	public Long countFacts(String search);
	
}
