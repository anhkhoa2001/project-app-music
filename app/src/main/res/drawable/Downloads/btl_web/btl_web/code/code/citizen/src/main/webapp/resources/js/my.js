const btnDropAccount = document.querySelector("#container .account");
const elementDropAccount = document.querySelector("#container .drop_account");
const rank = Cadres.rank;
console.log(rank);

// Xử lí sự kiện của menu bên trái
function clickTabMenu(element, type) {
	const btnNavigations = document.querySelector("#menu .btn_navi.open");
	const elementContentOpen = document.querySelector("#container > div.open");
	const elementContent = document.querySelectorAll("#container > div");
	btnNavigations.classList.remove("open");
	let i = 0;
	switch(type) {
		case 'home':
			i = 1
			break;
		case 'input':
			i = 2;
			break;
		case 'cadres':
			totalPage = totalPageCadres;
			isCadresOrCitizen = true;
			if(onclickCadres === 0) {
				varPromisePageCadres = handlePage(totalPage, true, false);
			}
			onclickCadres++;
            addPage(document.querySelector(".view .content_bot div"), true, handlePage(totalPage, true, false));
            addPage(document.querySelector(".person_info .content_bot div"), false, handlePage(totalPage, false, false));
			i = 3;
			break;
		case 'citizen':
			totalPage = totalPageCitizen;
			isCadresOrCitizen = false;
			if(onclickCitizen === 0) {
				varPromisePageCitizen = handlePage(totalPage, true, false);
			}
			onclickCitizen++;
            addPage(document.querySelector(".person_info .content_bot div"), true, handlePage(totalPage, false, false));
            addPage(document.querySelector(".view .content_bot div"), false, handlePage(totalPage, true, false));
			i = 4;
			break;
	}
	if((rank === 'A1' || rank === 'A2' || rank === 'A3') && i>2) {
		--i;
	}
	element.classList.add("open");
	elementContentOpen.classList.remove("open");
	elementContent[i].classList.add("open");
	document.getElementById("menu").classList.toggle("toggle_menu");
}

//xử lí gửi thông báo hoàn thành
function sendNotification() {
	let linkAPI = "/citizen_war_exploded/home/noti?id=" + Cadres.numberID;
	fetch(linkAPI, {
		method: 'GET',
	}).then(resp => {
		if(resp.status === 200) {
			return resp.text();
		}
	}).then(data => {
		DropMessageError(data);
	});
}

//xử lí cập nhật những thông báo đã đọc
function isSeen() {
	document.querySelector("#container .notification .count").innerHTML = 0;
	let linkAPI = "/citizen_war_exploded/home/noti/seen?id=" + Cadres.numberID;
	fetch(linkAPI, {
		method: 'GET',
	}).then(resp => {
		if(resp.status === 200) {
			return resp.text();
		}
	}).then(data => {
		console.log(data);
	});
}

// xử lí sự kiện khi click vào tùy chọn cấp quyền truy cập
const btnDropNoti = document.querySelector("#container .notification");
const elementDropNoti = document.querySelector("#container .drop_notification");
window.addEventListener('click', function(e) {
	const btnOptionLists = document.querySelectorAll(".view_content .action > div");
	const elementDropOptionLists = document.querySelectorAll(".view_content .action ul");
	const actionInfoList = document.querySelectorAll(".info_list .action > div");
	const elementDropActionInfoList = document.querySelectorAll(".info_list .action ul");
    for(i=0; i<btnOptionLists.length; i++) {
        if(btnOptionLists[i].contains(e.target)) {
            if(elementDropOptionLists[i].className.indexOf("open") !== -1){
                elementDropOptionLists[i].classList.remove("open");
            } else {
                elementDropOptionLists[i].classList.add("open");
            }
        } else {
            elementDropOptionLists[i].classList.remove("open");
        }
    }
	for (i = 0; i < actionInfoList.length; i++) {
		if (actionInfoList[i].contains(e.target)) {
			if (elementDropActionInfoList[i].className.indexOf("open") !== -1) {
				elementDropActionInfoList[i].classList.remove("open");
			} else {
				elementDropActionInfoList[i].classList.add("open");
			}
		} else {
			elementDropActionInfoList[i].classList.remove("open");
		}
	}
    if(btnDropAccount.contains(e.target)) {
        if(elementDropAccount.className.indexOf("open") !== -1) {
            elementDropAccount.classList.remove("open");
        } else {
            elementDropAccount.classList.add("open");
        }
    } else {
        elementDropAccount.classList.remove("open");
    }
	if (btnDropNoti.contains(e.target)) {
		if (elementDropNoti.className.indexOf("open") != -1) {
			elementDropNoti.classList.remove("open");
		} else {
			elementDropNoti.classList.add("open");
		}
	} else {
		elementDropNoti.classList.remove("open");
	}
	// Đóng khung thông tin, sửa tài khoản, cấp quyền của Trang cấp quyền truy cập
	$(".exit").click(function () {
		$(".edit").remove();
		$(".grant").remove();
		$(".detail").remove();
	})

})

// Responsive Menu
document.getElementById("menu_responsive").onclick = function () {
	if (document.getElementById("menu_responsive").checked === true) {
		document.getElementById("menu").classList.toggle("toggle_menu");
	}
	else {
		document.getElementById("menu").classList.toggle("toggle_menu");
	}
}

//Xóa tài khoản bằng username
function deleteAccountCadres(e) {
	let username = e.parentNode.parentNode.parentNode.parentNode.querySelector(".view table .view_username").innerHTML;
	let linkAPI = "/citizen_war_exploded/home/remove/cadres?username=" + username;
	console.log(linkAPI)
	if(Cadres.grant) {
		fetch(linkAPI, {
			method: 'GET',
		}).then(resp => {
			if(resp.status === 200) {
				return resp.text();
			}
		}).then(data => {
			DropMessageError(data);
		});
	} else {
		alert("Hết thời gian khai báo!!");
		location = '/citizen_war_exploded/home';
	}
}

//Chi tiet thong tin tài khoản
//done
viewDetailAccountCadres($(".view_content .detail_user"));
function viewDetailAccountCadres(e) {
	e.click(function () {
		let obj = {
			username: $(this).parent().parent().parent().parent().children(".view_username").html()
		}
		fetch("/citizen_war_exploded/home/APIcadres", {
			method: 'POST',
			headers: {
				"Content-type": "application/json",
			},
			body: JSON.stringify(obj),
		}).then(resp => {
			if(resp.status === 200) {
				return resp.json();
			}
		}).then(data => {
			//nhận data và tạo element
			let grant = (data.grant === 'true') ? "Kích hoạt" : "Chưa kích hoạt";
			let status = (data.status === 'true') ? "Hoàn thành" : "Chưa hoàn thành";
			if ($(this).parent().parent().parent().parent().next().prop("class") === "detail") {
				$(".detail").remove();
			} else {
				$(".edit").remove();
				$(".grant").remove();
				$(".detail").remove();
				let html = '<tr class="detail">';
				html += '<td colspan="10">';
				html += '<h3>Thông tin chi tiết</h3>';
				html += '<div class="detail_info">';
				html += '<div class="left">';
				html += '<p>Mã vùng: <span class="code">'+ data.numberID +'</span></p>'
				html += '<p>Trạng thái: <span class="static"> '+ status +'</span></p>';
				html += '<p>Quyền khai báo: <span class="static"> '+ grant +'</span></p>';
				html += '<p>Chức vụ: <span class="rank">'+ data.rank +'</span></p>';
				html += '<p>Thời gian bắt đầu: <span class="beginTime">'+ data.beginTime +'</span></p>';
				html += '<p>Thời gian kết thúc: <span class="endTime">'+ data.endTime +'</span></p></div>';
				html += '<div class="right"><h4>Tài khoản</h4>';
				html += '<div><label for="">Tên tài khoản: </label> <input type="text" value="'+ data.username +'"></div>';
				html += '<div><label>Mật khẩu: </label> <input type="text" value="'+ data.password +'"></div>';
				html += '<div><label>Ngày khởi tạo: </label> <input type="text" value="'+ data.createtime +'"></div></div></div>';
				html += '<label class="exit"><i class="fas fa-times"></i></label></td></tr>';
				$(this).parent().parent().parent().parent().after(html);
			}
		});
	})
}

