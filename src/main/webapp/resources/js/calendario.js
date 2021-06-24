/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
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


//Data atual
today = Date();
    
diasSemana = [
    {nome: "Domingo", num:0},
    {nome: "Segunda", num:1},
    {nome: "Terça", num:2},
    {nome: "Quarta", num:3},
    {nome: "Quinta", num:4},
    {nome: "Sexta", num:5},
    {nome: "Sábado", num:6},
];
disabledDays = [];

//Pega os dias da semana em que o profissional trabalha
const xhttpDiasProfissional = new XMLHttpRequest();
xhttpDiasProfissional.onload = function () {

    diasProfissional = JSON.parse(this.responseText);
    diasSemana.forEach(function(diaSemana){
        var exibir = false;
        diasProfissional.forEach(function(diaProfissional){

                if(diaProfissional === diaSemana.nome){

                    exibir = true;
                }
            }
        );
        if(!exibir){
            disabledDays.push(diaSemana.num);
        }
    });
};
xhttpDiasProfissional.open("GET", "http://localhost:8080/agendamentoweb/agendamentos/dias-profissional", true);
xhttpDiasProfissional.send();
    
    
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
