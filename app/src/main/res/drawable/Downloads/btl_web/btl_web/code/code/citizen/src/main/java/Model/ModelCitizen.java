package Model;

import java.util.*;

import Entity.*;
import Model.json.JSONObject;

//thực hiện tất cả các sự chuyển đối từ dữ liệu của client
//liên quan tới các người dân
public class ModelCitizen {
	private static DAO dao = new DAO();

	public static String convertVillageIDToStringAddress(String villageID) {
		String address = "";
		String nameVillage = dao.getVillageByVillageID(villageID).getRank() + " " + dao.getVillageByVillageID(villageID).getNameVillage();
		CommuneB1 communeB1 = dao.getCommuneByCommuneID(villageID.substring(0, villageID.length() - 2));
		address = nameVillage + " - " + communeB1.getNameCommune() + " - " + communeB1.getNameDistrict() + " - " + communeB1.getNameCity();

		return address;
	}
	
	public static String getPooByCommuneID(int communeID) {
		String poo = "";
		CommuneB1 communeA3 = dao.getCommuneByCommuneID(String.valueOf(communeID));
		poo += communeA3.getRank() + " " + communeA3.getNameCommune() + " - ";
		DistrictA3 districtA2 = dao.getDistrictByDistrictID(communeA3.getDistrictID());
		poo += districtA2.getRank() + " " + districtA2.getNameDistrict() + " - ";
		CityA2 cityA1 = dao.getCityByCityID(communeA3.getCityID());
		poo += cityA1.getRank() + " " + cityA1.getNameCity();
		return poo;
	}
	
	public static List<Citizen> filterCitizenByCadres(String[] IDs) {
		return dao.getAllCitizenByLikeVillageID(IDs);
	}
	
	
	public static List<Citizen> filterCitizenByPage(int left, int right, List<Citizen> oldList) {
		List<Citizen> list = new ArrayList<>();
		right = (oldList.size() < 25) ? oldList.size() : right;
		for(int i=left; i<right; i++) {
			list.add(oldList.get(i));
		}
		
		return list;
	}
	
	public static String convertListToJSON(List<Citizen> list) {
		String[] array = new String[list.size()];
		int index = 0;
		for (Citizen c : list) {
			array[index++] = c.toJSON();
		}
		
		return Arrays.toString(array);
	}
	
	public static List<Citizen> filterSearchCitizenByString(String word, List<Citizen> oldList) {
		List<Citizen> list = new ArrayList<>();
		List<Citizen> oList = oldList;
		for (Citizen citizen : oList) {
			if(citizen.getNameNotUnisign().contains(word)) {
				list.add(citizen);
			}
		}
		
		return list;
	}
	
