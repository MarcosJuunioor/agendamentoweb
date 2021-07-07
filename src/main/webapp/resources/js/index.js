/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//Configuração do prime faces para o calendário em português
PrimeFaces.locales['pt'] = {closeText: 'Fechar', prevText: 'Anterior', nextText: 'Próximo', currentText: 'Começo', monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'], monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'], dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'], dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'], dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'], weekHeader: 'Semana', firstDay: 0, isRTL: false, showMonthAfterYear: false, yearSuffix: '', timeOnlyTitle: 'Só Horas', timeText: 'Tempo', hourText: 'Hora', minuteText: 'Minuto', secondText: 'Segundo', ampm: false, month: 'Mês', week: 'Semana', day: 'Dia', allDayText: 'Todo o Dia'};

//Data atual
today = Date();

datasAgendadas = [];

//Pega as datas indisponíveis para agendamento
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
