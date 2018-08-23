$(function() {

	function getDPVal(msrmntData, divisor) {
		if (msrmntData.fv == undefined)
			if (msrmntData.iv == undefined)
				if (msrmntData.cv == undefined)
					return undefined;
				else
					return msrmntData.cv;
			else
				return msrmntData.iv;
		else {
			var numberToReturn = msrmntData.fv / divisor; 
			return  Number(numberToReturn.toFixed(2));
		}
			
	}

	function setCellValue(msrmntData, divisor) {
		var value = getDPVal(msrmntData, divisor);
		$("td#"+msrmntData.cId).text(value);
		$("td#"+msrmntData.cId).attr({'class': msrmntData.state});
	}

	function getDPData(canId,divisor) {

		$.ajax({
			async: false,
			url: '/bms.json?canId=' + canId,
			dataType:'json',
			success: function(result) {
				for (var i = 0; i < result.length; i++) {
					setCellValue(result[i],divisor);
				}
			}
		});
	}

	function getData() {
	
		getDPData(1780,1);
		getDPData(1781,1);
		getDPData(1782,1);
		getDPData(1783,1);
		getDPData(1784,1);
		getDPData(1786,1);
		getDPData(1787,1);
		getDPData(1788,1);
		getDPData(1537,10);
		getDPData(1540,10);
		getDPData(1543,10);
		getDPData(1546,10);
		getDPData(1549,10);
		getDPData(1538,1);
		getDPData(1539,1);
		getDPData(1541,1);
		getDPData(1542,1);
		getDPData(1544,1);
		getDPData(1545,1);
		getDPData(1547,1);
		getDPData(1548,1);
		getDPData(1550,1);
		getDPData(1551,1);
		
		
		
	}
	
	function update() {
		getData();
		setTimeout(update, 1000);
	}

	update();
});

