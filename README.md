
# 📦 Gestor de Pedidos

Aplicación web desarrollada con **Java Spring Boot** y **Thymeleaf** que permite gestionar pedidos, productos y sus líneas asociadas de forma intuitiva. Ideal para aprender o extender funcionalidades CRUD en un entorno profesional.

---

## 🏗️ Estructura del Proyecto

```bash
src/
└── main/
    ├── java/
    │   └── com/proyecto_final/demogestion_pedidos/
    │       ├── controller/         # Controladores para API REST y vistas
    │       │   ├── WebController.java
    │       │   ├── PedidoController.java
    │       │   ├── ProductoController.java
    │       │   └── LineaPedidoController.java
    │       ├── dto/                # Objetos de transferencia de datos
    │       │   ├── PedidoDTO.java
    │       │   ├── ProductoDTO.java
    │       │   └── LineaPedidoDTO.java
    │       ├── service/            # Lógica de negocio
    │       │   ├── PedidoService.java
    │       │   ├── ProductoService.java
    │       │   └── LineaPedidoService.java
    │       ├── model/              # Entidades JPA
    │       ├── repository/         # Interfaces de acceso a datos
    │       │   ├── PedidoRepository.java
    │       │   ├── ProductoRepository.java
    │       │   └── LineaPedidoRepository.java
    │       └── config/             # DataLoader u otras configuraciones
    ├── resources/
    │   ├── static/
    │   │   ├── css/styles.css
    │   │   ├── js/script.js
    │   │   └── img/ (opcional)
    │   └── templates/              # Vistas Thymeleaf
    │       ├── index.html
    │       ├── nuevo-pedido.html
    │       ├── editar-pedido.html
    │       ├── editar-pedido-formulario.html
    │       ├── detalle.html
    │       ├── listar-pedidos.html
    │       ├── eliminar-pedido.html
    │       └── buscar-pedido.html
```

---

## 🚀 Funcionalidades

- **CRUD de Pedidos**: crear, visualizar, editar y eliminar pedidos.
- **Asociación de Productos a Pedidos** mediante líneas de pedido.
- **Interfaz HTML amigable** con formularios simples y validaciones.
- **Cálculo automático de subtotales y totales**.
- **Mensajes de éxito y error** en las vistas.
- **Diseño centralizado y responsive** con CSS.

---

## ✅ Buenas prácticas aplicadas

- 🧩 **Separación por capas**: Controller, Service, Repository y DTOs.
- 🧼 **Validaciones con anotaciones** como `@NotBlank` y `@NotNull`.
- 🔁 **Uso de DTOs** para evitar acoplamiento entre capa de presentación y base de datos.
- 🗃️ **Uso de `@Transactional`** en métodos críticos.
- 🧪 **Mensajes de resultado (exitoso o error)** en formularios.
- 🔄 **Redirección clara tras acciones** como edición o eliminación.
- 💬 **Confirmaciones por JavaScript** al borrar datos.
- 📦 **Organización modular** con carpetas coherentes y nombres semánticos.

---

## 🧑‍💻 Cómo ejecutar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tuusuario/gestor-pedidos.git
   cd gestor-pedidos
   ```

2. Abre el proyecto con tu IDE (IntelliJ, Eclipse, VS Code).

3. Ejecuta la aplicación:
   - Desde el IDE: ejecuta `DemogestionPedidosApplication.java`
   - Desde consola:
     ```bash
     ./mvnw spring-boot:run
     ```

4. Accede a la app en tu navegador:
   ```
   http://localhost:8080/inicio
   ```

---

## 📸 Capturas
<img width="1414" height="557" alt="Captura de pantalla 2025-07-21 a la(s) 8 05 48 p  m" src="https://github.com/user-attachments/assets/e2670b97-17a8-4d3b-a9b5-05523055c151" />
<img width="1408" height="638" alt="Captura de pantalla 2025-07-21 a la(s) 8 06 21 p  m" src="https://github.com/user-attachments/assets/13f5d865-0192-41d2-b591-8bc0de6a43b1" />
<img width="1394" height="703" alt="Captura de pantalla 2025-07-21 a la(s) 8 07 27 p  m" src="https://github.com/user-attachments/assets/5bebcccb-a55f-481d-9303-9c282eac63e9" />
<img width="598" height="615" alt="Captura de pantalla 2025-07-21 a la(s) 8 08 02 p  m" src="https://github.com/user-attachments/assets/98d823cd-9dbd-48ad-97d7-e4f2ad9379be" />
<img width="1391" height="775" alt="Captura de pantalla 2025-07-21 a la(s) 8 08 24 p  m" src="https://github.com/user-attachments/assets/615b930d-1b89-4c74-9b63-1fe07fffb2f3" />
<img width="1405" height="626" alt="Captura de pantalla 2025-07-21 a la(s) 8 09 12 p  m" src="https://github.com/user-attachments/assets/e9e5c283-24a5-4037-a002-697c904a6cf7" />

---

## 📌 Requisitos

- Java 17 o superior (recomendado: Amazon Corretto 21)
- Maven 3.8+
- Navegador moderno
- IDE con soporte para Spring Boot

---

## 🤝 Autor

**Luis Díaz**  
Desarrollador Backend | QA Engineer Automation 
📧 contacto: [diazldev@gmail.com, https://www.linkedin.com/in/luis-augusto-diaz-c/, https://github.com/LuDev10]
🚀 ¡Con ganas de seguir aprendiendo y mejorando!

---

