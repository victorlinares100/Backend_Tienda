package com.example.elarayax.naves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.dto.CarritoRequest;
import com.example.elarayax.naves.dto.CarritoItemRequest;
import com.example.elarayax.naves.model.Comprobante;
import com.example.elarayax.naves.model.DetalleComprobante;
import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.model.Usuario;
import com.example.elarayax.naves.model.Estado;
import com.example.elarayax.naves.model.MetodoPago;
import com.example.elarayax.naves.repository.ComprobanteRepository;
import com.example.elarayax.naves.repository.DetalleComprobanteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private DetalleComprobanteRepository detalleRepo;

    public List<Comprobante> findAll() {
        return comprobanteRepository.findAll();
    }

    public Comprobante findById(Long id) {
        return comprobanteRepository.findById(id).orElse(null);
    }

    public Comprobante save(Comprobante comprobante) {
        return comprobanteRepository.save(comprobante);
    }

    public void delete(Long id) {
        comprobanteRepository.deleteById(id);
    }
   
    public Comprobante crearComprobanteDesdeCarrito(CarritoRequest req) {

        Usuario usuario = usuarioService.findById(req.getUsuarioId());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Estado estado = estadoService.findById(req.getEstadoId());
        if (estado == null) {
            throw new RuntimeException("Estado no encontrado");
        }

        MetodoPago metodo = metodoPagoService.findById(req.getMetodoPagoId());
        if (metodo == null) {
            throw new RuntimeException("Método de pago no encontrado");
        }

        Comprobante comprobante = new Comprobante();
        comprobante.setId(null);
        comprobante.setUsuario(usuario);
        comprobante.setEstado(estado);
        comprobante.setMetodoPago(metodo);
        comprobante.setFechaOrden(java.time.LocalDate.now().toString());
        comprobante.setTotalCompra(0.0);

        comprobante = comprobanteRepository.save(comprobante);

        double total = 0.0;

        for (CarritoItemRequest item : req.getItems()) {

            Producto producto = productoService.findById(item.getProductoId());
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado: " + item.getProductoId());
            }

            DetalleComprobante detalle = new DetalleComprobante();
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setProducto(producto);
            detalle.setComprobante(comprobante);

            detalleRepo.save(detalle);

            total += item.getCantidad() * item.getPrecioUnitario();
        }

        comprobante.setTotalCompra(total);
        return comprobanteRepository.save(comprobante);
    } // Fin del método local (HEAD)

    public Comprobante partialUpdate(Comprobante comprobante){
        Comprobante existingComprobante = comprobanteRepository.findById(comprobante.getId()).orElse(null);
        if (existingComprobante != null) {
            if (comprobante.getFechaOrden() != null) {
                existingComprobante.setFechaOrden(comprobante.getFechaOrden());
            }
                if (comprobante.getTotalCompra() != null) {
                existingComprobante.setTotalCompra(comprobante.getTotalCompra());
            }
            if (comprobante.getUsuario() != null) {
                existingComprobante.setUsuario(comprobante.getUsuario());
            }
        
            if(comprobante.getMetodoPago() != null) {
                existingComprobante.setMetodoPago(comprobante.getMetodoPago());
            }

            if(comprobante.getEstado() != null) {
                existingComprobante.setEstado(comprobante.getEstado());
            }

            if(comprobante.getEnvio() != null) {
                existingComprobante.setEnvio(comprobante.getEnvio());
            }

            return comprobanteRepository.save(existingComprobante);
        }
        return null;
    } 
}
