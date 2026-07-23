import { useEffect, useState } from "react";
import "../styles/categories.css";
import { 
    crearCategoria,
    actualizarCategoria
} from "../services/categoryService";


function CategoryModal({ 
    isOpen, 
    onClose, 
    onCategoriaCreada,
    categoriaEditar
}) {

    const [categoria, setCategoria] = useState({
        nombre: "",
        descripcion: ""
    });


    useEffect(() => {

        if (categoriaEditar) {

            setCategoria({
                nombre: categoriaEditar.nombre,
                descripcion: categoriaEditar.descripcion || ""
            });

        } else {

            setCategoria({
                nombre: "",
                descripcion: ""
            });

        }

    }, [categoriaEditar]);


    const guardarCategoria = async () => {

        try {

            if (categoriaEditar) {

                await actualizarCategoria(
                    categoriaEditar.id,
                    categoria
                );

                alert("Categoría actualizada correctamente");

            } else {

                await crearCategoria(categoria);

                alert("Categoría creada correctamente");

            }


            onCategoriaCreada();


            setCategoria({
                nombre: "",
                descripcion: ""
            });


            onClose();


        } catch (error) {

            console.error("Error guardando categoría:", error);

            alert("Error al guardar categoría");

        }

    };


    if (!isOpen) return null;


    return (
        <div className="category-modal-overlay">

            <div className="category-modal-content">

                <h2>
                    {categoriaEditar 
                        ? "Editar Categoría" 
                        : "Nueva Categoría"}
                </h2>


                <input
                    type="text"
                    placeholder="Nombre"
                    value={categoria.nombre}
                    onChange={(e) =>
                        setCategoria({
                            ...categoria,
                            nombre: e.target.value
                        })
                    }
                />


                <textarea
                    placeholder="Descripción"
                    value={categoria.descripcion}
                    onChange={(e) =>
                        setCategoria({
                            ...categoria,
                            descripcion: e.target.value
                        })
                    }
                />


                <div className="category-modal-actions">

                    <button onClick={onClose}>
                        Cancelar
                    </button>


                    <button onClick={guardarCategoria}>
                        Guardar
                    </button>

                </div>

            </div>

        </div>
    );
}


export default CategoryModal;