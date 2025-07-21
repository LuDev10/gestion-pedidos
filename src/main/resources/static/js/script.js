let productosAgregados = [];
let productosDisponibles = [];

window.addEventListener("DOMContentLoaded", () => {
  const select = document.getElementById("productoSelect");
  const options = select.querySelectorAll("option");

  productosDisponibles = Array.from(options)
    .filter(opt => opt.value)
    .map(opt => {
      const [nombre, precioStr] = opt.textContent.split(" - $");
      return {
        id: parseInt(opt.value),
        nombre: nombre.trim(),
        precio: parseFloat(precioStr.trim())
      };
    });
});

function agregarProducto() {
  const select = document.getElementById("productoSelect");
  const cantidadInput = document.getElementById("cantidadInput");
  const tabla = document.getElementById("tablaProductos").querySelector("tbody");
  const lineasContainer = document.getElementById("lineasContainer");

  const productoId = parseInt(select.value);
  const cantidad = parseInt(cantidadInput.value);

  if (!productoId || !cantidad || cantidad <= 0) {
    alert("Debe seleccionar un producto y una cantidad válida.");
    return;
  }

  const producto = productosDisponibles.find(p => p.id === productoId);
  const subtotal = producto.precio * cantidad;

  const existente = productosAgregados.find(p => p.id === productoId);
  if (existente) {
    alert("Este producto ya ha sido agregado.");
    return;
  }

  productosAgregados.push({ id: productoId, nombre: producto.nombre, precio: producto.precio, cantidad, subtotal });

  const row = document.createElement("tr");
  row.innerHTML = `
    <td>${producto.nombre}</td>
    <td>${cantidad}</td>
    <td>$${subtotal.toFixed(2)}</td>
    <td><button class="btn btn-danger btn-sm" onclick="eliminarProducto(${productoId})">Eliminar</button></td>
  `;
  row.setAttribute("data-producto-id", productoId);
  tabla.appendChild(row);

  const index = productosAgregados.length - 1;

  const inputId = crearInput(`lineas[${index}].productoId`, productoId);
  const inputCantidad = crearInput(`lineas[${index}].cantidad`, cantidad);
  const inputSubtotal = crearInput(`lineas[${index}].subtotal`, subtotal);

  lineasContainer.appendChild(inputId);
  lineasContainer.appendChild(inputCantidad);
  lineasContainer.appendChild(inputSubtotal);

  select.value = "";
  cantidadInput.value = "";
}

function crearInput(name, value) {
  const input = document.createElement("input");
  input.type = "hidden";
  input.name = name;
  input.value = value;
  return input;
}

function eliminarProducto(id) {
  const row = document.querySelector(`tr[data-producto-id="${id}"]`);
  if (row) row.remove();

  productosAgregados = productosAgregados.filter(p => p.id !== id);

  const lineasContainer = document.getElementById("lineasContainer");
  lineasContainer.innerHTML = "";

  productosAgregados.forEach((producto, index) => {
    lineasContainer.appendChild(crearInput(`lineas[${index}].productoId`, producto.id));
    lineasContainer.appendChild(crearInput(`lineas[${index}].cantidad`, producto.cantidad));
    lineasContainer.appendChild(crearInput(`lineas[${index}].subtotal`, producto.subtotal));
  });
}
