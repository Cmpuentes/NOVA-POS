import { useEffect, useState } from "react";
import Layout from "../components/layout/Layout";

import {
    getRoles,
    desactivarRol
} from "../services/rolService";

import RoleCard from "../components/RoleCard";
import RoleModal from "../components/RoleModal";

import "../styles/roles.css";


function Roles(){

    const [roles,setRoles] = useState([]);

    const [modalOpen,setModalOpen] = useState(false);

    const [rolEditar,setRolEditar] = useState(null);



    const cargarRoles = async()=>{

        try{

            const data = await getRoles();

            setRoles(data);


        }catch(error){

            console.error(
                "Error cargando roles",
                error
            );

        }

    };



    useEffect(()=>{

        cargarRoles();

    },[]);



    const editarRol=(rol)=>{

        setRolEditar(rol);

        setModalOpen(true);

    };



    const eliminarRol=async(id)=>{


        const confirmar = window.confirm(
            "¿Deseas desactivar este rol?"
        );


        if(!confirmar)return;


        try{

            await desactivarRol(id);

            alert(
                "Rol desactivado correctamente"
            );

            cargarRoles();


        }catch(error){

            console.error(error);

            alert(
                "Error desactivando rol"
            );

        }

    };



    return(

        <Layout>


            <div className="roles-page">



                <div className="roles-header">


                    <div className="roles-title-space"></div>



                    <button
                        className="roles-new-btn"
                        onClick={()=>{

                            setRolEditar(null);

                            setModalOpen(true);

                        }}
                    >

                        + Nuevo Rol

                    </button>



                </div>




                <div className="roles-container">


                {
                    roles.map((rol)=>(

                        <RoleCard

                            key={rol.id}

                            rol={rol}

                            onEdit={editarRol}

                            onDelete={eliminarRol}

                        />

                    ))
                }


                </div>




                <RoleModal

                    isOpen={modalOpen}

                    onClose={()=>
                        setModalOpen(false)
                    }

                    rolEditar={rolEditar}

                    onRolGuardado={cargarRoles}

                />



            </div>


        </Layout>

    );


}


export default Roles;