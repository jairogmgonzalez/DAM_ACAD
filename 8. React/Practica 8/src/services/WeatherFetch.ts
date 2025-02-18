export async function WeatherFetch(city: string) {
    const API_WEATHER = `https://api.weatherapi.com/v1/current.json?key=${import.meta.env.VITE_API_KEY}&lang=es&q=${city}`;

    try {
        const response = await fetch(API_WEATHER);
        if (!response.ok) {
            throw new Error('No se encontraron datos para la ciudad ingresada');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error en la API:", error);
        return null;
    }
}