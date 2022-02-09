package edd2_trab2.structures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TextReader{

    public String textRead(String caminho){
        Path path = Paths.get(caminho); //Recebe um diretório no formato de string e convert num Path utilizavel
        String texto = ""; //declara a variável que mais tarde irá comportar o texto

        List<String> linhasArquivo = null; //declara a lista que vai receber as linhas do texto
        try {
            linhasArquivo = Files.readAllLines(path); //a lista recebe as linhas do texto
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String linha : linhasArquivo) {
            texto += linha; //para cada linha do texto a variavel "texto" vai concatenar com o que já estiver nela
        }
        return texto;
    }
}