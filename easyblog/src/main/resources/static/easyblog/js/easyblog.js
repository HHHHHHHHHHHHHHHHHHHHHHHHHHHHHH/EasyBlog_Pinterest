function prt(str){
    console.log(str);
}

function prtid(id){
    prt(innerH(id));
    prt(innerT(id));
    prt(innerV(id));
}

function innerH(id){
    return document.getElementById(id).innerHTML; 
}

function innerT(id){
    return document.getElementById(id).innerText; 
}

function innerV(id){
    return document.getElementById(id).value; 
}

function showdiv(id, showit) {
    var w = document.getElementById(id);
    if (w) {
        if (!showit) {
            w.style.display = 'none';
        } else {
            w.style.display = 'block';
        }
    }
}



function getAtt(id,att){
    return document.getElementById(id).getAttribute(att);
}

function setAtt(id,att,sty){
    document.getElementById(id).setAttribute(att,sty);
}

function getEle(id){
    return document.getElementById(id);
}
