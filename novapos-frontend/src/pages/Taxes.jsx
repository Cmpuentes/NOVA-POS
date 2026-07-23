import { useEffect, useState } from "react";

import Layout from "../components/layout/Layout";

import TaxCard from "../components/TaxCard";
import TaxModal from "../components/TaxModal";

import {
    getImpuestos,
    desactivarImpuesto
} from "../services/taxService";


import "../styles/taxes.css";


function Taxes() {


    const [impuestos, setImpuestos] = useState([]);

    const [modalOpen, setModalOpen] = useState(false);

    const [impuestoEditar, setImpuestoEditar] = useState(null);



    useEffect(() => {

        cargarImpuestos();

    }, []);




    const cargarImpuestos = async () => {

        try {

            const data = await getImpuestos();

            setImpuestos(data);


        } catch(error){

            console.error(
                "Error cargando impuestos:",
                error
            );

        }

    };





    const editarImpuesto = (impuesto)=>{

        setImpuestoEditar(impuesto);

        setModalOpen(true);

    };






    const eliminarImpuesto = async(id)=>{


        const confirmar = window.confirm(
            "¿Deseas desactivar este impuesto?"
        );


        if(!confirmar) return;



        try{


            await desactivarImpuesto(id);



            alert(
                "Impuesto desactivado correctamente"
            );


            cargarImpuestos();



        }catch(error){


            console.error(
                "Error desactivando impuesto:",
                error
            );


            alert(
                "Error al desactivar impuesto"
            );


        }


    };






    return (

        <Layout>


            <div className="taxes-page">



                <div className="taxes-header">


                    <div className="taxes-title-space"></div>



                    <button

                        className="new-tax-btn"

                        onClick={()=>{

                            setImpuestoEditar(null);

                            setModalOpen(true);

                        }}

                    >

                        + Nuevo Impuesto

                    </button>



                </div>






                {
                    impuestos.length === 0 ? (


                        <p>
                            No hay impuestos registrados
                        </p>



                    ) : (



                        <div className="taxes-grid">


                            {
                                impuestos.map((impuesto)=>(


                                    <TaxCard

                                        key={impuesto.id}

                                        impuesto={impuesto}

                                        onEdit={editarImpuesto}

                                        onDelete={eliminarImpuesto}


                                    />


                                ))
                            }



                        </div>


                    )
                }





                <TaxModal

                    isOpen={modalOpen}

                    onClose={()=>setModalOpen(false)}

                    onImpuestoCreado={cargarImpuestos}

                    impuestoEditar={impuestoEditar}

                />




            </div>


        </Layout>


    );


}


export default Taxes;