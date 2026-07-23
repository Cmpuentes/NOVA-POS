import "../styles/taxes.css";

function TaxCard({ impuesto, onEdit, onDelete }) {

    return (
        <div className="tax-card">

            <div className="tax-info">

                <h3>
                    {impuesto.nombre}
                </h3>

                <p>
                    Porcentaje: {impuesto.porcentaje}%
                </p>

                <span>
                    Estado: {impuesto.activo ? "Activo" : "Inactivo"}
                </span>

                <div className="tax-actions">

                    <button
                        className="tax-edit-btn"
                        onClick={() => onEdit(impuesto)}
                    >
                        Editar
                    </button>

<button
    className="tax-delete-btn"
    onClick={() => onDelete(impuesto.id)}
>
    Desactivar
</button>

                </div>

            </div>

        </div>
    );
}

export default TaxCard;