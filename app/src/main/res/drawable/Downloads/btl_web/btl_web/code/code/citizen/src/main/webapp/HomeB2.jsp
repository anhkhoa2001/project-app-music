<%@page import="Entity.Citizen"%>
<%@page import="Entity.Cadres"%>
<%@ page import="Entity.VillageB2" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CitizenV</title>
    <link rel="stylesheet" href="resources/css/home.css">
    <link rel="stylesheet" href="resources/css/all.min.css">
</head>
<body>
<div id = "menu">
    <div class = "logo">
        <a href="">citizenv</a>
    </div>
    <ul class = "navigation">
        <li class = "input">
            <span class = "btn_navi" onclick = "clickTabMenu(this, 'input')">
                <i class="fas fa-edit"></i>Nhập liệu
            </span>
        </li>
    </ul>
</div>

<div id = "container">
    <div class="header">
        <div>
            <button type="submit" onclick="sendNotification()">Gửi thông báo</button>
        </div>
        <span class = "account">
            <img src="resources/img/avatar.jpg" alt="_self">
            <p>${sessionScope.account.username }<i class="fas fa-chevron-down"></i></p>
        </span>
        <div class = "drop_account">
            <p>Welcome!</p>
            <a href="#" class = "hover"><i class="far fa-user"></i>Tài khoản của tôi</a>
            <a href="#" class = "hover"><i class="far fa-life-ring"></i>Trợ giúp</a>
            <a href="/citizen_war_exploded/logout" class = "hover"><i class="fas fa-power-off"></i>Đăng xuất</a>
        </div>
    </div>
    <div class="input_general open">
        <div class = "input_header">
            <p>Nhập thông tin</p>
            <span>CitizenV<i class="fas fa-chevron-right"></i>Nhập liệu<i class="fas fa-chevron-right"></i>Nhập thông tin</span>
        </div>
        <div class="input_content">
            <div class = "input_content_grid">
                <h1>Phiếu khai báo thông tin cá nhân</h1>
                <div class = "fullname">
                    <label for="fullname">Họ và tên </label>
                    <input type="text" name = "fullname" id = "fullname" placeholder="VD: Đàm Tam Khoa">
                    <span id="name-message"></span>
                </div>
                <div class = "dob">
                    <label for="dob">Ngày sinh </label>
                    <input type="date" name="date" id="dob">
                    <span id='date-message'></span>
                </div>
                <div class = "id">
                    <label for="numberID">Số CMND</label>
                    <input type="text" name = "numberID" id = "numberID" placeholder="Nhập đủ 12 số">
                    <span id="id-message"></span>
                </div>
                <div class = "gender">
                    <label for="female">Giới tính</label>
                    <div>
                        <input type="radio" name="sex" id="female">
                        <label for="female" style="margin-right: 24px">Nữ</label>
                        <input type="radio" name="sex" id="male">
                        <label for="male">Nam</label>
                    </div>
                    <span id='sex-message'></span>
                </div>
                <div class = "poo">
                    <label for="poo">Quê quán</label>
                    <input type="text" name = "poo" id = "poo" placeholder="VD: Vĩnh Thượng-Khai Thái-Phú Xuyên-Hà Nội">
                    <span id="poo-message"></span>
                </div>
                <div class="address permanent">
                    <div class = "title">
                        <h2>Địa chỉ thường trú</h2>
                        <hr>
                    </div>
                    <div class = "postcode">
                        <label>Mã địa chỉ: </label>
                        <select>
                            <c:forEach items="${listvillage}" var = "o">
                                <option value="${o.villageID}">${o.villageID}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class = "village">
                        <label>Thôn/Đường: </label>
                        <input type="text" value="" name="village1" id="address1">
                    </div>
                    <div class = "commune">
                        <label>Xã/Phường: </label>
                        <input type="text" value="" name="commune1" id="address2">
                    </div>
                    <div class = "district">
                        <label>Quận/Huyện: </label>
                        <input type="text" value="" name="district1" id="address3">
                    </div>
                    <div class = "city">
                        <label>Tỉnh/Thành phố: </label>
                        <input type="text" value="" name="city1" id="address4">
                    </div>
                    <span id="address1-message"></span>
                </div>
                <div class="address temporary">
                    <div class = "title">
                        <h2>Địa chỉ tạm trú</h2>
                        <hr>
                    </div>
                    <div class = "postcode">
                        <label>Mã địa chỉ: </label>
                        <select>
                            <c:forEach items="${listvillage}" var = "o">
                                <option value="${o.villageID}">${o.villageID}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class = "village">
                        <label>Thôn/Đường: </label>
                        <input type="text" value="" name="village2" id="address5">
                    </div>
                    <div class = "commune">
                        <label>Xã/Phường: </label>
                        <input type="text" value="" name="commune2" id="address6">
                    </div>
                    <div class = "district">
                        <label>Quận/Huyện: </label>
                        <input type="text" value="" name="district2" id="address7">
                    </div>
                    <div class = "city">
                        <label>Tỉnh/Thành phố: </label>
                        <input type="text" value="" name="city2" id="address8">
                    </div>
                    <span id="address2-message"></span>
                </div>
                <div class="ethnicGroup">
                    <label for="ethnicGroup">Tôn giáo</label>
                    <input type="text" name="ethnicGroup" id="ethnicGroup" placeholder="VD: Không">
                    <span id="ethnicGroup-message"></span>
                </div>
                <div class="eduLevel">
                    <label for="eduLevel">Trình độ văn hóa</label>
                    <input type="text" name="eduLevel" id="eduLevel" placeholder="VD: 12/12">
                    <span id="eduLevel-message"></span>
                </div>
                <div class="job">
                    <label for="job">Nghề nghiệp</label>
                    <input type="text" name="job" id="job" placeholder="VD: Kĩ sư">
                    <span id="job-message"></span>
                </div>
                <div class = "submit" onclick = "checked()">
                    <button type="submit">Nhập</button>
                    <a href="https://docs.google.com/document/d/1xKSpF_4--lGqVgeD7SzvnPejPZ70vWjPd68Rj4b1tOc/edit" class="csw-btn-button" rel="nofollow" target="_blank" >In</a>
                </div>
            </div>
        </div>
        <div class="input_footer">
            <p>Created by <a href="">name@gmail.com</a></p>
            <ul>
                <li><a href="#">Thông tin về chúng tôi</a></li>
                <li><a href="#">Trợ giúp</a></li>
                <li><a href="#">Liên hệ</a></li>
            </ul>
        </div>
    </div>
</div>
<script>
    let Cadres = {
        rank: "${sessionScope.account.rank }",
        numberID: "${sessionScope.account.numberID }",
        username: "${sessionScope.account.username }",
        endTime: "${sessionScope.account.endTime }",
        manageArea: "${sessionScope.account.manageArea }",
        grant: ${sessionScope.account.grant }
    }

    let arrayVillages = [];
    <%
    List<VillageB2> listVillage = new ArrayList<>();
    if(request.getAttribute("listvillage") != null) {
        listVillage.addAll((List<VillageB2>) request.getAttribute("listvillage"));
    }
    for (int i=0; i<listVillage.size(); i++) { %>
    arrayVillages[arrayVillages.length++] = {
        postcode: "<%= listVillage.get(i).getVillageID() %>",
        namevillage: "<%= listVillage.get(i).getNameVillage() %>",
        namedistrict: "<%= listVillage.get(i).getNameDistrict() %>",
        namecity: "<%= listVillage.get(i).getNameCity() %>",
        namecommune: "<%= listVillage.get(i).getNameCommune() %>"
    }
    <% } %>
</script>
<script src="resources/js/homeb2.js"></script>
</body>
</html>
