import axios from "axios";

const API_URL = "http://localhost:8080/api/productos";

export const getProductos = async () => {
    const token = localStorage.getItem("accessToken");

    const response = await axios.get(API_URL, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return response.data;
};

export const getProductosPorCategoria = async (categoriaId) => {
    const token = localStorage.getItem("accessToken");

    const response = await axios.get(
        `${API_URL}/categoria/${categoriaId}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};
export const crearProducto = async (producto) => {
    const token = localStorage.getItem("accessToken");

    const response = await axios.post(
        API_URL,
        producto,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        }
    );

    return response.data;
};
export const actualizarProducto = async (id, producto) => {

    const token = localStorage.getItem("accessToken");

    const response = await axios.put(
        `${API_URL}/${id}`,
        producto,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const desactivarProducto = async (id) => {

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