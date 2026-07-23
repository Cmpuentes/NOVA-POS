import { useEffect, useState } from "react";
import Layout from "../components/layout/Layout";
import { 
    getProductos, 
    getProductosPorCategoria,
    desactivarProducto
} from "../services/productService";
import { getCategorias } from "../services/categoryService";
import { getImpuestos } from "../services/taxService";
import { getUnidades } from "../services/unitService";
import ProductCard from "../components/ProductCard";
import CategoryButton from "../components/CategoryButton";
import ProductModal from "../components/ProductModal";



function Products() {

const [productos, setProductos] = useState([]);
const [categorias, setCategorias] = useState([]);
const [categoriasOpen, setCategoriasOpen] = useState(false);
const [impuestos, setImpuestos] = useState([]);
const [unidades, setUnidades] = useState([]);
const [modalOpen, setModalOpen] = useState(false);
const [productoEditar, setProductoEditar] = useState(null);



useEffect(() => {
    cargarProductos();
    cargarCategorias();
    cargarImpuestos();
    cargarUnidades();
}, []);



const cargarProductos = async () => {
    try {
        const data = await getProductos();
        console.log("Productos recibidos:", data);
        setProductos(data);
    } catch (error) {
        console.error("Error cargando productos:", error);
    }
};



const cargarCategorias = async () => {
    try {
        const data = await getCategorias();
        console.log("Categorías recibidas:", data);
        setCategorias(data);
    } catch (error) {
        console.error("Error cargando categorías:", error);
    }
};



const cargarImpuestos = async () => {
    try {
        const data = await getImpuestos();
        console.log("Impuestos recibidos:", data);
        setImpuestos(data);
    } catch (error) {
        console.error("Error cargando impuestos:", error);
    }
};



const cargarUnidades = async () => {
    try {
        const data = await getUnidades();
        console.log("Unidades recibidas:", data);
        setUnidades(data);
    } catch (error) {
        console.error("Error cargando unidades:", error);
    }
};



const filtrarPorCategoria = async (categoriaId) => {
    try {
        const data = await getProductosPorCategoria(categoriaId);
        setProductos(data);
    } catch (error) {
        console.error("Error filtrando productos:", error);
    }
};



const eliminarProducto = async (id) => {

    const confirmar = window.confirm(
        "¿Seguro que deseas desactivar este producto?"
    );

    if (!confirmar) {
        return;
    }

    try {

        await desactivarProducto(id);

        alert("Producto desactivado correctamente");

        cargarProductos();

    } catch (error) {
        console.error("Error desactivando producto:", error);
        alert("No se pudo desactivar el producto");
    }
};



const editarProducto = (producto) => {

    setProductoEditar(producto);

    setModalOpen(true);

};



return (

    <Layout>

        <div>


            {/* HEADER PRODUCTOS */}
<div className="products-header">

    <div className="products-title-space"></div>

    <div className="products-actions">



                    <div className="category-container">


                        <button
                            className="category-toggle"
                            onClick={() => 
                                setCategoriasOpen(!categoriasOpen)
                            }
                        >
                            Categorías ▾
                        </button>



                        {categoriasOpen && (

    <div className="category-panel">

        <button
            onClick={() => {
                cargarProductos();
                setCategoriasOpen(false);
            }}
        >
            Todas las categorías
        </button>

        {categorias.map((categoria) => (

            <CategoryButton
                key={categoria.id}
                categoria={categoria}
                onClick={(id) => {
                    filtrarPorCategoria(id);
                    setCategoriasOpen(false);
                }}
            />

        ))}

    </div>

)}


                    </div>




                    <button 

                        className="new-product-btn"

                        onClick={() => {

                            setProductoEditar(null);

                            setModalOpen(true);

                        }}

                    >

                        + Nuevo Producto

                    </button>



                </div>


            </div>





            {/* PRODUCTOS */}


            {productos.length === 0 ? (

                <p>
                    No hay productos registrados
                </p>


            ) : (


                <div className="products-grid">


                    {productos.map((producto) => (


                        <ProductCard 


                            key={producto.id}

                            producto={producto}

                            onDelete={eliminarProducto}

                            onEdit={editarProducto}


                        />


                    ))}


                </div>


            )}






            {/* MODAL CREAR / EDITAR */}


            <ProductModal

                isOpen={modalOpen}

                onClose={() => setModalOpen(false)}

                categorias={categorias}

                impuestos={impuestos}

                unidades={unidades}

                onProductoCreado={cargarProductos}

                productoEditar={productoEditar}

            />



        </div>


    </Layout>

);


}



export default Products;