
$(document).ready(function(){

    "use strict";

     var forms = document.getElementsByClassName('validate-form');

     var validation = Array.prototype.filter.call(forms, function(form) {
         form.addEventListener('submit', function(event) {
             if (form.checkValidity() === false) {
                 event.preventDefault();
                 event.stopPropagation();
                 //
                 $(form).find(":invalid").first().focus();
                 //

             }
             form.classList.add('was-validated');
             try{sessionStorage.setItem('username', $("#username").val());
             }catch (e) { }
         }, false);
     });

    let username=null;
    try{
        username = sessionStorage.getItem('username');
    }catch (e) { }
    if ((null!=username)&&(username.length>0)) {
        $("#username").val(username);
        $("#password").focus();

    }else{
        $("#username").focus();
    }


    //Eye icon bindings
    $("#show_password").click(function() {
        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $(this).parent().find("input");

        if (input.attr("type") == "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });


    var isIE = /*@cc_on!@*/false || !!document.documentMode;
    var isEdge = !isIE && !!window.StyleMedia;
    var showButton = !(isIE || isEdge)
    if (!showButton) {
        document.getElementById("show_password").style.visibility = "hidden";
    }
});