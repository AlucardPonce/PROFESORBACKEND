package mx.edu.uteq.idgs09.idgs09_01.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import mx.edu.uteq.idgs09.idgs09_01.dto.ProgramaEducativoDTO;

@Service
public class ProgramaEducativoClient {
    private final RestTemplate restTemplate;

    public ProgramaEducativoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean existsByClave(String clavePE) {
        try {
            String url = "http://localhost:8080/api/pe/clave/" + clavePE;
            ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);
            System.out.println("Validando clave PE: " + clavePE + " - Status: " + response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("Clave PE NO encontrada: " + clavePE);
            return false;
        } catch (Exception e) {
            System.out.println("Error al validar clave PE: " + clavePE + " - " + e.getMessage());
            return false;
        }
    }

    public ProgramaEducativoDTO getProgramaEducativoByClave(String clavePE) {
        try {
            String url = "http://localhost:8080/api/pe/clave/" + clavePE;
            return restTemplate.getForObject(url, ProgramaEducativoDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}
