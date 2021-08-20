/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Configura��o do prime faces para o calend�rio em portugu�s
PrimeFaces.locales['pt'] = {closeText: 'Fechar', prevText: 'Anterior', nextText: 'Pr�ximo', currentText: 'Come�o', monthNames: ['Janeiro', 'Fevereiro', 'Mar�o', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'], monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'], dayNames: ['Domingo', 'Segunda', 'Ter�a', 'Quarta', 'Quinta', 'Sexta', 'S�bado'], dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'S�b'], dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'], weekHeader: 'Semana', firstDay: 0, isRTL: false, showMonthAfterYear: false, yearSuffix: '', timeOnlyTitle: 'S� Horas', timeText: 'Tempo', hourText: 'Hora', minuteText: 'Minuto', secondText: 'Segundo', ampm: false, month: 'M�s', week: 'Semana', day: 'Dia', allDayText: 'Todo o Dia'};
//Data atual
today = Date();

diasSemana = [
    {nome: "Domingo", num: 0},
    {nome: "Segunda", num: 1},
    {nome: "Terça", num: 2},
    {nome: "Quarta", num: 3},
    {nome: "Quinta", num: 4},
    {nome: "Sexta", num: 5},
    {nome: "Sábado", num: 6},
];
disabledDays = [];
disabledDates = [];

//Pega as datas indispon�veis para agendamento
const xhttpDatasIndisponiveis = new XMLHttpRequest();
xhttpDatasIndisponiveis.onload = function () {
    datasIndisponiveis = JSON.parse(this.responseText);
    datasIndisponiveis.forEach(function (dataIndisponivel) {
        disabledDates.push(dataIndisponivel);

    }
    );
    console.log(datasIndisponiveis);
};
xhttpDatasIndisponiveis.open("GET", "http://localhost:8080/agendamentoweb/agendamentos/datas-indisponiveis", false);
xhttpDatasIndisponiveis.send();

//Pega os dias da semana em que o profissional trabalha
const xhttpDiasProfissional = new XMLHttpRequest();
xhttpDiasProfissional.onload = function () {

    diasProfissional = JSON.parse(this.responseText);
    diasSemana.forEach(function (diaSemana) {
        var exibir = false;
        diasProfissional.forEach(function (diaProfissional) {

            if (diaProfissional === diaSemana.nome) {

                exibir = true;
            }
        }
        );
        if (!exibir) {
            disabledDays.push(diaSemana.num);
        }
    });
};
xhttpDiasProfissional.open("GET", "http://localhost:8080/agendamentoweb/agendamentos/dias-profissional", false);
xhttpDiasProfissional.send();

//Rotina para desabilitar datas 
function disableAllTheseDays(date) {
    var day = date.getDay();
    var disable = true;
    if(formatDate(Date.now()) === formatDate(date)){
        return [false, ''];
    }
    disabledDays.forEach(function (disableDay) {
        if (disableDay === day) {
            disable = false;
        }
    });
    disabledDates.forEach(function (disableDate) {
        if (disableDate === formatDate(date)) {
            disable = false;
        }
    });

    return [disable, ''];

}

function formatDate(date) {
    var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

function enviarData() {
    var data = document.getElementById("calendario:c1_input").value;
    window.location.href = 'http://localhost:8080/agendamentoweb/agendamentos/horarios?data=' + data;
}

function listarHorarios(procedimentoID, profissionalID) {
    var data = document.getElementById("calendario:c1_input").value;
    window.location.href = "http://localhost:8080/agendamentoweb/agendamentos/horarios?profissional=" + 1 + "&procedimento=" + 1 + "&dataSelecionada=" + data;
}

