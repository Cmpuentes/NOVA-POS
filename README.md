# NOVA-POS
Sistema de Punto de Venta SaaS desarrollado por GESNNOVA
Moderno, offline-first, multi-tenant y con facturación electrónica integrada.

¿Qué es POS-NOVA?

POS-NOVA es un sistema de punto de venta diseñado para operar como servicio (SaaS) sobre infraestructura propia de GESNNOVA. Está construido para funcionar en cualquier tipo de comercio —panaderías, tiendas, farmacias, cafeterías— con capacidad de operar sin conexión a internet, sincronizando automáticamente cuando la red se restablece.
Su arquitectura garantiza que múltiples clientes convivan en el mismo servidor de forma completamente aislada (multi-tenant por schema), y que cada instalación pueda emitir facturación electrónica válida ante la DIAN a través del integrador tecnológico habilitado.

Características principales

Offline-first — el sistema opera sin interrupciones aunque no haya internet. Las ventas se sincronizan automáticamente al recuperar la conexión.
Multi-tenant — cada cliente tiene su propio schema aislado en PostgreSQL. Sus datos nunca se mezclan con los de otros.
Facturación electrónica — integración con proveedor tecnológico habilitado para emisión de facturas y tiquetes válidos ante la DIAN (CUFE, UBL 2.1).
Compatible con hardware POS — impresora térmica (ESC/POS vía Web USB/Serial), cajón monedero y lector de código de barras.
Instalable como app — la PWA se instala en el escritorio del dispositivo de caja y se comporta como una aplicación nativa.
Escalable — arquitectura modular preparada para incorporar control de inventario, devoluciones y compras sin rediseñar el núcleo.
Control de acceso por roles — cajeros, administradores y supervisores con permisos configurables.
Sesiones de caja — apertura y cierre de caja con cuadre de efectivo, compatible con múltiples cajeros por terminal.

Stack tecnológico

Backend: Java 17 + Spring Boot 3
Base de datos: PostgreSQL 15 (schema por tenant)
Autenticación: Spring Security + JWT
Frontend: React + Vite PWA Plugin
Offline storage: IndexedDB + Workbox (Background Sync)
Hardware POS: Web USB API / Web Serial API + ESC/POS
Facturación electrónica: Aliado (integrador habilitado DIAN)

Módulos del sistema

Fase 1 — Núcleo POS (actual)

 Modelo de datos diseñado
 Entidades JPA y configuración multi-tenant
 Autenticación JWT y control de roles
 Gestión de cajas y sesiones
 Catálogo de productos y categorías
 Módulo de ventas y facturación electrónica
 Integración hardware (impresora, cajón, lector)
 Modo offline con IndexedDB y Background Sync
 Panel de administración del negocio

Fase 2 — Módulos opcionales (futuro)

 Control de inventario por referencia
 Control de inventario serializado (lotes)
 Módulo de devoluciones y notas crédito
 Módulo de proveedores y órdenes de compra
 Integración automática con datáfonos
 Panel SaaS de gestión de tenants

 Requisitos del entorno de desarrollo

Java 17+
Maven 3.9+
PostgreSQL 15+
Node.js 20+
Google Chrome o Microsoft Edge (para pruebas del frontend)

Licencia

Desarrollado y mantenido por GESNNOVA.
Todos los derechos reservados © 2026.
