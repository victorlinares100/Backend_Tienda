package com.example.elarayax.naves;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.elarayax.naves.dto.CarritoRequest;
import com.example.elarayax.naves.dto.CarritoItemRequest;
import com.example.elarayax.naves.model.Comprobante;
import com.example.elarayax.naves.model.DetalleComprobante;
import com.example.elarayax.naves.model.Estado;
import com.example.elarayax.naves.model.MetodoPago;
import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.model.Usuario;
import com.example.elarayax.naves.repository.ComprobanteRepository;
import com.example.elarayax.naves.repository.DetalleComprobanteRepository;
import com.example.elarayax.naves.service.ComprobanteService;
import com.example.elarayax.naves.service.EstadoService;
import com.example.elarayax.naves.service.MetodoPagoService;
import com.example.elarayax.naves.service.ProductoService;
import com.example.elarayax.naves.service.UsuarioService;

class ComprobanteServiceTest {

    @Mock
    private ComprobanteRepository comprobanteRepository;

    @Mock
    private DetalleComprobanteRepository detalleRepo;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EstadoService estadoService;

    @Mock
    private MetodoPagoService metodoPagoService;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ComprobanteService comprobanteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearComprobanteDesdeCarrito_ok_calculaTotalYGuarda() {

        // Usuario, estado y método
        Usuario usuario = new Usuario();
        Estado estado = new Estado();
        MetodoPago metodo = new MetodoPago();

        when(usuarioService.findById(1L)).thenReturn(usuario);
        when(estadoService.findById(1L)).thenReturn(estado);
        when(metodoPagoService.findById(1L)).thenReturn(metodo);

        // Producto
        Producto producto = new Producto();
        producto.setId(10L);

        when(productoService.findById(10L)).thenReturn(producto);

        // Item carrito
        CarritoItemRequest item = new CarritoItemRequest();
        item.setProductoId(10L);
        item.setCantidad(2);
        item.setPrecioUnitario(100.0);

        // Request
        CarritoRequest request = new CarritoRequest();
        request.setUsuarioId(1L);
        request.setEstadoId(1L);
        request.setMetodoPagoId(1L);
        request.setItems(List.of(item));

        // Guardado inicial y final
        when(comprobanteRepository.save(any(Comprobante.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Comprobante resultado = comprobanteService.crearComprobanteDesdeCarrito(request);

        assertNotNull(resultado);
        assertEquals(200.0, resultado.getTotalCompra());

        verify(detalleRepo, times(1)).save(any(DetalleComprobante.class));
        verify(comprobanteRepository, times(2)).save(any(Comprobante.class));
    }

    

}
