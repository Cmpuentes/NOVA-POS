import "../styles/units.css";

function UnitCard({ unidad, onDelete, onEdit }) {

    return (
        <div className="unit-card">

            <div className="unit-info">

                <h3>
                    {unidad.nombre}
                </h3>

                <p>
                    Abreviatura: {unidad.abreviatura}
                </p>


                <div className="unit-actions">

                    <button
                        className="unit-edit-btn"
                        onClick={() => onEdit(unidad)}
                    >
                        Editar
                    </button>


                    <button
                        className="unit-delete-btn"
                        onClick={() => onDelete(unidad.id)}
                    >
                        Eliminar
                    </button>

                </div>

            </div>

        </div>
    );
}

export default UnitCard;