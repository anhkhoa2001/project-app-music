const btnDropAccount = document.querySelector("#container .account");
const elementDropAccount = document.querySelector("#container .drop_account");
window.addEventListener('click', function (e) {
    if (btnDropAccount.contains(e.target)) {
        if (elementDropAccount.className.indexOf("open") !== -1) {
            elementDropAccount.classList.remove("open");
        } else {
            elementDropAccount.classList.add("open");
        }
    } else {
        elementDropAccount.classList.remove("open");
    }
})

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
        location = '/citizen_war_exploded/homeb2';
    }

}

//xử lí gửi thông báo hoàn thành
function  sendNotification() {
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
    location = '/citizen_war_exploded/homeb2';
}

//tự động điền thông tin khi có postcode trong nhập liệu
const inputVillageTemporary = document.querySelector(".input_content .temporary .village input");
const inputCommuneTemporary = document.querySelector(".input_content .temporary .commune input");
const inputDistrictTemporary = document.querySelector(".input_content .temporary .district input");
const inputCityTemporary = document.querySelector(".input_content .temporary .city input");
const inputVillage = document.querySelector(".input_content .permanent .village input");
const inputCommune = document.querySelector(".input_content .permanent .commune input");
const inputDistrict = document.querySelector(".input_content .permanent .district input");
const inputCity = document.querySelector(".input_content .permanent .city input");
let f = arrayVillages[0];
inputVillage.value = f.namevillage;
inputCommune.value = f.namecommune;
inputDistrict.value = f.namedistrict;
inputCity.value = f.namecity;
inputVillageTemporary.value = f.namevillage;
inputCommuneTemporary.value = f.namecommune;
inputDistrictTemporary.value = f.namedistrict;
inputCityTemporary.value = f.namecity;


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
    if(document.getElementById('eduLevel').value === '') {
        document.getElementById('eduLevel-message').innerHTML = result3;
        isno = false;
    } else {
        document.getElementById('eduLevel-message').innerHTML = '';
    }

    //Nghề nghiệp
    if(document.getElementById('job').value === '') {
        document.getElementById('job-message').innerHTML = result3;
        isno = false;
    } else {
        document.getElementById('job-message').innerHTML = '';
    }

    if(isno) {
        submitFormAddCitizen();
    }
}


