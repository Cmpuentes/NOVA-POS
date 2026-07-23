function UserCard({
    usuario,
    onEdit,
    onDelete
}) {

    return (

        <div className="user-card">


            <h3>
                {usuario.nombreCompleto}
            </h3>


            <p>
                Email: {usuario.email}
            </p>


            <p>
                Rol: {usuario.rolNombre}
            </p>


            <p>
                Estado:{" "}

                {
                    usuario.activo
                        ? "Activo"
                        : "Inactivo"
                }

            </p>



            <div className="user-actions">


                <button
                    className="btn-edit"
                    onClick={() => onEdit(usuario)}
                >
                    Editar
                </button>



                <button
                    className="btn-delete"
                    onClick={() => onDelete(usuario.id)}
                >
                    Desactivar
                </button>


            </div>


        </div>

    );

}


export default UserCard;