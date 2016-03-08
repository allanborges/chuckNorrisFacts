package norris.chuck.services.impl;

import java.util.List;

import javax.inject.Inject;

import norris.chuck.model.Fact;
import norris.chuck.repository.api.IFacts;
import norris.chuck.services.api.IFactsService;
import utils.parses.ExtractContent;


public class FactsServiceIMPL implements IFactsService {

	@Inject
	private ExtractContent extractContent;
	
	@Inject
	private IFacts facts;
	
	@Override
	public List<Fact> findFacts(int qtFacts, int pageNumber, String search){
		return facts.getFacts(qtFacts, pageNumber , search);
	}
	
	@Override
	public Long countFacts(String search){
		Long countFacts = this.facts.countFacts(search);
		countFacts = countFacts == null ? 0l : countFacts;
		return countFacts;
	}

	@Override
	public Fact saveFact(Fact fact) {
		return facts.saveFact(fact);
	}
	
}
