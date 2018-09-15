$(function() {
	var timers = [];
	
	// setup plot
    var defaultChartOptions = {
        series: { color: '#DA262E', shadowSize: 0, lines: { show: true, fill: false } }, // drawing is faster without shadows
        // yaxis: { min: lowErrorThreshold - (lowErrorThreshold/10) , max: highErrorThreshold + (highErrorThreshold/10)},
        xaxis: { mode: 'time', timeformat: '%H:%M:%S', timezone: 'browser' },
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
	
	$('select.device-select').on('change', function(event) {
		var s = $(this);
		
		$.ajax({
			async: false,
			url: '/measurements.json?deviceId=' + s.val(),
			dataType:'json',
			success: function(result) {
				var mOpts = '<option value="">-- Please select a measurement --</option>';
				$.each(result, function(idx, value) {
					mOpts += '<option value="' + value.id + '">' + value.name + '</option>';
				});
				
				s.closest('div.control-group').next().find('select.measurement-select').html($(mOpts));
			}
		});
	});
	
	$('select.measurement-select').on('change', function(event) {
		var s = $(this);
		
		$.ajax({
			async: false,
			url: '/datapoints.json?measurementId=' + s.val(),
			dataType:'json',
			success: function(result) {
				var mOpts = '<option value="">-- Please select a data point --</option>';
				$.each(result, function(idx, value) {
					mOpts += '<option value="' + value.dataPointCanId + '">' + value.name + '</option>';
				});
				
				s.closest('div.control-group').next().find('select.dataPoint-select').html($(mOpts));
			}
		});
	});
	
	$('select.dataPoint-select').on('change', function(event) {
		var theChart = $(this).parents('form').siblings('div');
		var theDeviceId = $(this).val();
		var thePlot = $.plot(theChart, getData(theDeviceId), defaultChartOptions);
		var tIdx = parseInt($(this).attr('data-chart-idx'));
		
		if (timers[tIdx]) {
			clearTimeout(timers[tIdx]);
		}
		
		updateData(thePlot, theDeviceId, $(this).closest('div.control-group').next().find('div.live-data-switch').bootstrapSwitch('status'), tIdx);
	});
	
	$('.live-data-switch').on('switch-change', function (e, data) {
	    /*var $el = $(data.el), 
	      value = data.value;
	    
	    console.log(e, $el, value);*/
	    
	    $(this).closest('div.control-group').prev().find('select').change();
	});
	
	function updateData(thePlot, theDeviceId, switchStatus, timerIndex) {
		if (switchStatus) {
			thePlot.setData(getData(theDeviceId));
			thePlot.setupGrid();
			thePlot.draw();
			
			timers[timerIndex] = setTimeout(function() { 
				updateData(thePlot, theDeviceId, switchStatus, timerIndex); 
			}, 1000);
		}
	}
	
	function getData(deviceLkup) {
    	var points = [];
    	$.ajax({
			// have to use synchronous here, else the function 
			// will return before the data is fetched
			async: false,
			url: '/measurement-data.json?deviceId=' + deviceLkup,
			dataType:'json',
			success: function(result) {
				// We only want data from the last minute otherwise
				// the graph renders incorrectly
				
				// First get the most recent time from the list by iterating and looking
				var lastTime = Date.parse(result[result.length-1].timestamp);
				
				for (var i = 0; i < result.length; i++) {
					if (Date.parse(result[i].timestamp) > lastTime) 
						lastTime = Date.parse(result[i].timestamp);
				}
				
				var fromTime = lastTime - 60000;
				
				//console.log("last time" + lastTime );
				//console.log("from time" + fromTime );
				
				for (var i = 0; i < result.length; i++) {
					if ( Date.parse(result[i].timestamp) >= fromTime) points.push([Date.parse(result[i].timestamp), result[i].fv]);
		            // console.log(new Date(result[i].timestamp), result[i].floatValue);
				}
			}
		});/*
    	var het = [], hwt = [], lwt = [], let = [];
    	for (var i = 0; i < points.length; i++) {
            het.push([i, highErrorThreshold]);
            hwt.push([i, highWarningThreshold]);
            lwt.push([i, lowWarningThreshold]);
            let.push([i, lowErrorThreshold]);
		}*/
    	
    	return [
	        {data: points, label: 'Data'}/*,
	        {data: het, label: 'High Error Theshold'},
	        {data: hwt, label: 'High Warning Theshold'},
	        {data: lwt, label: 'Low Warning Theshold'},
	        {data: let, label: 'Low Error Theshold'}*/];
    }
	
	function getDataPointValue(md) {
		return (!md.fv ? (!md.iv ? (!md.cv ? (md ? md : undefined) : md.cv) : md.iv) : Number(md.fv.toFixed(2)));
	}

	function setCellValue(msrmntData) {
		var value = getDataPointValue(msrmntData);
		$("td#"+msrmntData.dataPointCanId).text(value);
		$("td#"+msrmntData.dataPointCanId).attr({'class': msrmntData.state});
	}

	function getDPData(canId) {

		$.ajax({
			async: false,
			url: '/index.json?canId=' + canId,
			dataType:'json',
			success: function(result) {
				for (var i = 0; i < result.length; i++) {
					setCellValue(result[i]);
				}
			}
		});
	}
	
	function getCmuData(cmuIdx) {
		$.ajax({
			async: false,
			url: '/cmu.json?cmuIdx=' + cmuIdx,
			dataType:'json',
			success: function(result) {
				for (var i = 0; i < result.length; i++) {
					setCellValue(result[i]);
				}
			}
		});
	}
	
	function getConfiguredDataSet(url, data) {
		void function(url, data) {
			$.ajax({
				async: false,
				url: url,
				data: data,
				dataType:'json',
				success: function(result) {
					for (var prop in result) {
						if (result[prop] && typeof(result[prop]) === 'object') {
							// It's a measurementdata
							$('#' + prop + (data ? data['ptIdx'] : '')).attr({'class': result[prop].state}).text(getDataPointValue(result[prop]));
						} else if (!result[prop]) {
							$('#' + prop).attr({'class': 'Error'}).text(result[prop]);
						} else {
							$('#' + prop).text(result[prop]);
						}
					}
				}
			});
		}(url, data);
	}

    function update() {
    	getConfiguredDataSet('/battery-pack.json');
    	getConfiguredDataSet('/motor-controller-temperatures.json');
    	getConfiguredDataSet('/vehicle-velocity.json');
    	getConfiguredDataSet('/power-trackers.json', { ptIdx: 1});
    	getConfiguredDataSet('/power-trackers.json', { ptIdx: 2});
    	getConfiguredDataSet('/power-trackers.json', { ptIdx: 3});
    	     
        setTimeout(update, 5000);
    }

    update();
});