// Cấp quyền user
//done
grantAccountCadres($(".view_content .grant_user"));
function grantAccountCadres(e) {
	e.click(function () {
		let obj = {
			username: $(this).parent().parent().parent().parent().children(".view_username").html()
		}
		fetch("/citizen_war_exploded/home/APIcadres", {
			method: 'POST',
			headers: {
				"Content-type": "application/json",
			},
			body: JSON.stringify(obj),
		}).then(resp => {
			if(resp.status === 200) {
				return resp.json();
			}
		}).then(data => {
			let grant = (data.grant === 'true') ? "Kích hoạt" : "Chưa kích hoạt";
			let status = (data.status === 'true') ? "Hoàn thành" : "Chưa hoàn thành";
			if ($(this).parent().parent().parent().parent().next().prop("class") === "grant") {
				$(".grant").remove();
			} else {
				$(".edit").remove();
				$(".detail").remove();
				$(".grant").remove();
				let html = '<tr class="grant">';
				html += '<td colspan="10">';
				html += '<h3>Cấp quyền</h3>';
				html += '<div class="grant_info">';
				html += '<div class="left">';
				html += '<p>Mã vùng: <span class="code">'+ data.numberID +'</span></p>'
				html += '<p>Trạng thái: <span class="static"> '+ status +'</span></p>';
				html += '<p>Quyền khai báo: <span class="static">'+ grant +'</span></p>';
				html += '<p>Chức vụ: <span class="rank">'+ data.rank +'</span></p>';
				html += '<p>Thời gian bắt đầu: <span class="beginTime">'+ data.beginTime +'</span></p>';
				html += '<p>Thời gian kết thúc: <span class="endTime">'+ data.endTime +'</span></p></div>';
				html += '<div class="right">';
				html += '<div class = "grant_begintime"><label>Ngày bắt đầu: </label> <input type="date"></div>';
				html += '<div class = "grant_endtime"><label>Ngày kết thúc: </label> <input type="date"></div>';
				html += '<div class="submit_grant"><label><input type="button" value="Đồng ý" onclick="grantCadres(this, \''+ data.username +'\')"></label></div></div></div>';
				html += '<label class="exit"><i class="fas fa-times"></i></label></td></tr>';
				$(this).parent().parent().parent().parent().after(html);
			}
		});
	})
}

// Cấp quyền user
//done
function grantCadres(e, username) {
	var datebegin = new Date($(".view_content .grant_begintime input").val());
	var dateend = new Date($(".view_content .grant_endtime input").val());
	let dataJSON = {
		username: username,
		time: {
			begin: [datebegin.getFullYear(), datebegin.getMonth() + 1, datebegin.getDate()].join('-'),
			end: [dateend.getFullYear(), dateend.getMonth() + 1, dateend.getDate()].join('-')
		}
	}
	if(Cadres.grant) {
		fetch("/citizen_war_exploded/home/grant/cadres", {
			method: 'POST',
			headers: {
				"Content-type": "application/json",
			},
			body: JSON.stringify(dataJSON),
		}).then(resp => {
			if(resp.status === 200) {
				return resp.text();
			}
		}).then(data => {
			DropMessageError(data);
		});
	} else {
		alert("Hết thời gian khai báo!!");
		location = '/citizen_war_exploded/home';
	}
}


//CountDown
//mm-dd-yyyy
if(Cadres.grant && rank !== 'B2') {
	let accessDateCadres = Cadres.endTime;
	const second = 1000, minute = second * 60, hour = minute * 60, day = hour * 24
	const endCountDown = accessDateCadres.split("-")[1] + "/" + accessDateCadres.split("-")[2] + "/" + accessDateCadres.split("-")[0];
	const countDown = new Date(endCountDown).getTime();
	var start = setInterval(function () {
		const now = new Date().getTime(),
			distance = countDown - now;
		if (distance < 0) {
			document.getElementById("headline").innerText = "Hết thời gian điều tra";
			clearInterval(start);
		} else {
			document.getElementById("days").innerText = Math.floor(distance / (day));
			document.getElementById("hours").innerText = Math.floor((distance % (day)) / (hour));
		}
	}, 0);
}


