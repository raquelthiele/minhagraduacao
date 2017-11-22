/**
 * Classe principal utilizada para inicialização do programa
 * Created by raque on 06/11/2017.
 */
public class Main {

    public static void main(String[] args) {
        String path = "C:\\Raquel\\Pessoal\\CurriculoRaquelGodoyThiele.pdf";
        PdfManager manager = new PdfManager(path);
        try{

            manager.parse();
        }catch(Exception e){

        }


    }

}
