let global_Id;


function onClickMat ()
{
	getAllMat(function (data)
	{
		$("#catalogs").text(data[1].name);
	}, function ()
	{

	})
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function getAllMat (success, error)
{
	$.ajax({
		type: "GET",
		contentType: "application/json",
		cache: false,
		url: "/catalogs/mats",
		success: success,
		error: error
	});
}

function getAllContr (success, error)
{
	$.ajax({
		type: "GET",
		contentType: "application/json",
		cache: false,
		url: "/catalogs/contractors",
		success: success,
		error: error
	});
}

function getAllCauses (success, error)
{
	$.ajax({
		type: "GET",
		contentType: "application/json",
		cache: false,
		url: "/catalogs/causes",
		success: success,
		error: error
	});
}

function drop_button ()
{
	alert($("#selText").val());
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Добавление названий справочников
$(document).ready(function ()
{
	$('#catalogsNavbar').addClass("active");
	var catalogs = ["Справочник материалав", "Справочник причин неаттестации", "Справочник контрагентов",];
	$("#catal").select2({
		data: catalogs
	});
	changeCatal(); // Привязка обработчика событий выбора из выподающего списка


	//Добавить запись в таблицу материалов и сделать модальное окно прозрачным
	$('#popup-close_id_').click(function () // Добавляем обработчик события клика
	{
		var textMat = $("#text_mat6").val(); // Получаем значение из поля ввода
		save_mat({name: textMat}, function (data) // Ajax PUT
		{
			update_table_mat(); // => getAllMat - Ajax Get
			timerRR('alertOk');
		}, function ()
		{
			timerRR('alertError');
		})
		// сделать модальное окно прозрачным
		$(this).parents('#popupAddMat').fadeOut();
		return false;
	});
	//Добавить запись в таблицу контрагентов и сделать модальное окно прозрачным
	$('#popup-close_id_8').click(function () // Добавляем обработчик события клика
	{
		let textContr = $("#text_mat68").val(); // Получаем значение из поля ввода
		let check2 = $('#checkBox2').is(":checked");
		let check3 = $('#checkBox3').is(":checked");
		save_contr({name: textContr, shipper: check2, consignee: check3}, function (data) // Ajax PUT
		{
			update_table_Contr(); // => getAllMat - Ajax Get
			timerRR('alertOk');
		}, function ()
		{
			timerRR('alertError');
		})
		// сделать модальное окно прозрачным
		$(this).parents('#popupAddContr').fadeOut();
		return false;
	});
	//Добавить запись в таблицу причин неаттестации и сделать модальное окно прозрачным
	$('#popup-close_id51').click(function () // Добавляем обработчик события клика
	{
		var textCauses = $("#text_mat5").val(); // Получаем значение из поля ввода
		save_causes({name: textCauses}, function (data) // Ajax PUT
		{
			update_table_Causes(); // => getAllMat - Ajax Get
			timerRR('alertOk');
		}, function ()
		{
			timerRR('alertError');
		})
		// сделать модальное окно прозрачным
		$(this).parents('#popupAddСauses').fadeOut();
		return false;
	});


	//Изменить запись в таблице материалов и сделать модальное окно прозрачным
	$('#popup-close_id1').click(function () // Добавляем обработчик события клика
	{
		var textMat = $("#text_mat7").val(); // Получаем значение из поля ввода
		save_mat({name: textMat, id: global_Id}, function (data) // Ajax PUT
		{
			update_table_Contr(); // => getAllMat - Ajax Get
			timerRR('alertOk');
		}, function ()
		{
			timerRR('alertError');
		})
		let tbodyContr = $("#tbody_mat"); // Получаем узел тела таблицы материалов
		tbodyContr.empty();  // отчистка таблицы
		update_table_mat();
		// сделать модальное окно прозрачным
		$(this).parents('#popupChangeMat').fadeOut();
		return false;
	});
	//Изменить запись в таблице контрагентов и сделать модальное окно прозрачным
	$('#popup-close_id18').click(function () // Добавляем обработчик события клика
	{
		let textContr = $("#text_mat69").val(); // Получаем значение из поля ввода
		let check4 = $('#checkBox4').is(":checked");
		let check5 = $('#checkBox5').is(":checked");
		save_contr({name: textContr, id: global_Id, shipper: check4, consignee: check5}, function (data) // Ajax PUT
		{
			update_table_Contr(); // => getAllMat - Ajax Get
			timerRR('alertOk');
		}, function ()
		{
			timerRR('alertError');
		})
		let tbodyContr = $("#tbody_Contr8"); // Получаем узел тела таблицы материалов
		tbodyContr.empty();  // отчистка таблицы
		update_table_Contr();
		// сделать модальное окно прозрачным
		$(this).parents('#popupChangeContr').fadeOut();
		return false;
	});
	//Изменить запись в таблице причин неаттестации и сделать модальное окно прозрачным
	$('#popup-close_id53').click(function () // Добавляем обработчик события клика
	{
		let textCauses = $("#text_mat51").val(); // Получаем значение из поля ввода
		save_causes({name: textCauses, id: global_Id}, function (data) // Ajax PUT
		{
			update_table_Causes(); // => getAllMat - Ajax Get
			timerRR('alertOk');
		}, function ()
		{
			timerRR('alertError');
		})
		let tbodyCauses = $("#tbody_Сauses"); // Получаем узел тела таблицы материалов
		tbodyCauses.empty();  // отчистка таблицы
		update_table_Causes();
		// сделать модальное окно прозрачным
		$(this).parents('#popupChangeСauses').fadeOut();
		return false;
	});
});
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//  Получение данных для таблицы материалов
function update_table_mat ()
{
	getAllMat(function (data)
	{
		fillTableMat(data);
	}, function ()
	{
	})
}

//  Получение данных для таблицы контрагентов
function update_table_Contr ()
{
	getAllContr(function (data)
	{
		fillTableCont(data);
	}, function ()
	{
	})
}

//  Получение данных для таблицы причин неаттестации
function update_table_Causes ()
{
	getAllCauses(function (data)
	{
		fillTableCauses(data);
	}, function ()
	{
	})
}


// Получение данных для таблицы материалов
function changeCatal ()
{
	$('#catal').on("select2:select", function (e)
	{
		// Получение название справочника во время выбора
		var select_val = $(e.currentTarget).val();
		console.log(select_val);
		// Получение данных для таблицы от Ajax
		if (select_val === "Справочник материалав")
		{
			showCotalogsTables('tab1');
			update_table_mat();
		} else if (select_val === "Справочник контрагентов")
		{
			showCotalogsTables('tab2');
			update_table_Contr()
		} else if (select_val === "Справочник причин неаттестации")
		{
			showCotalogsTables('tab3');
			update_table_Causes()

		}
	});
}

function showCotalogsTables (tableName)
{
	hideElement('tab1');
	hideElement('tab2');
	hideElement('tab3');
	showElement(tableName);
}


function fillTableMat (mats) // функция добавления данных в таблицу материалов
{
	var tbodyMat = $("#tbody_mat"); // Получаем узел тела таблицы материалов
	tbodyMat.empty();  // отчистка таблицы
	$.each(mats, function (i, mat) // Перебираем
	{
		var tr = $('<tr>').append(
			$('<td class="td">').text(i + 1),
			$('<td class="td">').text(mat.name),
			$('<td class="td">').append(
				$('<button onclick="change_mat(' + mat.id + ')" type="button"' +
				' class="popup-open popupChangeMatBtn btn btn-secondary btn-sm " >').text("Изменить")),
		);
		tbodyMat.append(tr);
	})
}

function fillTableCont (conts) // функция добавления данных в таблицу контрагентов
{
	let tbodyContr8 = $("#tbody_Contr8"); // Получаем узел тела таблицы материалов
	tbodyContr8.empty();  // отчистка таблицы
	$.each(conts, function (i, cont) // Перебираем
	{
		var c = null;
		var sh = null;
		if (cont.shipper)
		{
			sh = $('<i class="fa fa-check">');
		}
		if (cont.consignee)
		{
			c = $('<i class="fa fa-check">');
		}
		var tr = $('<tr>').append(
			$('<td class="td">').text(i + 1),
			$('<td class="td">').text(cont.name),
			$('<td class="td">').append(sh),
			$('<td class="td">').append(c),
			$('<td class="td">').append($('<button onclick="change_cont(' + cont.id + ')" type="button"' +
				' class="popup-open popupChangeMatBtn btn btn-secondary btn-sm " >').text("Изменить")),
		);
		tbodyContr8.append(tr);
	})
}

function fillTableCauses (causes) // функция добавления данных в таблицу причин неаттестации
{
	let tbodyCauses = $("#tbody_Сauses"); // Получаем узел тела таблицы
	tbodyCauses.empty();  // отчистка таблицы
	$.each(causes, function (i, cause) // Перебираем
	{
		let tr = $('<tr>').append(
			$('<td class="td">').text(i + 1),
			$('<td class="td">').text(cause.name),
			$('<td class="td">').append($('<button onclick="change_causes(' + cause.id + ')" type="button"' +
				' class="popup-open popupChangeMatBtn btn btn-secondary btn-sm " >').text("Изменить")),
		);
		tbodyCauses.append(tr);
	})
}


function change_mat (id) // обработчик кнопки изменения материалов
{
	global_Id = id;
	$('#popupChangeMat').fadeIn();
	return false;
}

function change_cont (id) // обработчик кнопки изменения контрагентов
{
	global_Id = id;
	$('#popupChangeContr').fadeIn();
	return false;
}

function change_causes (id) // обработчик кнопки изменения контрагентов
{
	global_Id = id;
	$('#popupChangeСauses').fadeIn();
	return false;
}


// сохранение на сервер строки материалов
function save_mat (data, success, error)
{
	$.ajax({
		type: "PUT",
		data: JSON.stringify(data),
		contentType: "application/json",
		cache: false,
		url: "/catalogs/save/mat",
		success: success,
		error: error
	});
}

// сохранение на сервер строки контрагента
function save_contr (data, success, error)
{
	$.ajax({
		type: "PUT",
		data: JSON.stringify(data),
		contentType: "application/json",
		cache: false,
		url: "/catalogs/save/contractor ",
		success: success,
		error: error
	});
}

// сохранение на сервер строки материалов
function save_causes (data, success, error)
{
	$.ajax({
		type: "PUT",
		data: JSON.stringify(data),
		contentType: "application/json",
		cache: false,
		url: "/catalogs/save/cause",
		success: success,
		error: error
	});
}

function timerRR (alert) // Таймер для алерта
{
	//var _Seconds = $('#notEqualsErrorMessage').text();
	let int;
	let _Seconds = 10;
	int = setInterval(function ()
	{
		showElement(alert);
		// запускаем интервал
		if (_Seconds > 0)
		{
			_Seconds--; // вычитаем 1
			//$('#timerR').text(_Seconds); // выводим получившееся значение в блок
		} else
		{
			clearInterval(int); // очищаем интервал, чтобы он не продолжал работу при _Seconds = 0
			//alert('End!');
			hideElement('alertError');
			hideElement('alertOk');
		}
	}, 1000);
}