const selectCountCitizen = document.querySelector(".person_info .content_top_count");
const selectCountCadres = document.querySelector(".view .content_top_count");
const elementInputSearchCitizen = document.querySelector(".person_info .search input");
const elementInputSearchCadres = document.querySelector(".view .search input");
// Cấp tài khoản user
if(rank !== 'B2') {
	document.querySelector(".add_user > button").onclick = function () {
		document.querySelector(".add_user_model").style.display = 'flex';
	}
	document.querySelector(".add_user_detail > button").onclick = function () {
		document.querySelector(".add_user_model").style.display = 'none';
	}

	// Add tài khoản mới
	$(".add_user_detail .submit > input").click(function() {
		let datebegin = document.querySelector('.view .add_date_begin').value;
		let dateend = document.querySelector('.view .add_date_end').value;
		let data = {
			user: document.querySelector('.view .add_username').value,
			pass: document.querySelector('.view .add_password').value,
			repass: document.querySelector('.view .add_repass').value,
			managearea: document.querySelector('.view .add_managearea').value,
			postcode: document.querySelector('.view .add_postcode').value,
			date: {
				begin: datebegin.replace('/', '-'),
				end: dateend.replace('/', '-')
			}
		}
		if(Cadres.grant) {
			fetch("/citizen_war_exploded/home/add/cadres", {
				method: 'POST',
				headers: {
					"Content-type": "application/json; charset=utf-8",
				},
				body: JSON.stringify(data),
			}).then(resp => {
				if(resp.status === 200) {
					return resp.text();
				}
			}).then(data => {
				DropMessageError(data);
			});
		} else {
			alert("Hết thời gian khai báo!!");
			location = '/citizen_war_exploded/home';
		}
	})


	// Xóa thông tin người dân
	//done
	handleDeleteCitizen($(".delete_person"));
	function handleDeleteCitizen(e) {
		e.click(function () {
			var result = confirm("Bạn có chắc chắn xóa không?");
			let ordinal = $(this).parent().parent().parent().parent().children(".ordinal").html();
			let linkAPI = "/citizen_war_exploded/home/remove/citizen?ordinal=" + ordinal;
			if (result) {
				fetch(linkAPI, {
					method: 'GET',
				}).then(resp => {
					if(resp.status === 200) {
						return resp.text();
					}
				}).then(data => {
					//nhận người dân và tạo element
					DropMessageError(data);
				});
			}
		})
	}
	//Sửa thông tin người dân
	addElementEditCitizen($(".person_info .edit_person"));
	function addElementEditCitizen(e) {
		e.click(function () {
			let ordinal = $(this).parent().parent().parent().parent().children(".ordinal").html();
			let linkAPI = "/citizen_war_exploded/home/APIcitizen?ordinal=" + ordinal;
			//fetch lấy thông tin 1 người dân theo cmtnd
			fetch(linkAPI, {
				method: 'GET',
			}).then(resp => {
				if(resp.status === 200) {
					return resp.json();
				}
			}).then(data => {
				//nhận người dân và tạo element
				console.log(data)
				if(data !== 'error') {
					let address = data.permanent;
					if ($(this).parent().parent().parent().parent().next().prop("class") === "edit") {
						$(".edit").remove();
					} else {
						$(".edit").remove();
						$(".detail").remove();
						let address = "";
						let oldAddress = null;
						for(let d of arrayVillages) {
							if(data.villageID === d.postcode) {
								oldAddress = d;
								address += "<option value=\"\" selected>"+ d.postcode +"</option>\n";
							} else {
								address += "<option value=\"\">"+ d.postcode +"</option>\n";
							}
						}
						let html = "<tr class=\"edit\">\n" +
							"                            <td colspan=\"7\">\n" +
							"                                <h3>Sửa thông tin người dân</h3>\n" +
							"                                <div class=\"edit_info\">\n" +
							"                                    <div class=\"left\">\n" +
							"                                        <p>Mã đơn vị quản lý: <span class=\"code\">"+ Cadres.numberID +"</span></p>\n" +
							"                                        <p>Đơn vị quản lý: <span class=\"static\">"+ Cadres.manageArea +"</span></p>\n" +
							"                                    </div>\n" +
							"                                    <div class=\"right\">\n" +
							"                                        <h4>Thông tin</h4>\n" +
							"                                        <div class='fullname'><label>Họ tên: </label> <input type=\"text\" value=\""+ data.name +"\"></div>\n" +
							"                                        <div class='dob'><label for=\"\">Ngày sinh </label><input type=\"date\" value=\""+ data.dob +"\"></div>\n" +
							"                                        <div class='gender'><label>Giới tính: </label> <input type=\"text\" value=\""+ data.gender +"\"></div>\n" +
							"                                        <div class='id'><label>Số CCCD: </label> <input type=\"text\" value=\""+ data.numberID +"\"></div>\n" +
							"                                        <div class='poo'><label>Quê quán: </label> <input type=\"text\" value=\""+ data.poo +"\"></div>\n" +
							"                                        <div class=\"address permanent\">\n" +
							"\t\t\t\t\t\t                               <div class = \"title\">\n" +
							"                                               <h2>Địa chỉ thường trú</h2>\n" +
							"                                               <hr>\n" +
							"                                           </div>\n" +
							"                                           <div class = \"postcode\">\n" +
							"                                               <label>Mã địa chỉ: </label>\n" +
							"                                               <select>\n" + address +
							"                                               </select>\n" +
							"                                           </div>\n" +
							"                                           <div class = \"village\">\n" +
							"                                               <label>Thôn/Đường: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namevillage+"\">\n" +
							"                                           </div>\n" +
							"                                           <div class = \"commune\">\n" +
							"                                               <label>Xã/Phường: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namecommune+"\">\n" +
							"                                            </div>\n" +
							"                                           <div class = \"district\">\n" +
							"                                               <label>Quận/Huyện: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namedistrict+"\">\n" +
							"                                           </div>\n" +
							"                                               <div class = \"city\">\n" +
							"                                               <label>Tỉnh/Thành phố: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namecity+"\">\n" +
							"                                           </div>\n" +
							"                                        </div>\n" +
							"                                        <div class=\"address temporary\">\n" +
							"\t\t\t\t\t\t                    		<div class = \"title\">\n" +
							"                                               <h2>Địa chỉ tạm trú</h2>\n" +
							"                                               <hr>\n" +
							"                                           </div>\n" +
							"                                           <div class = \"postcode\">\n" +
							"                                               <label>Mã địa chỉ: </label>\n" +
							"                                               <select>\n" + address +
							"                                               </select>\n" +
							"                                           </div>\n" +
							"                                           <div class = \"village\">\n" +
							"                                               <label>Thôn/Đường: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namevillage+"\">\n" +
							"                                           </div>\n" +
							"                                           <div class = \"commune\">\n" +
							"                                               <label>Xã/Phường: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namecommune+"\">\n" +
							"                                            </div>\n" +
							"                                           <div class = \"district\">\n" +
							"                                               <label>Quận/Huyện: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namedistrict+"\">\n" +
							"                                           </div>\n" +
							"                                               <div class = \"city\">\n" +
							"                                               <label>Tỉnh/Thành phố: </label>\n" +
							"                                               <input type=\"text\" value=\""+oldAddress.namecity+"\">\n" +
							"                                           </div>\n" +
							"                                        </div>\n" +
							"                                        <div class='group'><label>Tôn giáo: </label> <input type=\"text\" value=\""+data.ethnicGroup+"\"></div>\n" +
							"                                        <div class='level'><label>Trình độ: </label> <input type=\"text\" value=\""+data.eduLevel+"\"></div>\n" +
							"                                        <div class='job'><label>Nghề nghiệp: </label> <input type=\"text\" value=\""+data.job+"\"></div>\n" +
							"                                        <div class=\"submit_edit\"><label for=\"\"><input onclick='editCitizen("+ data.ordinal +")' type=\"button\" value=\"Đồng ý\"></label></div>\n" +
							"                                    </div>\n" +
							"                                </div>\n" +
							"                                <label class=\"exit\"><i class=\"fas fa-times\"></i></label>\n" +
							"                            </td>\n" +
							"                        </tr>";

						$(this).parent().parent().parent().parent().after(html);
						autoCompleteEditCitizen();
					}
				} else {
					DropMessageError('error');
				}
			});
		})
	}

	//Xem thông tin chi tiết người dân
	addElementViewCitizen($(".person_info .detail_person"));
	function addElementViewCitizen(e) {
		e.click(function () {
			let ordinal = $(this).parent().parent().parent().parent().children(".ordinal").html();
			let linkAPI = "/citizen_war_exploded/home/APIcitizen?ordinal=" + ordinal;
			console.log(ordinal);
			console.log(linkAPI)
			//fetch lấy thông tin 1 người dân theo stt
			fetch(linkAPI, {
				method: 'GET',
			}).then(resp => {
				if (resp.status === 200) {
					return resp.json();
				}
			}).then(data => {
				//nhận người dân và tạo element
				if (data !== 'error') {
					if ($(this).parent().parent().parent().parent().next().prop("class") === "detail") {
						$(".detail").remove();
					} else {
						$(".edit").remove();
						$(".detail").remove();
						let html = '<tr class="detail"><td colspan="9"><h3>Thông tin chi tiết</h3>';
						html += '<div class="detail_info"><div class="left">';
						html += '<p>Mã đơn vị quản lý: <span class="code">0101</span></p>';
						html += '<p>Đơn vị quản lý: <span class="static">Xã Khai Thái</span></p>';
						html += '<div class="bottom"><h5>Liên hệ</h5>';
						html += '<p>Địa chỉ: <span >Thôn Vĩnh Thượng</span></p><p>SĐT: <span>0123456789</span></p>';
						html += '<p>Email: <span>khoadamtam@gmail.com</span></p></div></div>';
						html += '<div class="right"><h4>Thông tin</h4>'
						html += '<div><label>Họ tên: </label> <input type="text" value="' + data.name + '"></div>';
						html += '<div><label for="">Ngày sinh </label><input type="date" value="' + data.dob + '"></div>';
						html += '<div><label>Giới tính: </label> <input type="text" value="' + data.gender + '"></div>';
						html += '<div><label>Số CCCD: </label> <input type="text" value="' + data.numberID + '"></div>';
						html += '<div><label>Tôn giáo: </label> <input type="text" value="' + data.ethnicGroup + '"></div>';
						html += '<div><label>Quê quán: </label> <input type="text" value="' + data.poo + '"></div>';
						html += '<div><label>Địa chỉ thường trú: </label> <input type="text" value="' + data.permanent + '"></div>';
						html += '<div><label>Địa chỉ tạm trú: </label> <input type="text" value="' + data.temporary + '"></div>';
						html += '<div><label>Học vấn: </label> <input type="text" value="' + data.eduLevel + '"></div>';
						html += '<div><label>Nghề nghiệp: </label> <input type="text" value="' + data.job + '"></div>';
						html += '</div></div><label class="exit"><i class="fas fa-times"></i></label></td></tr>';

						$(this).parent().parent().parent().parent().after(html);
					}
				} else {
					DropMessageError('error');
				}
			});
		})
	}

	//thực hiện việc đổi số lượng hiển thị trên bảng cán bộ
	selectCountCadres.onchange = function() {
		let linkAPI = "/citizen_war_exploded/home/cadresadmin";
		let dataBody;
		const page = document.querySelector(".content_bot .btn_page.active");
		const search = document.querySelector(".view .search input");
		dataBody = new InfoCadres(selectCountCadres.options[selectCountCadres.selectedIndex].value,
			"1", search.value);
		fetchCadres(linkAPI, dataBody, true);
	}

	//thực hiện việc đổi số lượng hiển thị trên bảng dân số
	selectCountCitizen.onchange = function() {
		let dataBody = {
			ID: arrayIDs,
			page: "1",
			count: this.options[this.selectedIndex].value,
			search: elementInputSearchCitizen.value
		};
		console.log(arrayIDs)
		fetchCitizen("/citizen_war_exploded/home/citizenadmin", dataBody, true, false);
	}



	//thực hiện việc tìm người dân theo tên
	elementInputSearchCitizen.addEventListener('keyup', function(e) {
		if(e.keyCode === 13) {
			let dataBodyCitizen = {
				ID: arrayIDs,
				page: "1",
				count: (selectCountCitizen != null) ? selectCountCitizen.value : "25",
				search: elementInputSearchCitizen.value
			};
			console.log(dataBodyCitizen);
			fetchCitizen("/citizen_war_exploded/home/citizenadmin", dataBodyCitizen, true, false);
		}
	});

	//thực hiện việc tìm cán bộ theo tên tài khoản và khu vực quản lí
	elementInputSearchCadres.addEventListener('keyup', function(e) {
		if(e.keyCode === 13) {
			let linkAPI = "/citizen_war_exploded/home/cadresadmin";
			let dataBody = new InfoCadres(selectCountCadres.options[selectCountCadres.selectedIndex].value,
				"1", elementInputSearchCadres.value);
			fetchCadres(linkAPI, dataBody, true);
		}
	});



}
//tự động điền thông tin trong sửa đổi thông tin người dân
function autoCompleteEditCitizen() {
	if(rank === 'B1' || rank === 'B2') {
		const elementSelectPostCodeTemporary = document.querySelector(".edit_info .temporary .postcode select");
		const inputVillageTemporary = document.querySelector(".edit_info .temporary .village input");
		const inputCommuneTemporary = document.querySelector(".edit_info .temporary .commune input");
		const inputDistrictTemporary = document.querySelector(".edit_info .temporary .district input");
		const inputCityTemporary = document.querySelector(".edit_info .temporary .city input");
		const elementSelectPostCode = document.querySelector(".edit_info .permanent .postcode select");
		const inputVillage = document.querySelector(".edit_info .permanent .village input");
		const inputCommune = document.querySelector(".edit_info .permanent .commune input");
		const inputDistrict = document.querySelector(".edit_info .permanent .district input");
		const inputCity = document.querySelector(".edit_info .permanent .city input");
		if(rank === 'B1' && elementSelectPostCode != null) {
			elementSelectPostCode.onchange = function (e) {
				console.log(elementSelectPostCode.options[elementSelectPostCode.selectedIndex].text);
				let pc = elementSelectPostCode.options[elementSelectPostCode.selectedIndex].text;
				for(let f of arrayVillages) {
					if(f.postcode === pc) {
						inputVillage.value = f.namevillage;
						inputCommune.value = f.namecommune;
						inputDistrict.value = f.namedistrict;
						inputCity.value = f.namecity;
						break;
					}
				}
			}
			elementSelectPostCodeTemporary.onchange = function () {
				let pc = elementSelectPostCodeTemporary.options[elementSelectPostCodeTemporary.selectedIndex].text;
				for(let f of arrayVillages) {
					if(f.postcode === pc) {
						inputVillageTemporary.value = f.namevillage;
						inputCommuneTemporary.value = f.namecommune;
						inputDistrictTemporary.value = f.namedistrict;
						inputCityTemporary.value = f.namecity;
						break;
					}
				}
			}
		}
		if(rank === 'B2') {
			let f = arrayVillages[0];
			inputVillage.value = f.namevillage;
			inputCommune.value = f.namecommune;
			inputDistrict.value = f.namedistrict;
			inputCity.value = f.namecity;
			inputVillageTemporary.value = f.namevillage;
			inputCommuneTemporary.value = f.namecommune;
			inputDistrictTemporary.value = f.namedistrict;
			inputCityTemporary.value = f.namecity;
		}
	}
}

