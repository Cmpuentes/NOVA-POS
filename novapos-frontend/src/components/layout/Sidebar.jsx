import { Link, useLocation } from "react-router-dom";
import "../../styles/sidebar.css";
import {
  FaHome,
  FaBox,
  FaTags,
  FaRuler,
  FaUsers,
  FaUserShield,
  FaMoneyBill,
  FaCog,
  FaSignOutAlt
} from "react-icons/fa";

function Sidebar() {

  const location = useLocation();


  const menuItems = [

    {
      name: "Inicio",
      path: "/dashboard",
      icon: <FaHome />
    },

    {
      name: "Productos",
      path: "/productos",
      icon: <FaBox />
    },

    {
      name: "Categorías",
      path: "/categorias",
      icon: <FaTags />
    },

    {
      name: "Unidades",
      path: "/unidades-medida",
      icon: <FaRuler />
    },

    {
      name: "Usuarios",
      path: "/usuarios",
      icon: <FaUsers />
    },

    {
      name: "Roles",
      path: "/roles",
      icon: <FaUserShield />
    },

    {
      name: "Impuestos",
      path: "/impuestos",
      icon: <FaMoneyBill />
    }

  ];


  return (

    <aside className="sidebar">




      <nav className="sidebar-menu">


        {
          menuItems.map((item)=>(

            <Link

              key={item.path}

              to={item.path}

              className={
                location.pathname === item.path
                ? "menu-item active"
                : "menu-item"
              }

            >

              <span className="menu-icon">
                {item.icon}
              </span>


              <span>
                {item.name}
              </span>


            </Link>


          ))
        }


      </nav>



      <div className="sidebar-bottom">


        <Link
          className="menu-item settings"
          to="/configuracion"
        >

          <FaCog />

          Configuración

        </Link>

        


      </div>

        

    </aside>

  );

}


export default Sidebar;