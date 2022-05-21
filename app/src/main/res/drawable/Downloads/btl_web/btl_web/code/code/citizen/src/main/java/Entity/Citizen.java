package Entity;

import Model.DAO;

public class Citizen {
	private int ordinal;
	private String name;
	private String numberID;
	private String dob;
	private String gender;
	private String ethnicGroup;
	private String eduLevel;
	private String poo;
	private String permanent;//thuong tru
	private String temporary;//tam tru
	private String job;
	private String villageID;
	private String time;
	
	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getName() {
		return name;
	}
	
	public String getNameNotUnisign() {
		String nameBotSign = DAO.convertAccentToUnisigned(this.getName().toLowerCase());
		nameBotSign = nameBotSign.replace("đ", "d");
		return nameBotSign;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumberID() {
		return numberID;
	}

	public void setNumberID(String numberID) {
		this.numberID = numberID;
	}

	public String getDob() {
		return dob;
	}

	public String getDobV2() {
		String dobv2 = (dob.contains("-")) ? dob.split("-")[2] + "/" +dob.split("-")[1] + "/" + dob.split("-")[0] : dob;
		return dobv2;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getPoo() {
		return poo;
	}

	public void setPoo(String poo) {
		this.poo = poo;
	}

	public String getPermanent() {
		return permanent;
	}

	public void setPermanent(String permanent) {
		this.permanent = permanent;
	}

	public String getTemporary() {
		return temporary;
	}

	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getVillageID() {
		return villageID;
	}

	public void setVillageID(String villageID) {
		this.villageID = villageID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Citizen(int ordinal, String numberID, String name, String dob, String gender, String poo, String permanent,
					String tempory,String ethnicGroup, String eduLevel, String job, String villageID, String time) {
		this.ordinal = ordinal;
		this.numberID = numberID;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.ethnicGroup = ethnicGroup;
		this.eduLevel = eduLevel;
		this.permanent = permanent;
		this.poo = poo;
		this.temporary = tempory;
		this.job = job;
		this.villageID = villageID;
		this.time = time;
	}
	public Citizen() {}

	@Override
	public int hashCode() {
		return ordinal;
	}


	@Override
	public boolean equals(Object obj) {
		return obj instanceof Citizen && ((Citizen) obj).toJSON().equals(this.toJSON());
	}


	public String toJSON() {
		return "\n{\n\t\"ordinal\": " + ordinal + ",\n"
				+ "\t\"numberID\": \"" + numberID + "\",\n"
				+ "\t\"name\": \"" + name + "\",\n"
				+ "\t\"dobv2\": \"" + this.getDobV2() + "\",\n"
				+ "\t\"dob\": \"" + this.getDob() + "\",\n"
				+ "\t\"gender\": \"" + gender + "\",\n"
				+ "\t\"poo\": \"" + poo + "\",\n"
				+ "\t\"permanent\": \"" + permanent + "\",\n"
				+ "\t\"temporary\": \"" + temporary + "\",\n"
				+ "\t\"ethnicGroup\": \"" + ethnicGroup + "\",\n"
				+ "\t\"eduLevel\": \"" + eduLevel + "\",\n"
				+ "\t\"job\": \"" + job + "\",\n"
				+ "\t\"villageID\": \"" + villageID + "\",\n"
				+ "\t\"time\": \"" + time + "\"\n"
				+ "}";
	}
}
