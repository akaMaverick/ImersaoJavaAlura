import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;




public class App {
    public static void main(String[] args) throws Exception {
        

        // fazer uma conexão http e buscar os top 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        


        //extrair só os dados que interessam
        var parser =  new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        System.out.println(listaDeFilmes.size());
        //exibir e manipular os dados

        var geradora = new GeradoraDeFigurinhas();

        for (Map<String, String> filme : listaDeFilmes) {


            String urlImagem = filme.get("image");
            //Aqui é substituido a string, pois ela estava em miniatura com a Url original
            String novaUrlImagem = urlImagem.substring(0, 116) + ".jpg";
            String titulo = filme.get("title");

            InputStream inputStream = new URL(novaUrlImagem).openStream();
            String nomeArquivo = titulo + ".png";

           
            geradora.cria(inputStream, nomeArquivo);

            System.out.println("\u001b[1m"  + titulo);

            // Desafio da Imersão em "colorir" o terminal, no titulo do filme também é colocado Bold
            System.out.println("\u001b[37m \u001b[41m Classificação \u001b[m");

            // Feito um parse de String para Float e depois de Float para Int
            float estrela = Float.parseFloat(filme.get("imDbRating"));
            int convertidoEstrelas = Math.round(estrela);

            // Nesse for é realizado a classificação do filme e mostrado no terminal em estrelas

            for(int i = 0; convertidoEstrelas > i; i++) { 
                System.out.print(" ★ ");
            }
           
            System.out.println("\n");
        }

    }



}
