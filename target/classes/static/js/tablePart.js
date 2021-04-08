$(document).ready(function() {
    $('#tablePartNavbar').addClass("active");
})

function getCriteriaFromForm(){


    return {
         id: "9340A16E-C118-4B19-A1AA-02C65AEF92E2",
        start: document.getElementById("start").value  !== '' ?
            new Date(document.getElementById("start").value).getTime().toString() : null, // .getUTCMinutes()
        end:  document.getElementById("end").value  !== '' ?
            new Date(document.getElementById("end").value).getTime().toString() : null,
    };
}

function onClickFindParts(){
    showLoadingIndicator();
    var criteria = getCriteriaFromForm();
    findParts(criteria, function(data) {
        // У джей квери получаем элемент по ид и вставляем туда текст
        parsePartsToTable(data);
        hideLoadingIndicator();
    }, function() {
        alert("Error /tablePart/find");
        hideLoadingIndicator();
    });
}

function findParts(criteria, success, error){
    $.ajax({
            type: "POST",
            data: JSON.stringify(criteria),
            contentType: "application/json",
            cache: false,
            url: "/tablePart/find",
            success: success,
            error: error
        });
}


/*
<tr th:if="${parts!=null}" th:each="part: ${parts}" th:onclick="loadPart([[${part.id}]])">
                    <div>
                        <td th:text="${part.startTime}" />
                        <td th:text="${part.endTime}" />
                        <td th:text="${part.oper}" />
                        <td>[[${part.id}]]</td>
                    </div>
                </tr>
 */

function getViewDate(date) {
    if (date == null) {
        return null;
    }
    return dataFormat(new Date(Number.parseInt(date, 10)));
}

// https://learn.javascript.ru/intl
function dataFormat(date) {
    var options = {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "numeric",
        minute: "numeric"};
    var format = new Intl.DateTimeFormat("ru", options).format;
    return format(date);
}

function dataFormatForMoment(date) {
    var options = {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "numeric",
        minute: "numeric",
        second: "numeric"};
    var format = new Intl.DateTimeFormat("ru", options).format;
    return format(date);
}

function parsePartsToTable(data) {
    var tbody = $('#tParts tbody');
    tbody.empty();
    $.each(data, function(i, item) {
        var tr = $('<tr onclick="onClickLoadPart(\''+item.id+'\')">').append(
            $('<td>').text(getViewDate(item.startTimeUts)),
        );
        tbody.append(tr);
    });
}

function onClickLoadPart(id){
    showLoadingIndicator();
    loadPart(id, function(data) {
        parsePartToTable(data)
        hideLoadingIndicator();
    },function(e) {
        hideLoadingIndicator();
        alert("Ошибка загрузки партии");
    })
}

function loadPart(id, success, error) {
    $.ajax(
        {
            type: "get",
            // data: criteria,
            cache: false,
            url: "/tablePart/"+id,
            success: success,
            error: error

        });
}

/*
        <tr th:if="${part!=null}" th:each="car: ${part.cars}">
            <td th:text="${car.id}" />
            <td th:text="${car.num}" />
            <td th:text="${car.tara}" />
            <td th:text="${car.taraE}" />
<!--            <td th:text="Выщитывается tara - taraE " />--><td></td>
<!--            <td th:text="Груз-сть" />--><td></td>
            <td th:text="${car.zoneE}" />
            <td th:text="${car?.consignee?.name}" />
            <td th:text="${car?.shipper?.name}" />
            <td th:text="${car?.mat?.name}" />
            <td></td> <!-- Фото ХЗ НАДО НЕ ? -->
            <td th:text="${car.attCode}" />
            <td th:text="${car?.cause?.name}" />
        </tr>
 */

function diffDate(start, end) {
    if (start == null || end == null){
        return null
    }


    // var startDateM = moment(dataFormatForMoment(new Date(Number.parseInt(start, 10))),"DD.MM.YYYY, HH:mm:ss");
    // var endDateM = moment(dataFormatForMoment(new Date(Number.parseInt(end, 10))),"DD.MM.YYYY, HH:mm:ss");
    //
    // var duration = moment.duration(endDateM.diff(startDateM));
    //
    // console.log(duration.asDays() + " " + duration.asHours() + " " + duration.asMinutes() + " " + duration.asSeconds());
    //
    // startDateM = moment(Number.parseInt(end, 10),"DD/MM/YYYY HH:mm:ss");
    // endDateM = moment(Number.parseInt(start, 10),"DD/MM/YYYY HH:mm:ss");
    //
    // duration = moment.duration(endDateM.diff(startDateM));
    //
    // console.log(duration.asDays() + " " + duration.asHours() + " " + duration.asMinutes() + " " + duration.asSeconds());

    var startDateM = moment(Number.parseInt(end, 10));
    var endDateM = moment(Number.parseInt(start, 10));

    return moment.duration(startDateM.diff(endDateM));

    // console.log(duration.asDays() + " " + duration.asHours() + " " + duration.asMinutes() + " " + duration.asSeconds());
    //
    // return duration.asMinutes()
}