//tự động điền thông tin khi có postcode trong nhập liệu
if(rank === 'B1' || rank === 'B2') {
	const elementSelectPostCodeTemporary = document.querySelector(".input_content .temporary .postcode select");
	const inputVillageTemporary = document.querySelector(".input_content .temporary .village input");
	const inputCommuneTemporary = document.querySelector(".input_content .temporary .commune input");
	const inputDistrictTemporary = document.querySelector(".input_content .temporary .district input");
	const inputCityTemporary = document.querySelector(".input_content .temporary .city input");
	const elementSelectPostCode = document.querySelector(".input_content .permanent .postcode select");
	const inputVillage = document.querySelector(".input_content .permanent .village input");
	const inputCommune = document.querySelector(".input_content .permanent .commune input");
	const inputDistrict = document.querySelector(".input_content .permanent .district input");
	const inputCity = document.querySelector(".input_content .permanent .city input");
	if(rank === 'B1' && elementSelectPostCode != null) {
		elementSelectPostCode.onchange = function (e) {
			console.log(elementSelectPostCode.options[elementSelectPostCode.selectedIndex].text);
			let pc = elementSelectPostCode.options[elementSelectPostCode.selectedIndex].text;
			for(let f of arrayVillages) {
				if(f.postcode === pc) {
					inputVillage.value = f.namevillage;
					inputCommune.value = f.namecommune;
					inputDistrict.value = f.namedistrict;
					inputCity.value = f.namecity;
					break;
				}
			}
		}
		elementSelectPostCodeTemporary.onchange = function () {
			let pc = elementSelectPostCodeTemporary.options[elementSelectPostCodeTemporary.selectedIndex].text;
			for(let f of arrayVillages) {
				if(f.postcode === pc) {
					inputVillageTemporary.value = f.namevillage;
					inputCommuneTemporary.value = f.namecommune;
					inputDistrictTemporary.value = f.namedistrict;
					inputCityTemporary.value = f.namecity;
					break;
				}
			}
		}
	}
	if(rank === 'B2') {
		let f = arrayVillages[0];
		inputVillage.value = f.namevillage;
		inputCommune.value = f.namecommune;
		inputDistrict.value = f.namedistrict;
		inputCity.value = f.namecity;
		inputVillageTemporary.value = f.namevillage;
		inputCommuneTemporary.value = f.namecommune;
		inputDistrictTemporary.value = f.namedistrict;
		inputCityTemporary.value = f.namecity;
	}
}


function DropMessageError(message) {
	if(message === 'okbro!') {
		alert("Thành công!!!!");
	} else if(message === 'error-grant') {
		alert("Bạn không đủ quyền!!Vui lòng thử lại");
	} else if(message === 'error-login') {
		alert("Vui lòng đăng nhập!!Vui lòng thử lại");
	} else {
		alert("Đã xảy ra lỗi!!Vui lòng thử lại");
	}
	location = '/citizen_war_exploded/home';
}

/*========================================================================*/
/*========================================================================*/
/*========================================================================*/
/*========================================================================*/
//xử lí drop select tìm kiếm người dân
let isDisplaySelect = [
	true, true, true, true
];
//true la chua drop
function dropSelectSearchCitizen(type) {
	let i, j;
	const elementSearchSelect = document.querySelectorAll(".person_info .select ul");
	switch(type) {
		case 'city':
			i = 0;
			break;
		case 'district':
			i = 1;
			break;
		case 'commune':
			i = 2;
			break;
		case 'village':
			i = 3;
			break;
	}
	switch(rank) {
		case 'A1':
			j = 0;
			break;
		case 'A2':
			j = 1;
			i = i-1;
			break;
		case 'A3':
			j = 2;
			i = i-2;
			break;
		case 'B1':
			j = 3;
			i = i-3;
			break;
	}
	let index = 0;
	do {
		if(index === i) {
			if(elementSearchSelect[i].className.indexOf('open') === -1) {
				elementSearchSelect[i].classList.add("open");
			} else {
				elementSearchSelect[i].classList.remove("open");
			}
		} else {
			elementSearchSelect[index].classList.remove("open");
		}
		++index;
	} while (index < 4 - j);
	handleSubmitCity();
	handleSubmitDistrict();
	handleSubmitCommune();
	handleSubmitVillage();
}
//submit tìm kiếm của thành phố
// chức năng A1 mới đc xem
function handleSubmitCity() {
	if(rank === 'A1') {
		document.querySelector(".person_info .submit.city button").onclick = function(e) {
			let array = [];
			const elementInput = document.querySelectorAll(".person_info .select_city ul input");
			for(let element of elementInput) {
				if(element.checked) {
					array[array.length++] = element.value;
				}
			}
			if(array.length === 0) {
				array[0] = Cadres.numberID;
			}
			let dataBody = {
				id: array,
				type: 'districtID'
			}
			fetch("/citizen_war_exploded/home/APIcity", {
				method: 'POST',
				headers: {
					"Content-type": "application/json; charset=UTF-8",
				},
				body: JSON.stringify(dataBody),
			}).then(resp => {
				if(resp.status === 200) {
					return resp.json();
				}
			}).then(data => {
				console.log(data);
				let element = document.querySelector(".person_info .select_district ul");
				while (element.hasChildNodes()) {
					element.removeChild(element.firstChild);
				}
				for(let d of data) {
					addElementSelect(element, d.districtID, d.nameDistrict, "district");
				}
				const elementBr = document.createElement("br");
				element.appendChild(elementBr);
				const elementLiEnd = document.createElement("li");
				elementLiEnd.setAttribute("class", "submit district");
				const elementBtn = document.createElement("button");
				elementBtn.innerHTML = 'Đồng ý';
				elementLiEnd.appendChild(elementBtn);
				element.appendChild(elementBr);
				element.appendChild(elementLiEnd);
			});
			document.querySelector(".person_info .select_city ul").classList.remove("open");
			let JSONBody = {
				ID: array,
				page: 1,
				count: (selectCountCitizen != null) ? selectCountCitizen.value : "25",
				search: elementInputSearchCitizen.value
			}
			fetchCitizen("/citizen_war_exploded/home/citizenadmin", JSONBody, true, true);
		}
	}
}

//submit tìm kiếm của quận huyện
//chức năng A2 trở lên mới đc xem
function handleSubmitDistrict() {
	const elementBtnDropDistrict = document.querySelector(".person_info .submit.district button");
	if(elementBtnDropDistrict != null && (rank === 'A2' || rank === 'A1')) {
		elementBtnDropDistrict.onclick = function() {
			let array = [];
			const elementInput = document.querySelectorAll(".person_info .select_district ul input");
			for(let element of elementInput) {
				if(element.checked) {
					array[array.length++] = element.value;
				}
			}
			if(array.length === 0) {
				array[0] = Cadres.numberID;
			}
			let dataBody = {
				id: array,
				type: 'communeID'
			}
			fetch("/citizen_war_exploded/home/APIcity", {
				method: 'POST',
				headers: {
					"Content-type": "application/json; charset=UTF-8",
				},
				body: JSON.stringify(dataBody),
			}).then(resp => {
				if(resp.status === 200) {
					return resp.json();
				}
			}).then(data => {
				console.log(data);
				let element = document.querySelector(".person_info .select_commune ul");
				while (element.hasChildNodes()) {
					element.removeChild(element.firstChild);
				}
				for(let d of data) {
					addElementSelect(element, d.communeID, d.nameCommune, "commune");
				}
				const elementBr = document.createElement("br");
				element.appendChild(elementBr);
				const elementLiEnd = document.createElement("li");
				elementLiEnd.setAttribute("class", "submit commune");
				const elementBtn = document.createElement("button");
				elementBtn.innerHTML = 'Đồng ý';
				elementLiEnd.appendChild(elementBtn);
				element.appendChild(elementBr);
				element.appendChild(elementLiEnd);
			});
			document.querySelector(".person_info .select_district ul").classList.remove("open");
			let JSONBody = {
				ID: array,
				page: 1,
				count: (selectCountCitizen != null) ? selectCountCitizen.value : "25",
				search: elementInputSearchCitizen.value
			}
			fetchCitizen("/citizen_war_exploded/home/citizenadmin", JSONBody, true, true);
		}
	}
}

