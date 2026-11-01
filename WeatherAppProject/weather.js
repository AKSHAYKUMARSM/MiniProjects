let addressInput;
let Output;

document.addEventListener("DOMContentLoaded",()=>{
    addressInput=document.getElementById("address");
    Output=document.getElementById("data");
    document.getElementById("search").addEventListener("click",getLocation);
    document.getElementById("currentLocation").addEventListener("click",()=>{
        Output.innerHTML='';
        const location=navigator.geolocation.getCurrentPosition((position)=>{
        const latitude=position.coords.latitude;
        const longitude=position.coords.longitude;

        getWeather(latitude,longitude,{name:'My location',admin1:'Browser'})
        },(error)=>{
            switch(error.code) {
                case error.PERMISSION_DENIED:
                    console.error("User denied the request for Geolocation.");
                break;
                case error.POSITION_UNAVAILABLE:
                    console.error("Location information is unavailable.");
                break;
                case error.TIMEOUT:
                    console.error("The request to get user location timed out.");
                break;
                default:
                    console.error("An unknown error occurred.");
                break;
            }
        })
    })
})


async function getWeather(latitude,longitude,geolocation){

    const weatherResponse= await fetch(`https://api.open-meteo.com/v1/forecast?latitude=${latitude}&longitude=${longitude}&current=temperature_2m,relative_humidity_2m,wind_speed_10m,apparent_temperature,weather_code,&forecast_days=7&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset`)
    const weatherData = await weatherResponse.json();

    const weather=weatherData.current;
    const weatherUnits=weatherData.current_units;
    const forecast=weatherData.daily;
    const forecastUnit=weatherData.daily_units;
    let forecastdata=`<table><caption>forecast</caption>
    <tr>
    <th>Day</th>
    <th>Date</th>
    <th>Max Temperature</th>
    <th>Min Temperature</th>
    <th>Sunrise</th>
    <th>Sunset</th>
    </tr>`;
    
    for(let day=0;day<forecast.time.length;day++){
        let instant = Temporal.Instant.from(forecast.sunrise[day]+':00z');
        const browserZone = Temporal.Now.timeZoneId()
        const sunrise = instant.toZonedDateTimeISO(browserZone);
        instant = Temporal.Instant.from(forecast.sunset[day]+':00z')
        const sunset=instant.toZonedDateTimeISO(browserZone);
        const dayOfWeek=sunrise.toLocaleString('en-US', { weekday: 'long' });;
        
        forecastdata+=`<tr class="forecast">
        <th>${dayOfWeek}</th>
        <td>${sunrise.toPlainDate()}</td>
        <td>${forecast.temperature_2m_max[day]} ${forecastUnit.temperature_2m_max}</td>
        <td>${forecast.temperature_2m_min[day]} ${forecastUnit.temperature_2m_min}</td>
        <td>${sunrise.toPlainTime()}</td>
        <td>${sunset.toPlainTime()}</td>
        </tr>`
    }

    forecastdata+=`</table>`


    Output.innerHTML=   `<legend>Description</legend>
                        <p><strong>Time:</strong> ${weather.time} UTC</p>
                        <legend>Weather ${getWeatherIcon(weather.weather_code)}</legend>
                        <div class="weather box">
                        <p><strong>Temperature:</strong> ${weather.temperature_2m} ${weatherUnits.temperature_2m}</p>
                        <p><strong>Feels Like:</strong> ${weather.apparent_temperature} ${weatherUnits.apparent_temperature}</p>
                        <p><strong>Humidity:</strong> ${weather.relative_humidity_2m} ${weatherUnits.relative_humidity_2m}</p>
                        <p><strong>Wind Speed:</strong> ${weather.wind_speed_10m} ${weatherUnits.wind_speed_10m}</p></div>`+forecastdata;

}

function error(errorMsg){
    Output.innerHTML=errorMsg;
}

function getWeatherIcon(code){
    switch (true) {
        case code===0:                  return "☀️";
        case code===1||code===2:        return "🌤️";    
        case code===3:                  return "☁️";
        case code===45 ||code===48:     return "🌫️";
        case code >= 51 && code <= 57:  return "🌦️";
        case code >= 61 && code <= 67:  return "🌧️";
        case code >= 71 && code <= 77:  return "🌨️";
        case code >= 80 && code <= 82:  return "🌧️";
        case code >= 95:                return "⛈️";
        default:                        return "❓";
    }
}


async function getLocation(){
    const searchAddress=addressInput.value.trim();
    
    Output.innerHTML='';
    if(searchAddress==='') return error(`<p class="error">Please enter an address or Pincode</p>`);

    const geoResponse = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${searchAddress}`);
    const geoData= await geoResponse.json();

    if(!geoData.results||geoData.results.lenght===0) return error(`<p class="error">Address not found</p>`);
    
    const geolocation = geoData.results[0];
    
    const longitude=geolocation.longitude;
    const latitude=geolocation.latitude;

    getWeather(latitude,longitude,geolocation)
}