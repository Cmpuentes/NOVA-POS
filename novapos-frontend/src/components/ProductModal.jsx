import { useEffect, useState } from "react";
import {
    crearProducto,
    actualizarProducto
} from "../services/productService";
import "../styles/products.css";

function ProductModal({
    isOpen,
    onClose,
    categorias,
    impuestos,
    unidades,
    onProductoCreado,
    productoEditar
}) {

    const [producto, setProducto] = useState({

        nombre: "",
        descripcion: "",
        codigo: "",
        precioBase: "",
        imagenUrl: "",
        categoriaId: "",
        impuestoId: "",
        unidadMedidaId: ""

    });



    const limpiarFormulario = () => {

        setProducto({

            nombre: "",
            descripcion: "",
            codigo: "",
            precioBase: "",
            imagenUrl: "",
            categoriaId: "",
            impuestoId: "",
            unidadMedidaId: ""

        });

    };





    const guardarProducto = async () => {

        if (
            !producto.nombre ||
            !producto.codigo ||
            !producto.precioBase ||
            !producto.categoriaId ||
            !producto.impuestoId ||
            !producto.unidadMedidaId
        ) {

            alert("Completa todos los campos obligatorios");

            return;

        }



        try {

            if (productoEditar) {

                await actualizarProducto(
                    productoEditar.id,
                    producto
                );

            } else {

                await crearProducto(producto);

            }


            limpiarFormulario();

            onProductoCreado();

            onClose();

        } catch (error) {

            console.error("Error guardando producto:", error);

            alert("Error al guardar producto");

        }

    };





    useEffect(() => {

        if (productoEditar) {

            setProducto({

                nombre: productoEditar.nombre,
                descripcion: productoEditar.descripcion || "",
                codigo: productoEditar.codigo,
                precioBase: productoEditar.precioBase,
                imagenUrl: productoEditar.imagenUrl || "",
                categoriaId: productoEditar.categoriaId,
                impuestoId: productoEditar.impuestoId,
                unidadMedidaId: productoEditar.unidadMedidaId

            });

        } else {

            limpiarFormulario();

        }

    }, [productoEditar]);





    if (!isOpen) return null;





    return (

        <div className="modal-overlay">

            <div className="product-modal">

                <div className="modal-header">

                    <h2>

                        {
                            productoEditar
                                ? "Editar Producto"
                                : "Nuevo Producto"
                        }

                    </h2>

                    <button onClick={onClose}>
                        ✕
                    </button>

                </div>





                <div className="modal-body">

                    <label>

                        Nombre

                        <input
                            type="text"
                            value={producto.nombre}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    nombre: e.target.value
                                })
                            }
                        />

                    </label>





                    <label>

                        Código

                        <input
                            type="text"
                            value={producto.codigo}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    codigo: e.target.value
                                })
                            }
                        />

                    </label>





                    <label>

                        Precio base

                        <input
                            type="number"
                            value={producto.precioBase}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    precioBase: e.target.value
                                })
                            }
                        />

                    </label>





                    <label>

                        Descripción

                        <textarea
                            value={producto.descripcion}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    descripcion: e.target.value
                                })
                            }
                        />

                    </label>





                    <label>

                        URL de la imagen

                        <input
                            type="text"
                            placeholder="https://..."
                            value={producto.imagenUrl}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    imagenUrl: e.target.value
                                })
                            }
                        />

                    </label>





                    {
                        producto.imagenUrl && (

                            <img
                                src={producto.imagenUrl}
                                alt="Vista previa"
                                className="image-preview"
                                onError={(e) => {
                                    e.target.style.display = "none";
                                }}
                            />

                        )
                    }






                    <label>

                        Categoría

                        <select
                            value={producto.categoriaId}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    categoriaId: e.target.value
                                })
                            }
                        >

                            <option value="">
                                Seleccione una categoría
                            </option>

                            {
                                categorias?.map((categoria) => (

                                    <option
                                        key={categoria.id}
                                        value={categoria.id}
                                    >

                                        {categoria.nombre}

                                    </option>

                                ))
                            }

                        </select>

                    </label>






                    <label>

                        Impuesto

                        <select
                            value={producto.impuestoId}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    impuestoId: e.target.value
                                })
                            }
                        >

                            <option value="">
                                Seleccione un impuesto
                            </option>

                            {
                                impuestos?.map((impuesto) => (

                                    <option
                                        key={impuesto.id}
                                        value={impuesto.id}
                                    >

                                        {impuesto.nombre} ({impuesto.porcentaje}%)

                                    </option>

                                ))
                            }

                        </select>

                    </label>






                    <label>

                        Unidad de medida

                        <select
                            value={producto.unidadMedidaId}
                            onChange={(e) =>
                                setProducto({
                                    ...producto,
                                    unidadMedidaId: e.target.value
                                })
                            }
                        >

                            <option value="">
                                Seleccione una unidad
                            </option>

                            {
                                unidades?.map((unidad) => (

                                    <option
                                        key={unidad.id}
                                        value={unidad.id}
                                    >

                                        {unidad.nombre} ({unidad.abreviatura})

                                    </option>

                                ))
                            }

                        </select>

                    </label>






                    <button
                        className="save-product-btn"
                        onClick={guardarProducto}
                    >

                        {
                            productoEditar
                                ? "Actualizar Producto"
                                : "Guardar Producto"
                        }

                    </button>

                </div>

            </div>

        </div>

    );

}

export default ProductModal;