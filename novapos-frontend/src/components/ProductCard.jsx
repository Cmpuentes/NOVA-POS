import "../styles/products.css";

function ProductCard({ 
    producto, 
    onDelete,
    onEdit
}) {
    return (
        <div className="product-card">

            {producto.imagenUrl ? (
                <img 
                    src={producto.imagenUrl}
                    alt={producto.nombre}
                    className="product-image"
                />
            ) : (
                <div className="product-placeholder">
                    Sin imagen
                </div>
            )}

            <div className="product-info">

                <h3>{producto.nombre}</h3>

                <p>
                    {producto.descripcion || "Sin descripción"}
                </p>

                <span>
                    Código: {producto.codigo}
                </span>

                <span>
                    Categoría: {producto.categoriaNombre}
                </span>

                <span>
                    Impuesto: {producto.impuestoNombre} ({producto.impuestoPorcentaje}%)
                </span>

                <span>
                    Unidad: {producto.unidadMedidaNombre} ({producto.unidadMedidaAbreviatura})
                </span>

                <strong>
                    ${producto.precioBase}
                </strong>
                <div className="product-actions">

<button 
    className="edit-btn"
    onClick={() => onEdit(producto)}
>
    Editar
</button>

  <button 
    className="delete-btn"
    onClick={() => onDelete(producto.id)}
>
    Desactivar
</button>

</div>

            </div>

        </div>
    );
}

export default ProductCard;