	public static List<Citizen> sortCitizenByTypeAndValue(String type, String value, List<Citizen> oldList) {
		List<Citizen> list = oldList;
		switch (type) {
			case "name":
				if(value.equals("top")) {
					//a->z
					Collections.sort(list, new Comparator<Citizen>() {
						@Override
						public int compare(Citizen o1, Citizen o2) {
							return o1.getNameNotUnisign().compareTo(o2.getNameNotUnisign());
						}
					});
				} else {
					//z->a
					Collections.sort(list, new Comparator<Citizen>() {
						@Override
						public int compare(Citizen o1, Citizen o2) {
							return o2.getNameNotUnisign().compareTo(o1.getNameNotUnisign());
						}
					});
				}
				break;
		}
		return list;
	}
	public static String filterGeneralCitizen(String[] ID, String search, int count, int page,List<Citizen> oldList) {
		List<Citizen> list = oldList;
		list = ModelCitizen.filterCitizenByCadres(ID);
		list = (!search.equals("")) ? ModelCitizen.filterSearchCitizenByString(search, list) : list;

		int size = list.size();
		int countPage = list.size()/count + 1;
		int countleft = 1;
		int countright = count;
		if(page < countPage) {
			countleft = (page-1)*count;
			countright = page*count;
		} else if(page == countPage) {
			countleft = (page-1)*count ;
			countright = list.size();
		}
		list = ModelCitizen.filterCitizenByPage(countleft, countright, list);
		String dataJSON = ModelCitizen.convertListToJSON(list);
		Map<Integer, Integer> chart3 = getCountCitizenInTime(ID);
		Map<String, Integer> chart4 = getRadioCitizenByArea(ID);
		Map<String, Integer> chart5 = getRatioGenderCitizen(ID);
		Map<String, Integer> chart6 = getRatioAgeCitizen(ID);

		String[] labels = new String[chart3.size()];
		int[] datas = new int[chart3.size()];
		int index = 0;
		for(int s:chart3.keySet()) {
			labels[index] = "\"" + s + "\"";
			datas[index] = chart3.get(s);
			index++;
		}
		String datachart3 = "\"chart3\": {\n";
		datachart3 += "\t\"chartlabel\": " + Arrays.toString(labels) + ",\n";
		datachart3 += "\t\"chartdata\": " + Arrays.toString(datas) + "\n}";

		String data = "{\n"
						+ "\t\"countPage\": " + countPage + ",\n"
						+ "\t\"countleft\": " + countleft + ",\n"
						+ "\t\"countright\": " + countright + ",\n"
						+ "\t\"size\": " + size + ",\n"
						+ "\t\"dataresponse\": " + dataJSON + ",\n"
						+ datachart3 + ",\n"
						+ convertMapToJson(chart4, "chart4") + ",\n"
						+ convertMapToJson(chart5, "chart5") + ",\n"
						+ convertMapToJson(chart6, "chart6") + "\n"
						+ "}";
		return data;
	}

	//xử lí các chuỗi
	public static String handlingString(String str) {
		str = str.toLowerCase();
		str = str.replaceAll("\\s+", " ");
		if(str.charAt(0) == ' ') {
			str = str.substring(1, str.length());
		}

		if(str.charAt(str.length() - 1) == ' ') {
			str = str.substring(0, str.length() - 1);
		}

		for(int i=0; i<str.split(" ").length; i++) {
			String s1 = str.split(" ")[i];
			String s2 = str.split(" ")[i];
			s2 = s2.substring(0, 1).toUpperCase() + s2.substring(1, s2.length());
			str = str.replaceAll(s1, s2);
		}
		return str;
	}

