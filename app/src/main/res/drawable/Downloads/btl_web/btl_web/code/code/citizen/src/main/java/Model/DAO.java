package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Entity.*;
import Model.json.JSONObject;
import Model.json.JSONcheck;

//có chức năng lấy dữ liệu từ database lên
//và chuyển hóa dữ liệu thành đối tượng
public class DAO {
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	public static LocalDateTime currenTime = LocalDateTime.now();
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public List<CityA2> getAllCity() {
		List<CityA2> list = new ArrayList<>();
		String query = "SELECT * FROM citizen.city_a2;";
		
		try {
			ps = ConnectionJDBC.getJDBC().prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new CityA2(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<DistrictA3> getAllDistrict() {
		List<DistrictA3> list = new ArrayList<>();
		String query = "select * from citizen.district_a3v2;";
		try {
			ps = ConnectionJDBC.getJDBC().prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new DistrictA3(rs.getInt(1), 
										rs.getString(2), 
										rs.getString(3), 
										rs.getString(4), 
										rs.getString(5),
										rs.getString(6),
									rs.getInt(7)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<CommuneB1> getALLCommune() {
		List<CommuneB1> list = new ArrayList<>();
		String query = "SELECT * FROM citizen.commune_b1v2;";
		try {
			ps = ConnectionJDBC.getJDBC().prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new CommuneB1(rs.getInt(1), 
										rs.getString(2), 
										rs.getString(3), 
										rs.getString(4), 
										rs.getString(8), 
										rs.getString(5), 
										rs.getString(7),
										rs.getString(6),
										rs.getInt(9)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Cadres getCadresByUsername(String username) {
		Cadres cadres = null;
		try {
			String citizen = "citizen";
			String query = "select * from " + citizen + ".account where username = ?";
			ps = ConnectionJDBC.getJDBC().prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				cadres = new Cadres(rs.getInt(1), 
									rs.getString(2), 
									rs.getString(3), 
									rs.getBoolean(4),
									rs.getString(5), 
									rs.getString(6), 
									rs.getString(7),
									rs.getString(8),
									rs.getString(9),
									rs.getBoolean(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cadres;
	}

	public CityA2 getCityByCityID(String cityID) {
		CityA2 cityA1 = null;
		Connection connection = null;
		try {
			String query = "select * from citizen.city_a2 where cityID = ?";
			connection = ConnectionJDBC.getJDBC();
			ps = connection.prepareStatement(query);
			ps.setString(1, cityID);
			rs = ps.executeQuery();
			while(rs.next()) {
				cityA1 = new CityA2(rs.getInt(1), 
									rs.getString(2), 
									rs.getString(3), 
									rs.getString(4), 
									rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cityA1;
	}
	
	public DistrictA3 getDistrictByDistrictID(String districtID) {
		DistrictA3 districtA2 = null;
		Connection connection = null;
		try {
			String query = "SELECT * FROM citizen.district_a3v2 where districtID = ?;";
			connection = ConnectionJDBC.getJDBC();
			ps = connection.prepareStatement(query);
			ps.setString(1, districtID);
			rs = ps.executeQuery();
			while(rs.next()) {
				districtA2 = new DistrictA3(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getString(5),
											rs.getString(6),
											rs.getInt(7));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return districtA2;
	}
	
	public CommuneB1 getCommuneByCommuneID(String communeID) {
		CommuneB1 communeA3 = null;
		Connection connection = null;
		String query = "SELECT * FROM citizen.commune_b1v2 where communeID = ?;";
		try { 
			connection = ConnectionJDBC.getJDBC();
			ps = connection.prepareStatement(query);
			ps.setString(1, communeID);
			rs = ps.executeQuery();
			while(rs.next()) {
				communeA3 = new CommuneB1(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getString(8),
											rs.getString(5),
											rs.getString(7),
											rs.getString(6),
											0);
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return communeA3;
	}
	
	public VillageB2 getVillageByVillageID(String villageID) {
		VillageB2 villageB2 = null;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "select * from citizen.village_b2 where villageID = ?;";
			ps = connection.prepareStatement(query);
			ps.setString(1, villageID);
			rs = ps.executeQuery();
			while(rs.next()) {
				villageB2 = new VillageB2(rs.getInt(1), 
										rs.getString(2), 
										rs.getString(3), 
										rs.getString(4), 
										rs.getString(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getInt(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return villageB2;
	}
	
	public List<Citizen> getAllCitizen() {
		List<Citizen> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT * FROM citizen.infomation1 order by ordinal asc;";
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Citizen(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getString(9),
										rs.getString(10),
										rs.getString(11),
										rs.getString(12),
										rs.getString(13)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public List<DistrictA3> getAllDistrictByType(String ID, String type) {
		List<DistrictA3> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT * FROM citizen.district_a3v2 where "+ type +" = ? order by population desc;";
			ps = connection.prepareStatement(query);
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new DistrictA3(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getString(6),
										rs.getInt(7)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}

	public List<DistrictA3> getAllDistrictByTypeAndListID(String[] IDs, String type) {
		List<DistrictA3> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		String query = "SELECT * FROM citizen.district_a3v2 where ";
		for(int i=0; i<IDs.length - 1; i++) {
			query += type + "=" + IDs[i] + " or ";
		}
		query += type + "=" + IDs[IDs.length - 1];
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new DistrictA3(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getString(6),
										rs.getInt(7)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}
	
	public List<CommuneB1> getAllCommuneByType(String ID, String type) {
		List<CommuneB1> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT * FROM citizen.commune_b1v2 where "+ type +" = ? order by population desc;";
			ps = connection.prepareStatement(query);
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new CommuneB1(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(8),
										rs.getString(5),
										rs.getString(7),
										rs.getString(6),
										rs.getInt(9)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}

	public List<CommuneB1> getAllCommuneByTypeAndListID(String[] IDs, String type) {
		List<CommuneB1> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		String query = "SELECT * FROM citizen.commune_b1v2 where ";
		for(int i=0; i<IDs.length - 1; i++) {
			query += type + "=" + IDs[i] + " or ";
		}
		query += type + "=" + IDs[IDs.length - 1];
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new CommuneB1(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(8),
										rs.getString(5),
										rs.getString(7),
										rs.getString(6),
										rs.getInt(9)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}
	
	public List<Cadres> getAllCadres() {
		List<Cadres> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "select * from citizen.account order by ordinal asc;";
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Cadres(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getBoolean(4),
									rs.getString(5),
									rs.getString(6),
									rs.getString(7),
									rs.getString(8),
									rs.getString(9),
									rs.getBoolean(10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}

	public List<Cadres> getAllCadresLikeUsername(String username) {
		List<Cadres> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		username += "%";
		try {
			String query = "select * from citizen.account where username like ?;";
			ps = connection.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Cadres(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getBoolean(4),
										rs.getString(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getString(9),
										rs.getBoolean(10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	public List<VillageB2> getAllVillage() {
		List<VillageB2> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT b2.ordinal, b2.nameVillage, b2.villageID, b.commune, b.communeID, "
						+ "b.district, b.districtID, b.nameCity, b.cityID FROM citizen.village_b2 as "
						+ "b2, citizen.commune_b1v2 as b where b.communeID = b2.communeID;";
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new VillageB2(rs.getInt(1), 
										rs.getString(2), 
										rs.getString(3), 
										rs.getString(4), 
										rs.getString(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getString(8),
										rs.getString(9)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}


	public List<VillageB2> getAllVillageByType(String ID, String type) {
		List<VillageB2> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "select * from citizen.village_b2 where "+ type +" = ? order by population desc;";
			ps = connection.prepareStatement(query);
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new VillageB2(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getInt(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}


	public List<VillageB2> getAllVillageByTypeAndListID(String[] IDs, String type) {
		List<VillageB2> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		String query = "select * from citizen.village_b2 where ";
		for(int i=0; i<IDs.length - 1; i++) {
			query += type + "=" + IDs[i] + " or ";
		}
		query += type + "=" + IDs[IDs.length - 1];
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new VillageB2(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getString(6),
										rs.getString(7),
										rs.getInt(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}




	public String getDateRandom(int left, int right) {
		GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(left, right);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
     
    	return gc.get(gc.DAY_OF_MONTH) + "/" + (gc.get(gc.MONTH) + 1) + "/" + (gc.get(gc.YEAR));	
	}
	public static String convertAccentToUnisigned(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		s = pattern.matcher(temp).replaceAll("");
		s = s.replace("Đ", "D");
		s = s.replace("đ", "d");
		return s;
	}
	
	public static String createTimeRandom() {
		final Random random = new Random();
		final int millisInDay = 24*60*60*1000;
		Time time = new Time((long)random.nextInt(millisInDay));
		return time.toString();
	}
	
	public int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
	
	public boolean isNumeric(String str) { 
		  try {  
		    Integer.parseInt(str);
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	public List<Cadres> getAllCadresByRALID(String rank, List<String> listNumberID) {
		List<Cadres> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		String query = "select * from citizen.account where rank = ? and ( ";
		for (String i : listNumberID) {
			query += " numberID = \'" + i + "\' or ";
 		}
		query += " numberID = 0);";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, rank);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Cadres(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getBoolean(4),
									rs.getString(5),
									rs.getString(6),
									rs.getString(7),
									rs.getString(8),
									rs.getString(9),
									rs.getBoolean(10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
	public List<Cadres> getAllCadresByRAID(String rank, int numberID) {
		List<Cadres> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "select * from citizen.account where rank = ? and numberID = ? ";
			ps = connection.prepareStatement(query);
			ps.setString(1, rank);
			ps.setInt(2, numberID);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Cadres(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getBoolean(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getBoolean(10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
	public List<Cadres> getAllCadresByRank(String rank) {
		List<Cadres> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "select * from citizen.account where rank = ?";
			ps = connection.prepareStatement(query);
			ps.setString(1, rank);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Cadres(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getBoolean(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getBoolean(10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	//kiểm tra và chuyển data thành json trong body request
	public JSONObject convertDataBodyToJSON(BufferedReader br) {
		String line;
		StringBuilder data = new StringBuilder();
		try {
			while((line = br.readLine()) != null) {
				data.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean checkJson = JSONcheck.isJSONValid(data.toString());
		return (checkJson) ? new JSONObject(data.toString()) : null;
	}
	
/*	public Set<Citizen> createNameCitizen(){
		List<String> listmiddleMale = new ArrayList<>();
		List<String> listmiddleFemale = new ArrayList<>();
		Set<Citizen> generalList = new HashSet<>();
		
		Set<String> listCitizen = new HashSet<>();
//		String[] namesMale = {"Anh", "Nghĩa", "Ngọc", "Nguyên", "Nhân", "Hoàn", "Phi", "Phong", "Phúc",
//							"Quân", "Quang", "Quốc", "Tâm", "Thái", "Thành", "Thiên", "Thịnh", "Trung", "Tuấn", "Sơn", "Tùng", 
//							"Việt", "Vinh", "Uy", "Gia","Bách", "Bảo", "Công", "Cường", "Đức", "Dũng", "Dương", "Đạt", "Duy",
//							"Hải", "Hiếu", "Hoàng", "Huy", "Hùng", "Khải", "Khang", "Khánh", "Khoa", "Khôi", "Kiên", "Lâm",
//							"Long", "Lộc", "Minh", "Nam"};//9
//		String[] middlesMale = {"An", "Bảo", "Công", "Duy", "Gia", "Hải", "Hạo", "Hiếu", "Hoàng", "Hùng", "Hương", "Hữu", 
//				"Huy", "Ngọc", "Nguyên", "Phong", "Phước", "Quân", "Quang", "Quốc", "Qúy", "Thiên", "Tiến", "Vĩ", "Vũ", "Xuân"};//7
		String[] surs = {"Nguyễn", "Lê", "Đào", "Đàm", "Phạm", "Lê", "Lý", "Trần"};//7
		
		String[] namesFemale = {"Anh", "Như", "Thảo", "Thu", "Dung", "Duyên", "Giang", "Hà", "Nhung", "Yến", "Hạnh", 
				"Hoa", "Huế", "Hường", "Khánh", "Thủy", "Lan", "Linh", "Loan", "Mai", "Quỳnh", "Trang", "Nga", "Ngân", "Ngọc"};
//		"Nhi", "Nhiên", "Bích", "Châu", "Chi", "Diệp", 
//		"Hạ", "Oanh", "Quyên", "My", "Tâm", "Điệp", "Đoan", "Thư", "Khuê", "Minh", "Trà", "Uyên", "Vy", "Hân"
		String[] middlesFemale = {"Ngân", "Ngọc", "Nguyệt", "Như", "Phương",
				"Quỳnh", "Thanh", "Thảo", "Thư", "Thu", "Thúy", "Thùy", "Thủy", "Uyên", "Vân", "Yến"};
		//"Quế", "Nhã", "Nhật","Mỹ", 
		String[] ethnic = {"Phật giáo", "Đạo giáo", "Tin Lành", "Không", "Hồi giáo", "Không", "Không", "Không", "Không", "Không", "Không"};
		String[] jobs = {"Học sinh", "Sinh viên"};
		
		Set<String> nameSetMale = new HashSet<>();
		Set<String> nameSetFemale = new HashSet<>();
		
		Map<String, String> mapMale = new HashMap<>();
		Map<String, String> mapFemale = new HashMap<>();
		
		Set<Citizen> set = new HashSet<>();
//		for(int i=0; i<middlesMale.length; i++) {
//			listmiddleMale.add(middlesMale[i]);
//			for(int j=i+1; j<middlesMale.length; j++) {
//				if(!middlesMale[i].equals(middlesMale[j])) {
//					listmiddleMale.add(middlesMale[i] + " " + middlesMale[j]);
//				}
//			}
//		}
		for(int i=0; i<middlesFemale.length; i++) {
//			listmiddleFemale.add(middlesFemale[i]);
			for(int j=i+1; j<middlesFemale.length; j++) {
				if(!middlesFemale[i].equals(middlesFemale[j])) {
					listmiddleFemale.add(middlesFemale[i] + " " + middlesFemale[j]);
				}
			}
		}
		
//		for(int i=0; i<surs.length; i++) {
//			String fullname = "";
//			fullname += surs[i];
//			for(int j=0; j<listmiddleMale.size(); j++) {
//				if(!fullname.contains(listmiddleMale.get(j))) {
//					fullname += " " + listmiddleMale.get(j);
//					mapMale.put(fullname, listmiddleMale.get(j));
//					fullname = surs[i];
//				}
//			}
//		}
//		
//		for (String string : mapMale.keySet()) {
//			String fullname = string;
//			for(int i=0; i<namesMale.length; i++) {
//				if(!string.contains(namesMale[i])) {
//					fullname += " " + namesMale[i];
//					listCitizen.add(fullname);
//				}
//				fullname = string;
//			}
//		}
		
		for(int i=0; i<surs.length; i++) {
			String fullname = "";
			fullname += surs[i];
			for(int j=0; j<listmiddleFemale.size(); j++) {
				if(!fullname.contains(listmiddleFemale.get(j))) {
					fullname += " " + listmiddleFemale.get(j);
					mapFemale.put(fullname, listmiddleFemale.get(j));
					fullname = surs[i];
				}
			}
		}
		
		for (String string : mapFemale.keySet()) {
			String fullname = string;
			for(int i=0; i<namesFemale.length; i++) {
				if(!string.contains(namesFemale[i])) {
					fullname += " " + namesFemale[i];
					listCitizen.add(fullname);
				}
				fullname = string;
			}
		}
		List<VillageB2> listB1 = getAllVillage();
		List<CommuneB1> listaA3s = getALLCommune();
		List<Integer> listCommuneID = new ArrayList<>();
		
		for (CommuneB1 c : listaA3s) {
			listCommuneID.add(Integer.parseInt(c.getCommuneID()));
		}
		
		
		int ordinal = 137333;
		System.out.println(listCitizen.size());
		for (String name : listCitizen) {
			ordinal++;
			int r = (name.hashCode() < 0 ? name.hashCode()*(-1) : name.hashCode());
			int numberID = 100121363;
			numberID += ordinal;
			String dob = getDateRandom(1999, 2007);
			String gender = "Nữ";
			String ethnicGroup = ethnic[r%ethnic.length];
			String eduLevel = "12/12";
			int villageID = r%listB1.size() + 1;
			String time =	getDateRandom(2021, 2021) + " " + createTimeRandom();
			String poo = ModelCitizen.getPooByCommuneID(listCommuneID.get(r%listCommuneID.size()));
			String job = jobs[r%jobs.length];
			Citizen citizen = new Citizen(1,"000" + numberID,  name , dob, gender, poo, null, null,
											ethnicGroup, eduLevel, job, null, time);
			generalList.add(citizen);
			System.out.println(ordinal - 137333);
		}
		
		return generalList;
	}*/
	
	public String convert(VillageB2 communeB1) {
		String address = "";
		
		if(communeB1.getNameDistrict().contains("Thành phố")) {
			communeB1.setNameDistrict(communeB1.getNameDistrict().replace("Thành phố ", ""));
		} else if(communeB1.getNameDistrict().contains("Huyện")) {
			communeB1.setNameDistrict(communeB1.getNameDistrict().replace("Huyện ", ""));
		}
		
		if(communeB1.getNameCity().contains("Tỉnh")) {
			communeB1.setNameCity(communeB1.getNameCity().replace("Tỉnh ", ""));
		} else if(communeB1.getNameCity().contains("Thành phố")) {
			communeB1.setNameCity(communeB1.getNameCity().replace("Thành phố ", ""));
		} 
		
		if(communeB1.getNameCommune().contains("Xã")) {
			communeB1.setNameCommune(communeB1.getNameCommune().replace("Xã ", ""));
		} else if(communeB1.getNameCommune().contains("Thị trấn")) {
			communeB1.setNameCommune(communeB1.getNameCommune().replace("Thị trấn ", ""));
		} 
		
		address += "Đường " + communeB1.getNameVillage() + " - " + communeB1.getNameCommune() + " - " + communeB1.getNameDistrict() 
					+ " - " + communeB1.getNameCity();
 		
		return address;
	}
	
	public List<Citizen> getAllCitizenByLikeVillageID(String[] IDs) {
		String query = "SELECT * FROM citizen.infomation1 where ";
		for(int i=0; i< IDs.length - 1; i++) {
			query += " villageID like '" + IDs[i] + "%' or ";
		}
		query += " villageID like '" + IDs[IDs.length - 1] + "%' order by ordinal desc";
		List<Citizen> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			ps = connection.prepareStatement(query);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Citizen(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6),
									rs.getString(7),
									rs.getString(8),
									rs.getString(9),
									rs.getString(10),
									rs.getString(11),
									rs.getString(12),
									rs.getString(13)));
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
	public boolean resetCadres() {
		int i=0;
		List<CityA2> listcity = getAllCity();
		List<DistrictA3> listd = getAllDistrict();
		List<CommuneB1> listc = getALLCommune();
		List<VillageB2> listv = getAllVillage();

		ArrayList<String> list = new ArrayList<>();
		int indexcity = 0;
		for (CityA2 c:listcity) {
			Cadres cadres = getCadresByUsername(c.getCityID());
			if(cadres == null) {
				list.add(c.getCityID());
			}
			System.out.println(i++);
		}
		for (DistrictA3 c:listd) {
			Cadres cadres = getCadresByUsername(c.getDistrictID());
			if(cadres == null) {
				list.add(c.getDistrictID());
			}
			System.out.println(i++);
		}

		for (CommuneB1 c:listc) {
			Cadres cadres = getCadresByUsername(c.getCommuneID());
			if(cadres == null) {
				list.add(c.getCommuneID());
			}
			System.out.println(i++);
		}

		for (VillageB2 c:listv) {
			Cadres cadres = getCadresByUsername(c.getVillageID());
			if(cadres == null) {
				list.add(c.getVillageID());
			}
			System.out.println(i++);
		}
		int index = 0;
		for(String s: list) {
			int count = 1;
			List<Cadres> listcadres = getAllCadres();
			for (Cadres c:listcadres) {
				if (count == c.getOrdinal()) {
					count++;
				}
			}
			String pass = ModelCadres.hashPassword("1");
			String create = formatter.format(currenTime);
			String begin = "2021-12-20";
			String end = "2022-12-20";
			String rank = null;
			switch (s.length()){
				case 2:
					rank = "A2";
					break;
				case 4:
					rank = "A3";
					break;
				case 6:
					rank = "B1";
					break;
				case 8:
					rank = "B2";
					break;
			}
			Cadres cadres = new Cadres(count, s, pass, true, s, rank, create, begin, end, false);
			index += putCadresToSQL(cadres);
		}

		return index == list.size();
	}

	//có chức năng lấy người dân theo cmnd
	public Citizen getCitizenByNumberID(String numberID) {
		Citizen citizen = null;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT * FROM citizen.infomation1 WHERE ID = ?";
			ps = connection.prepareStatement(query);
			ps.setString(1, numberID);
			rs = ps.executeQuery();
			while (rs.next()) {
				citizen = new Citizen(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getString(9),
										rs.getString(10),
										rs.getString(11),
										rs.getString(12),
										rs.getString(13));
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return citizen;
	}

	//có chức năng lấy người dân theo stt
	public Citizen getCitizenByOdinal(int ordinal) {
		Citizen citizen = null;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT * FROM citizen.infomation1 WHERE ordinal = ?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, ordinal);
			rs = ps.executeQuery();
			while (rs.next()) {
				citizen = new Citizen(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13));
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return citizen;
	}

	//có chức năng thêm cán bộ vào cơ sở dữ liệu
	public int putCadresToSQL(Cadres cadres) {
/*		int count = 1;
		List<Cadres> list = getAllCadres();
		for (Cadres c:list) {
			if(count == c.getOrdinal()) {
				count++;
			}
		}*/
		int i=0;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "INSERT INTO `citizen`.`account` (`ordinal`, `username`, `password`, `grant`, `numberID`, " +
							"`rank`, `createTime`, `beginTime`, `endTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, cadres.getOrdinal());
			ps.setString(2, cadres.getUsername());
			ps.setString(3, cadres.getPassword());
			ps.setBoolean(4, cadres.isGrant());
			ps.setString(5, cadres.getNumberID());
			ps.setString(6, cadres.getRank());
			ps.setString(7, cadres.getCreateTime());
			ps.setString(8, cadres.getBeginTime());
			ps.setString(9, cadres.getEndTime());

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	//có chức năng xóa tài khoản trong SQL
	public int removeCadresToSQL(String username) {
		int i=0;
		Connection connection = ConnectionJDBC.getJDBC();
		username += "%";
		try {
			String query = "DELETE FROM `citizen`.`account` WHERE (`username` like ?);";
			ps = connection.prepareStatement(query);
			ps.setString(1, username);

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	//cấp quyền cán bộ
	public int editGrantCadres(String begin, String end, String username) {
		int i=0;
		boolean grant = ModelCadres.checkAccessTimeCadres(begin, end);
		Connection connection = ConnectionJDBC.getJDBC();
		username += "%";
		try {
			String query = "UPDATE `citizen`.`account` SET `grant` = ?, `beginTime` = ?, `endTime` = ? WHERE (`username` like ?);";
			ps = connection.prepareStatement(query);
			ps.setBoolean(1, grant);
			ps.setString(2, begin);
			ps.setString(3, end);
			ps.setString(4, username);

			i += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	//cập nhật tình trạng khảo sát dân số
	public int editStatusCadres(String username) {
		int i=0;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "UPDATE `citizen`.`account` SET `status` = ? WHERE (`username` = ?);";
			ps = connection.prepareStatement(query);
			ps.setBoolean(1, true);
			ps.setString(2, username);

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	//có chức năng thêm người dân vào database
	public int addCitizenToSQL(Citizen citizen) {
		int count = getAllCitizen().get(getAllCitizen().size() - 1).getOrdinal() + 1;
		int i=0;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "INSERT INTO `citizen`.`infomation1` (`ordinal`, `ID`, `name`, `dob`, `gender`, `poo`, `permanent`, " +
							"`temporary`, `ethnicGroup`, `eduLevel`, `job`, `villageID`, `time`) " +
							"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, count);
			ps.setString(2, citizen.getNumberID());
			ps.setString(3, citizen.getName());
			ps.setString(4, citizen.getDob());
			ps.setString(5, citizen.getGender());
			ps.setString(6, citizen.getPoo());
			ps.setString(7, citizen.getPermanent());
			ps.setString(8, citizen.getTemporary());
			ps.setString(9, citizen.getEthnicGroup());
			ps.setString(10, citizen.getEduLevel());
			ps.setString(11, citizen.getJob());
			ps.setString(12, citizen.getVillageID());
			ps.setString(13, citizen.getTime());

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	//có chức năng thêm người dân vào database
	public int editCitizenToSQL(Citizen citizen, int ordinal) {
		int i=0;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "UPDATE `citizen`.`infomation1` SET `ID` = ?, `name` = ?, `dob` = ?, `gender` = ?, `poo` = ?, " +
						"`permanent` = ?, `temporary` = ?, `ethnicGroup` = ?, `eduLevel` = ?, `job` = ?, `villageID` = ?, `time` = ? " +
						"WHERE (`ordinal` = ?);";

			ps = connection.prepareStatement(query);
			ps.setString(1, citizen.getNumberID());
			ps.setString(2, citizen.getName());
			ps.setString(3, citizen.getDob());
			ps.setString(4, citizen.getGender());
			ps.setString(5, citizen.getPoo());
			ps.setString(6, citizen.getPermanent());
			ps.setString(7, citizen.getTemporary());
			ps.setString(8, citizen.getEthnicGroup());
			ps.setString(9, citizen.getEduLevel());
			ps.setString(10, citizen.getJob());
			ps.setString(11, citizen.getVillageID());
			ps.setString(12, citizen.getTime());
			ps.setInt(13, ordinal);

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}
	public static boolean validateLetters(String txt) {
		String regx = "^[\\p{L} .'-]+$";
		Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(txt);
		return matcher.find();
	}

	//xóa người dân theo số thứu tự
	public int removeCitizenByOrdinal(int ordinal) {
		Citizen citizen = null;
		int i = 0;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "DELETE FROM `citizen`.`infomation1` WHERE (`ordinal` = ?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, ordinal);

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	//lấy 10 người dân nhập liệu gần đây nhất trong 1 khu vuc
	public List<Citizen> getCitizenMostTime(String ID, int count) {
		List<Citizen> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		ID += "%";
		try {
			String query = "SELECT * FROM citizen.infomation1 where villageID like ? order by time desc, ordinal desc limit ?;";
			ps = connection.prepareStatement(query);
			ps.setString(1, ID );
			ps.setInt(2, count);

			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Citizen(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6),
									rs.getString(7),
									rs.getString(8),
									rs.getString(9),
									rs.getString(10),
									rs.getString(11),
									rs.getString(12),
									rs.getString(13)));
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	//lấy số dân nam trong 1 khu vực
	public int getAllCitizenMale(String gender, String ID) {
		int count = 0;
		Connection connection = ConnectionJDBC.getJDBC();
		ID += "%";
		try {
			String query = "SELECT count(ordinal) as count FROM citizen.infomation1 where gender = ? and villageID like ? " +
							"order by ordinal desc;";
			ps = connection.prepareStatement(query);
			ps.setString(1, gender);
			ps.setString(2, ID);

			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return count;
	}

	//lấy số dân trong 1 khu vực
	public int getAllCitizenInArea(String ID) {
		int count = 0;
		Connection connection = ConnectionJDBC.getJDBC();
		ID += "%";
		try {
			String query = "SELECT count(ordinal) as count FROM citizen.infomation1 where villageID like ? ";
			ps = connection.prepareStatement(query);
			ps.setString(1, ID);

			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return count;
	}

	//lấy số người dân khai báo trong 1 ngày
	public int getCountCitizenByTime(String time, String ID) {
		List<Citizen> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		int count = 0;
		ID += "%";
		try {
			String query = "SELECT count(ordinal) FROM citizen.infomation1 where time = ? and villageID like ? ";
			ps = connection.prepareStatement(query);
			ps.setString(1, time);
			ps.setString(2, ID);

			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return count;
	}

	//lấy số người dân theo độ tuổi
	public int getCountCitizenByAge(int begin, int end, String ID) {
		List<Citizen> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		int count = 0;
		ID += "%";
		try {
			String query = "SELECT count(ordinal) FROM citizen.infomation1 where  year(dob) >= ? and year(dob) <= ? and " +
							"villageID like ?;";
			ps = connection.prepareStatement(query);
			ps.setInt(1, begin);
			ps.setInt(2, end);
			ps.setString(3, ID);

			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return count;
	}

	//có chức năng xóa tài khoản trong SQL
	public int update(int population, String ID) {
		int i=0;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "UPDATE citizen.village_b2 SET `population` = ? WHERE (`villageID` = ?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, population);
			ps.setString(2, ID);

			i += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	public List<Notification> getAllNotificationByID(String ID) {
		List<Notification> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT * FROM citizen.notification where dest = ?;";
			ps = connection.prepareStatement(query);
			ps.setString(1, ID);

			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Notification(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
						rs.getString(5),
						rs.getBoolean(6)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return  list;
	}
	// lay toan bo thong bao chua doc
	public List<Notification> getAllNotification() {
		List<Notification> list = new ArrayList<>();
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "SELECT * FROM citizen.notification";
			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Notification(rs.getInt(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getBoolean(6)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return  list;
	}

	//them thong bao vao sql
	public int addNotification(String src, String dest, String date, String area) {
		int i = 0;
		int ordinal = 1;
		int index = 0;
		for(Notification notification:getAllNotification()) {
			if(ordinal == notification.getOrdinal()) {
				ordinal++;
			}
			if(src.equals(notification.getSrc())) {
				index++;
			}
		}
		if(index==1) {
			return i;
		}

		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "INSERT INTO `citizen`.`notification` (`ordinal`, `src`, `dest`, `time`, area, status) " +
								"VALUES (?, ?, ?, ?, ?, ?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, ordinal);
			ps.setString(2, src);
			ps.setString(3, dest);
			ps.setString(4, date);
			ps.setString(5, area);
			ps.setBoolean(6, false);

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return  i;
	}

	//cap nhat thong bao
	public int updateNotification(int ordinal) {
		int i = 0;
		Connection connection = ConnectionJDBC.getJDBC();
		try {
			String query = "UPDATE `citizen`.`notification` SET `status` = '1' WHERE (`ordinal` = ?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, ordinal);

			i = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return  i;
	}

	public static void main(String[] args){
		DAO dao = new DAO();
		long startTime = System.nanoTime();
		Random random = new Random();


		System.out.println(dao.resetCadres());

		long endTime   = System.nanoTime();
		long totalTime = (long) ((endTime - startTime)/Math.pow(10, 6));
		System.out.println(totalTime + "ms");
	}
}
