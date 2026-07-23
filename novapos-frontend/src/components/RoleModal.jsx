import {useEffect,useState} from "react";

import {
crearRol,
actualizarRol
} from "../services/rolService";


function RoleModal({
isOpen,
onClose,
rolEditar,
onRolGuardado
}){


const [rol,setRol]=useState({

nombre:"",
descripcion:""

});



useEffect(()=>{


if(rolEditar){

setRol({

nombre:rolEditar.nombre,

descripcion:rolEditar.descripcion

});


}else{

setRol({

nombre:"",

descripcion:""

});


}


},[rolEditar]);




const guardar=async()=>{


try{


if(rolEditar){


await actualizarRol(
rolEditar.id,
rol
);


alert(
"Rol actualizado"
);



}else{


await crearRol(rol);


alert(
"Rol creado"
);


}



onRolGuardado();

onClose();


}catch(error){

console.error(error);

alert(
"Error guardando rol"
);


}



};



if(!isOpen)
return null;



return(

<div className="modal">


<div className="modal-content">


<h2>

{
rolEditar
?
"Editar Rol"
:
"Nuevo Rol"
}

</h2>



<input

placeholder="Nombre"

value={rol.nombre}

onChange={(e)=>
setRol({

...rol,

nombre:e.target.value

})
}

/>



<textarea

placeholder="Descripción"

value={rol.descripcion}

onChange={(e)=>
setRol({

...rol,

descripcion:e.target.value

})
}

/>



<div>

<button onClick={onClose}>
Cancelar
</button>


<button onClick={guardar}>
Guardar
</button>

</div>


</div>


</div>

);


}


export default RoleModal;