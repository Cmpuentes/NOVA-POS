import { useState, useEffect } from 'react';
import { Routes, Route } from "react-router-dom";
import './App.css';

import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Products from "./pages/Products";
import Categories from "./pages/Categories";
import Taxes from "./pages/Taxes";
import Units from "./pages/Units";
import Users from "./pages/Users";
import Roles from "./pages/Roles";


function App() {


  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);



  useEffect(() => {

    const token = localStorage.getItem('accessToken');

    setIsAuthenticated(!!token);

    setLoading(false);

  }, []);




  if (loading) {

    return (

      <div 
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          height: '100vh'
        }}
      >

        Cargando...

      </div>

    );

  }





  return (

    <Routes>


      <Route 
        path="/" 
        element={
          isAuthenticated 
          ? <Dashboard /> 
          : <Login />
        } 
      />


      <Route 
        path="/login" 
        element={<Login />} 
      />



      <Route 
        path="/dashboard" 
        element={<Dashboard />} 
      />



      <Route 
        path="/productos" 
        element={<Products />} 
      />


      <Route 
        path="/categorias" 
        element={<Categories />} 
      />


      <Route 
        path="/impuestos" 
        element={<Taxes />} 
      />


      <Route 
        path="/unidades-medida" 
        element={<Units />} 
      />


      <Route 
        path="/usuarios" 
        element={<Users />} 
      />


      <Route 
        path="/roles" 
        element={<Roles />} 
      />


    </Routes>

  );

}


export default App;