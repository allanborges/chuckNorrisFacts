package norris.chuck.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import norris.chuck.model.Fact;
import norris.chuck.repository.api.IFacts;

public class Facts implements IFacts{
	
	@Inject
	private EntityManager entityManager;

	@Override
	public Fact saveFact(Fact fact) {
		//TODO depois alterar para um intercepetador , onde se abrir e fechar a transação
		entityManager.getTransaction().begin();
		fact = entityManager.merge(fact);
		entityManager.getTransaction().commit();
		return fact;
	}
	
	
	@Override
	public List<Fact> getFacts(int qtFacts, int pageNumber, String search){
		
		String jpql = " FROM Fact ";
		
		if (StringUtils.isNotBlank(search))
			jpql += " WHERE UPPER(fact.factText) LIKE UPPER(:search) ";
		
		TypedQuery<Fact> query = entityManager.createQuery(" from Fact ", Fact.class);
		
		query.setFirstResult((pageNumber - 1) * qtFacts);
		query.setMaxResults(qtFacts);
		if (StringUtils.isNotBlank(search))
			query.setParameter("search", "%" + search + "%");
		
		return query.getResultList();
	}
	
	@Override
	public Long countFacts(String search){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Fact> rt = cq.from(Fact.class);
        cq.select(cb.count(rt));
        if (StringUtils.isNotBlank(search))
        	cq.where(cb.like((Expression)rt.get("factText"), "%" + search + "%"));             
        TypedQuery<Long> q = entityManager.createQuery(cq);
        return q.getSingleResult();
	}
}
