package Model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entity.*;

//thực hiện tất cả các sự chuyển đối từ dữ liệu của client
//liên quan tới các cán bộ
public class ModelCadres {
	private static DAO dao = new DAO();
	
	public static String convertNumberIDToAddress(Cadres cadres) {
		String address = "";
			
		String rank = cadres.getRank();
		String numberID = cadres.getNumberID();
		switch(rank) {
			case "A1":
				address = "cả nước";
				break;
			case "A2":
				CityA2 cityA1 = dao.getCityByCityID(numberID);
				address = cityA1.getRank() + " " + cityA1.getNameCity();
				break;
			case "A3":
				DistrictA3 districtA2 = dao.getDistrictByDistrictID(numberID);
				CityA2 cityA1A2 = dao.getCityByCityID(districtA2.getCityID());
				address = districtA2.getRank() + " " + districtA2.getNameDistrict() + " - " 
							+ cityA1A2.getRank() + " " + cityA1A2.getNameCity();
				break;
			case "B1":
				CommuneB1 communeA3 = dao.getCommuneByCommuneID(numberID);
				DistrictA3 districtA2A3 = dao.getDistrictByDistrictID(communeA3.getDistrictID());
				CityA2 cityA1A2A3 = dao.getCityByCityID(communeA3.getCityID());
				address = communeA3.getNameCommune() + " - " + districtA2A3.getRank() + " " +
							districtA2A3.getNameDistrict() + " - " + cityA1A2A3.getRank() + " " + cityA1A2A3.getNameCity();
				break;
			case "B2":
				VillageB2 villageB2 = dao.getVillageByVillageID(numberID);
				CommuneB1 communeA3B1 = dao.getCommuneByCommuneID(villageB2.getCommuneID());
				DistrictA3 districtA2A3B1 = dao.getDistrictByDistrictID(communeA3B1.getDistrictID());
				CityA2 cityA1A2A3B1 = dao.getCityByCityID(communeA3B1.getCityID());
				address = villageB2.getRank() + " " + villageB2.getNameVillage() + " - " + communeA3B1.getNameCommune() + " - " +
						districtA2A3B1.getRank() + " " + districtA2A3B1.getNameDistrict() + " - " + cityA1A2A3B1.getRank() +
						" " + cityA1A2A3B1.getNameCity();
				break;
		}
		
		return address;
	}
	
	public static List<Cadres> getAllLowerCadresByCadres(Cadres cadres) {
		List<Cadres> list = new ArrayList<>();
		
		switch (cadres.getRank()) {
			case "A1":
				list.addAll(dao.getAllCadresByRank("A2"));
				break;
			case "A2":
				List<DistrictA3> listA3s = dao.getAllDistrictByType(cadres.getNumberID(), "cityID");
				List<String> listI = new ArrayList<>();
				for (DistrictA3 d : listA3s) {
					listI.add(d.getDistrictID());
				}
				list.addAll(dao.getAllCadresByRALID("A3", listI));
				break;
			case "A3":
				List<CommuneB1> listB1s = dao.getAllCommuneByType(cadres.getNumberID(), "districtID");
				List<String> listII = new ArrayList<>();
				for (CommuneB1 c : listB1s) {
					listII.add(c.getCommuneID());
				}
				list.addAll(dao.getAllCadresByRALID("B1", listII));
				break;
			case "B1":
				List<VillageB2> listB2s = dao.getAllVillageByType(cadres.getNumberID(), "communeID");
				List<String> listIII = new ArrayList<>();
				for (VillageB2 v : listB2s) {
					listIII.add(v.getVillageID());
				}
				list.addAll(dao.getAllCadresByRALID("B2", listIII));
				break;
		}
		
		return list;
	}
	
	public static List<Cadres> filterCadresByCityID(List<Cadres> oldList, String cityID) {
		List<String> listI = new ArrayList<>();
		listI.add(cityID);
		List<Cadres> list = dao.getAllCadresByRALID("A1", listI);
		List<DistrictA3> listA2s = dao.getAllDistrictByType(cityID, "cityID");
		List<CommuneB1> listA3s = dao.getAllCommuneByType(cityID, "cityID");
		List<VillageB2> listB1s = dao.getAllVillageByType(cityID, "cityID");
		List<String> listII = new ArrayList<>();
		for (DistrictA3 districtA2 : listA2s) {
			listII.add(districtA2.getDistrictID());
		}
		list.addAll(dao.getAllCadresByRALID("A2", listII));
		
		List<String> listIII = new ArrayList<>();
		for (CommuneB1 c : listA3s) {
			listIII.add(c.getCommuneID());
		}
		list.addAll(dao.getAllCadresByRALID("A3", listIII));
		List<String> listIIII = new ArrayList<>();
		for (VillageB2 v : listB1s) {
			listIIII.add(v.getVillageID());
		}
		list.addAll(dao.getAllCadresByRALID("B1", listIIII));
		
		List<String> listIIIII = new ArrayList<>();
		for (VillageB2 v : listB1s) {
			listIIIII.add(v.getVillageID());
		}
		list.addAll(dao.getAllCadresByRALID("B2", listIIIII));
		
		return list;
	}
	
