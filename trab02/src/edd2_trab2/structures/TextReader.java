package edd2_trab2.structures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TextReader{

    public String textRead(String caminho){
        Path path = Paths.get(caminho);
        String texto = "";

        List<String> linhasArquivo = null;
        try {
            linhasArquivo = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String linha : linhasArquivo) {
            texto += linha;
        }
        return texto;
    }
}