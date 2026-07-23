import axios from "axios";

const API_URL = "http://localhost:8080/api/usuarios";


export const getUsuarios = async () => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.get(API_URL, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return response.data;
};


export const crearUsuario = async (usuario) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.post(
        API_URL,
        usuario,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const actualizarUsuario = async (id, usuario) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.put(
        `${API_URL}/${id}`,
        usuario,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const desactivarUsuario = async (id) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.delete(
        `${API_URL}/${id}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};