/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function checarMenu(){
    let enable = true;
    let menus = document.getElementsByClassName('select-menu');
    let tam = menus.length;
    for(i=0; i<tam; i++){
        if(menus[i].children[2].textContent === "--- Selecione um profissional ---"){
            enable = false;
        }
    };
    if(enable){
        PF('realocBTN').enable();
    } else {
        PF('realocBTN').disable();
    }
    
}