	public static List<Cadres> filterCadresByDistrictID(List<Cadres> oldList, String districtID) {
		List<String> listI = new ArrayList<>();
		listI.add(districtID);
		List<Cadres> list = dao.getAllCadresByRALID("A2", listI);
		List<CommuneB1> listA3s = dao.getAllCommuneByType(districtID, "districtID");
		List<VillageB2> listB1s = dao.getAllVillageByType(districtID, "districtID");
		
		List<String> listIII = new ArrayList<>();
		for (CommuneB1 c : listA3s) {
			listIII.add(c.getCommuneID());
		}
		list.addAll(dao.getAllCadresByRALID("A3", listIII));
		List<String> listIIII = new ArrayList<>();
		for (VillageB2 v : listB1s) {
			listIIII.add(v.getVillageID());
		}
		list.addAll(dao.getAllCadresByRALID("B1", listIIII));
		List<String> listIIIII = new ArrayList<>();
		for (VillageB2 v : listB1s) {
			listIIIII.add(v.getVillageID());
		}
		list.addAll(dao.getAllCadresByRALID("B2", listIIIII));
		
		return list;
	}
	
	public static List<Cadres> filterCadresByCommuneID(List<Cadres> oldList, String communeID) {
		List<String> listI = new ArrayList<>();
		listI.add(communeID);
		List<Cadres> list = dao.getAllCadresByRALID("A3", listI);
		List<VillageB2> listB1s = dao.getAllVillageByType(communeID, "communeID");
		
		List<String> listIIII = new ArrayList<>();
		for (VillageB2 v : listB1s) {
			listIIII.add(v.getVillageID());
		}
		list.addAll(dao.getAllCadresByRALID("B1", listIIII));
		List<String> listIIIII = new ArrayList<>();
		for (VillageB2 v : listB1s) {
			listIIIII.add(v.getVillageID());
		}
		list.addAll(dao.getAllCadresByRALID("B2", listIIIII));
		
		return list;
	}

	// lọc cán bộ ứng với số trang client yêu cầu
	public static List<Cadres> filterCadresByPage(int left, int right, List<Cadres> oldList) {
		List<Cadres> list = new ArrayList<>();
		right = (oldList.size() < 25) ? oldList.size() : right;
		for(int i=left; i<right; i++) {
			list.add(oldList.get(i));
		}
		
		return list;
	}

	//chuyển 1 danh sách cán bộ sang JSON
	public static String convertListToJSON(List<Cadres> list) {
		String[] array = new String[list.size()];
		int index = 0;
		for (Cadres c : list) {
			array[index++] = c.toJSON();
		}
		
		return Arrays.toString(array);
	}

	//băm mật khẩu
	public static String hashPassword(String password) {
		String hashPass = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			
		    StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
		    for (int i = 0; i < encodedhash.length; i++) {
		        String hex = Integer.toHexString(0xff & encodedhash[i]);
		        if(hex.length() == 1) {
		            hexString.append('0');
		        }
		        hexString.append(hex);
		    }
		    hashPass = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		return hashPass;
	}
	
	// thực hiện việc kiểm tra xem cán bộ còn thời gian cấp quyền hay không
	//true la co quyen //false la khong
	public static boolean checkAccessTimeCadres(String begin, String end) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowDay = formatter.format(LocalDateTime.now());

		int yearend = Integer.parseInt(end.split("-")[0]) - Integer.parseInt(nowDay.split("-")[0]);
		int monthend = Integer.parseInt(end.split("-")[1]) - Integer.parseInt(nowDay.split("-")[1]);
		int dayend = Integer.parseInt(end.split("-")[2]) - Integer.parseInt(nowDay.split("-")[2]);

		int yearbegin = Integer.parseInt(nowDay.split("-")[0]) - Integer.parseInt(begin.split("-")[0]);
		int monthbegin = Integer.parseInt(nowDay.split("-")[1]) - Integer.parseInt(begin.split("-")[1]);
		int daybegin = Integer.parseInt(nowDay.split("-")[2]) - Integer.parseInt(begin.split("-")[2]);

