// Variables globales
let computadores = [];
let computadorToDelete = null;

// URL base de la API
const API_BASE = '/api/computadores';

// Cargar computadores al iniciar la página
document.addEventListener('DOMContentLoaded', function() {
    loadAllComputadores();
});

// Función para cargar todos los computadores
async function loadAllComputadores() {
    try {
        const response = await fetch(API_BASE);
        if (response.ok) {
            computadores = await response.json();
            renderTable(computadores);
        } else {
            showAlert('Error al cargar los computadores', 'danger');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Error de conexión', 'danger');
    }
}

// Función para buscar computadores
async function searchComputadores() {
    const marca = document.getElementById('searchMarca').value.trim();
    const modelo = document.getElementById('searchModelo').value.trim();
    const precio = document.getElementById('searchPrecio').value.trim();

    let url = API_BASE + '/buscar?';
    const params = [];

    if (marca) params.push(`marca=${encodeURIComponent(marca)}`);
    if (modelo) params.push(`modelo=${encodeURIComponent(modelo)}`);
    if (precio) params.push(`precioMax=${parseFloat(precio)}`);

    if (params.length === 0) {
        showAlert('Por favor ingresa al menos un criterio de búsqueda', 'warning');
        return;
    }

    url += params.join('&');

    try {
        const response = await fetch(url);
        if (response.ok) {
            computadores = await response.json();
            renderTable(computadores);
            showAlert(`Se encontraron ${computadores.length} computadores`, 'success');
        } else {
            showAlert('Error en la búsqueda', 'danger');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Error de conexión', 'danger');
    }
}

// Función para renderizar la tabla
function renderTable(computadoresData) {
    const tbody = document.getElementById('computadoresTable');
    tbody.innerHTML = '';

    if (computadoresData.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="10" class="text-center text-muted">
                    <i class="fas fa-info-circle"></i> No se encontraron computadores
                </td>
            </tr>
        `;
        return;
    }

    computadoresData.forEach(computador => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${computador.id}</td>
            <td>${computador.marca}</td>
            <td>${computador.modelo}</td>
            <td>${computador.procesador}</td>
            <td>${computador.memoriaRam}</td>
            <td>${computador.almacenamiento}</td>
            <td>$${computador.precio.toLocaleString('es-CO')} COP</td>
            <td>
                <span class="badge ${computador.stock > 10 ? 'bg-success' : computador.stock > 5 ? 'bg-warning' : 'bg-danger'}">
                    ${computador.stock}
                </span>
            </td>
            <td>${new Date(computador.fechaCreacion).toLocaleDateString()}</td>
            <td>
                <button class="btn btn-sm btn-primary btn-action" onclick="editComputador(${computador.id})" title="Editar">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-danger btn-action" onclick="deleteComputador(${computador.id})" title="Eliminar">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Función para mostrar el modal de agregar
function showAddModal() {
    document.getElementById('modalTitle').textContent = 'Nuevo Computador';
    document.getElementById('computadorForm').reset();
    document.getElementById('computadorId').value = '';
    
    const modal = new bootstrap.Modal(document.getElementById('computadorModal'));
    modal.show();
}

// Función para editar computador
async function editComputador(id) {
    try {
        const response = await fetch(`${API_BASE}/${id}`);
        if (response.ok) {
            const computador = await response.json();
            
            // Llenar el formulario
            document.getElementById('computadorId').value = computador.id;
            document.getElementById('marca').value = computador.marca;
            document.getElementById('modelo').value = computador.modelo;
            document.getElementById('procesador').value = computador.procesador;
            document.getElementById('memoriaRam').value = computador.memoriaRam;
            document.getElementById('almacenamiento').value = computador.almacenamiento;
            document.getElementById('precio').value = computador.precio;
            document.getElementById('stock').value = computador.stock;
            
            document.getElementById('modalTitle').textContent = 'Editar Computador';
            
            const modal = new bootstrap.Modal(document.getElementById('computadorModal'));
            modal.show();
        } else {
            showAlert('Error al cargar el computador', 'danger');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Error de conexión', 'danger');
    }
}

// Función para guardar computador
async function saveComputador() {
    const form = document.getElementById('computadorForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const computadorData = {
        marca: document.getElementById('marca').value.trim(),
        modelo: document.getElementById('modelo').value.trim(),
        procesador: document.getElementById('procesador').value.trim(),
        memoriaRam: document.getElementById('memoriaRam').value.trim(),
        almacenamiento: document.getElementById('almacenamiento').value.trim(),
        precio: parseFloat(document.getElementById('precio').value),
        stock: parseInt(document.getElementById('stock').value)
    };

    const id = document.getElementById('computadorId').value;
    const isEdit = id !== '';

    try {
        const url = isEdit ? `${API_BASE}/${id}` : API_BASE;
        const method = isEdit ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(computadorData)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('computadorModal'));
            modal.hide();
            
            showAlert(isEdit ? 'Computador actualizado exitosamente' : 'Computador creado exitosamente', 'success');
            loadAllComputadores();
        } else {
            const errorData = await response.json();
            showAlert(errorData.message || 'Error al guardar el computador', 'danger');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Error de conexión', 'danger');
    }
}

// Función para eliminar computador
function deleteComputador(id) {
    computadorToDelete = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}

// Función para confirmar eliminación
async function confirmDelete() {
    if (!computadorToDelete) return;

    try {
        const response = await fetch(`${API_BASE}/${computadorToDelete}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
            modal.hide();
            
            showAlert('Computador eliminado exitosamente', 'success');
            loadAllComputadores();
        } else {
            showAlert('Error al eliminar el computador', 'danger');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Error de conexión', 'danger');
    } finally {
        computadorToDelete = null;
    }
}

// Función para mostrar alertas
function showAlert(message, type) {
    // Crear el elemento de alerta
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    alertDiv.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    // Agregar al body
    document.body.appendChild(alertDiv);

    // Remover después de 5 segundos
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.parentNode.removeChild(alertDiv);
        }
    }, 5000);
}

// Función para limpiar búsqueda
function clearSearch() {
    document.getElementById('searchMarca').value = '';
    document.getElementById('searchModelo').value = '';
    document.getElementById('searchPrecio').value = '';
    loadAllComputadores();
}
