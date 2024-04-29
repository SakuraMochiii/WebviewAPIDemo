// script.js
var hint = null;
var datas = [];
var line = 0;
window.onload = function() {
    line = 0;
    hint = document.getElementById('hint');
    getXml();
};
function getXml(){
        var childItems = [];
        var request = new XMLHttpRequest();
        var xmlName = sdk.getXml();
        request.open('GET', xmlName, true);
        request.onreadystatechange = function () {
            if (request.readyState == 4 && request.status == 200) {
                var xmlDoc = request.responseXML;
                var items = xmlDoc.documentElement.getElementsByTagName('MainItem');
                for (var i = 0; i < items.length; i++) {
                    var mainItem = items[i];
                    var mainItemName = mainItem.getAttribute('name_CN');
                    var mainNameEN = mainItem.getAttribute('name_EN');
                    var mainCommand = mainItem.getAttribute('command');
                    var subItems = mainItem.getElementsByTagName('SubItem');
                    childItems = [];
                    for (var j = 0; j < subItems.length; j++) {
                        var subItem = subItems[j];
                        var command = subItem.getAttribute('command');
                        var nameCN = subItem.getAttribute('name_CN');
                        var nameEN = subItem.getAttribute('name_EN');
                        var needTest = subItem.getAttribute('needTest');
                        childItems.push({
                            command:command,
                            displayNameCN:nameCN,
                            displayNameEN:nameEN,
                            needTest:needTest
                            });
                    }
                    var newItem = {
                        command:mainCommand,
                        displayNameCN:mainItemName,
                        displayNameEN:mainNameEN,
                        subItems:childItems
                        }
                    datas.push(newItem);
                }
                updateList(0,0);
                var objects = JSON.stringify(datas);
                sdk.initAction(objects);
            }
        }
        request.send();
};

function updateList(type,fatherIndex){
    const list = document.getElementById('list');
    const listView = document.getElementById('listView');
    list.innerHTML = "";
    list.scrollTop = 0;
    if(type == 0){
        sdk.isMain(true);
        datas.forEach((item,index) => {
            const li = document.createElement('dl');
            li.classList.add('listItem');
//            if(type == 1){
//                li.innerHTML = `<div>${item.displayNameCN}</div>`;
//            }else{
                li.innerHTML = `<div>${item.displayNameEN}</div>`;
//            }
            list.appendChild(li);
            li.addEventListener("click", function(event){
                setTextColor("Welcome to "+item.displayNameEN,"black");
                console.log("click mainItem ：" + item.displayNameCN);
                updateList(1,index);
            });
        });
    }else if(type == 1){
       sdk.isMain(false);
       datas[fatherIndex].subItems.forEach((item,index) => {
            const li = document.createElement('dl');
            li.classList.add('listItem');
            li.innerHTML = `<div>${item.displayNameCN}</div>`;
            list.appendChild(li);
            li.addEventListener("click", function(event){
            setTextColor(item.displayNameEN,"black");
            message = sdk.javaSDKDevice(datas[fatherIndex].command,item.command);
            console.log("click subItem：" + item.displayNameCN);
            });
       });
    };

};

function setTextColor(message,color){
      line = line + 1;
      hint.innerHTML +=`<div style="color:${color}">${message}</div>`;
      hint.scrollTop = 30 * line;
};