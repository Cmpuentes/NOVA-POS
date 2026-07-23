import "../styles/categories.css";
import { desactivarCategoria } from "../services/categoryService";


function CategoryCard({ categoria, onEdit, onDelete }) {


const handleDelete = async () => {


    const confirmar = window.confirm(
        "¿Deseas desactivar esta categoría?"
    );


    if (!confirmar) return;



    try {


        await desactivarCategoria(categoria.id);


        alert(
            "Categoría desactivada correctamente"
        );


        onDelete();



    } catch(error){


        console.error(
            "Error desactivando categoría:",
            error
        );


        alert(
            "Error al desactivar categoría"
        );


    }


};




return (

<div className="category-card">


    <div className="category-info">


        <h3>

            {categoria.nombre}

        </h3>



        <p>

            {categoria.descripcion || "Sin descripción"}

        </p>




        <span>

            Estado: {categoria.activa ? "Activa" : "Inactiva"}

        </span>




        <div className="category-actions">


            <button

                className="category-edit-btn"

                onClick={() => onEdit(categoria)}

            >

                Editar

            </button>



            <button

                className="category-delete-btn"

                onClick={handleDelete}

            >

                Desactivar

            </button>



        </div>


    </div>



</div>

);


}


export default CategoryCard;