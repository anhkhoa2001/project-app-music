package Entity;

public class CommuneB1 extends DistrictA3 {
	private String nameCommune;
	private String communeID;
	
	public String getNameCommune() {
		return nameCommune;
	}
	
	public void setNameCommune(String nameCommune) {
		this.nameCommune = nameCommune;
	}
	
	public String getCommuneID() {
		return communeID;
	}
	
	public int getCommuneIDInterger() {
		return communeID == null ? -1 : (communeID.contains(".") ? Math.round(Float.parseFloat(communeID)*1000) : Integer.parseInt(communeID));
	}
	
	public void setCommuneID(String communeID) {
		this.communeID = communeID;
	}
	
	public CommuneB1(String nameCommune, String communeID, String cityID, String districtID ,String rank) {
		super(null, districtID, rank, cityID);
		this.nameCommune = nameCommune;
		this.communeID = communeID;
	}
	
	public CommuneB1() {
		super();
	}
	
	public CommuneB1(int ordinal, String nameCommune, String communeID, String cityID, String nameCity, String districtID, 
						String nameDistrict ,String rank, int population) {
		super(ordinal, nameDistrict, districtID, rank, cityID, nameCity, population);
		this.nameCommune = nameCommune;
		this.communeID = communeID;
	}
	public CommuneB1(int ordinal, String nameCommune, String communeID, String cityID,  String districtID, String rank) {
		super(ordinal, null, districtID, rank, cityID);
		this.nameCommune = nameCommune;
		this.communeID = communeID;
	}
	@Override
	public int hashCode() {
		return communeID == null ? -1 : communeID.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof CommuneB1 && (((CommuneB1) obj).toJSON().equals(this.toJSON()));
	}
	
	@Override
	public String toString() {
		return "CommuneB1 [ordinal=" + super.getOrdinal() + ",nameCommune= "+ nameCommune +",communeID= " + communeID 
				+ ", cityID= "+ super.getCityID() + ", districtID= " + super.getDistrictID() + ",rank= "+ super.getRank()
				+", population= "+ super.getPopulation() + "]";
	}
	@Override
	public String toJSON() {
		return "{\n\t\"ordinal\": " + super.getOrdinal() + ",\n"
				+ "\t\"nameCommune\": \"" + nameCommune + "\",\n"
				+ "\t\"communeID\": \"" + communeID + "\",\n"
				+ "\t\"cityID\": \"" + super.getCityID() + "\",\n"
				+ "\t\"nameCity\": \"" + super.getNameCity() + "\",\n"
				+ "\t\"districtID\": \"" + super.getDistrictID() + "\",\n"
				+ "\t\"nameDistrict\": \"" + super.getNameDistrict() + "\",\n"
				+ "\t\"rank\": \"" + super.getRank() + "\",\n"
				+ "\t\"population\": \"" + super.getPopulation() + "\"\n"
				+ "}";
	}
}
