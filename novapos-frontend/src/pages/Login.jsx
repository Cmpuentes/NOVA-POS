import { useState } from 'react';
import '../styles/login.css';

const API_BASE_URL = 'http://localhost:8080';

const normalizeLoginPayload = (email, password) => ({
  email: email.trim(),
  password: password.trim(),
});

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage('');
    setSuccessMessage('');
    setLoading(true);

    try {
      const payload = normalizeLoginPayload(email, password);

      if (!payload.email || !payload.password) {
        throw new Error('Ingresa usuario y contraseña');
      }

      const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      let data = null;
      const text = await response.text();
      if (text) {
        try {
          data = JSON.parse(text);
        } catch {
          data = { message: text };
        }
      }

      if (!response.ok) {
        const backendMessage = data?.message || data?.error || 'No se pudo iniciar sesión';
        throw new Error(backendMessage);
      }

      localStorage.setItem('accessToken', data?.accessToken || '');
      localStorage.setItem('refreshToken', data?.refreshToken || '');
      localStorage.setItem('userEmail', data?.email || payload.email);
      localStorage.setItem('userRole', data?.rol || '');
      localStorage.setItem('tenantId', data?.tenantId || '');

      setSuccessMessage(data?.message || 'Inicio de sesión correcto');

      // Redireccionar al dashboard después de 1 segundo
      setTimeout(() => {
        window.location.href = '/dashboard';
      }, 1000);
    } catch (error) {
      const err = error;
      setErrorMessage(err.message || 'No se pudo conectar con el servidor');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      {/* Left - Branding */}
      <div className="login-left">
        <div className="decorative-dots decorative-dots--top-left"></div>
        <div className="decorative-dots decorative-dots--top-right"></div>
        <div className="corner-wave corner-wave--top-right"></div>
        <div className="corner-wave corner-wave--bottom-left"></div>
        <div className="wave-shape wave-shape--soft"></div>
        <div className="wave-shape wave-shape--overlay"></div>
        <div className="animated-bg"></div>
        <div className="login-branding">
          <div className="logo-icon-wrapper">
            <svg width="130" height="120" viewBox="0 0 130 120" fill="none" xmlns="http://www.w3.org/2000/svg" className="main-cart-svg">
              {/* Speed lines */}
              <line x1="8" y1="38" x2="28" y2="38" stroke="white" strokeWidth="3.5" strokeLinecap="round" opacity="0.7"/>
              <line x1="4" y1="50" x2="22" y2="50" stroke="white" strokeWidth="3.5" strokeLinecap="round" opacity="0.5"/>
              <line x1="8" y1="62" x2="24" y2="62" stroke="white" strokeWidth="3.5" strokeLinecap="round" opacity="0.35"/>
              {/* Green item boxes on top of cart */}
              <rect x="56" y="14" width="14" height="12" rx="2" fill="#4ade80"/>
              <rect x="74" y="14" width="14" height="12" rx="2" fill="#4ade80"/>
              {/* Cart handle/pole */}
              <line x1="30" y1="30" x2="42" y2="30" stroke="white" strokeWidth="4" strokeLinecap="round"/>
              <line x1="30" y1="30" x2="30" y2="22" stroke="white" strokeWidth="4" strokeLinecap="round"/>
              {/* Cart body */}
              <path d="M42 30 L48 72 H102 L112 30 Z" fill="white" opacity="0.15" stroke="white" strokeWidth="4" strokeLinecap="round" strokeLinejoin="round"/>
              {/* Cart inner lines */}
              <line x1="50" y1="46" x2="110" y2="46" stroke="white" strokeWidth="2.5" strokeLinecap="round" opacity="0.5"/>
              <line x1="52" y1="59" x2="108" y2="59" stroke="white" strokeWidth="2.5" strokeLinecap="round" opacity="0.5"/>
              {/* Wheels */}
              <circle cx="60" cy="88" r="10" fill="white"/>
              <circle cx="60" cy="88" r="4.5" fill="#1565c0"/>
              <circle cx="95" cy="88" r="10" fill="white"/>
              <circle cx="95" cy="88" r="4.5" fill="#1565c0"/>
            </svg>
          </div>

          <div className="logo-heading">
            <h1 className="logo-text-new">
              <span className="nova-text-new">NOVA</span><span className="pos-text-new">POS</span>
            </h1>
            <span className="logo-underline-new"></span>
          </div>
          <p className="logo-subtitle">Tu punto de venta, siempre contigo.</p>

          <div className="features">
            <div className="feature">
              <div className="feature-icon">
                {/* Lightning bolt - fast sales */}
                <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
                  <path d="M13 2L4.5 13.5H11L10 22L19.5 10.5H13L13 2Z" fill="white" stroke="white" strokeWidth="0.5" strokeLinejoin="round"/>
                </svg>
              </div>
              <div className="feature-content">
                <h3>Ventas rápidas y eficientes</h3>
                <p>Agiliza tu negocio en cada transacción.</p>
              </div>
            </div>

            <div className="feature">
              <div className="feature-icon">
                {/* Bar chart - smart management */}
                <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
                  <rect x="3" y="12" width="4" height="9" rx="1" fill="white"/>
                  <rect x="10" y="7" width="4" height="14" rx="1" fill="white"/>
                  <rect x="17" y="3" width="4" height="18" rx="1" fill="white"/>
                </svg>
              </div>
              <div className="feature-content">
                <h3>Gestión inteligente</h3>
                <p>Controla productos, inventario y reportes.</p>
              </div>
            </div>

            <div className="feature">
              <div className="feature-icon">
                {/* Wifi/signal - online & offline */}
                <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
                  <path d="M5 12.5C7.5 10 10.5 8.5 12 8.5C13.5 8.5 16.5 10 19 12.5" stroke="white" strokeWidth="2" strokeLinecap="round" fill="none"/>
                  <path d="M2 9C5.5 5.5 9.5 3.5 12 3.5C14.5 3.5 18.5 5.5 22 9" stroke="white" strokeWidth="2" strokeLinecap="round" fill="none"/>
                  <path d="M8 16C9.5 14.5 10.8 13.5 12 13.5C13.2 13.5 14.5 14.5 16 16" stroke="white" strokeWidth="2" strokeLinecap="round" fill="none"/>
                  <circle cx="12" cy="20" r="1.5" fill="white"/>
                </svg>
              </div>
              <div className="feature-content">
                <h3>Funciona online y offline</h3>
                <p>Tu negocio nunca se detiene.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Right - Form */}
      <div className="login-right">
        <div className="language-selector"></div>

        <div className="login-form-wrapper">
          <div className="login-header">
            <div className="user-icon">
              <svg width="36" height="36" viewBox="0 0 36 36" fill="none">
                <circle cx="18" cy="12" r="6" stroke="currentColor" strokeWidth="2"/>
                <path d="M6 28 Q6 22 18 22 Q30 22 30 28" stroke="currentColor" strokeWidth="2" fill="none" strokeLinecap="round"/>
              </svg>
            </div>
            <h2>Bienvenido de nuevo</h2>
            <p>Inicia sesión para continuar</p>
          </div>

          <form onSubmit={handleLogin} className="login-form">
            <div className="form-group">
              <label>Usuario o correo electrónico</label>
              <div className="input-wrapper">
                <svg className="input-icon" width="18" height="18" viewBox="0 0 18 18" fill="none">
                  <circle cx="9" cy="6" r="3" stroke="currentColor" strokeWidth="1.5"/>
                  <path d="M3 14 Q3 11 9 11 Q15 11 15 14" stroke="currentColor" strokeWidth="1.5" fill="none"/>
                </svg>
                <input
                  type="text"
                  placeholder="Ingresa tu usuario o correo"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="form-input"
                />
              </div>
            </div>

            <div className="form-group">
              <label>Contraseña</label>
              <div className="password-wrapper">
                <svg className="input-icon" width="18" height="18" viewBox="0 0 18 18" fill="none">
                  <rect x="3" y="8" width="12" height="7" rx="1" stroke="currentColor" strokeWidth="1.5"/>
                  <path d="M6 8 V5 Q6 3 9 3 Q12 3 12 5 V8" stroke="currentColor" strokeWidth="1.5" fill="none"/>
                  <circle cx="9" cy="12" r="1" fill="currentColor"/>
                </svg>
                <input
                  type={showPassword ? 'text' : 'password'}
                  placeholder="Ingresa tu contraseña"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="form-input"
                />
                <button
                  type="button"
                  className="password-toggle"
                  onClick={() => setShowPassword(!showPassword)}
                  aria-label={showPassword ? 'Ocultar contraseña' : 'Mostrar contraseña'}
                >
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                    {showPassword ? (
                      <>
                        <circle cx="12" cy="12" r="3" stroke="currentColor" strokeWidth="2"/>
                        <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8" stroke="currentColor" strokeWidth="2" fill="none"/>
                      </>
                    ) : (
                      <>
                        <path d="M1 1 L23 23" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
                        <path d="M9.88 9.88C9.35 10.5 9 11.28 9 12C9 13.66 10.34 15 12 15C12.72 15 13.5 14.65 14.12 14.12" stroke="currentColor" strokeWidth="2" strokeLinecap="round" fill="none"/>
                        <path d="M12 5C7 5 2.73 8.11 1 12.46M12 19C17 19 21.27 15.89 23 11.54" stroke="currentColor" strokeWidth="2" strokeLinecap="round" fill="none"/>
                      </>
                    )}
                  </svg>
                </button>
              </div>
              <a href="#" className="forgot-password">¿Olvidaste tu contraseña?</a>
            </div>

            {errorMessage ? (
              <p className="error-message">{errorMessage}</p>
            ) : null}
            {successMessage ? (
              <p className="success-message">{successMessage}</p>
            ) : null}

            <button type="submit" className="login-button" disabled={loading}>
              <svg width="16" height="16" viewBox="0 0 16 16" fill="none" className="button-icon">
                <rect x="3" y="6" width="10" height="8" rx="0.5" stroke="white" strokeWidth="1.2"/>
                <path d="M5 6 V4 Q5 2 8 2 Q11 2 11 4 V6" stroke="white" strokeWidth="1.2" fill="none"/>
              </svg>
              {loading ? 'Ingresando...' : 'Iniciar sesión'}
            </button>
          </form>

          <div className="footer">
            <p>© 2025 <span className="novapos-text">NOVAPOS</span>. Todos los derechos reservados.</p>
          </div>
        </div>
      </div>
    </div>
  );
}
