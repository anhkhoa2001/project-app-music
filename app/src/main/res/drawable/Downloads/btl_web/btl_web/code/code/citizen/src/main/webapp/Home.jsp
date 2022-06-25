<%@page import="Entity.Citizen"%>
<%@page import="Entity.Cadres"%>
<%@ page import="Entity.VillageB2" %>
<%@ page import="java.util.*" %>
<%@ page import="Entity.Notification" %>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
</head>
<body>
    <div id = "menu">
        <div class = "logo">
            <a href="">citizenv</a>
        </div>
        <ul class = "navigation">
            <li class = "home">
                <span class = "btn_navi open" onclick = "clickTabMenu(this, 'home')"><i class="fas fa-home"></i>Trang chủ</span>
            </li>
            <c:if test = "${sessionScope.account.rank == 'B1' || sessionScope.account.rank == 'B2'}">
                <li class = "input">
                    <span class = "btn_navi" onclick = "clickTabMenu(this, 'input')">
                        <i class="fas fa-edit"></i>Nhập liệu
                    </span>
                </li>
            </c:if>
            <c:if test = "${sessionScope.account.rank != 'B2'}" >
                <li class = "view">
                    <span class = "btn_navi" onclick = "clickTabMenu(this, 'cadres')">
                        <i class="fas fa-street-view"></i>Cấp quyền truy cập
                    </span>
                </li>
                <li class="info_user">
                    <span class = "btn_navi" onclick = "clickTabMenu(this, 'citizen')">
                        <i class="fas fa-users"></i>Thông tin người dân
                    </span>
                </li>
            </c:if>
        </ul>
    </div>

    <div id = "container">
        <div class="header">
            <%
                List<Notification> listNoti = (List<Notification>) request.getAttribute("listnoti");
                int count = 0;
                for(int i=0; i<listNoti.size(); i++) {
                    if(!listNoti.get(i).isStatus()) {
                        count++;
                    }
                    }
            %>
            <!-- Responsive Menu -->
            <label for="menu_responsive">
                <i class="fas fa-bars"></i>
            </label>
            <input hidden type="checkbox" name="" id="menu_responsive">
            <!-- Responsive Menu -->
            <span class = "notification" onclick="isSeen()">
                <i class="far fa-bell"></i>
                <span class = "count"><%=count%></span>
            </span>
            <c:if test="${sessionScope.account.rank != 'A1'}">
                <div>
                    <button type="submit" onclick="sendNotification()">Gửi thông báo</button>
                </div>
            </c:if>
            <div class = "drop_notification">
                <p>Thông báo</p>
                <%
                    for(int i=0; i<listNoti.size(); i++) {
                        if(!listNoti.get(i).isStatus()){
                %>
                    <a href="">Khu vực mã <%=listNoti.get(i).getSrc() %> thuộc <%=listNoti.get(i).getArea() %> đã hoàn thành xong</a>
                <%}}%>
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

        <div class = "home open">
            <div class = "home_header">
                <p>Trang chủ</p>
                <span>CitizenV<i class="fas fa-chevron-right"></i>Trang chủ</span>
            </div>
            <!-- phần canvas vẽ biểu đồ -->
            <div class = "home_statistical">
                <div class="left_diagram">
                    <canvas id="myChart1"></canvas>
                </div>
                <div class="right_diagram">
                    <canvas id="myChart2"></canvas>
                </div>
            </div>
            <div class = "home_recent">
                <p>Số liệu nhập gần đây</p>
                <table class="tbl_responsive">
                    <thead>
                        <tr>
                            <th class = "responsive_ipad">ID</th>
                            <th>Họ và tên</th>
                            <th>Ngày sinh</th>
                            <th>CMND</th>
                            <th>Địa chỉ thường trú</th>
                            <th class = "responsive_ipad">Tôn giáo</th>
                            <th class = "responsive_ipad">Trình độ văn hóa</th>
                            <th class = "responsive_ipad">Nghề nghiệp</th>
                            <th>Thời gian nhập</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Citizen> listcitizen10 = (List<Citizen>) request.getAttribute("listcitizen10");
                            for(int i=0; i<listcitizen10.size(); i++) {
                                Citizen c = listcitizen10.get(i);
                        %>
                            <tr>
                                <td data-title="STT" class = "responsive_ipad"><%=i+1 %></td>
                                <td class = "info" data-title="Họ và tên"><%=c.getName() %></td>
                                <td data-title="Ngày sinh"><%=c.getDobV2() %></td>
                                <td data-title="CMND"><%=c.getNumberID() %></td>
                                <td class = "info_list_address" data-title="Địa chỉ thường cú">
                                    <span><%=c.getPermanent() %></span>
                                </td>
                                <td data-title="Tôn giáo" class = "responsive_ipad"><%=c.getEthnicGroup() %></td>
                                <td data-title="Trình độ văn hóa" class = "responsive_ipad"><%=c.getEduLevel() %></td>
                                <td data-title="Nghề nghiệp" class = "responsive_ipad"><%=c.getJob() %></td>
                                <td data-title="Thời gian nhập"><%=c.getTime() %></td>
                            </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="home_footer">
                <p>Created by <a href="">name@gmail.com</a></p>
                <ul>
                    <li><a href="#">Thông tin về chúng tôi</a></li>
                    <li><a href="#">Trợ giúp</a></li>
                    <li><a href="#">Liên hệ</a></li>
                </ul>
            </div>
        </div>

        <c:if test = "${sessionScope.account.rank == 'B1' || sessionScope.account.rank == 'B2'}">
            <div class="input_general">
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
        </c:if>
            <div class="view">
                <div class = "view_header">
                    <p>Danh sách cán bộ ${managecadres }</p>
                    <span>CitizenV<i class="fas fa-chevron-right"></i>Cấp quyền truy cập</span>
                </div>
                <div class="view_content">
                    <div class="grant_time">
                        <div class="left">
                            <div><h3 id="headline">Thời gian điều tra</h3></div>
                            <div>
                                <p>Bắt đầu: <span>${sessionScope.account.beginTime }</span></p>
                                <p>Kết thúc: <span>${sessionScope.account.endTime }</span></p>
                            </div>
                        </div>
                        <div id="countdown">
                            <ul>
                                <li><span id="days"></span>days</li>
                                <li><span id="hours"></span>Hours</li>
                               <%-- <li><span id="minutes"></span>Minutes</li>
                                <li><span id="seconds"></span>Seconds</li>--%>
                            </ul>
                        </div>
                    </div>
                    <div class = "content_top">
                        <div class = "content_select">
                            <div class = "count">
                                <label>Số lượng</label>
                                <select class="content_top_count">
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                    <option value="200">200</option>
                                </select>
                            </div>
                            <div class="search_select">
                            </div>
                        </div>
                        <div class="add_user">
                            <button><i class="fas fa-plus"></i>Thêm người dùng</button>
                            <div class="add_user_model">
                                <div class = "add_user_detail">
                                    <h2>Thêm tài khoản cán bộ</h2>
                                    <div>
                                        <label>Tài khoản:</label>
                                        <input type="text" class="add_username">
                                    </div>
                                    <div>
                                        <label>Mật khẩu: </label>
                                        <input type="text" class="add_password">
                                    </div>
                                    <div>
                                        <label>Nhập lại mật khẩu: </label>
                                        <input type="text" class="add_repass">
                                    </div>
                                    <div>
                                        <label>Khu vực quản lí: </label>
                                        <input type="text" class="add_managearea">
                                    </div>
                                    <div>
                                        <label>Mã khu vực: </label>
                                        <input type="text" class="add_postcode">
                                    </div>
                                    <div>
                                        <label>Thời gian bắt đầu: </label>
                                        <input type="date" class="add_date_begin">
                                    </div>
                                    <div>
                                        <label>Thời gian kết thúc: </label>
                                        <input type="date" class="add_date_end">
                                    </div>
                                    <div class = "submit">
                                        <input type="button" value="Thêm">
                                    </div>
                                    <button><i class="fas fa-times"></i></button>
                                </div>
                            </div>
                        </div>
                        <div class="search">
                            <label for="view_search">Tìm kiếm</label>
                            <input type="text" name="view_search">
                        </div>
                    </div>
                    <table class="tbl_responsive">
                        <thead>
                            <tr>
                                <th class = "responsive_ipad">STT</th>
                                <th>Tài khoản</th>
                                <th class = "responsive_ipad">Mật khẩu</th>
                                <th>Cấp bậc</th>
                                <th>Khu vực quản lí</th>
                                <th>Quyền</th>
                                <th>Trạng thái</th>
                                <th class = "responsive_ipad">Thời gian bắt đầu</th>
                                <th class = "responsive_ipad">Thời gian kết thúc</th>
                                <th>Tùy chọn</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Cadres> listCadres = (List<Cadres>) request.getAttribute("listcadres");
                            int sizecadres = listCadres.size() >= 25 ? 25 : listCadres.size();
                            for(int i=0; i<sizecadres; i++) {
                                String grant = listCadres.get(i).isGrant() ? "Kích hoạt" : "Chưa kích hoạt";
                                String status = listCadres.get(i).isStatus() ? "Hoàn thành" : "Chưa hoàn thành";
                        %>
                            <tr>
                                <td data-title="STT" class = "responsive_ipad"><%=(i+1) %></td>
                                <td class = "view_username" data-title="Tài khoản"><%=listCadres.get(i).getUsername() %></td>
                                <td class = "view_password responsive_ipad" data-title="Mật khẩu">
                                    <p><%=listCadres.get(i).getPassword() %></p>
                                </td>
                                <td data-title="Cấp bậc"><%=listCadres.get(i).getRank() %></td>
                                <td data-title="Khu vực quản lí" class = "manage_area"><%=listCadres.get(i).getManageArea() %></td>
                                <td data-title="Quyền"><%=grant %></td>
                                <td data-title="Trạng thái"><%=status %></td>
                                <td data-title="Thời gian bắt đầu" class = "responsive_ipad"><%=listCadres.get(i).getBeginTime() %></td>
                                <td data-title="Thời gian kết thúc" class = "responsive_ipad"><%=listCadres.get(i).getEndTime() %></td>
                                <td class = "action" data-title="Tuy chon">
                                    <div class = "list_action">
                                        <i class="fas fa-ellipsis-h"></i>
                                        <ul class = "">
                                            <li class="grant_user"><i class="fas fa-street-view"></i>Cấp quyền</li>
                                            <li class="delete_user" onclick="deleteAccountCadres(this)"><i class="far fa-trash-alt"></i>Xóa</li>
                                            <li class="detail_user"><i class="fas fa-user-tag"></i>Thông tin</li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        <%} %>
                        </tbody>
                    </table>
                    <div class="content_bot">
                        <p>Biểu diễn <span class = "count_left">1</span> đến <span class = "count_right"><%=sizecadres %></span> của <span class = "size">${sizecadres }</span> cán bộ</p>
                        <div>
                        </div>
                    </div>
                </div>
                <div class="view_footer">
                    <p>Created by <a href="">name@gmail.com</a></p>
                    <ul>
                        <li><a href="#">Thông tin về chúng tôi</a></li>
                        <li><a href="#">Trợ giúp</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                </div>
            </div>

            <div class = "person_info">
                <div class = "info_header">
                    <p>Danh sách người dân ${managearea }</p>
                    <span>CitizenV<i class="fas fa-chevron-right"></i>Thông tin người dân</span>
                </div>
                <div class="info_list">
                    <div class = "content_top">
                        <div class = "content_select" ${sessionScope.account.rank == 'A1' ? 'style=\"flex-basis: 100%;\"' : 'style="flex-basis: 75%;"' }>
                            <div class = "count">
                                <label>Số lượng</label>
                                <select class="content_top_count">
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                    <option value="200">200</option>
                                </select>
                            </div>
                            <div class="search_select">
                                <c:if test = "${sessionScope.account.rank == 'A1' }">
                                    <div class = "select select_city">
                                <span onclick="dropSelectSearchCitizen('city')">
                                    Tỉnh/Thành phố
                                    <i class="fas fa-chevron-down"></i>
                                </span>
                                        <ul>
                                            <c:forEach items = "${listcity }" var = "o">
                                                <li>
                                                    <input type="checkbox" value="${o.cityID}" id = "select_city_${o.cityID}">
                                                    <label for="select_city_${o.cityID}">${o.nameCity}</label>
                                                </li>
                                            </c:forEach>
                                            <br>
                                            <li class = "submit city"><button>Đồng ý</button></li>
                                        </ul>
                                    </div>
                                </c:if>
                                <c:if test = "${sessionScope.account.rank == 'A2' || sessionScope.account.rank == 'A1' }">
                                    <div class = "select select_district">
                                    <span onclick="dropSelectSearchCitizen('district')">
                                        Quận/Huyện
                                        <i class="fas fa-chevron-down"></i>
                                    </span>
                                        <ul>
                                            <c:forEach items = "${listdistrict }" var = "o">
                                                <li>
                                                    <input type="checkbox" value="${o.districtID}" id = "select_district_${o.districtID}">
                                                    <label for="select_district_${o.districtID}">${o.nameDistrict}</label>
                                                </li>
                                            </c:forEach>
                                            <c:if test = "${sessionScope.account.rank == 'A2'}">
                                                <br>
                                                <li class = "submit district"><button>Đồng ý</button></li>
                                            </c:if>
                                        </ul>
                                    </div>
                                </c:if>
                                <c:if test = "${sessionScope.account.rank == 'A3' || sessionScope.account.rank == 'A2' || sessionScope.account.rank == 'A1' }">
                                    <div class = "select select_commune">
                                    <span onclick="dropSelectSearchCitizen('commune')">
                                        Xã/Phường
                                        <i class="fas fa-chevron-down"></i>
                                    </span>
                                        <ul>
                                            <c:forEach items = "${listcommune }" var = "o">
                                                <li>
                                                    <input type="checkbox" value="${o.communeID}" id = "select_commune_${o.communeID}">
                                                    <label for="select_commune_${o.communeID}">${o.nameCommune}</label>
                                                </li>
                                            </c:forEach>
                                            <c:if test = "${sessionScope.account.rank == 'A3'}">
                                                <br>
                                                <li class = "submit commune"><button>Đồng ý</button></li>
                                            </c:if>
                                        </ul>
                                    </div>
                                </c:if>
                                <c:if test = "${sessionScope.account.rank == 'B1' || sessionScope.account.rank == 'A3' || sessionScope.account.rank == 'A2' || sessionScope.account.rank == 'A1' }">
                                    <div class = "select select_village">
                                    <span onclick="dropSelectSearchCitizen('village')">
                                        Đường/Thôn
                                        <i class="fas fa-chevron-down"></i>
                                    </span>
                                        <ul>
                                            <c:forEach items = "${listvillage }" var = "o">
                                                <li>
                                                    <input type="checkbox" value="${o.villageID}" id = "select_village_${o.villageID}">
                                                    <label for="select_village_${o.villageID}">${o.nameVillage}</label>
                                                </li>
                                            </c:forEach>
                                            <c:if test = "${sessionScope.account.rank == 'B1'}">
                                                <br>
                                                <li class = "submit village"><button>Đồng ý</button></li>
                                            </c:if>
                                        </ul>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="search" ${sessionScope.account.rank == 'A1' ? 'style=\"flex-basis: 100%; margin-top: 20px;\"' : 'style="flex-basis: 25%;"' }>
                            <label>Tìm kiếm</label>
                            <input type="text" name="view_search" id="view_search">
                        </div>
                    </div>
                    <div class = "chart">
                        <div class="above_diagram">
                            <canvas id="myChart3"></canvas>
                        </div>
                        <div class="below_diagram">
                            <div class="chart4">
                                <canvas id="myChart4"></canvas>
                            </div>
                            <div class="chart5">
                                <canvas id="myChart5"></canvas>
                            </div>
                            <div class="chart6">
                                <canvas id="myChart6"></canvas>
                            </div>
                        </div>
                    </div>
                    <table class="tbl_responsive">
                        <thead>
                            <tr>
                                <th class="responsive_ipad">STT</th>
                                <th>Họ tên</th>
                                <th>Ngày sinh</th>
                                <th class="responsive_ipad">CMND</th>
                                <th>Địa chỉ thường trú</th>
                                <th class="responsive_ipad">Tôn giáo</th>
                                <th class="responsive_ipad">Trình độ văn hóa</th>
                                <th class="responsive_ipad">Nghề nghiệp</th>
                                <th>Thời gian nhập</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Citizen> listCitizens = (List<Citizen>) request.getAttribute("listcitizen");
                            for(int i=0; i<listCitizens.size(); i++) {
                        %>
                            <tr>
                                <td style="display: none" class = "ordinal"><%=listCitizens.get(i).getOrdinal() %></td>
                                <td data-title="STT" class="responsive_ipad"><%=i+1 %></td>
                                <td class = "info" data-title="Họ tên"><%=listCitizens.get(i).getName() %></td>
                                <td data-title="Ngày sinh"><%=listCitizens.get(i).getDobV2() %></td>
                                <td data-title="CMND" class = "view_numberID responsive_ipad"><%=listCitizens.get(i).getNumberID() %></td>
                                <td class = "info_list_address" data-title="Địa chỉ thường trú">
                                    <p><%=listCitizens.get(i).getPermanent() %></p>
                                </td>
                                <td data-title="Tôn giáo" class = "responsive_ipad"><%=listCitizens.get(i).getEthnicGroup() %></td>
                                <td data-title="Trình độ văn hóa" class = "responsive_ipad"><%=listCitizens.get(i).getEduLevel() %></td>
                                <td data-title="Nghề nghiệp" class = "responsive_ipad"><%=listCitizens.get(i).getJob() %></td>
                                <td class = "action" data-title="Tùy chọn">
                                    <div class="list_action">
                                        <i class="fas fa-ellipsis-h"></i>
                                        <ul class = "">
                                            <c:if test = "${sessionScope.account.rank == 'B1' || sessionScope.account.rank == 'B2'}">
                                                <li class="edit_person"><i class="far fa-edit"></i>Sửa đổi</li>
                                                <li class="delete_person"><i class="far fa-trash-alt"></i>Xóa</li>
                                            </c:if>
                                            <li class="detail_person"><i class="fas fa-user-tag"></i>Thông tin</li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        <%} %>
                        </tbody>
                    </table>
                    <div class="content_bot">
                        <p>Biểu diễn <span class = "count_left">1</span> đến <span class = "count_right">${count }</span> của <span class = "size">${sizecitizen }</span> người dân</p>
                        <div>
                        </div>
                    </div>
                </div>
                <div class="info_footer">
                    <p>Created by <a href="">name@gmail.com</a></p>
                    <ul>
                        <li><a href="#">Thông tin về chúng tôi</a></li>
                        <li><a href="#">Trợ giúp</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                </div>
            </div>
    </div>
    <%
    	int size = (int) request.getAttribute("sizecadres");
    	int sizecitizen = (int) request.getAttribute("sizecitizen");
    	int totalPage = size/25 + 1;
    	int totalPageCitizen = sizecitizen/25 + 1;
    %>
    <script>
		let totalPage = 0;
		let totalPageCitizen = parseInt("<%=totalPageCitizen %>");
		let totalPageCadres = parseInt("<%=totalPage %>");
		let varPromisePageCitizen = null;
		let varPromisePageCadres = null;
		let isCadresOrCitizen = true;
		let onclickCadres = 0;
		let onclickCitizen = 0;
		//true la cadres false la citizen

		let Cadres = {
		    rank: "${sessionScope.account.rank }",
            numberID: "${sessionScope.account.numberID }",
            username: "${sessionScope.account.username }",
            endTime: "${sessionScope.account.endTime }",
            manageArea: "${sessionScope.account.manageArea }",
            grant: ${sessionScope.account.grant }
        }
		let arrayVillages = [];
        if(Cadres.rank === 'B1' || Cadres.rank === 'B2') {
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
        }


        <%--let chart1Label = [];--%>
        <%--let chart1Dataset = [];--%>
        <%--<%--%>
        <%--Map<Integer, Integer> mapchart1 = (HashMap<Integer, Integer>) request.getAttribute("mapcountchart13");--%>
        <%--for (int i : mapchart1.keySet()) { %>--%>
        <%--    chart1Label[chart1Label.length++] = <%=i %>;--%>
        <%--    chart1Dataset[chart1Dataset.length++] = <%=mapchart1.get(i) %>;--%>
        <%--<% } %>--%>

        <%--let chart5Label = [];--%>
        <%--let chart5Dataset = [];--%>
        <%--<%--%>
        <%--Map<String, Integer> mapchart5 = (HashMap<String, Integer>) request.getAttribute("mapcountchart5");--%>
        <%--for (String i : mapchart5.keySet()) {%>--%>
        <%--    chart5Label[chart5Label.length++] = '<%=i %>';--%>
        <%--    chart5Dataset[chart5Dataset.length++] = <%=mapchart5.get(i) %>;--%>
        <%--<% } %>--%>

        <%--let chart6Label = [];--%>
        <%--let chart6Dataset = [];--%>
        <%--<%--%>
        <%--Map<String, Integer> mapchart6 = (HashMap<String, Integer>) request.getAttribute("mapcountchart6");--%>
        <%--for (String i : mapchart6.keySet()) {%>--%>
        <%--    chart6Label[chart6Label.length++] = '<%=i %>';--%>
        <%--    chart6Dataset[chart6Dataset.length++] = <%=mapchart6.get(i) %>;--%>
        <%--<% } %>--%>
	</script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="resources/js/my.js"></script>
</body>
</html>