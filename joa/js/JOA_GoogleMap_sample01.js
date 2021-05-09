/**
 * 
 * Google Map API Ver.3
 * 
 * K.Someya, Hiroaki Tateshita Reference from
 * http://www.ajaxtower.jp/googlemaps/ Reference from
 * https://developers.google.com/maps/documentation/javascript/events?hl=ja
 * 
 * 仕様 google mapの任意の点をクリックすると、値が情報ウィンドウに表示される。
 * 
 * 
 */

var map;
var latlng = "36.068235,140.129172";　// 地図の初期値
var url_DataSource = "prc";
var url_Date ="2012-08-20";

function initialize() {

	/* Setting for initial map info. */
	var mapOptions = {
			zoom: 2,
			center: new google.maps.LatLng(36.068235,140.129172),
			mapTypeId: google.maps.MapTypeId.ROADMAP
	};

	/* Generating map */
	map = new google.maps.Map(document.getElementById('map_canvas'),
			mapOptions);

	/* Event when click */  
	google.maps.event.addListener(map, 'click', function(event) {
		latlng = event.latLng;
		var url_lat = latlng.lat();
		var url_lng = latlng.lng();
		var url_Date_temp = $('#datepicker').val();
		// alert("date_temp "+url_Date_temp);
		if(url_Date_temp!=""){
			url_Date = url_Date_temp;
		}
		url_DataSource=$('#sel1').val();
		var url =  "https://joa.epi.bz/api/"　+　url_DataSource　+　"avg?token=TOKEN_zzkC_&date="+ url_Date + "&lat="　+　url_lat　+　"&lon="　+　url_lng　+　"&format=jsonp";
		// var url = "http://localhost:8080/api/" + url_DataSource +
		// "all?token=aaa&date="+ url_Date + "&lat=" + url_lat + "&lon=" +
		// url_lng + "&format=jsonp";
		// alert(url);
		load_src(url);
	});

}

window.callback = function(data){
	if(url_DataSource=="prc"){
	addInfowindow(data.prc,latlng);
	}else if(url_DataSource=="ssw"){
	addInfowindow(data.ssw,latlng);
	}else if(url_DataSource=="sst"){
	addInfowindow(data.sst,latlng);
	}else if(url_DataSource=="smc"){
	addInfowindow(data.smc,latlng);
	}else if(url_DataSource=="prc"){
	addInfowindow(data.snd,latlng);
	}
};

function load_src(url){
	var script = document.createElement('script');
	script.src = url;
	document.body.appendChild(script);
}

function addInfowindow(text, latLng){
	/* Add information window at click point */
	var text2 = url_Date+": "+url_DataSource + "=" + text;
	var infowindow = new google.maps.InfoWindow({
		content: text2,
		position: latLng
	});
	infowindow.open(map);
}

google.maps.event.addDomListener(window, 'load', initialize);