		boolean isnoDateEnd = yearend > 0 || (yearend == 0 && (monthend > 0 || (monthend == 0 && (dayend >= 0 ))));
		boolean isnoDateBegin = yearbegin > 0 || (yearbegin == 0 && (monthbegin > 0 || (monthbegin == 0 && (daybegin >= 0 ))));
		return isnoDateEnd && isnoDateBegin;
	}

	//có chức năng tìm cấp dưới
	public static String searchLowerCadres(Cadres cadres) {
		String rank = null;
		switch (cadres.getRank()) {
			case "A1":
				rank = "A2";
				break;
			case "A2":
				rank = "A3";
				break;
			case "A3":
				rank = "B1";
				break;
			case "B1":
				rank = "B2";
				break;
		}
		return rank;
	}

	//kiểm tra tài khoản tạo của cán bộ có hợp lệ không
	public static boolean checkInfoCadres(Cadres oldCadres, Cadres cadresUp, String managearea, String repass) {
		String rank = searchLowerCadres(cadresUp);
		String address = convertNumberIDToAddress(oldCadres).toLowerCase();
		int length = 0;
		switch (rank) {
			case "A2":
				length = 2;
				break;
			case "A3":
				length = 4;
				break;
			case "B1":
				length = 6;
				break;
			case "B2":
				length = 8;
				break;
		}
		int lengthUp = length - 2;
		boolean isLength = oldCadres.getUsername().length() == length;
		boolean isLeft = lengthUp == 0 || (oldCadres.getUsername().indexOf(cadresUp.getUsername()) == 0);
		boolean isExist = (dao.getCadresByUsername(oldCadres.getUsername()) == null);
		boolean isUC = oldCadres.getUsername().equals(oldCadres.getNumberID());
		boolean isAddress = address.contains(managearea.toLowerCase());
		boolean isPass = oldCadres.getPassword().equals(repass);

		System.out.println(isLength);
		System.out.println(isLeft);
		System.out.println(isExist);
		System.out.println(isUC);
		System.out.println(isAddress);
		System.out.println(isPass);
		return isExist && isLeft && isLength && isUC && isAddress && isPass;
	}

	//có chức năng thực hiện tìm kiếm cán bộ theo tk và khu vực quản lí
	public static List<Cadres> filterSearchCadresByUsernameAndManageArea(List<Cadres> oldList, String search) {
		List<Cadres> list = new ArrayList<>();
		for (Cadres cadres : oldList) {
			if(cadres.getManageArea().toLowerCase().contains(search.toLowerCase())
			 	|| DAO.convertAccentToUnisigned(cadres.getManageArea()).toLowerCase().contains(search)) {
				list.add(cadres);
			}
		}

		return list;
	}

	//có chức năng lấy tên các đường làng thuộc B1 và B2
	public static List<VillageB2> getAllVillageByCadres(Cadres cadres, String type) {
		CommuneB1 communeB1 = null;
		List<VillageB2> list = dao.getAllVillageByType(cadres.getNumberID(), type);
		String city = null, commune = null, district = null, village = null;
		if(cadres.getRank().equals("B1")) {
			communeB1 = dao.getCommuneByCommuneID(cadres.getNumberID());
		} else {
			communeB1 = dao.getCommuneByCommuneID(cadres.getNumberID().substring(0, cadres.getNumberID().length() - 2));
		}
		city = communeB1.getNameCity();
		district = communeB1.getNameDistrict();
		commune = communeB1.getNameCommune();
		commune = commune.replace(communeB1.getRank() + " ", "");
		if(city.contains("Thành phố")) {
			city = city.replace("Thành phố ", "");
		} else if(city.contains("Tỉnh")) {
			city = city.replace("Tỉnh ", "");
		}

		if(district.contains("Thành phố")) {
			district = (district.replace("Thành phố ", ""));
		} else if(district.contains("Thị xã")) {
			district = (district.replace("Thị xã ", ""));
		} else if(district.contains("Quận")) {
			district = (district.replace("Quận ", ""));
		} else if(district.contains("Huyện")) {
			district = (district.replace("Huyện ", ""));
		}
		for (VillageB2 v:list) {
			v.setNameCity(city);
			v.setNameCommune(commune);
			v.setNameDistrict(district);
		}
		return list;
	}

	//thong bao
	public static int handleNotification(String ID) {
		String address = null;
		switch (ID.length()) {
			case 2:
				CityA2 cityA2 = dao.getCityByCityID(ID);
				address = cityA2.getRank() + " " + cityA2.getNameCity();
				break;
			case 4:
				DistrictA3 districtA3 = dao.getDistrictByDistrictID(ID);
				address = districtA3.getRank() + " " + districtA3.getNameDistrict();
				break;
			case 6:
				CommuneB1 communeB1 = dao.getCommuneByCommuneID(ID);
				address = communeB1.getNameCommune();
				break;
			case 8:
				VillageB2 villageB2 = dao.getVillageByVillageID(ID);
				address = villageB2.getRank() + " " + villageB2.getNameVillage();
				break;
		}
		int i = dao.editStatusCadres(ID);
		int j = dao.addNotification(ID, ID.substring(0, ID.length() - 2),
				DAO.formatterDay.format(DAO.currenTime), address);

		return  i + j;
	}


}