//submit tìm kiếm của xẫ, phường
//chức năng A3 trở lên mới đc xem
function handleSubmitCommune() {
	const elementBtnDropCommune = document.querySelector(".person_info .submit.commune button");
	if((rank === 'A3' || rank === 'A2' || rank === 'A1') && elementBtnDropCommune != null) {
		document.querySelector(".person_info .submit.commune button").onclick = function(e) {
			let array = [];
			const elementInput = document.querySelectorAll(".person_info .select_commune ul input");
			for(let element of elementInput) {
				if(element.checked) {
					array[array.length++] = element.value;
				}
			}
			if(array.length === 0) {
				array[0] = Cadres.numberID;
			}
			let dataBody = {
				id: array,
				type: 'villageID'
			}
			fetch("/citizen_war_exploded/home/APIcity", {
				method: 'POST',
				headers: {
					"Content-type": "application/json; charset=UTF-8",
				},
				body: JSON.stringify(dataBody),
			}).then(resp => {
				if(resp.status === 200) {
					return resp.json();
				}
			}).then(data => {
				if(data.length !== 0) {
					let element = document.querySelector(".person_info .select_village ul");
					while (element.hasChildNodes()) {
						element.removeChild(element.firstChild);
					}
					for(let d of data) {
						addElementSelect(element, d.villageID, d.nameVillage, "village");
					}
					const elementBr = document.createElement("br");
					element.appendChild(elementBr);
					const elementLiEnd = document.createElement("li");
					elementLiEnd.setAttribute("class", "submit village");
					const elementBtn = document.createElement("button");
					elementBtn.innerHTML = 'Đồng ý';
					elementLiEnd.appendChild(elementBtn);
					element.appendChild(elementBr);
					element.appendChild(elementLiEnd);
				}
			});
			let JSONBody = {
				ID: array,
				page: 1,
				count: (selectCountCitizen != null) ? selectCountCitizen.value : "25",
				search: elementInputSearchCitizen.value
			}
			fetchCitizen("/citizen_war_exploded/home/citizenadmin", JSONBody, true, true);
			document.querySelector(".person_info .select_commune ul").classList.remove("open");
		}
	}
}

//submit tìm kiếm của đường thôn
//chức năng B1 trở lên mới đc xem
function handleSubmitVillage() {
	const elementBtnDropVillage = document.querySelector(".person_info .submit.village button");
	if((rank === 'B1' || rank === 'A3' || rank === 'A2' || rank === 'A1') && elementBtnDropVillage != null) {
		document.querySelector(".person_info .submit.village button").onclick = function(e) {
			let array = [];
			const elementInput = document.querySelectorAll(".person_info .select_village ul input");
			for(let element of elementInput) {
				if(element.checked) {
					array[array.length++] = element.value;
				}
			}
			if(array.length === 0) {
				array[0] = Cadres.numberID;
			}
			document.querySelector(".person_info .select_village ul").classList.remove("open");
			let JSONBody = {
				ID: array,
				page: 1,
				count: (selectCountCitizen != null) ? selectCountCitizen.value : "25",
				search: elementInputSearchCitizen.value
			}
			fetchCitizen("/citizen_war_exploded/home/citizenadmin", JSONBody, true, true);
		}
	}
}

var arrayIDs = [Cadres.numberID];

function addElementSelect(element, value, text, type) {
	const elementLi = document.createElement("li");
	const elementInput = document.createElement("input");
	elementInput.setAttribute("type", "checkbox");
	elementInput.setAttribute("value", value);
	elementInput.setAttribute("id", "select_" + type + "_" + value);
	elementLi.appendChild(elementInput);
	const elementLabel = document.createElement("label");
	elementLabel.setAttribute("for", "select_" + type + "_" + value);
	elementLabel.innerHTML = text;
	elementLi.appendChild(elementLabel);
	element.appendChild(elementLi);
}
function InfoCadres(count, page, search) {
        this.count = count;
        this.page = page;
        this.search = search;
}

function fetchCadres(linkAPI, dataBody, isno) {
	fetch(linkAPI, {
    method: 'POST',
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(dataBody),
    }).then(resp => {
        if(resp.status === 200) {
            return resp.json();
        }
    }).then(data => {
		//cap nhat trang
		if(isno) {
			totalPage = data.countPage;
			addPage(document.querySelector(".person_info .content_bot div"), false, handlePage(totalPage, true, true));
	        addPage(document.querySelector(".view .content_bot div"), true, handlePage(totalPage, true, true));	
			varPromisePageCadres = handlePage(totalPage, true, true);
		}

		document.querySelector(".view .content_bot .count_left").innerHTML = data.countleft + 1;
		document.querySelector(".view .content_bot .count_right").innerHTML = data.countright;
		document.querySelector(".view .content_bot .size").innerHTML = data.size;
		
		updateTableView(data.dataresponse, data.countleft, data.countright, 'cadres');
		viewDetailAccountCadres($(".view_content .detail_user"));
		grantAccountCadres($(".view_content .grant_user"));
 	});
}
function convertObjToQueryString(obj, prefix) {
	var str = [], p;
	for (p in obj) {
		if (obj.hasOwnProperty(p)) {
			var k = prefix ? prefix : p, v = obj[p];
			str.push((v !== null && typeof v === "object") ?
				convertObjToQueryString(v, k) :
				encodeURIComponent(k) + "=" + encodeURIComponent(v));
		}
	}
	return str.join("&");
}
function fetchCitizen(linkAPI, dataBody, isPage, isChart) {
	arrayIDs = dataBody.ID;
	linkAPI += '?' + convertObjToQueryString(dataBody);
	fetch(linkAPI, {
		method: 'GET',
    }).then(resp => {
        if(resp.status === 200) {
            return resp.json();
        }
    }).then(data => {
    	if(data === 'error') {
    		alert("Đã có lỗi xảy ra!!")
			location = '/citizen_war_exploded';
		} else {
			// cap nhat trang
			if(isPage) {
				totalPage = data.countPage;
				addPage(document.querySelector(".person_info .content_bot div"), true, handlePage(totalPage, false, true));
				addPage(document.querySelector(".view .content_bot div"), false, handlePage(totalPage, true, true));
				varPromisePageCitizen = handlePage(totalPage, true, true);
			}

			console.log(data);
			if(isChart) {
				updateChart(myChart3, data.chart3.chartlabel, data.chart3.chartdata);
				updateChart(myChart4, data.chart4.chartlabel, data.chart4.chartdata);
				updateChart(myChart5, data.chart5.chartlabel, data.chart5.chartdata);
				updateChart(myChart6, data.chart6.chartlabel, data.chart6.chartdata);
			}

			document.querySelector(".person_info .content_bot .count_left").innerHTML = data.countleft + 1;
			document.querySelector(".person_info .content_bot .count_right").innerHTML = data.countright;
			document.querySelector(".person_info .content_bot .size").innerHTML = data.size;

			updateTableView(data.dataresponse, data.countleft, data.countright, 'citizen');
			addElementEditCitizen($(".person_info .edit_person"));
			addElementViewCitizen($(".person_info .detail_person"));
			handleDeleteCitizen($(".person_info .delete_person"));
		}
 	});
}

