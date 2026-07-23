import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaShoppingCart, FaSignOutAlt } from "react-icons/fa";
import "../../styles/header.css";

function Header() {
  const navigate = useNavigate();

  const [userEmail, setUserEmail] = useState(null);
  const [userRole, setUserRole] = useState(null);
  const [currentTime, setCurrentTime] = useState("");

  useEffect(() => {
    setUserEmail(localStorage.getItem("userEmail"));
    setUserRole(localStorage.getItem("userRole"));
  }, []);

  useEffect(() => {
    const updateTime = () => {
      const now = new Date();
      const hours = now.getHours().toString().padStart(2, "0");
      const minutes = now.getMinutes().toString().padStart(2, "0");
      const seconds = now.getSeconds().toString().padStart(2, "0");

      setCurrentTime(`${hours}:${minutes}:${seconds}`);
    };

    updateTime();

    const interval = setInterval(updateTime, 1000);

    return () => clearInterval(interval);
  }, []);

  const handleLogout = async () => {
    const refreshToken = localStorage.getItem("refreshToken");

    try {
      const response = await fetch("http://localhost:8080/api/auth/logout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Refresh-Token": refreshToken || "",
        },
      });

      if (!response.ok) {
        throw new Error("No se pudo cerrar sesión en el servidor");
      }

localStorage.clear();

window.location.replace("/login");
    } catch (error) {
      console.error("Error cerrando sesión:", error);
      alert("No se pudo cerrar sesión correctamente");
    }
  };

  return (
    <header className="header">
      <div className="sidebar-logo">
        <div className="logo-circle">
          <FaShoppingCart />
        </div>

        <div className="logo-text">
          <span className="nova">NOVA</span>
          <span className="pos">POS</span>
        </div>
      </div>

      <div className="header-user">

        <div className="header-clock">
          {currentTime}
        </div>

        <div>
          <strong>{userEmail}</strong>
          <br />
          <span>{userRole}</span>
        </div>

        <button className="logout-button" onClick={handleLogout}>
          <FaSignOutAlt />
          Salir
        </button>
      </div>
    </header>
  );
}

export default Header;