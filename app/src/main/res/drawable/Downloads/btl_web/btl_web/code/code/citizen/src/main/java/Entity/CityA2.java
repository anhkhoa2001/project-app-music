package Entity;

public class CityA2 {
	private int ordinal;
	private String nameCity;
	private String cityID;
	private int population;
	private String rank;
	
	public int getOrdinal() {
		return ordinal;
	}
	
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	
	public String getNameCity() {
		return nameCity;
	}
	
	public void setNameCity(String name) {
		this.nameCity = name;
	}
	
	public String getCityID() {
		return cityID;
	}
	
	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public void setPopulation(int population) {
		this.population = population;
	}
	
	public String getRank() {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public CityA2(int ordinal, String name, String cityID,String rank, int population) {
		this.ordinal = ordinal;
		this.nameCity = name;
		this.cityID = cityID;
		this.population = population;
		this.rank = rank;
	}
	
	public CityA2(int ordinal, String cityID, int population, String rank) {
		this.ordinal = ordinal;
		this.cityID = cityID;
		this.population = population;
		this.rank = rank;
	}
	
	public CityA2(String name, String cityID, String rank) {
		this.nameCity = name;
		this.cityID = cityID;
		this.rank = rank;
	}
	
	public CityA2() {}
	
	@Override
	public int hashCode() {
		return ordinal;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof CityA2) ? (((CityA2) obj).toJSON().equals(this.toJSON())) : false; 
	}
	
	@Override
	public String toString() {
		return "CityA2 [ordinal=" + ordinal + ", nameCity=" + nameCity + ", cityID=" + cityID + ", population="
				+ population + ", rank=" + rank + "]";
	}
	
	public String toJSON() {
		return "{\n\t\"ordinal\": " + ordinal + ",\n"
				+ "\t\"name\": \"" + nameCity + "\",\n"
				+ "\t\"cityID\": \"" + cityID + "\",\n"
				+ "\t\"rank\": \"" + rank + "\",\n"
				+ "\t\"population\": \"" + population + "\"\n"
				+ "}";
	}
}
