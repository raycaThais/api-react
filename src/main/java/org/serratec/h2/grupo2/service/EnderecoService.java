package org.serratec.h2.grupo2.service;

import java.util.Map;
import org.serratec.h2.grupo2.domain.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class EnderecoService {
	
	@Autowired
	private RestTemplate restTemplate;

	public Map<String, String> consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Map<String, String>>() {}
        );
        return response.getBody();
    }

   
    public Endereco preencherEndereco(String cep, String numero) {
        Map<String, String> dadosCep = consultarCep(cep);

        if (dadosCep.containsKey("erro")) {
            throw new IllegalArgumentException("CEP inválido: " + cep);
        }

        Endereco endereco = new Endereco();
        endereco.setRua(dadosCep.get("logradouro"));
        endereco.setBairro(dadosCep.get("bairro"));
        endereco.setCidade(dadosCep.get("localidade"));
        endereco.setEstado(dadosCep.get("uf"));
        endereco.setCep(cep);
        endereco.setNumero(numero);

        return endereco;
    }
}
	