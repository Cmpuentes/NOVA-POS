function CategoryButton({ categoria, onClick }) {
    return (
        <button
            onClick={() => onClick(categoria.id)}
        >
            {categoria.nombre}
        </button>
    );
}

export default CategoryButton;