package Entity;

public class VillageB2 extends CommuneB1 {
	private String nameVillage;
	private String villageID;
	public String getNameVillage() {
		return nameVillage;
	}
	public void setNameVillage(String nameVillage) {
		this.nameVillage = nameVillage;
	}
	public String getVillageID() {
		return villageID;
	}
	public void setVillageID(String villageID) {
		this.villageID = villageID;
	}
	public VillageB2(int ordinal, String nameVillage, String villageID, String communeID, String districtID, String cityID, String rank,
							int population) {
		super(null, communeID, cityID, districtID, rank);
		this.nameVillage = nameVillage;
		this.villageID = villageID;
		super.setPopulation(population);
		super.setOrdinal(ordinal);
	}
	
	public VillageB2(int ordinal, String nameVillage, String villageID,String nameCommune, String communeID, String nameDistrict, 
			String districtID, String nameCity, String cityID) {
		super(ordinal, nameCommune, communeID, cityID, nameCity, districtID, nameDistrict, null, 0);
		this.nameVillage = nameVillage;
		this.villageID = villageID;
	}
	
	@Override
	public int hashCode() {
		return super.getOrdinal();
	}
	@Override
	public boolean equals(Object obj) {
		return obj instanceof VillageB2 && (((VillageB2) obj).toJSON().equals(this.toJSON()));
	}
	@Override
	public String toString() {
		return "VillageB1 [ordinal=" + super.getOrdinal() + ", nameVillage=" + nameVillage + ", villageID=" + 
					villageID + ",rank= "+ super.getRank() +",communeID="+ super.getCommuneIDInterger() +",districtID="
					+ super.getDistrictID() + ",cityID="+ super.getCityID() +", population="+ super.getPopulation() +"]";
	}
	@Override
	public String toJSON() {
		return "{\n\t\"ordinal\": " + super.getOrdinal() + ",\n"
				+ "\t\"nameVillage\": \"" + nameVillage + "\",\n"
				+ "\t\"villageID\": \"" + villageID + "\",\n"
				+ "\t\"rank\": \"" + super.getRank() + "\",\n"
				+ "\t\"communeID\": \"" + super.getCommuneID() + "\",\n"
				+ "\t\"districtID\": \"" + super.getDistrictID() + "\",\n"
				+ "\t\"cityID\": \"" + super.getCityID() + "\",\n"
				+ "\t\"population\": \"" + super.getPopulation() + "\"\n"
				+ "}";
	}

	public String toJSONFull() {
		return "{\n\t\"ordinal\": " + super.getOrdinal() + ",\n"
				+ "\t\"nameVillage\": \"" + nameVillage + "\",\n"
				+ "\t\"villageID\": \"" + villageID + "\",\n"
				+ "\t\"rank\": \"" + super.getRank() + "\",\n"
				+ "\t\"communeID\": \"" + super.getCommuneID() + "\",\n"
				+ "\t\"districtID\": \"" + super.getDistrictID() + "\",\n"
				+ "\t\"cityID\": \"" + super.getCityID() + "\",\n"
				+ "\t\"nameCommune\": \"" + super.getNameCommune() + "\",\n"
				+ "\t\"nameDistrict\": \"" + super.getNameDistrict() + "\",\n"
				+ "\t\"nameCity\": \"" + super.getNameCity() + "\",\n"
				+ "\t\"population\": \"" + super.getPopulation() + "\"\n"
				+ "}";
	}
}
