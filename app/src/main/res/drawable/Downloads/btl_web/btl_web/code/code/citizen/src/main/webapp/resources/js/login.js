function Submit() {
    let linkAPI = "/citizen_war_exploded/authorication";
    let obj = {
        username: document.getElementById("user").value,
        password: document.getElementById("pass").value
    }
    console.log(obj);
    fetch(linkAPI, {
        method: 'POST',
        headers: {
        	"Content-type": "application/json",
        },
        body: JSON.stringify(obj),
    }).then(resp => {
        if(resp.status === 200) {
            return resp.text();
        }
    }).then(data => {
        if(data === 'error') {
        	alert("Bạn đã nhập sai mật khẩu");
        	location = '/citizen_war_exploded';
        } else {
            location = data;
        }
    });
}