//cập nhật lại bảng dân số
function updateTableView(data, countleft, countright, type) {
	if(type === 'cadres') {
		document.querySelector(".view table tbody").innerHTML = "";
		let html1 = "";
		for(let d of data) {
			countleft++;
			let grant = d.grant === 'true' ? "Kích hoạt" : "Chưa kích hoạt";
			let status = d.status === 'true' ? "Hoàn thành" : "Chưa hoàn thành";
			html1 += "<tr>\r\n"
			+ "	                            <td data-title=\"STT\" class = \"responsive_ipad\">"+ countleft +"</td>\r\n"
			+ "	                            <td class = \"view_username\" data-title=\"Tài khoản\">"+ d.username +"</td>\r\n"
			+ "	                            <td class = \"view_password responsive_ipad\" data-title=\"Mật khẩu\"><p>"+ d.password +"</p></td>\r\n"
			+ "	                            <td data-title=\"Cấp bậc\">"+ d.rank +"</td>\r\n"
			+ "	                            <td data-title=\"Khu vực quản lí\" class = \"manage_area\">"+ d.managearea +"</td>\r\n"
			+ "	                            <td data-title=\"Quyền\">"+ grant +"</td>\r\n"
			+ "	                            <td data-title=\"Trạng thái\">"+ status +"</td>\r\n"
			+ "	                            <td data-title=\"Thời gian bắt đầu\" class = \"responsive_ipad\">"+ d.beginTime +"</td>\r\n"
			+ "	                            <td data-title=\"Thời gian kết thúc\" class = \"responsive_ipad\">"+ d.endTime +"</td>\r\n"
			+ "	                            <td class = \"action\">\r\n"
			+ "	                                <div>\r\n"
			+ "	                                    <i class=\"fas fa-ellipsis-h\"></i>\r\n"
			+ "	                                    <ul class = \"\">\r\n"
			+ "	                                        <li class=\"grant_user\"><i class=\"fas fa-street-view\"></i>Cấp quyền</li>\r\n"
			+ "	                                        <li class=\"delete_user\" onclick=\"deleteAccountCadres(this)\"><i class=\"far fa-trash-alt\"></i>Xóa</li>\r\n"
			+ "	                                        <li class=\"detail_user\"><i class=\"fas fa-user-tag\"></i>Thông tin</li>\r\n"
			+ "	                                    </ul>\r\n"
			+ "	                                </div>\r\n"
			+ "	                            </td>\r\n"
			+ "	                        </tr>";
		}
		
		document.querySelector(".view table tbody").innerHTML = html1;
	} else {
		document.querySelector(".person_info table tbody").innerHTML = "";
		let html1 = "";
		let ea = (Cadres.rank === 'B1' || Cadres.rank === 'B2') ? "style='display: inherit'" : "style='display: none'";
		for(let d of data) {
			countleft++;
			html1 += "<tr>\r\n"
						+ "	                            <td style='display: none' class='ordinal'>"+ d.ordinal +"</td>\r\n"
						+ "	                            <td class = \"ordinal\">"+ countleft +"</td>\r\n"
						+ "	                            <td class = \"info\" data-title=\"Họ tên\">"+ d.name +"</td>\r\n"
						+ "	                            <td data-title=\"Ngày sinh\">"+ d.dobv2 +"</td>\r\n"
						+ "	                            <td data-title=\"CMND\" class = \"view_numberID responsive_ipad\">"+ d.numberID +"</td>\r\n"
						+ "	                            <td class = \"info_list_address\" data-title=\"Địa chỉ thường trú\">\r\n"
						+ "	                                <p>"+ d.permanent +"</p>\r\n"
						+ "	                            </td>\r\n"
						+ "	                            <td data-title=\"Tôn giáo\" class = \"responsive_ipad\">"+ d.ethnicGroup +"</td>\r\n"
						+ "	                            <td data-title=\"Trình độ văn hóa\" class = \"responsive_ipad\">"+ d.eduLevel +"</td>\r\n"
						+ "	                            <td data-title=\"Nghề nghiệp\" class = \"responsive_ipad\">"+ d.job +"</td>\r\n"
						+ "   							<td class = \"action\">\n"
						+ "                                    <div class=\"list_action\">\n"
						+ "                                        <i class=\"fas fa-ellipsis-h\"></i>\n"
						+ "                                        <ul class = \"\">\n"
						+ "                                            <li "+ ea +" class=\"edit_person\"><i class=\"far fa-edit\"></i>Sửa đổi</li>\n"
						+ "                                            <li "+ ea +" class=\"delete_person\"><i class=\"far fa-trash-alt\"></i>Xóa</li>\n"
						+ "                                            <li class=\"detail_person\"><i class=\"fas fa-user-tag\"></i>Thông tin</li>\n"
						+ "                                        </ul>\n"
						+ "                                    </div>\n"
						+ "                                </td>"
						+ "	                        </tr>";
		}
		document.querySelector(".person_info table tbody").innerHTML = html1;
	}
}

//thực hiện việc phân trang
function clickBtnPagingView(e, value) {
/*	$(".view tbody .edit").remove();
	$(".view tbody .detail").remove();
	$(".view tbody .grant").remove();
	$(".person_info tbody .edit").remove();
	$(".person_info tbody .detail").remove();
	$(".person_info tbody .grant").remove();

	BUGGGGGGGGGGGGGGGGGGGGGGG


	*/
    const btnPagingActive = document.querySelector(".content_bot .btn_page.active");
    const btnPagingMiddle = document.querySelector(".content_bot .btn_page_middle");
    const btnPaging = document.querySelectorAll(".content_bot .btn_page");
    const elementPaging = document.querySelector(".content_bot .paging");

	const search = document.querySelector(".view .search input");
	if(isCadresOrCitizen) {
		let dataBodyCadres = new InfoCadres(document.querySelector(".view .content_top_count").value,
								value + "", search.value);
								
		fetchCadres("/citizen_war_exploded/home/cadresadmin", dataBodyCadres, false, false);
	} else {
		let dataBodyCitizen = {
			ID: arrayIDs,
			page: value,
			count: (selectCountCitizen != null) ? selectCountCitizen.value : "25",
			search: elementInputSearchCitizen.value
		};
		fetchCitizen("/citizen_war_exploded/home/citizenadmin", dataBodyCitizen, false, false);
	}

    btnPagingActive.classList.remove("active");
    e.classList.add("active");
    let count = 0;
	if(totalPage > countPageAppear) {
		for(i=0; i<document.querySelectorAll(".content_bot .paging li").length; i++) {
	        if(document.querySelectorAll(".content_bot .paging li")[i] === btnPagingMiddle) {
	            count = i;
	        }
	    }
	    console.log(isBefore);
		if(totalPage < 7) {
			btnPagingActive.classList.remove("active");
		} else {
			if(value >= totalPage - countPageAppear + 1 && isBefore) {
				isBefore = false;
				console.log(isBefore)
				for(i=1; i<count; i++) {
					elementPaging.removeChild(btnPaging[i]);
				}
				createBtnPage(elementPaging, 1, "btn_page", btnPagingMiddle);
				for(i=1; i<countPageAppear; i++) {
					if(totalPage + i - countPageAppear === value) {
						createBtnPage(elementPaging, value, "btn_page active", btnPaging[count]);
					} else {
						createBtnPage(elementPaging, totalPage + i - countPageAppear, "btn_page", btnPaging[count]);
					}
				}
			} else {
				if(!isBefore && value === 1){
					isBefore = true;
					for(i=2; i<6; i++) {
						elementPaging.removeChild(btnPaging[i]);
					}
					for(i=2; i<=countPageAppear; i++) {
						createBtnPage(elementPaging, i, "btn_page", btnPagingMiddle);
					}
				}
				const btnPagingFirst = btnPaging[1];
				let result = value - btnPagingFirst.firstChild.innerHTML;
				if(result === 3 && !isBefore) {
					isBefore = true;
					for(i=2; i<6; i++) {
						elementPaging.removeChild(btnPaging[i]);
					}
					for(i=2; i<=countPageAppear; i++) {
						if(value === i) {
							createBtnPage(elementPaging, value, "btn_page active", btnPagingMiddle);
						} else {
							createBtnPage(elementPaging, i, "btn_page", btnPagingMiddle);
						}
					}
				} else if(result === 3 && isBefore) {
					createBtnPage(elementPaging, value + result - 1, "btn_page", btnPagingMiddle);
					elementPaging.removeChild(btnPagingFirst);
				} else if(result === 4 && isBefore) {
					let index = 0;
					while(index < 2) {
						index++;
						createBtnPage(elementPaging, value + index, "btn_page", btnPagingMiddle);
					}
					elementPaging.removeChild(btnPagingFirst);
					elementPaging.removeChild(btnPaging[2]);
				}
			}
		}
	    /*if(value >= totalPage - countPageAppear + 1 && isBefore) {
	        isBefore = false;
	        for(i=1; i<count; i++) {
	            elementPaging.removeChild(btnPaging[i]);
	        }
	        createBtnPage(elementPaging, 1, "btn_page", btnPagingMiddle);
	        for(i=1; i<countPageAppear; i++) {
	            if(totalPage + i - countPageAppear === value) {
	                createBtnPage(elementPaging, value, "btn_page active", btnPaging[count]);
	            } else {
	                createBtnPage(elementPaging, totalPage + i - countPageAppear, "btn_page", btnPaging[count]);
	            }
	        }
	    } else {
	        if(!isBefore && value === 1){
	            isBefore = true;
	            for(i=2; i<6; i++) {
	                elementPaging.removeChild(btnPaging[i]);
	            }
	            for(i=2; i<=countPageAppear; i++) {
	                createBtnPage(elementPaging, i, "btn_page", btnPagingMiddle);
	            }
		    }
	        const btnPagingFirst = btnPaging[1];
	        let result = value - btnPagingFirst.firstChild.innerHTML;
	        if(result === 3 && !isBefore) {
	            isBefore = true;
	            for(i=2; i<6; i++) {
	                elementPaging.removeChild(btnPaging[i]);
	            }
	            for(i=2; i<=countPageAppear; i++) {
	                if(value === i) {
	                    createBtnPage(elementPaging, value, "btn_page active", btnPagingMiddle);
	                } else {
	                    createBtnPage(elementPaging, i, "btn_page", btnPagingMiddle);
	                }
	            }
	        } else if(result === 3 && isBefore) {
	            createBtnPage(elementPaging, value + result - 1, "btn_page", btnPagingMiddle);
	            elementPaging.removeChild(btnPagingFirst);
	        } else if(result === 4 && isBefore) {
	            let index = 0;
	            while(index < 2) {
	                index++;
	                createBtnPage(elementPaging, value + index, "btn_page", btnPagingMiddle);
	            }
	            elementPaging.removeChild(btnPagingFirst);
	            elementPaging.removeChild(btnPaging[2]);
	        } else {

	        }
	    }*/
	} else {
		btnPagingActive.classList.remove("active");
		e.classList.add("active");
	}
	
	if(isCadresOrCitizen) {
		varPromisePageCadres = document.querySelector(".view .content_bot div").innerHTML;
	} else {
		varPromisePageCitizen = document.querySelector(".person_info .content_bot div").innerHTML;
		console.log(varPromisePageCitizen);
	}
}
function clickBtnArrow(e, arrow) {
    const btnPagingActive = document.querySelector(".content_bot .btn_page.active");
    const btnPagingMiddle = document.querySelector(".content_bot .btn_page_middle");
    const btnPaging = document.querySelectorAll(".content_bot .btn_page");
    const elementPaging = document.querySelector(".content_bot .paging");
    let count = 0;
	let index = (totalPage > 7) ? 6 : totalPage;
	let val = (arrow === 'left') ? btnPaging[1].firstChild.innerHTML : btnPaging[index].firstChild.innerHTML;
	if(isCadresOrCitizen) {
		const search = document.querySelector(".view .search input");
		let dataBodyCadres = new InfoCadres(document.querySelector(".view .content_top_count").value,
												val + "", search.value);
		console.log(dataBodyCadres);
		fetchCadres("/citizen/home/cadresadmin", dataBodyCadres, false);
	} else {
		let dataBodyCitizen = {
			ID: arrayIDs,
			page: val,
			count: (selectCountCitizen != null) ? selectCountCitizen.value : "25",
			search: elementInputSearchCitizen.value
		};
		fetchCitizen("/citizen_war_exploded/home/citizenadmin", dataBodyCitizen, false, false);
	}
	if(totalPage > countPageAppear) {
		for(i=0; i<document.querySelectorAll(".content_bot .paging li").length; i++) {
	        if(document.querySelectorAll(".content_bot .paging li")[i] === btnPagingMiddle) {
	            count = i;
	        }
	    }
		if(arrow === 'left') {
			btnPagingActive.classList.remove("active");
			if(totalPage < 7) {
				btnPaging[1].classList.add("active");
			} else {
				if(isBefore) {
					for(i=1; i<count; i++) {
						elementPaging.removeChild(btnPaging[i]);
					}
					createBtnPage(elementPaging, 1, "btn_page active", btnPagingMiddle);
					for(i=2; i<=countPageAppear; i++) {
						createBtnPage(elementPaging, i, "btn_page", btnPagingMiddle);
					}
				} else {
					isBefore = true;
					for(i=1; i<6; i++) {
						elementPaging.removeChild(btnPaging[i]);
					}
					createBtnPage(elementPaging, 1, "btn_page active", btnPagingMiddle);
					for(i=2; i<=countPageAppear; i++) {
						createBtnPage(elementPaging, i, "btn_page", btnPagingMiddle);
					}
				}
			}
		} else {
			btnPagingActive.classList.remove("active");
			if(totalPage < 7) {
				btnPaging[6].classList.add("active");
			} else {
				if(isBefore) {
					for(i=2; i<count; i++) {
						elementPaging.removeChild(btnPaging[i]);
					}
					for(i=1; i<countPageAppear; i++) {
						createBtnPage(elementPaging, totalPage + i - countPageAppear, "btn_page", btnPaging[6]);
					}
					isBefore = false;
				} else {
					btnPagingActive.classList.remove("active");
				}
				btnPaging[6].classList.add("active");
			}
		}
	} else {
		btnPagingActive.classList.remove("active");
		if(arrow === 'left') {
			btnPaging[1].classList.add("active");
		} else {
			btnPaging[totalPage].classList.add("active");
		}
	}
	if(isCadresOrCitizen) {
		varPromisePageCadres = document.querySelector(".view .content_bot div").innerHTML;
	} else {
		varPromisePageCitizen = document.querySelector(".person_info .content_bot div").innerHTML;
		console.log(varPromisePageCitizen);
	}
}


