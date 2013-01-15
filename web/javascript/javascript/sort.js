   Sortable.create("sortable_list");

function getOrder() {
  var orderList = '';
  orderedNodes = document.getElementById("sortable_list").getElementsByTagName("li");
  for (var i=0;i < orderedNodes.length;i++) {
    orderList += orderedNodes[i].getAttribute('recordid') + ', ';
    }
  alert(orderList);
  }

function getOrder2() {
  var orderList = Sortable.serialize("sortable_list");
  alert(orderList);
  $('static_example').innerHTML = orderList;
  }

