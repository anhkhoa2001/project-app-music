package Entity;

public class DistrictA3 extends CityA2 {
	private String nameDistrict;
	private String districtID;
	
	public String getNameDistrict() {
		return nameDistrict;
	}
	
	public void setNameDistrict(String nameDistrict) {
		this.nameDistrict = nameDistrict;
	}
	
	public String getDistrictID() {
		return districtID;
	}
	
	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}
	
	public DistrictA3(int ordinal, String nameDistrict, String districtID, String rank, String cityID, String nameCity, int population) {
		super(ordinal, nameCity, cityID, rank, population);
		this.districtID = districtID;
		this.nameDistrict = nameDistrict;
	}
	public DistrictA3(int ordinal, String nameDistrict, String districtID, String rank, String cityID, int population) {
		super(ordinal, null, cityID, rank, population);
		this.districtID = districtID;
		this.nameDistrict = nameDistrict;
	}
	public DistrictA3(int ordinal, String nameDistrict, String districtID, String rank, String cityID) {
		super(ordinal, null, cityID, rank, 0);
		this.districtID = districtID;
		this.nameDistrict = nameDistrict;
	}
	public DistrictA3(String nameDistrict, String districtID, String rank,  String cityID) {
		super(null, cityID, rank);
		this.nameDistrict = nameDistrict;
		this.districtID = districtID;
	}
	
	public DistrictA3() {
		super();
	}
	
	@Override
	public int hashCode() {
		return super.getOrdinal();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof DistrictA3) ? (((DistrictA3) obj).toJSON().equals(this.toJSON())) : false;
	}
	
	@Override
	public String toString() {
		return "DistrictA2 [ordinal=" + super.getOrdinal() + ", nameDistrict=" + nameDistrict + ", districtID=" + 
					districtID + ",rank= "+ super.getRank() +",cityID="+ super.getCityID() +", population="+ super.getPopulation() +"]";
	}
	
	@Override
	public String toJSON() {
		return "{\n\t\"ordinal\": " + super.getOrdinal() + ",\n"
				+ "\t\"nameDistrict\": \"" + nameDistrict + "\",\n"
				+ "\t\"districtID\": \"" + districtID + "\",\n"
				+ "\t\"rank\": \"" + super.getRank() + "\",\n"
				+ "\t\"cityID\": \"" + super.getCityID() + "\",\n"
				+ "\t\"nameCity\": \"" + super.getNameCity() + "\",\n"
				+ "\t\"population\": \"" + super.getPopulation() + "\"\n"
				+ "}";
	}
	
	public String toJSON(String nameDistrict, String districtID) {
		return "{\n\t\"nameDistrict\": \"" + nameDistrict + "\",\n"
				+ "\t\"districtID\": " + districtID + "\n"
				+ "}";
	}
	
}
