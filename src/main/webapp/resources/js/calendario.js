/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = () => {
    //Configura��o do prime faces para o calend�rio em portugu�s
    PrimeFaces.locales['pt'] = {closeText: 'Fechar', prevText: 'Anterior', nextText: 'Pr�ximo', currentText: 'Come�o', monthNames: ['Janeiro', 'Fevereiro', 'Mar�o', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'], monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'], dayNames: ['Domingo', 'Segunda', 'Ter�a', 'Quarta', 'Quinta', 'Sexta', 'S�bado'], dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'S�b'], dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'], weekHeader: 'Semana', firstDay: 0, isRTL: false, showMonthAfterYear: false, yearSuffix: '', timeOnlyTitle: 'S� Horas', timeText: 'Tempo', hourText: 'Hora', minuteText: 'Minuto', secondText: 'Segundo', ampm: false, month: 'M�s', week: 'Semana', day: 'Dia', allDayText: 'Todo o Dia'};

    //Pega as datas indispon�veis para agendamento
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
    //Array com os dias a serem desabilitados (ex: seg, ter�a e quart).
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
