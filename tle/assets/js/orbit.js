function calc() {
    let data_part = document.getElementById("data");
    data_part.innerHTML = "this is it!";

    const date = new Date(2021, 4, 11, 11, 30, 0);
    const tle = {
        first_line: "1 25544U 98067A   21130.82534197  .00000540  00000-0  17959-4 0  9990",
        second_line: "2 25544  51.6449 167.5222 0002900 345.0941 184.7772 15.48989745282809"
    }

    let output_string = "";
    const satellite = new Orb.SGP4(tle);
    const xyz = satellite.xyz(date); // equatorial rectangular coordinates (x, y, z ,xdot, ydot, zdot)
    const latlng = satellite.latlng(date); // geographic spherical coordinates(latitude, longitude, altitude, velocity)
    //data_part.innerHTML = "that is " + date.toUTCString() + " , " + " , lat: "+latlng.latitude + " lng: "+latlng.longitude;
    const time = new Orb.Time(date);
    const gmst = time.gmst();
    output_string = date.toUTCString()+"  "+ xyz.x+ ","+xyz.y+","+xyz.z+":"+latlng.latitude+","+latlng.longitude+","+latlng.altitude+"<br>";
    const date2 = new Date(2012, 4, 31, 2, 57, 31);
    const time2 = new Orb.Time(date2);
    const gmst2 = time2.gmst();
    output_string += date2.toUTCString()+"+ GMST: "+ gmst2+"<br>";
    data_part.innerHTML = output_string;
    

}