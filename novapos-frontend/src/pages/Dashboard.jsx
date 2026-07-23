import { useEffect } from "react";
import Layout from "../components/layout/Layout";

function Dashboard() {

  useEffect(() => {

    const token = localStorage.getItem("accessToken");

    if (!token) {
      window.location.href = "/";
    }

  }, []);

  return (

    <Layout>

      <div className="dashboard-page">

        <div className="dashboard-content">

          {/* Por ahora se deja vacío */}

        </div>

      </div>

    </Layout>

  );

}

export default Dashboard;