	//hàm này xử lí dữ liệu JSON trong request
	// trả về đối tượng người dân
	public static Citizen handleDataJsonToCitizen(JSONObject obj, String type) {
		Citizen citizen = new Citizen();
		int ordinal = dao.getAllCitizen().size() + 1;
		String name = handlingString(obj.getString("name"));
		String dob = obj.getString("dob");//year-month-day
		String numberID = obj.getString("numberID");
		String gender = obj.getBoolean("gender") ? "Nữ" : "Nam";
		String poo = handlingString(obj.getString("poo"));
		//dia chi thuong tru
		String permanent_postcode = handlingString(obj.getJSONObject("permanent").getString("postcode"));
		String permanent_village = handlingString(obj.getJSONObject("permanent").getString("village"));
		String permanent_commune = handlingString(obj.getJSONObject("permanent").getString("commune"));
		String permanent_district = handlingString(obj.getJSONObject("permanent").getString("district"));
		String permanent_city = handlingString(obj.getJSONObject("permanent").getString("district"));
		//dia chi tam tru
		String temporary_postcode = handlingString(obj.getJSONObject("temporary").getString("postcode"));
		String temporary_village = handlingString(obj.getJSONObject("temporary").getString("village"));
		String temporary_commune = handlingString(obj.getJSONObject("temporary").getString("commune"));
		String temporary_district = handlingString(obj.getJSONObject("temporary").getString("district"));
		String temporary_city = handlingString(obj.getJSONObject("temporary").getString("district"));

		String ethnicGroup = handlingString(obj.getString("ethnicGroup"));
		String eduLevel = obj.getString("eduLevel");
		String job = obj.getString("job");
		//kiem tra cmnd
		int yearDob = Integer.parseInt(dob.substring(0, 4));
		boolean isNumberID = yearDob > 2003 || (numberID.length() == 12 && dao.getCitizenByNumberID(numberID) == null);
		//kiem tra que quan
		boolean isPoo = false;
		List<CommuneB1> listCommunes = dao.getALLCommune();
		for(CommuneB1 c : listCommunes) {
			if(c.getNameCommune().contains(handlingString(poo.split("-")[0]))
					&& c.getNameDistrict().contains(handlingString(poo.split("-")[1]))
					&& c.getNameCity().contains(handlingString(poo.split("-")[2]))) {
				isPoo = true;
				break;
			}
		}
		// kiem tra dia chi thuong tru
		boolean isPermanent = false;
		String address_permanent = convertVillageIDToStringAddress(permanent_postcode);
		int countpermanent = 0;
		while((address_permanent.contains(permanent_city) || address_permanent.contains(permanent_commune) ||
				address_permanent.contains(permanent_district) || address_permanent.contains(permanent_village))
				&& countpermanent < 4) {
			countpermanent++;
		}
		isPermanent = countpermanent == 4;

		//kiem tra dia chi tam tru
		boolean isTemporary = true;
		String address_temporary = convertVillageIDToStringAddress(permanent_postcode);
		int counttemporary = 0;
		while((address_temporary.contains(temporary_city) || address_temporary.contains(temporary_commune) ||
				address_temporary.contains(temporary_district) || address_temporary.contains(temporary_village))
				&& counttemporary < 4) {
			counttemporary++;
			System.out.println(counttemporary);
		}
		isTemporary = counttemporary == 4;
		citizen = new Citizen(1, numberID, name, dob, gender, poo, address_permanent, address_temporary, ethnicGroup,
						eduLevel, job, permanent_postcode, DAO.formatter.format(DAO.currenTime).split(" ")[0]);
		System.out.println(citizen.toJSON());
		System.out.println(isNumberID);
		System.out.println(isPoo);
		System.out.println(isPermanent);
		System.out.println(isTemporary);
		if(yearDob > 2003) {
			citizen.setNumberID("Chưa được cấp");
		}

		if(type.equals("add")) {
			return (isNumberID && isPoo && isPermanent && isTemporary) ? citizen : null;
		} else {
			return (isPoo && isPermanent && isTemporary) ? citizen : null;
		}
	}
	//chart 1 and chart 3
	//xử lí tỉ lệ lấy số người dân trong từng ngày
	public static Map<Integer, Integer> getCountCitizenInTime(String[] ID) {
		List<Citizen> listCitizen = dao.getAllCitizenByLikeVillageID(ID);
		int nowDay = Integer.parseInt(DAO.formatter.format(DAO.currenTime).split(" ")[0].split("-")[2]);
		Map<Integer, Integer> map = new HashMap<>();
		for(int i=0; i<nowDay; i++) {
			String day = "2021-12-" + String.format("%02d", i+1);
			int val = 0;
			for(int j=0; j<listCitizen.size(); j++) {
				if(listCitizen.get(j).getTime().equals(day)) {
					val++;
				}
			}
			map.put(i+1, val);
		}

		return map;
	}
	//chart 2 - 4
	//xử lí lấy số người dân trong khu vực
	public static Map<String, Integer> getRadioCitizenByArea(String[] IDs) {
		List<String> listID = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		for(String ID:IDs) {
			switch (ID.length()) {
				case 0:
					List<CityA2> lista2 = dao.getAllCity();
					for(int i=0; i<lista2.size(); i++) {
						if(lista2.get(i).getPopulation() > 0) {
							map.put(lista2.get(i).getNameCity(), lista2.get(i).getPopulation());
						}
					}
					break;
				case 2:
					List<DistrictA3> lista3 = dao.getAllDistrictByType(ID, "cityID");
					for(int i=0; i<lista3.size(); i++) {
						if(lista3.get(i).getPopulation() > 0) {
							map.put(lista3.get(i).getRank() + " " +lista3.get(i).getNameDistrict(), lista3.get(i).getPopulation());
						}
					}
					break;
				case 4:
					List<CommuneB1> listb1 = dao.getAllCommuneByType(ID, "districtID");
					for(int i=0; i<listb1.size(); i++) {
						if(listb1.get(i).getPopulation() > 0) {
							map.put(listb1.get(i).getNameCommune(), listb1.get(i).getPopulation());
						}
					}
					break;
				case 6:
					List<VillageB2> listb2 = dao.getAllVillageByType(ID, "communeID");
					for(int i=0; i<listb2.size(); i++) {
						if(listb2.get(i).getPopulation() > 0) {
							map.put(listb2.get(i).getRank() + " " + listb2.get(i).getNameVillage(), listb2.get(i).getPopulation());
						}
					}
					break;
				case 8:
					VillageB2 villageB2 = dao.getVillageByVillageID(ID);
					map.put(villageB2.getRank() + " " + villageB2.getNameVillage(), villageB2.getPopulation());
					break;
			}
		}
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		int count = 0, value = 5;
		if(map.size() < value) {
			return map;
		} else {
			int sum = 0;
			map = new HashMap<>();
			for (Map.Entry<String, Integer> entry : list) {
				count++;
				sum += entry.getValue();
				if(count <= value) {
					sum -= entry.getValue();
					map.put(entry.getKey(), entry.getValue());
				}
			}
			map.put("Khu vực khác", sum);
			return map;
		}
	}

