/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//Configura��o do prime faces para o calend�rio em portugu�s
PrimeFaces.locales['pt'] = {closeText: 'Fechar', prevText: 'Anterior', nextText: 'Pr�ximo', currentText: 'Come�o', monthNames: ['Janeiro', 'Fevereiro', 'Mar�o', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'], monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'], dayNames: ['Domingo', 'Segunda', 'Ter�a', 'Quarta', 'Quinta', 'Sexta', 'S�bado'], dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'S�b'], dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'], weekHeader: 'Semana', firstDay: 0, isRTL: false, showMonthAfterYear: false, yearSuffix: '', timeOnlyTitle: 'S� Horas', timeText: 'Tempo', hourText: 'Hora', minuteText: 'Minuto', secondText: 'Segundo', ampm: false, month: 'M�s', week: 'Semana', day: 'Dia', allDayText: 'Todo o Dia'};

//Data atual
today = Date();

datasAgendadas = [];

//Pega as datas indispon�veis para agendamento
const xhttpDatasIndisponiveis = new XMLHttpRequest();
xhttpDatasIndisponiveis.onload = function () {
    datasAgend = JSON.parse(this.responseText);
    datasAgend.forEach(function (dataAgendada) {
            datasAgendadas.push(dataAgendada);

        }
    );
    console.log(datasAgendadas);
};
xhttpDatasIndisponiveis.open("GET", "http://localhost:8080/agendamentoweb/agendamentos/datas-agendadas", false);
xhttpDatasIndisponiveis.send();


//Rotina para desabilitar datas 
function disableAllTheseDays(date) {
    
    var enable = false;

    datasAgendadas.forEach(function (dataAgendada) {
        if (dataAgendada === formatDate(date)) {
            enable = true;
        }
    });

    return [enable, ''];

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


function listarAgendamentos() {
    var data = document.getElementById("calendario:c1_input").value;
    window.location.href = "http://localhost:8080/agendamentoweb/agendamentos/agendamentos-usuario?data="+data;
}
