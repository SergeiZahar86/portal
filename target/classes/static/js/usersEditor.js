var users;
var roles;
var BUTTON_PREFIX_ID = "buttonRole"
var selectedUserId;
var idSelectRoles = new Set();


$(document).ready(function () {
    showLoadingIndicator();
    loadData();
    hideLoadingIndicator();

    $('#userEditNavbar').addClass("active");

    $('#Rpassword').keyup( function(){
        hideElement("notEqualsErrorMessage");
    });

    $('#password').keyup( function(){
        hideElement("notEqualsErrorMessage");
    });

    $("#isTemporaryPassword").change(function(){
        if(this.checked){
            showElement("formDateTemporaryPassword");
        }else{
            hideElement("formDateTemporaryPassword");
        }
    });

    // $('#selectRole').select2({
    //     placeholder: "Выбирите роль",
    //     maximumSelectionLength: 2,
    //     language: "ru"
    // });
});

function clearForm() {
    $('#fio').val(null);
    $('#login').val(null);
    $('#password').val(null);
    $('#Rpassword').val(null);
    $('#selectRole').empty();
    $('#roles').empty();
    $("#endDateTemporaryPassword").val(null);
    $("#isTemporaryPassword").prop('checked', false);
    hideElement("formDateTemporaryPassword");
}

function onClickSaveUser() {
    if (selectedUserId !== null || selectedUserId === "new"){
        var fio = $('#fio').val();
        var login = $('#login').val();
        var password = $('#password').val();
        var Rpassword = $('#Rpassword').val();
        var endDateTemporaryPassword = document.getElementById("endDateTemporaryPassword").value  !== '' ?
             new Date(document.getElementById("endDateTemporaryPassword").value).getTime().toString() : null;
        var isTemporaryPassword = $('#isTemporaryPassword').is(":checked");
        var roles =  [];
        for (const children of $('#roles').children("button")){
            roles.push({
                id: parseInt(children.value, 10)
            });
        }
        if (password !== Rpassword) {
            showElement("notEqualsErrorMessage");
            return ;
        }

        var user = {
            id: selectedUserId !== "new" ? selectedUserId : null,
            fio: fio,
            login: login,
            password: password,
            expiration: isTemporaryPassword ? endDateTemporaryPassword : null,
            roles: roles,
        };
        saveUser(user,function (data) {
            selectedUserId = data.id;
            getAllUsers(function (data) {
                users = data;
                updateUsersTable(users);
                showElement("successSaveMessage");
            }, function () {
                showElement("errorSaveMessage");
            })
        },function () {
            showElement("errorSaveMessage");
        });
    }
}

function hideMessages(){
    hideElement("successSaveMessage");
    hideElement("errorSaveMessage");
}

function loadData() {
    getAllUsers(function (data) {
        users = data;
        updateUsersTable(users);
    }, function () {
        showElement("errorSaveMessage")
    })
    getAllRoles(function (data) {
        roles = data;
        updateUsersTable(users);
    }, function () {
        showElement("errorSaveMessage")
    })
}

function onClickEditUser(userId) {
    hideMessages();
    showElement("userFormEdit");
    for (const user of users) {
        if (user.id === userId) {
            fillUserForm(user);
            break;
        }
    }
}

function fillUserForm(user) {
    clearForm();
    selectedUserId = user.id;
    idSelectRoles = new Set();
    $("#fio").val(user.fio);
    $("#login").val(user.login);

    if (user.expiration) {
        $("#endDateTemporaryPassword").val( moment(Number.parseInt(user.expiration, 10))
            .format("YYYY-MM-DDTHH:mm")
        );
        $("#isTemporaryPassword").prop('checked', true);
        showElement("formDateTemporaryPassword");
    } else {
        $("#isTemporaryPassword").prop('checked', false);
        hideElement("formDateTemporaryPassword");
    }

    var selectRole = $("#selectRole");
    selectRole.empty();
    $.each(roles, function (i, role) {
        selectRole.append($('<option value="' + role.id + '">').text(role.name))
    })
    $("#roles").empty()
    for (role of user.roles) {
        addRole(role.id)
    }
}

function onClickNewUserOnForm(){
    hideMessages();
    showElement("userFormEdit");
    clearForm();
    selectedUserId = "new";
    idSelectRoles = new Set()

    var selectRole = $("#selectRole");
    selectRole.empty();
    $.each(roles, function (i, role) {
        var description = description != null ? " ("+role.description+")" : "";
        selectRole.append($('<option value="' + role.id + '">').text(role.name + description))
    })
    // $("#roles").empty()
}

function addRole(idRole) {
    for (const role of roles) {
        if (role.id === idRole) {
            if (idSelectRoles.has(idRole)) {
                return
            }
            idSelectRoles.add(role.id);
            $("#roles").append(
                $('<button ' +
                    'id="'+ BUTTON_PREFIX_ID + role.id +'" ' +
                    'class="btn btn-warning btn-sm padding-left-5" ' +
                    'onclick="removeRole(' + role.id + ')"' +
                    'value="'+role.id+'">')
                    .text(role.name+" ")
                    .append($('<i class="fa fa-times">'))
            );
            return ;
        }
    }
}

function removeRole(idRole) {
    idSelectRoles.delete(idRole);
    $("#" + BUTTON_PREFIX_ID + idRole + "").remove();
}

function onClickAddRole() {
    var selectedVal = $("#selectRole").val();
    if (selectedVal === null) {
        return;
    }
    addRole(parseInt(selectedVal, 10));
}

function updateUsersTable(users) {
    var tbody = $('#tUsers tbody');
    tbody.empty();
    $.each(users, function (i, item) {
        var fioDiv = $('<div>').text(item.fio);
        var loginDiv = $('<div>').text(item.login);
        var tr = $('<tr onclick="onClickEditUser(' + item.id + ')">').append(
            $('<td>')
                .append(fioDiv)
                .append(loginDiv),
            //$('<td>').text(getViewDate(item.endTimeUts)),
            //$('<td>').text(item.oper),
            //$('<td>').append($('<button class="btn btn-success btn-sm">').text("PDF"))
        );
        tbody.append(tr);
    });
}

function saveUser(user, success, error) {
    $.ajax({
        type: "PUT",
        data: JSON.stringify(user),
        contentType: "application/json",
        cache: false,
        url: "/user/save",
        success: success,
        error: error
    });
}

function getAllUsers(success, error) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        cache: false,
        url: "/user/all",
        success: success,
        error: error
    });
}

function getAllRoles(success, error) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        cache: false,
        url: "/role/all",
        success: success,
        error: error
    });
}