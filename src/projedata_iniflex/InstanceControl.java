package projedata_iniflex;

import java.nio.file.Paths;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InstanceControl {
  private static Scanner input;
  private static ArrayList<Funcionario> valores = new ArrayList<>();
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public ArrayList<Funcionario> loader(String file) {
    try {

      input = new Scanner(Paths.get(file));

      input.nextLine();

      while (input.hasNext()) {
        String[] data = input.nextLine().split(",");

        // Cria um novo funcionário com os valores lidos do arquivo CSV
        Funcionario funcionario = new Funcionario(data[0], LocalDate.parse(data[1], formatter), new BigDecimal(data[2]),
            data[3]);

        // Adiciona o funcionario a lista de valores
        valores.add(funcionario);
      }

    } catch (Exception e) {
      System.out.println("Algo deu errado na leitura do arquivio CSV: " + e.getMessage());
    }
    return valores;
  }

}
