import axios from "axios";

const API_URL = "http://localhost:8080/api/impuestos";


export const getImpuestos = async () => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.get(API_URL, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return response.data;
};


export const crearImpuesto = async (impuesto) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.post(
        API_URL,
        impuesto,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const actualizarImpuesto = async (id, impuesto) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.put(
        `${API_URL}/${id}`,
        impuesto,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const desactivarImpuesto = async (id) => {

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