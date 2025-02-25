# Documentación de la Solución de Gestión de Clientes

## 1. Introducción y Descripción General

**Contexto y Problema:**  
La empresa enfrenta dificultades para agregar, actualizar, eliminar y consultar información de clientes de forma rápida y segura. La necesidad de contar con un sistema que gestione eficientemente estos datos motivó el desarrollo de esta solución.

**Solución Propuesta:**  
Se desarrolló un servicio RESTful utilizando Java y el framework Quarkus, que permite realizar operaciones CRUD sobre una lista de clientes. La solución también se integra con el API de [restcountries.com](https://restcountries.com) para obtener el gentilicio basado en el código ISO del país, garantizando así la validez y confiabilidad de los datos.

---

## 2. Requisitos del Sistema

### Requisitos Funcionales
- **Creación de Cliente:** Permitir la inserción de nuevos registros de clientes.
- **Consulta de Clientes:**
   - Obtener todos los clientes existentes.
   - Filtrar clientes por país.
   - Consultar un cliente específico por su identificador.
- **Actualización de Cliente:** Modificar únicamente los campos de correo, dirección, teléfono y país.
- **Eliminación de Cliente:** Remover un cliente mediante su identificador.

### Requisitos No Funcionales
- **Tecnología y Framework:** Uso de Java y Quarkus.
- **Persistencia de Datos:** Utilización de una base de datos para almacenar los clientes.
- **Intercambio de Información:** Comunicación en formato JSON.
- **Pruebas Unitarias:** Implementación de pruebas para garantizar el correcto funcionamiento de las funcionalidades principales.
- **Buenas Prácticas:** Nomenclatura, estructura de proyecto y manejo de excepciones.
- **Integración Externa:** Uso del API de [restcountries.com](https://restcountries.com) para obtener el gentilicio.
- **Documentación y Diagramas:** Se incluirán diagramas que ilustren la arquitectura y la interacción entre componentes.

---

## 3. Arquitectura y Estructura del Proyecto

### Capas de la Aplicación
- **Controladores REST:**
   - Gestionan la recepción y respuesta de solicitudes HTTP.
   - Ejemplo: Endpoints para crear, consultar, actualizar y eliminar clientes.

- **Servicios:**
   - Contienen la lógica de negocio necesaria para procesar las operaciones CRUD.

- **Repositorios/DAO:**
   - Encargados de la comunicación con la base de datos, asegurando la persistencia y recuperación de los datos.

- **Integraciones Externas:**
   - Módulo encargado de comunicarse con el API de restcountries.com para obtener información adicional (gentilicio) basada en el código de país.

### Diagrama de Componentes

```mermaid
graph LR
    A[Cliente REST Controller] --> B[Cliente Service]
    B --> C[Cliente Repository]
    B --> D[RestCountries Integration]
