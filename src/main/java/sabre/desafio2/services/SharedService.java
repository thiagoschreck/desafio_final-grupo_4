package sabre.desafio2.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SharedService {
    public ModelMapper mapper = new ModelMapper();

}
