import { useEffect, useState } from "react";
import {
    crearUsuario,
    actualizarUsuario
} from "../services/userService";
import { getRoles } from "../services/rolService";


function UserModal({
    isOpen,
    onClose,
    onUsuarioCreado,
    usuarioEditar
}) {


    const [usuario, setUsuario] = useState({

        nombreCompleto: "",
        email: "",
        password: "",
        rolId: ""

    });
    const [roles, setRoles] = useState([]);



    useEffect(() => {

        if (usuarioEditar) {

            setUsuario({

                nombreCompleto: usuarioEditar.nombreCompleto,
                email: usuarioEditar.email,
                password: "",
                rolId: usuarioEditar.rolId || ""

            });

        } else {

            setUsuario({

                nombreCompleto: "",
                email: "",
                password: "",
                rolId: ""

            });

        }

    }, [usuarioEditar]);

    useEffect(() => {

    const cargarRoles = async () => {

        try {

            const data = await getRoles();

            setRoles(data);

        } catch (error) {

            console.error(
                "Error cargando roles:",
                error
            );

        }

    };


    cargarRoles();


}, []);



    const guardarUsuario = async () => {

        try {

            if (usuarioEditar) {

                await actualizarUsuario(
                    usuarioEditar.id,
                    usuario
                );

                alert(
                    "Usuario actualizado correctamente"
                );


            } else {

                await crearUsuario(usuario);

                alert(
                    "Usuario creado correctamente"
                );

            }


            onUsuarioCreado();


            setUsuario({

                nombreCompleto: "",
                email: "",
                password: "",
                rolId: ""

            });


            onClose();


        } catch(error) {

            console.error(error);

            alert(
                "Error al guardar usuario"
            );

        }

    };



    if (!isOpen) return null;



    return (

        <div className="user-modal-overlay">


            <div className="user-modal">


                <h2>

                    {
                        usuarioEditar
                        ? "Editar Usuario"
                        : "Nuevo Usuario"
                    }

                </h2>



                <input

                    type="text"

                    placeholder="Nombre completo"

                    value={
                        usuario.nombreCompleto
                    }

                    onChange={(e)=>
                        setUsuario({

                            ...usuario,

                            nombreCompleto:
                                e.target.value

                        })
                    }

                />



                <input

                    type="email"

                    placeholder="Correo"

                    value={
                        usuario.email
                    }

                    onChange={(e)=>
                        setUsuario({

                            ...usuario,

                            email:
                                e.target.value

                        })
                    }

                />



                <input

                    type="password"

                    placeholder="Contraseña"

                    value={
                        usuario.password
                    }

                    onChange={(e)=>
                        setUsuario({

                            ...usuario,

                            password:
                                e.target.value

                        })
                    }

                />



<select

    value={usuario.rolId}

    onChange={(e)=>
        setUsuario({

            ...usuario,

            rolId: e.target.value

        })
    }

>

    <option value="">
        Seleccionar rol
    </option>


    {
        roles.map((rol) => (

            <option
                key={rol.id}
                value={rol.id}
            >
                {rol.nombre}
            </option>

        ))
    }


</select>


                <div className="modal-actions">


                    <button
                        className="btn-cancel"
                        onClick={onClose}
                    >
                        Cancelar
                    </button>



                    <button
                        className="btn-primary"
                        onClick={guardarUsuario}
                    >
                        Guardar
                    </button>


                </div>



            </div>


        </div>

    );

}


export default UserModal;