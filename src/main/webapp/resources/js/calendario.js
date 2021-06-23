/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = () => {
    //Configuração do prime faces para o calendário em português
    PrimeFaces.locales['pt'] = {closeText: 'Fechar', prevText: 'Anterior', nextText: 'Próximo', currentText: 'Começo', monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'], monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'], dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'], dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'], dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'], weekHeader: 'Semana', firstDay: 0, isRTL: false, showMonthAfterYear: false, yearSuffix: '', timeOnlyTitle: 'Só Horas', timeText: 'Tempo', hourText: 'Hora', minuteText: 'Minuto', secondText: 'Segundo', ampm: false, month: 'Mês', week: 'Semana', day: 'Dia', allDayText: 'Todo o Dia'};

    //Pega as datas indisponíveis para agendamento
    const xhttpDatasIndisponiveis = new XMLHttpRequest();
    xhttpDatasIndisponiveis.onload = function () {
        datasIndisponiveis = JSON.parse(this.responseText);
        console.log(datasIndisponiveis);
    };
    xhttpDatasIndisponiveis.open("GET", "http://localhost:8080/agendamentoweb/agendamentos/datas-indisponiveis");
    xhttpDatasIndisponiveis.send();
    
    //Pega os dias da semana em que o profissional trabalha
    const xhttpDiasProfissional = new XMLHttpRequest();
    xhttpDiasProfissional.onload = function () {
        diasProfissional = JSON.parse(this.responseText);
        console.log(diasProfissional);
    };
    xhttpDiasProfissional.open("GET", "http://localhost:8080/agendamentoweb/agendamentos/dias-profissional");
    xhttpDiasProfissional.send();
    
    //Data atual
    today = Date();
    //Array com os dias a serem desabilitados (ex: seg, terça e quart).
    disabledDays = [1, 2, 3];
};

//Rotina para desabilitar datas 
function disableAllTheseDays(date) {
    var day = date.getDay();
    var disable = true;
    disabledDays.forEach(function (disableDay) {
        if (disableDay === day) {
            disable = false;
        }
    });

    return [disable, ''];
}
