package Entity;

import Model.ModelCadres;

public class Cadres {
	private int ordinal;
	private String username;
	private String password;
	private boolean grant;//add--edit-remove
	private String numberID;
	private String rank;
	private String createTime;
	private String beginTime;
	private String endTime;
	private boolean status;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public boolean isGrant() {
		return grant;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setGrant(boolean grant) {
		this.grant = grant;
	}

	public String getManageArea() {
		return this.getRank().equals("A1") ? "cả nước" : ModelCadres.convertNumberIDToAddress(this);
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getNumberID() {
		return numberID;
	}

	public void setNumberID(String numberID) {
		this.numberID = numberID;
	}

	public Cadres(int ordinal, String username, String password, boolean grant, String numberID, String rank,
			String createTime, String beginTime, String endTime, boolean status) {
		this.ordinal = ordinal;
		this.username = username;
		this.password = password;
		this.grant = grant;
		this.numberID = numberID;
		this.rank = rank;
		this.createTime = createTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.status = status;
	}
	public Cadres(String username, String password, String numberID, String beginTime, String endTime) {
		this.username = username;
		this.password = password;
		this.numberID = numberID;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
//		return (super.getOrdinal() + username + password).hashCode();
		return ordinal;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Cadres && ((Cadres) obj).toJSON().equals(this.toJSON());
	}

	@Override
	public String toString() {
		return "Cadres{" +
				"ordinal=" + ordinal +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", grant=" + grant +
				", numberID='" + numberID + '\'' +
				", rank='" + rank + '\'' +
				", createTime='" + createTime + '\'' +
				", beginTime='" + beginTime + '\'' +
				", endTime='" + endTime + '\'' +
				'}';
	}

	public String toJSON() {
		return "{\n\t\"ordinal\": " + ordinal + ",\n"
				+ "\t\"username\": \"" + username + "\",\n"
				+ "\t\"password\": \"" + password + "\",\n"
				+ "\t\"grant\": \"" + grant + "\",\n"
				+ "\t\"status\": \"" + status + "\",\n"
				+ "\t\"rank\": \"" + rank + "\",\n"
				+ "\t\"numberID\": \"" + numberID + "\",\n"
				+ "\t\"beginTime\": \"" + beginTime + "\",\n"
				+ "\t\"endTime\": \"" + endTime + "\",\n"
				+ "\t\"createtime\": \"" + createTime + "\",\n"
				+ "\t\"managearea\": \"" + this.getManageArea() + "\"\n"
				+ "}";
	}
	
	
}
