import axios from "axios";

const API_URL = "http://localhost:8080/api/categorias";


export const getCategorias = async () => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.get(API_URL, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return response.data;
};



export const crearCategoria = async (categoria) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.post(
        API_URL,
        categoria,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};



export const actualizarCategoria = async (id, categoria) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.put(
        `${API_URL}/${id}`,
        categoria,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};

export const desactivarCategoria = async (id) => {

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