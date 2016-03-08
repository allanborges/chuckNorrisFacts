package borges.allan.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import norris.chuck.beans.InfoBean;
import norris.chuck.model.Fact;
import norris.chuck.services.api.IFactsService;

@Path("/")
@RequestScoped
public class ChuckNorrisFacts {
	
	@Inject
	private IFactsService factsService;
	
	@Path("/facts")
	@GET
	@Produces("application/json" + ";charset=UTF-8")
	public Response getFacts(@DefaultValue("15") @QueryParam("qtFacts") int qtFacts,
							 @QueryParam("page") int page,
							 @QueryParam("search") String search){
		
		List<Fact> facts = factsService.findFacts(qtFacts, page, search);		
		GenericEntity<List<Fact>> list = new GenericEntity<List<Fact>>(facts){};		
		return Response.ok(list).build();
	}
	
	@Path("/facts/counts")
	@GET
	@Produces("application/json")
	public InfoBean countFacts(@QueryParam("search") String search){
		Long countFacts = factsService.countFacts(search);
		InfoBean infoBean = new InfoBean();
		infoBean.setTotalFacts(countFacts);
		
		return infoBean;
	}

	@Path("/facts")
	@POST
	@Consumes("application/json;charset=UTF-8")
	public Response insertNewFact(Fact fact){
		factsService.saveFact(fact);
		return Response.ok().build();
	}
	
}
