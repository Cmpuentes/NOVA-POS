import { useEffect, useState } from "react";
import Layout from "../components/layout/Layout";
import "../styles/users.css";

import {
    getUsuarios,
    desactivarUsuario
} from "../services/userService";

import UserCard from "../components/UserCard";
import UserModal from "../components/UserModal";


function Users() {

    const [usuarios, setUsuarios] = useState([]);

    const [modalOpen, setModalOpen] = useState(false);

    const [usuarioEditar, setUsuarioEditar] = useState(null);



    useEffect(() => {

        cargarUsuarios();

    }, []);



    const cargarUsuarios = async () => {

        try {

            const data = await getUsuarios();

            setUsuarios(data);


        } catch (error) {

            console.error(
                "Error cargando usuarios:",
                error
            );

        }

    };



    const editarUsuario = (usuario) => {

        setUsuarioEditar(usuario);

        setModalOpen(true);

    };



    const eliminarUsuario = async (id) => {

        const confirmar = window.confirm(
            "¿Deseas desactivar este usuario?"
        );


        if (!confirmar) return;


        try {

            await desactivarUsuario(id);


            alert(
                "Usuario desactivado correctamente"
            );


            cargarUsuarios();


        } catch (error) {

            console.error(error);


            alert(
                "Error al desactivar usuario"
            );

        }

    };



    return (

        <Layout>

            <div className="users-page">


                <div className="users-header">


                    {/* Espacio reservado para mantener alineación */}
                    <div className="users-title-space"></div>



                    <button
                        className="btn-primary"
                        onClick={() => {

                            setUsuarioEditar(null);

                            setModalOpen(true);

                        }}
                    >
                        + Nuevo Usuario
                    </button>


                </div>




                {
                    usuarios.length === 0 ? (

                        <p>
                            No hay usuarios registrados
                        </p>


                    ) : (


                        <div className="users-container">


                            {
                                usuarios.map((usuario) => (

                                    <UserCard

                                        key={usuario.id}

                                        usuario={usuario}

                                        onEdit={editarUsuario}

                                        onDelete={eliminarUsuario}

                                    />

                                ))
                            }


                        </div>


                    )
                }




                <UserModal

                    isOpen={modalOpen}


                    onClose={() =>
                        setModalOpen(false)
                    }


                    usuarioEditar={usuarioEditar}


                    onUsuarioCreado={
                        cargarUsuarios
                    }


                />


            </div>


        </Layout>

    );

}


export default Users;