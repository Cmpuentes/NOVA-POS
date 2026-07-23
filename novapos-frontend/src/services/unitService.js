import axios from "axios";

const API_URL = "http://localhost:8080/api/unidades-medida";


export const getUnidades = async () => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.get(API_URL, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return response.data;
};


export const crearUnidad = async (unidad) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.post(
        API_URL,
        unidad,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const actualizarUnidad = async (id, unidad) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.put(
        `${API_URL}/${id}`,
        unidad,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const eliminarUnidad = async (id) => {

    const token = localStorage.getItem("accessToken");

    await axios.delete(
        `${API_URL}/${id}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

};