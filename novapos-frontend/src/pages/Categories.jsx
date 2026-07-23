import { useEffect, useState } from "react";
import Layout from "../components/layout/Layout";
import CategoryCard from "../components/CategoryCard";
import CategoryModal from "../components/CategoryModal";
import {
    getCategorias
} from "../services/categoryService";
import "../styles/categories.css";


function Categories() {


    const [categorias, setCategorias] = useState([]);

    const [modalOpen, setModalOpen] = useState(false);

    const [categoriaEditar, setCategoriaEditar] = useState(null);



    useEffect(() => {

        cargarCategorias();

    }, []);




    const cargarCategorias = async () => {

        try {

            const data = await getCategorias();

            setCategorias(data);


        } catch (error) {

            console.error(
                "Error cargando categorías:",
                error
            );

        }

    };





    const editarCategoria = (categoria) => {

        setCategoriaEditar(categoria);

        setModalOpen(true);

    };





    return (

        <Layout>


            <div>


                {/* HEADER CATEGORIAS */}

                <div className="products-header">


                    <div className="products-title-space"></div>



                    <div className="products-actions">



                        <button

                            className="new-product-btn"

                            onClick={() => {

                                setCategoriaEditar(null);

                                setModalOpen(true);

                            }}

                        >

                            + Nueva Categoría

                        </button>



                    </div>



                </div>





                {/* CARDS CATEGORIAS */}



                {categorias.length === 0 ? (


                    <p>
                        No hay categorías registradas
                    </p>


                ) : (


                    <div className="categories-grid">


                        {categorias.map((categoria) => (


                            <CategoryCard

                                key={categoria.id}

                                categoria={categoria}

                                onEdit={editarCategoria}

                                onDelete={cargarCategorias}


                            />


                        ))}


                    </div>


                )}







                <CategoryModal

                    isOpen={modalOpen}

                    onClose={() => setModalOpen(false)}

                    onCategoriaCreada={cargarCategorias}

                    categoriaEditar={categoriaEditar}

                />



            </div>


        </Layout>

    );

}



export default Categories;