import { useEffect, useState } from "react";
import "../styles/taxes.css";
import {
    crearImpuesto,
    actualizarImpuesto
} from "../services/taxService";

function TaxModal({
    isOpen,
    onClose,
    onImpuestoCreado,
    impuestoEditar
}) {

const [impuesto, setImpuesto] = useState({
    nombre: "",
    porcentaje: "",
    codigoDian: ""
});
    useEffect(() => {

        if (impuestoEditar) {

            setImpuesto({
    nombre: impuestoEditar.nombre,
    porcentaje: impuestoEditar.porcentaje,
    codigoDian: impuestoEditar.codigoDian
});

        } else {

            setImpuesto({
    nombre: "",
    porcentaje: "",
    codigoDian: ""
});

        }

    }, [impuestoEditar]);

    const guardarImpuesto = async () => {

        try {

            if (impuestoEditar) {

                await actualizarImpuesto(
                    impuestoEditar.id,
                    impuesto
                );

                alert("Impuesto actualizado correctamente");

            } else {

                await crearImpuesto(impuesto);

                alert("Impuesto creado correctamente");

            }

            onImpuestoCreado();

setImpuesto({
    nombre: "",
    porcentaje: "",
    codigoDian: ""
});

            onClose();

        } catch (error) {

            console.error(error);

            alert("Error al guardar el impuesto");

        }

    };

    if (!isOpen) return null;

    return (

        <div className="tax-modal-overlay">

            <div className="tax-modal-content">

                <h2>
                    {impuestoEditar
                        ? "Editar Impuesto"
                        : "Nuevo Impuesto"}
                </h2>

                <input
                    type="text"
                    placeholder="Nombre"
                    value={impuesto.nombre}
                    onChange={(e) =>
                        setImpuesto({
                            ...impuesto,
                            nombre: e.target.value
                        })
                    }
                />

                <input
                    type="number"
                    placeholder="Porcentaje"
                    value={impuesto.porcentaje}
                    onChange={(e) =>
                        setImpuesto({
                            ...impuesto,
                            porcentaje: e.target.value
                        })
                    }
                />
                <input
    type="text"
    placeholder="Código DIAN"
    value={impuesto.codigoDian}
    onChange={(e) =>
        setImpuesto({
            ...impuesto,
            codigoDian: e.target.value
        })
    }
/>

                <div className="tax-modal-actions">

                    <button onClick={onClose}>
                        Cancelar
                    </button>

                    <button onClick={guardarImpuesto}>
                        Guardar
                    </button>

                </div>

            </div>

        </div>

    );
}

export default TaxModal;