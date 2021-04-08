function getDataForm(){
    return {
        current_password: document.getElementById("current_password").value,
        new_password: document.getElementById("new_password").value,
        new_password_copy: document.getElementById("new_password_copy").value
    };
}

function onClickChangePassword(){
    var dataForm = getDataForm();
    hideElement("notEqualsErrorMessage");
    hideElement("errorChangePwdHolder");
    hideElement("successChangePwdHolder");
    if (dataForm.new_password === dataForm.new_password_copy) {
        showLoadingIndicator();
        changePassword({
            oldPassword: dataForm.current_password,
            newPassword: dataForm.new_password
            }, function(data) {
            parse(data);
            hideLoadingIndicator();
        }, function() {
            alert("Error changePassword");
            hideLoadingIndicator();
            showElement("errorChangePwdHolder");
        });
    } else {
        showElement("notEqualsErrorMessage")
    }
}

function changePassword(userData, success, error){
    $.ajax({
        type: "POST",
        data: JSON.stringify(userData),
        contentType: "application/json",
        cache: false,
        url: "/changepassword",
        success: success,
        error: error
    });
}

function parse(data){
    if (data) {
        showElement("successChangePwdHolder");
        //$("div[id=successChangePwdHolder]").removeClass( "d-none" );
    } else {
        showElement("errorChangePwdHolder");
    }
}