// xử lí phân trang
function handlePage(totalPage, type, update) {
	let htmlPage;
	if(totalPage > 7) {
		htmlPage = "<ul class = \"paging\">\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnArrow(this, 'left')\"><span><i class=\"fas fa-chevron-left\"></i></span></li>\r\n"
					+ "                        <li class = \"btn_page active\" onclick=\"clickBtnPagingView(this, 1)\"><span>1</span></li>\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnPagingView(this, 2)\"><span>2</span></li>\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnPagingView(this, 3)\"><span>3</span></li>\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnPagingView(this, 4)\"><span>4</span></li>\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnPagingView(this, 5)\"><span>5</span></li>\r\n"
					+ "                        <li class = \"btn_page_middle\">.....</li>\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnPagingView(this, "+ totalPage +")\"><span>"+ totalPage +"</span></li>\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnArrow(this, 'right')\"><span><i class=\"fas fa-chevron-right\"></i></span></li>\r\n"
					+ "                    </ul>";
	
	} else {
		htmlPage = "<ul class = \"paging\">\r\n"
					+ "                        <li class = \"btn_page\" onclick=\"clickBtnArrow(this, 'left')\"><span><i class=\"fas fa-chevron-left\"></i></span></li>\r\n"
					+ "                        <li class = \"btn_page active\" onclick=\"clickBtnPagingView(this, 1)\"><span>1</span></li>\r\n";
		for(i=2; i<=totalPage; i++) {
			htmlPage += "<li class = \"btn_page\" onclick=\"clickBtnPagingView(this, "+ i +")\"><span>"+ i +"</span></li>\r\n";
		}
		
		htmlPage += "                    <li class = \"btn_page\" onclick=\"clickBtnArrow(this, 'right')\"><span><i class=\"fas fa-chevron-right\"></i></span></li>\r\n"
					+ "                    </ul>";
	}
	
	if(type) {
		htmlPage = (onclickCadres > 1) ? varPromisePageCadres : htmlPage;
	} else {
		htmlPage = (onclickCitizen > 1) ? varPromisePageCitizen : htmlPage;
	}
	return htmlPage;	
}


function addPage(element, isno, htmlPage) {
    if(isno) {
        element.innerHTML = htmlPage;
    } else {
        element.innerHTML = "";
    }
}
let countPageAppear = 5;
let isBefore = true;

function createBtnPage(element, value, classname, elementBefore) {
    const liPage = document.createElement("li");
    liPage.className = classname;
    const spanPage = document.createElement("span");
    spanPage.innerHTML = value;
    liPage.setAttribute("onclick", "clickBtnPagingView(this, "+ value +")");
    liPage.appendChild(spanPage);
    element.insertBefore(liPage, elementBefore);
}

function createPageMiddle(e) {
    const element = document.createElement("li");
    element.className = "btn_page_middle";
    element.innerHTML = "....";
    e.appendChild(element);
}


//nhập liệu người dân
const elementInput = document.querySelector(".input_general .input_content_grid");
function submitFormAddCitizen() {
	let postCodePermanent = document.querySelector(".input_general .permanent .postcode select");
	let postCodeTemporary = document.querySelector(".input_general .temporary .postcode select");
	let dataBody = {
		name: document.querySelector(".input_general #fullname").value,
		dob: document.querySelector(".input_general #dob").value,
		numberID: document.querySelector(".input_general #numberID").value,
		gender: document.querySelector(".input_general #female").checked,
		ethnicGroup: document.querySelector(".input_general #ethnicGroup").value,
		poo: document.querySelector(".input_general #poo").value,
		permanent: {
			postcode: postCodePermanent.options[postCodePermanent.selectedIndex].text,
			city: document.querySelector(".input_general .permanent .city input").value,
			district: document.querySelector(".input_general .permanent .district input").value,
			commune: document.querySelector(".input_general .permanent .commune input").value,
			village: document.querySelector(".input_general .permanent .village input").value
		},
		temporary: {
			postcode: postCodeTemporary.options[postCodeTemporary.selectedIndex].text,
			city: document.querySelector(".input_general .temporary .city input").value,
			district: document.querySelector(".input_general .temporary .district input").value,
			commune: document.querySelector(".input_general .temporary .commune input").value,
			village: document.querySelector(".input_general .temporary .village input").value
		},
		job: document.querySelector(".input_general #job").value,
		eduLevel: document.querySelector(".input_general #eduLevel").value
	}
	if(Cadres.grant) {
		fetch("/citizen_war_exploded/add/citizen", {
			method: 'POST',
			headers: {
				"Content-type": "application/json; charset=utf-8",
			},
			body: JSON.stringify(dataBody),
		}).then(resp => {
			if(resp.status === 200) {
				return resp.text();
			}
		}).then(data => {
			DropMessageError(data);
		});
	} else {
		alert("Bạn đã hết thời gian điều tra!!!")
		location = '/citizen_war_exploded/home';
	}

}