	//chart 6
	//xử lí tính tỉ lệ số nam và nữ trong 1 khu vực
	public static Map<String, Integer> getRatioGenderCitizen(String[] IDs) {
		Map<String, Integer> map = new HashMap<>();
		int countMale = 0;
		int countFemale = 0;
		for(String ID:IDs) {
			countMale += dao.getAllCitizenMale("Nam", ID);
			countFemale += dao.getAllCitizenMale("Nữ", ID);
		}
		map.put("Nam", countMale);
		map.put("Nữ", countFemale);
		return map;
	}

	//chart 5
	//xử lí tính tỉ lệ độ tuổi
	//0-17   18-25   26-40    41>
	//2021-2004  2003-1996  1995-1981   1980-
	public static Map<String, Integer> getRatioAgeCitizen(String[] IDs) {
		Map<String, Integer> map = new HashMap<>();
		int age0to17 = 0;
		int age18to25 = 0;
		int age26to40 = 0;
		int age41 = 0;

		for(String ID:IDs) {
			age0to17 += dao.getCountCitizenByAge(2004, 2021, ID);
			age18to25 += dao.getCountCitizenByAge(1996, 2003, ID);
			age26to40 += dao.getCountCitizenByAge(1981, 1995, ID);
			age41 += dao.getCountCitizenByAge(1900, 1980, ID);
		}

		map.put("Dưới 18 tuổi", age0to17);
		map.put("Từ 18 tới 25 tuổi", age18to25);
		map.put("Từ 26 tới 40 tuổi", age26to40);
		map.put("Trên 40 tuổi", age41);
		return map;
	}

	public static String convertMapToJson(Map<String, Integer> map, String name) {
		String[] labels = new String[map.size()];
		int[] datas = new int[map.size()];
		int index = 0;
		for(String s:map.keySet()) {
			labels[index] = "\"" + s + "\"";
			datas[index] = map.get(s);
			index++;
		}
		String data = "\"" + name + "\": {\n";
		data += "\t\"chartlabel\": " + Arrays.toString(labels) + ",\n";
		data += "\t\"chartdata\": " + Arrays.toString(datas) + "\n}";
		return data;
	}
}
