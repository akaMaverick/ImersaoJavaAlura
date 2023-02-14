import java.io.InputStream;
import java.net.URL;
import java.util.List;





public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão http e buscar os top 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        //String url = "https://api.nasa.gov/planetary/apod?api_key="Sua chave da Nasa"&start_date=2023-01-19&end_date=2023-01-22";
        
        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        //exibir e manipular os dados
        ExtratorDeConteudoDoImdb extrator = new ExtratorDeConteudoDoImdb();
        List<Conteudo> conteudos = extrator.extraiConteudos(json);
        
        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < 23; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            
            String nomeArquivo = conteudo.getTitulo() + ".png";
           
            geradora.cria(inputStream, nomeArquivo);

            System.out.println("\u001b[1m"  + conteudo.getTitulo());

        }

    }



}
