<%@ page import="org.apache.taglibs.standard.tag.common.core.ForEachSupport" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="biz.binarysolutions.cryptocap.data.Cryptocurrency" %>
<%@ page import="biz.binarysolutions.cryptocap.data.Total" %>
<%@ page import="biz.binarysolutions.cryptocap.util.DatastoreUtil" %>
<%@ page import="biz.binarysolutions.cryptocap.util.ObjectifyUtil" %>
<!DOCTYPE html>
<%
ObjectifyUtil.setup();

Total  total = DatastoreUtil.getLastTotal();
String date  = total.getId();

List<Cryptocurrency> items = DatastoreUtil.getItems(date);
Collections.sort(items);

double remaining = total.getAmount();
for (Cryptocurrency item : items) {
	remaining -= item.getCap();
}
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {

        var data = google.visualization.arrayToDataTable([
          ['Name', 'Market capitalization'],
          ['<%=items.get(0).getName()%>', <%=items.get(0).getCap()%>],
          ['<%=items.get(1).getName()%>', <%=items.get(1).getCap()%>],
          ['<%=items.get(2).getName()%>', <%=items.get(2).getCap()%>],
          ['<%=items.get(3).getName()%>', <%=items.get(3).getCap()%>],
          ['<%=items.get(4).getName()%>', <%=items.get(4).getCap()%>],
          ['<%=items.get(5).getName()%>', <%=items.get(5).getCap()%>],
          ['<%=items.get(6).getName()%>', <%=items.get(6).getCap()%>],
          ['<%=items.get(7).getName()%>', <%=items.get(7).getCap()%>],
          ['<%=items.get(8).getName()%>', <%=items.get(8).getCap()%>],
          ['<%=items.get(9).getName()%>', <%=items.get(9).getCap()%>],
          ['Other', <%=remaining%>],
        ]);
        
        var formatter = new google.visualization.NumberFormat({decimalSymbol: '.',groupingSymbol: ',', prefix: 'USD $'});
        formatter.format(data, 1);

        var options = {
          legend:    { alignment: 'center' },
          chartArea: { left:0, top:0, width:"80%", height:"80%" },
          slices: {
              10: { color: 'gray' }
            }
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        chart.draw(data, options);
      }
    </script>
  	<style>
		h1  { text-align:center; font-family: arial }
		h3  { text-align:center; font-family: arial }
		h4  { text-align:center; font-family: arial }
		a   { font-family: arial }
	</style>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Cryptocurrency market capitalization TOP10</title>
  </head>

  <body>
    <h1>Cryptocurrency market capitalization TOP10</h1>
    <h4><%=total.getDate().toString()%> (updated hourly)</h4>
    <h3>Total: <%=String.format(Locale.US, "$%,.2f", Double.valueOf(total.getAmount()))%> USD</h3>
    <div id="piechart" style="width: 900px; height: 500px; margin: 0 auto; padding-top: 1cm"></div>
  </body>
</html>