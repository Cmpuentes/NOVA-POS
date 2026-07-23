function RoleCard({
    rol,
    onEdit,
    onDelete
}){


return(

<div className="role-card">


<div className="role-info">


<h3>
{rol.nombre}
</h3>


<p>
{rol.descripcion || "Sin descripción"}
</p>


<span>

Estado:

{
rol.activo
?
" Activo"
:
" Inactivo"
}

</span>



<div className="role-actions">


<button
className="role-edit-btn"
onClick={()=>onEdit(rol)}
>
Editar
</button>



<button
className="role-delete-btn"
onClick={()=>onDelete(rol.id)}
>
Desactivar
</button>


</div>


</div>


</div>

);


}


export default RoleCard;