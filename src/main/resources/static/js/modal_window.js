$(document).ready(function ($)
{
	// Показать модальное окно добавления материалов
	$('#popupAddMatBtn').click(function ()
	{
		$('#popupAddMat').fadeIn();
		return false;
	});
	// Показать модальное окно добавления контрагентов
	$('#popupAddContrBtn').click(function ()
	{
		$('#popupAddContr').fadeIn();
		return false;
	});
	// Показать модальное окно добавления причин неаттестации
	$('#popupAddСausesBtn').click(function ()
	{
		$('#popupAddСauses').fadeIn();
		return false;
	});





	// Сделать прозрачным добавления материалов
	$('#popup-close_id0').click(function ()
	{
		$(this).parents('#popupAddMat').fadeOut();
		return false;
	});
	// Сделать прозрачным добавления контрагентов
	$('#popup-close_id08').click(function ()
	{
		$(this).parents('#popupAddContr').fadeOut();
		return false;
	});
	// Сделать прозрачным добавления причин неаттестации
	$('#popup-close_id5').click(function ()
	{
		$(this).parents('#popupAddСauses').fadeOut();
		return false;
	});





	// Сделать прозрачным изменение материалов
	$('#popup-close_id1').click(function ()
	{
		$(this).parents('#popupChangeMat').fadeOut();
		return false;
	});
	$('#popup-close_id2').click(function ()
	{
		$(this).parents('#popupChangeMat').fadeOut();
		return false;
	});

	// Сделать прозрачным изменение контрагентов
	$('#popup-close_id28').click(function ()
	{
		$(this).parents('#popupChangeContr').fadeOut();
		return false;
	});
	$('#popup-close_id18').click(function ()
	{
		$(this).parents('#popupChangeContr').fadeOut();
		return false;
	});

	// Сделать прозрачным изменение контрагентов
	$('#popup-close_id52').click(function ()
	{
		$(this).parents('#popupChangeСauses').fadeOut();
		return false;
	});
	$('#popup-close_id53').click(function ()
	{
		$(this).parents('#popupChangeСauses').fadeOut();
		return false;
	});






	// Скрытие по нажатию клавиши ESC
	$(document).keydown(function (e)
	{
		// if (e.keyCode === 27) {
		// 	e.stopPropagation();
		// 	$('.popup-fade').fadeOut();
		// }
	});
	// Скрытие по клику на незанятой облости
	$('.popup-fade').click(function (e)
	{
		// if ($(e.target).closest('.popup').length === 0) {
		// 	$(this).fadeOut();
		// }
	});
});
