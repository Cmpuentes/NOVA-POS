import { useEffect, useState } from "react";
import "../styles/units.css";
import {
    crearUnidad,
    actualizarUnidad
} from "../services/unitService";


function UnitModal({
    isOpen,
    onClose,
    onUnidadCreada,
    unidadEditar
}) {


    const [unidad, setUnidad] = useState({
        nombre: "",
        abreviatura: ""
    });



    useEffect(() => {

        if (unidadEditar) {

            setUnidad({
                nombre: unidadEditar.nombre,
                abreviatura: unidadEditar.abreviatura
            });

        } else {

            setUnidad({
                nombre: "",
                abreviatura: ""
            });

        }

    }, [unidadEditar]);



    const guardarUnidad = async () => {

        try {


            if (unidadEditar) {

                await actualizarUnidad(
                    unidadEditar.id,
                    unidad
                );

                alert("Unidad actualizada correctamente");


            } else {

                await crearUnidad(unidad);

                alert("Unidad creada correctamente");

            }


            onUnidadCreada();


            setUnidad({
                nombre: "",
                abreviatura: ""
            });


            onClose();



        } catch (error) {

            console.error(
                "Error guardando unidad:",
                error
            );

            alert("Error al guardar unidad");

        }

    };



    if (!isOpen) return null;



    return (

        <div className="unit-modal-overlay">


            <div className="unit-modal-content">


                <h2>
                    {
                        unidadEditar
                        ? "Editar Unidad"
                        : "Nueva Unidad"
                    }
                </h2>



                <input
                    type="text"
                    placeholder="Nombre"
                    value={unidad.nombre}
                    onChange={(e) =>
                        setUnidad({
                            ...unidad,
                            nombre: e.target.value
                        })
                    }
                />



                <input
                    type="text"
                    placeholder="Abreviatura"
                    value={unidad.abreviatura}
                    onChange={(e) =>
                        setUnidad({
                            ...unidad,
                            abreviatura: e.target.value
                        })
                    }
                />



                <div className="unit-modal-actions">


                    <button onClick={onClose}>
                        Cancelar
                    </button>


                    <button onClick={guardarUnidad}>
                        Guardar
                    </button>


                </div>


            </div>


        </div>

    );

}


export default UnitModal;