$(function() {
	// setup plot
    var defaultChartOptions = {
        series: { shadowSize: 0, lines: { show: true, fill: false } }, // drawing is faster without shadows
        // yaxis: { min: lowErrorThreshold - (lowErrorThreshold/10) , max: highErrorThreshold + (highErrorThreshold/10)},
        xaxis: { mode: 'time', timeformat: '%H:%M:%S', timezone: 'browser' },
        legend: {
            /*show: true,*/
            backgroundOpacity: 0.15
        },
        grid: {
			borderWidth: 1,
			minBorderMargin: 20,
			labelMargin: 10,
			backgroundColor: {
				colors: ["#fff", "#e4f4f4"]
			},
			margin: {
				top: 8,
				bottom: 20,
				left: 20
			}/*,
			markings: function(axes) {
				var markings = [];
				var xaxis = axes.xaxis;
				for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
					markings.push({ xaxis: { from: x, to: x + xaxis.tickSize }, color: "rgba(232, 232, 255, 0.2)" });
				}
				return markings;
			}*/
		}
    };
    
    function getData() {
    	var points = [];
    	var het = [], hwt = [], lwt = [], let = [];
    	
    	$.ajax({
			// have to use synchronous here, else the function 
			// will return before the data is fetched
			async: false,
			url: '/telemetry/' + referenceUrl,
			data: { 
				'deviceId': deviceLkup
			},
			dataType:'json',
			success: function(result) {
				for (var i = 0; i < result.length; i++) {
					if (result[i].ts && result[i].fv) { 
						points.push([result[i].ts, result[i].fv]);
					} else {
						// This is for power, which doesn't seem to marshall properly
						points.push([result[i][0], result[i][3]]);
					}
				
		            het.push([result[i].ts, highErrorThreshold]);
		            hwt.push([result[i].ts, highWarningThreshold]);
		            lwt.push([result[i].ts, lowWarningThreshold]);
		            let.push([result[i].ts, lowErrorThreshold]);
				}
			}
		});
    	
    	return [
	        {data: points, label: 'Data'},
	        {data: het, label: 'High Error Theshold'},
	        {data: hwt, label: 'High Warning Theshold'},
	        {data: lwt, label: 'Low Warning Theshold'},
	        {data: let, label: 'Low Error Theshold'},];
    }
    
    var plot = $.plot($("#chartdiv"), getData(), defaultChartOptions);

});