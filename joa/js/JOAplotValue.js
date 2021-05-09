/**
 * 
 * Google Map API Ver.3
 * 
 * K.Someya, Hiroaki Tateshita Reference from
 * http://www.ajaxtower.jp/googlemaps/ Reference from
 * https://developers.google.com/maps/documentation/javascript/events?hl=ja
 * 
 * 仕様 google mapの任意の点をクリックすると、その点を中心に指定した範囲に物理量に対応した 色を表示させる。
 * 
 * 
 */

var map;
var latlng = "36.068235,140.129172";　// 地図の初期値
var range = 0.5;
var radius = 4000;//[m]
var url_DataSource = "prc";
var url_Date ="2012-08-20";
var token = "TOKEN_zzkC_";
var valueCircle;

function initialize() {

	/* Setting for initial map info. */
	var mapOptions = {
			zoom: 8,
			center: new google.maps.LatLng(35.6586212780812,139.70509886741638),
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
		var url =  "https://joa.epi.bz/api/"　+　url_DataSource　+　"all?token="+token+"&date="
		+ url_Date + "&lat="　+　url_lat　+　"&lon="　+　url_lng　+　"&format=jsonp&range="
		+ range+"&callback=callback";
		
		load_src(url);
	});

}

window.callback = function(data){
	if(url_DataSource=="prc"){
	plotRange(data.values, latlng);
	}else if(url_DataSource=="ssw"){
		plotRange(data.values, latlng);
	}else if(url_DataSource=="sst"){
		plotRange(data.values, latlng);
	}else if(url_DataSource=="smc"){
		plotRange(data.values, latlng);
	}else if(url_DataSource=="snd"){
		plotRange(data.values, latlng);
	}
};

function load_src(url){
	var script = document.createElement('script');
	script.src = url;
	document.body.appendChild(script);
}

function plotRange(values, latLng){
	values.forEach(function(ele,index,array){
		valueCircle = new google.maps.Circle({
		     strokeColor: colorString(ele),
		      strokeOpacity: 0.8,
		      strokeWeight: 2,
		      fillColor: colorString(ele),
		      fillOpacity: 0.35,
		      map: map,
		      center: new google.maps.LatLng(ele.lat,ele.lon),
		      radius: radius
		});
	});
}

function colorString(value){
	var temp;
	if(url_DataSource=="prc"){
		temp = value.prc/30.0;
	}else if(url_DataSource=="ssw"){
		temp = value.ssw/18.0;
	}else if(url_DataSource=="sst"){
		temp = value.sst/30.0;
	}else if(url_DataSource=="smc"){
		temp = value.smc/100;
	}else if(url_DataSource=="snd"){
		temp = value.snd/50;
	}
	if(temp==0){
		return null;
	}else if(temp<0.1){
		return '#00008B';
	}else if(temp<0.2){
		return '#0000ff';
	}else if(temp<0.3){
		return '#00BFFF';
	}else if(temp<0.4){
		return '#20B2AA';
	}else if(temp<0.5){
		return '#98FB98';
	}else if(temp<0.6){
		return '#FFD700';
	}else if(temp<0.7){
		return '#FFA07A';
	}else if(temp<0.8){
		return '#FF4500';
	}else if(temp<0.9){
		return '#FF69B4';
	}else {
		return '#C71585';
	}
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


