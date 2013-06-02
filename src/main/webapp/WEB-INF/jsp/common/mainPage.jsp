<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<html>
<head>
    <title>Home</title>

    <script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script>
        var stationId;
        function loadMe(id) {
            stationId = id;
            drawChart();
        }
        function initialize() {


            var myLatlng = new google.maps.LatLng(-25.363882, 131.044922);

            var map_canvas = document.getElementById('map_canvas');
            var map_options = {
                center: myLatlng,
                zoom: 4,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            var map = new google.maps.Map(map_canvas, map_options);
            var openWindow;
            markers = Array();
            infoWindows = Array();
            <c:forEach items="${stations}" var="station" varStatus="status">

            var location = new google.maps.LatLng(${station.lat}, ${station.lng});
            var marker = new google.maps.Marker({
                position: location,
                map: map,
                animation: google.maps.Animation.DROP,
                title: '${station.stationId}',
                infoWindowIndex: '${status.index}'
            });

            var contentString = '<div id="contentNote">' +
                    '<h1 id="firstHeading" class="firstHeading">${station.name}</h1>' +
                    '<div id="bodyContent"><p>' +
                    'Max Temperature:' + ${station.maxTmp} + ' on ${station.maxTmpDate}<br>' +
                    'Min Temperature:' + ${station.minTmp} + ' on ${station.minTmpDate}<br>' +
                    'Max Rain Fall:' + ${station.maxRainFall} + ' on ${station.maxRainFallDate}<br>' +
                    '</p></div>' +
                    '</div>';
            var infoWindow = new google.maps.InfoWindow({
                content: contentString
            });

            google.maps.event.addListener(marker, 'click',
                    function (event) {
                        map.setZoom(4);
                        if (openWindow) {
                            infoWindows[openWindow].close();
                        }
                        infoWindows[this.infoWindowIndex].open(map, this);
                        openWindow = this.infoWindowIndex;
                        stationId = this.title;
                        drawChart();
                    }
            );

            infoWindows.push(infoWindow);
            markers.push(marker);
            </c:forEach>
        }
        google.maps.event.addDomListener(window, "load", initialize);

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', {'packages': ['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {
            if (!stationId) return;

            var jsonData = $.ajax({
                url: "/amoc/annotated/dashboard/temperature/" + stationId,
                dataType: "json",
                async: false
            }).responseText;

            var json = new google.visualization.DataTable(jsonData);

            var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
            var option = {
                title: 'Temperature per Year',
                hAxis: {title: 'Year', minValue: 1900, maxValue: 2013},
                vAxis: {title: 'Temparature', minValue: 0, maxValue: 30},
                legend: 'none',
                trendlines: { 0: {} },    // Draw a trendline for data series 0.
                series: {0:{color: 'red', visibleInLegend: false}, 1:{color: 'blue', visibleInLegend: false}}
            };

            chart.draw(json, option);

            var jsonData = $.ajax({
                url: "/amoc/annotated/dashboard/rainfall/" + stationId,
                dataType: "json",
                async: false
            }).responseText;

            var json = new google.visualization.DataTable(jsonData);

            var chart2 = new google.visualization.ScatterChart(document.getElementById('chart_div2'));
            var option2 = {
                title: 'Average Rainfall per Year',
                hAxis: {title: 'Year', minValue: 1900, maxValue: 2013},
                vAxis: {title: 'Rain Fall', minValue: 0, maxValue: 200},
                legend: 'none',
                trendlines: { 0: {} }    // Draw a trendline for data series 0.
            };

            chart2.draw(json, option2);

        }
    </script>

    <style type="text/css">
        #content {
            height: auto;
            vertical-align: top;
        }

        #map_canvas {
            width: 550px;
            height: 500px;
        }

        #contentNote {
            width: auto;
            height: auto;
        }

        #firstHeading {
            font-weight: bold;
            font-size: 12pt;
        }
    </style>

</head>
<body>
<div id="content">
    <div class="tblHead"><h1>Australian Meteorology over a century</h1></div>
    <table>
        <tr style="vertical-align: top">
            <td>
                <div id="map_canvas">Loading ......</div>
            </td>
            <td style="vertical-align: top">
                <div id="chart_div" style="width:400px; height:280px;"></div>
                <div id="chart_div2" style="width:400px; height:280px;"></div>
            </td>
        </tr>
    </table>

</div>
</body>
</html>
