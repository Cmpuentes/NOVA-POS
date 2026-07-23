import { useEffect, useState } from "react";
import Layout from "../components/layout/Layout";
import UnitCard from "../components/UnitCard";
import UnitModal from "../components/UnitModal";
import {
    getUnidades,
    eliminarUnidad
} from "../services/unitService";


function Units() {


    const [unidades, setUnidades] = useState([]);

    const [modalOpen, setModalOpen] = useState(false);

    const [unidadEditar, setUnidadEditar] = useState(null);



    useEffect(() => {

        cargarUnidades();

    }, []);




    const cargarUnidades = async () => {

        try {

            const data = await getUnidades();

            setUnidades(data);


        } catch (error) {

            console.error(
                "Error cargando unidades:",
                error
            );

        }

    };




    const editarUnidad = (unidad) => {

        setUnidadEditar(unidad);

        setModalOpen(true);

    };






    const eliminar = async (id) => {

        try {

            await eliminarUnidad(id);

            alert("Unidad eliminada correctamente");

            cargarUnidades();


        } catch (error) {

            console.error(
                "Error eliminando unidad:",
                error
            );

        }

    };






    return (

        <Layout>


            <div className="units-page">



                <div className="units-header">



                    <button
                        onClick={() => {

                            setUnidadEditar(null);

                            setModalOpen(true);

                        }}
                    >

                        + Nueva Unidad

                    </button>


                </div>






                <div className="units-container">


                    {
                        unidades.length === 0 ? (

                            <p>
                                No hay unidades registradas
                            </p>


                        ) : (


                            unidades.map((unidad) => (

                                <UnitCard

                                    key={unidad.id}

                                    unidad={unidad}

                                    onEdit={editarUnidad}

                                    onDelete={eliminar}

                                />


                            ))


                        )
                    }


                </div>







                <UnitModal

                    isOpen={modalOpen}

                    onClose={() => setModalOpen(false)}

                    onUnidadCreada={cargarUnidades}

                    unidadEditar={unidadEditar}

                />



            </div>



        </Layout>

    );

}


export default Units;