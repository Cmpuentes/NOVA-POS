import axios from "axios";

const API_URL = "http://localhost:8080/api/roles";


const getToken = () => {
    return localStorage.getItem("accessToken");
};



export const getRoles = async () => {

    const response = await axios.get(API_URL, {

        headers: {
            Authorization: `Bearer ${getToken()}`
        }

    });

    return response.data;

};



export const crearRol = async (rol) => {

    const response = await axios.post(
        API_URL,
        rol,
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    );

    return response.data;

};



export const actualizarRol = async (id, rol) => {

    const response = await axios.put(
        `${API_URL}/${id}`,
        rol,
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    );

    return response.data;

};



export const desactivarRol = async (id) => {

    await axios.delete(
        `${API_URL}/${id}`,
        {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        }
    );

};