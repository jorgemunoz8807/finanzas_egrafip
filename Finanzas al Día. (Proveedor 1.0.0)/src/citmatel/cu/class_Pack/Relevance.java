package citmatel.cu.class_Pack;

public class Relevance implements Comparable<Relevance> {
	private int variety;
	private int matches;
	private int contained;
	
	public int getVariety() {
		return variety;
	}
	public int getMatches() {
		return matches;
	}
	public int getContained() {
		return contained;
	}
	
	public Relevance(int variety, int matches, int contained)
	{
		this.variety = variety;
		this.matches = matches;
		this.contained = contained;
	}
	
	public Boolean isValueRelevant()
	{
		return this.variety>0 || this.contained>0;
	}
	@Override
	public int compareTo(Relevance other) {
		return this.variety>other.variety?1:this.variety<other.variety?-1:
				this.matches>other.matches?1:this.matches<other.matches?-1:
				 this.contained>other.contained?1:this.contained<other.contained?-1:
					 0;
	}
	
}