function formatDuration(duration) {
    var durationStr = "ч:мин; ";

    var durationAsMinutes = duration.asMinutes();
    var durationAsHours = duration.asHours();

    if (Math.trunc(durationAsMinutes) !== 0) {

        if (Math.trunc(durationAsHours) === 0) {
            durationStr = durationStr + "0:" + Math.trunc(durationAsMinutes);
        } else {
            durationStr = durationStr +
                Math.trunc(durationAsHours)+":" +
                (Math.trunc(durationAsMinutes) - Math.trunc(durationAsHours)*60);
        }
    } else {
        durationStr = durationStr + "0:0"
    }

    return durationStr;
}



function parsePartToTable(data) {

    diffDate(data.startTimeUts, data.endTimeUts);

    $("#excelButton").attr("href", "/tablePart/exel/"+data.id);

    $("#videoButton").attr("href", "/media/video/"+data.id);

    // $("#partNum").text(data.id);
    $("#partStartTime").text(getViewDate(data.startTimeUts));
    $("#partEndTime").text(getViewDate(data.endTimeUts));

    $("#partOper").text(data.oper);

    $("#partDurationTime").text(formatDuration(diffDate(data.startTimeUts, data.endTimeUts)));
    var cars = data.cars;
    var tbody = $('#tPart tbody');
    tbody.empty();
    $.each(cars, function(i, car) {

        var netto = car.brutto - car.taraE;
        var tr = $('<tr>').append(
            $('<td>').text(car.id),
            $('<td>').text(car.num),
            $('<td>').text(getViewDate(car.weighingTimeUts)),
            $('<td>').text(Math.round10(car.tara, -2)),
            $('<td>').text(Math.round10(car.taraE, -2)),
            $('<td>').text(Math.round10(car.taraE - car.tara, -2)),
            $('<td>').text(Math.round10(car.brutto, -2)),
            $('<td>').text(Math.round10(netto, -2)),
            $('<td>').text(Math.round10(netto - car.maxWheight, -2)),
            $('<td>').text(Math.round10(car.leftTruck, -2)),
            $('<td>').text(Math.round10(car.rightTruck, -2)),
            $('<td>').text(Math.round10(car.leftTruck - car.rightTruck, -2)),
            $('<td>').text(Math.round10(car.maxWheight, -2)),
            $('<td>').text(car.zoneE),
            $('<td>').text(car?.consignee?.name),
            $('<td>').text(car?.shipper?.name),
            $('<td>').text(car?.mat?.name),
            $('<td>').append(
                $('<a ' +
                    'class="btn btn-success font-Calibri-14 " ' +
                    'onclick="openPhotosModal(\''+ data.id +'\',\''+ car.id +'\')">').append('<i class="fa fa-th">')),
            $('<td>').text(car.attCode),
            $('<td>').text(car?.cause?.name)
        );
        tbody.append(tr);
    });
}

function openPhotosModal(uidPart, idCar) {
    let photos = window.open("/media/viewPhotos/" + uidPart + "/" + idCar, "Просмотр фотографий",
        "width=1100,height=900,status=no,toolbar=no,menubar=no");
}


function test(name, password){
    alert("name: " + name + " password:" + password);
}


(function() {
    /**
     * Корректировка округления десятичных дробей.
     *
     * @param {String}  type  Тип корректировки.
     * @param {Number}  value Число.
     * @param {Integer} exp   Показатель степени (десятичный логарифм основания корректировки).
     * @returns {Number} Скорректированное значение.
     */
    function decimalAdjust(type, value, exp) {
        // Если степень не определена, либо равна нулю...
        if (typeof exp === 'undefined' || +exp === 0) {
            return Math[type](value);
        }
        value = +value;
        exp = +exp;
        // Если значение не является числом, либо степень не является целым числом...
        if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0)) {
            return NaN;
        }
        // Сдвиг разрядов
        value = value.toString().split('e');
        value = Math[type](+(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp)));
        // Обратный сдвиг
        value = value.toString().split('e');
        return +(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp));
    }

    // Десятичное округление к ближайшему
    if (!Math.round10) {
        Math.round10 = function(value, exp) {
            return decimalAdjust('round', value, exp);
        };
    }
    // Десятичное округление вниз
    if (!Math.floor10) {
        Math.floor10 = function(value, exp) {
            return decimalAdjust('floor', value, exp);
        };
    }
    // Десятичное округление вверх
    if (!Math.ceil10) {
        Math.ceil10 = function(value, exp) {
            return decimalAdjust('ceil', value, exp);
        };
    }
})();