//sửa thông tin người dân
function editCitizen(ordinal) {
	let postCodePermanent = document.querySelector(".edit_info .permanent .postcode select");
	let postCodeTemporary = document.querySelector(".edit_info .temporary .postcode select");
	console.log(123);
	let dataBody = {
		ordinal: ordinal + "",
		name: document.querySelector(".edit_info .fullname input").value,
		dob: document.querySelector(".edit_info .dob input").value,
		numberID: document.querySelector(".edit_info .id input").value,
		gender: document.querySelector(".edit_info .gender input").value === "Nữ",
		ethnicGroup: document.querySelector(".edit_info .group input").value,
		poo: document.querySelector(".edit_info .poo input").value,
		permanent: {
			postcode: postCodePermanent.options[postCodePermanent.selectedIndex].text,
			city: document.querySelector(".edit_info .permanent .city input").value,
			district: document.querySelector(".edit_info .permanent .district input").value,
			commune: document.querySelector(".edit_info .permanent .commune input").value,
			village: document.querySelector(".edit_info .permanent .village input").value
		},
		temporary: {
			postcode: postCodeTemporary.options[postCodeTemporary.selectedIndex].text,
			city: document.querySelector(".edit_info .temporary .city input").value,
			district: document.querySelector(".edit_info .temporary .district input").value,
			commune: document.querySelector(".edit_info .temporary .commune input").value,
			village: document.querySelector(".edit_info .temporary .village input").value
		},
		job: document.querySelector(".edit_info .job input").value,
		eduLevel: document.querySelector(".edit_info .level input").value
	}
	console.log(dataBody)
	if(Cadres.grant) {
		fetch("/citizen_war_exploded/home/edit/citizen", {
			method: 'POST',
			headers: {
				"Content-type": "application/json; charset=utf-8",
			},
			body: JSON.stringify(dataBody),
		}).then(resp => {
			if(resp.status === 200) {
				return resp.text();
			}
		}).then(data => {
			DropMessageError(data);
		});
	} else {
		alert("Bạn đã hết thời gian điều tra!!!");
		location = '/citizen_war_exploded/home';
	}
}

//Biểu đồ trang chủ
const ctx1 = document.getElementById('myChart1').getContext('2d');
const myChart1 = new Chart(ctx1, {
	type: 'line',
	data: {
		labels: chart1Label,
		datasets: [{
			label: 'số dân nhập trong 1 ngày',
			data: chart1Dataset,
			borderColor: [
				'rgba(54, 162, 235, 1)'
			],
		}]
	},
	options: {
		responsive: true,
		maintainAspectRatio: false
	}
});


const ctx2 = document.getElementById('myChart2').getContext('2d');
const myChart2 = new Chart(ctx2, {
	type: 'doughnut',
	data: {
		labels: chart2Label,
		datasets: [{
			label: 'citizenV',
			data: chart2Dataset,
			backgroundColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			]
		}]
	},
	options: {
		responsive: true,
		maintainAspectRatio: false
	}
});

function checked() {
	var result1 = 'Vui lòng chọn giới tính';
	var result2 = 'Vui lòng chọn ngày sinh';
	var result3 = 'Vui lòng nhập đầy đủ thông tin';
	let isno = true;
	//Giới tính
	if(document.getElementById('male').checked === false && document.getElementById('female').checked === false) {
		document.getElementById('sex-message').innerHTML = result1;
		isno = false;
	} else {
		document.getElementById('sex-message').innerHTML = '';
	}
	//Ngày sinh
	if(document.getElementById('dob').value === '') {
		document.getElementById('date-message').innerHTML = result2;
		isno = false;
	} else {
		document.getElementById('date-message').innerHTML = '';
	}

	//Địa chỉ
	if(document.getElementById('address1').value === ''|| document.getElementById('address2').value === '' ||
		document.getElementById('address3').value === '' || document.getElementById('address4').value === '') {
		document.getElementById('address1-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('address1-message').innerHTML = '';
	}
	if(document.getElementById('address5').value === ''|| document.getElementById('address6').value === '' ||
		document.getElementById('address7').value === '' || document.getElementById('address8').value === '') {
		document.getElementById('address2-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('address2-message').innerHTML = '';
	}

	//Họ tên
	if(document.getElementById('fullname').value === '') {
		document.getElementById('name-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('name-message').innerHTML = '';
	}

	//CMND
	if(document.getElementById('numberID').value === '' && Number(document.getElementById('dob').value.split("-")[0]) <= 2003 ) {
		document.getElementById('id-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('id-message').innerHTML = '';
	}

	//Quê quán
	if(document.getElementById('poo').value === '') {
		document.getElementById('poo-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('poo-message').innerHTML = '';
	}

	//Tôn giáo
	if(document.getElementById('ethnicGroup').value === '') {
		document.getElementById('ethnicGroup-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('ethnicGroup-message').innerHTML = '';
	}

	//Trình độ văn hóa
	if(document.getElementById('eduLevel').value == '') {
		document.getElementById('eduLevel-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('eduLevel-message').innerHTML = '';
	}

	//Nghề nghiệp
	if(document.getElementById('job').value == '') {
		document.getElementById('job-message').innerHTML = result3;
		isno = false;
	} else {
		document.getElementById('job-message').innerHTML = '';
	}

	if(isno) {
		submitFormAddCitizen();
	}
}


//Biểu đồ trang thông tin người dân
const ctx3 = document.getElementById('myChart3').getContext('2d');
const myChart3 = new Chart(ctx3, {
	type: 'line',
	data: {
		labels: chart1Label,
		datasets: [{
			label: 'citizenV',
			data: chart1Dataset,
			borderColor: [
				'rgba(54, 162, 235, 1)'
			],
		}]
	},
	options: {
		responsive: true,
		maintainAspectRatio: false
	}
});

function updateChart(element, label, data) {
	element.data.labels = label;
	element.data.datasets[0].data = data;
	element.update();
}

//giống chart 2
const ctx4 = document.getElementById('myChart4').getContext('2d');
const myChart4 = new Chart(ctx4, {
	type: 'doughnut',
	data: {
		labels: chart2Label,
		datasets: [{
			label: 'citizenV',
			data: chart2Dataset,
			backgroundColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			]
		}]
	},
	options: {
		responsive: true,
		maintainAspectRatio: false
	}
});

const ctx5 = document.getElementById('myChart5').getContext('2d');
const myChart5 = new Chart(ctx5, {
	type: 'doughnut',
	data: {
		labels: chart5Label,
		datasets: [{
			label: 'citizenV',
			data: chart5Dataset,
			backgroundColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			]
		}]
	},
	options: {
		responsive: true,
		maintainAspectRatio: false
	}
});

const ctx6 = document.getElementById('myChart6').getContext('2d');
const myChart6 = new Chart(ctx6, {
	type: 'doughnut',
	data: {
		labels: chart6Label,
		datasets: [{
			label: 'citizenV',
			data: chart6Dataset,
			backgroundColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			]
		}]
	},
	options: {
		responsive: true,
		maintainAspectRatio: false
	}
});


let chart1Label = [];
let chart1Dataset = [];
let chart2Label = [];
let chart2Dataset = [];
let chart5Label = [];
let chart5Dataset = [];
let chart6Label = [];
let chart6Dataset = [];

fetch("/citizen_war_exploded/home/chart/onethree", {
    method: 'GET',
}).then(resp => {
    if(resp.status === 200) {
        return resp.json();
    }
}).then(data => {
    chart1Label[chart1Label.length++] = data.chart.chartlabel;
    chart1Dataset[chart1Dataset.length++] = data.chart.chartdata;
	myChart1.data.labels = chart1Label;
	myChart1.data.datasets[0].data = chart1Dataset;
	myChart1.update();
	myChart3.data.labels = chart1Label;
	myChart3.data.datasets[0].data = chart1Dataset;
	myChart3.update();
});



fetch("/citizen_war_exploded/home/chart/twofour", {
    method: 'GET',
}).then(resp => {
    if(resp.status === 200) {
        return resp.json();
    }
}).then(data => {
    chart2Label[chart2Label.length++] = data.chart.chartlabel;
    chart2Dataset[chart2Dataset.length++] = data.chart.chartdata;
	myChart2.data.labels = chart2Label;
	myChart2.data.datasets[0].data = chart2Dataset;
	myChart2.update();
	myChart4.data.labels = chart2Label;
	myChart4.data.datasets[0].data = chart2Dataset;
	myChart4.update();
});



fetch("/citizen_war_exploded/home/chart/five", {
    method: 'GET',
}).then(resp => {
    if(resp.status === 200) {
        return resp.json();
    }
}).then(data => {
    chart5Label[chart5Label.length++] = data.chart.chartlabel;
    chart5Dataset[chart5Dataset.length++] = data.chart.chartdata;
	myChart5.data.labels = chart5Label;
	myChart5.data.datasets[0].data = chart5Dataset;
	myChart5.update();
});

fetch("/citizen_war_exploded/home/chart/six", {
    method: 'GET',
}).then(resp => {
    if(resp.status === 200) {
        return resp.json();
    }
}).then(data => {
    chart6Label[chart6Label.length++] = data.chart.chartlabel;
    chart6Dataset[chart6Dataset.length++] = data.chart.chartdata;
	myChart6.data.labels = chart6Label;
	myChart6.data.datasets[0].data = chart6Dataset;
	myChart6.update();
});
