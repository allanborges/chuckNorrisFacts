package norris.chuck.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InfoBean {
	
	private Long totalFacts;
	

	public Long getTotalFacts() {
		return totalFacts;
	}

	public void setTotalFacts(Long totalFacts) {
		if (totalFacts == null)
			totalFacts = 0l;
		
		this.totalFacts = totalFacts;
	}

}
