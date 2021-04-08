const TIME_UPDATE_IMAGE = 10000;

$ (document).ready(function() {
    $('#screenNavbar').addClass("active");
    drawScreen();
    refresh();
});

function refresh() {
    setTimeout (drawScreen, TIME_UPDATE_IMAGE);
}

function drawScreen() {
    var canvas = document.getElementById('canvas-image');
    var ctx = canvas.getContext('2d');
    var img = new Image;
    img.onload = function(){
        var sizeW = img.naturalWidth / 1.1;
        var sizeH = img.naturalHeight / 1.1;
        canvas.width = sizeW;
        canvas.height = sizeH;
        // var ratio = img.naturalWidth / img.naturalHeight;
        // var scale = scale / 1.2;
        ctx.drawImage(img,0,0, sizeW, sizeH); // Or at whatever offset you like
        // ctx.drawImage(img,0,0, myCanvas.offsetWidth, myCanvas.offsetHeight ); // Or at whatever offset you like
    };
    img.src = "/screen/1";
    refresh();
}