package com.example.elarayax.naves.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.model.Envio;
import com.example.elarayax.naves.repository.EnvioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EnvioService {

	@Autowired
	private EnvioRepository envioRepository;

	public List<Envio> findAll() {
		return envioRepository.findAll();
	}

	public Optional<Envio> findById(Long id) {
		return envioRepository.findById(id);
	}

	public Envio save(Envio envio) {
		return envioRepository.save(envio);
	}

	public void delete(Long id) {
		envioRepository.deleteById(id);
	}

	public Envio partialUpdate(Envio envio){
        Envio existingEnvio = envioRepository.findById(envio.getId()).orElse(null);
        if (existingEnvio != null) {
            if (envio.getDireccionDestino() != null) {
                existingEnvio.setDireccionDestino(envio.getDireccionDestino());
            }
                if (envio.getFechaSalida() != null) {
                existingEnvio.setFechaSalida(envio.getFechaSalida());
            }
            if (envio.getFechaEntrega() != null) {
                existingEnvio.setFechaEntrega(envio.getFechaEntrega());
            }
        
            if(envio.getUsuario() != null) {
                existingEnvio.setUsuario(envio.getUsuario());
            }

            if(envio.getComprobante() != null) {
                existingEnvio.setComprobante(envio.getComprobante());
            }

            if(envio.getEstado() != null) {
                existingEnvio.setEstado(envio.getEstado());
            }

			if(envio.getDelivery() != null) {
                existingEnvio.setDelivery(envio.getDelivery());
            }
            return envioRepository.save(existingEnvio);
        }
        return null;
    }
}
