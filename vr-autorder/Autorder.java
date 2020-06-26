import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Autorder {

  public static void main(String[] args)
  {
    if (args.length != 3) {
      System.out.println("Usagem: java Autorder <caminhoDoArquivo> <codigoDoFornecedor> <codigoDoComprador>");
      return;
    }

    String filepath = args[0];

    List<Product> products = new ArrayList<>();
    List<String> notFound = new ArrayList<>();

    Log("Lendo arquivo...");
    try {
      FileInputStream fis = new FileInputStream(filepath);
      try (Scanner scanner = new Scanner(fis)) {

        while (scanner.hasNext()) {
          String line = scanner.nextLine();
          String[] tokens = line.split(";");
          Product product = Product.fromTokens(tokens);
          product.id = productIdFrom(product.eancode);

          if (product.id > 0) {
            products.add(product);
          } else {
            notFound.add(line);
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    if (products.isEmpty()) {
      Error("Nenhum produto pode ser identificado nesse arquivo.");
      return;
    }

    Log("Criando pedido...");
    Order order = new Order();
    order.storeId = products.get(0).storeId;
    order.providerId = Integer.parseInt(args[1]);
    order.purchaserId = Integer.parseInt(args[2]);
    
    int dueDelivery = dueDeliveryFrom(order.providerId);
    order.deliveryDate = order.purchaseDate.plusDays(dueDelivery);

    order.totalPurchase = products.stream()
    .map(p -> p.quantity * p.price)
    .reduce((a, b) -> a + b)
    .orElse(0.0);

    createOrder(order);
    products.forEach(product -> createOrderItem(order, product));
  
    if (!notFound.isEmpty()) {
      Log("Relatando todos os produtos cujo código de barras não foi encontrado para o arquivo produtosNãoEncontrado.txt");
      reportNotFoundProducts(notFound);
    }

    Log("Pronto, pedido criado com sucesso!");
  }

  private static void reportNotFoundProducts(List<String> notFound)
  {
    try (PrintWriter writer = new PrintWriter("produtosNãoEncontrado.txt")) {
      notFound.forEach(writer::println);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static int productIdFrom(String eancode)
  {
    int id = 0;
    List<String> resultSet = Query("select id_produto from produtoautomacao where codigobarras = " + eancode);

    if (resultSet.size() > 2) {
      id = Integer.parseInt(resultSet.get(1));
    } else {
      Warn("Produto com o código de barras " + eancode + " não foi encontrado no banco.");
    }

    return id;
  }

  private static int dueDeliveryFrom(int providerId)
  {
    int dueDelivery = 0;

    List<String> resultSet = Query("select prazoentrega from fornecedorprazo where id_fornecedor = " + providerId);

    if (resultSet.size() > 2) {
      dueDelivery = Integer.parseInt(resultSet.get(1));
    } else {
      Warn("FornecedorPrazoEntrega não encontrado para o fornecedor " + providerId + ", então dataentrega será salvo com a data de hoje.");
    }

    return dueDelivery;
  }

  private static void createOrder(Order order)
  {
    Query("insert into pedido (" +
      "id_loja, id_fornecedor, datacompra, " +
      "dataentrega, valortotal, id_comprador, " +
      "id_tipofretepedido, id_situacaopedido, " +
      "observacao, desconto, id_divisaofornecedor, " +
      "id_precotacaofornecedor, valordesconto, " +
      "email, id_tipoatendidopedido, enviado" +
    ") values (" +
      order.storeId + ", " +
      order.providerId + ", " +
      "'" + order.purchaseDate + "', " +
      "'" + order.deliveryDate + "', " +
      order.totalPurchase + ", " +
      order.purchaserId + ", " +
      "0, 2, 'Pedido gerado pelo Autorder', " +
      "0.00, 1, null, 0.00, " +
      " false, 0, false" +
    ")");

    List<String> resultSet = Query("select id from pedido order by id desc limit 1");
    if (resultSet.size() > 2) {
      order.id = Integer.parseInt(resultSet.get(1));
    } else {
      Warn("Ocorreu algum erro inesperado, parando execucao!");
      System.exit(0);
    }
  }

  private static void createOrderItem(Order order, Product product)
  {
    Query("insert into pedidoitem (" +
      "id_loja, id_pedido, id_produto, " +
      "quantidade, qtdembalagem, custocompra, " +
      "dataentrega, valortotal,  quantidadeatendida, " +
      "desconto, id_tipopedido, custofinal, " +
      "id_tipoatendidopedido" +
    ") values (" +
      order.storeId + ", " +
      order.id + ", " +
      product.id + ", " +
      product.quantity + ", " +
      product.wrapper + ", " +
      product.price + ", " +
      "'" + order.deliveryDate + "', " +
      "0, " +
      (product.quantity * product.price) + ", " +
      product.quantity + ", " +
      "0, 0, 0)");
  }

  private static List<String> Query(String query)
  {
    try {
      String script = System.getProperty("os.name").equals("Linux") ? "./database.sh" : "./database.bat";
      Process process = Runtime.getRuntime().exec(new String[]{script, query});

      return readFrom(process.getInputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private static List<String> readFrom(InputStream inputStream)
  {
    List<String> lines = new ArrayList<>();
    try (Scanner scanner = new Scanner(inputStream)) {
      while (scanner.hasNext()) {
        lines.add(scanner.nextLine());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return lines;
  }

  private static Integer tokenToInt(String token)
  {
    try {
      return Integer.parseInt(token);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private static Double tokenToDouble(String token)
  {
    try {
      return Double.parseDouble(token.replace(",", "."));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private static void Log(String logMessage)
  {
    System.out.println(timestamp() + " INFO: " + logMessage);
  }

  private static void Warn(String warnMessage)
  {
    System.out.println(timestamp() + " AVISO: " + warnMessage);
  }

  private static void Error(String errorMessage)
  {
    System.out.println(timestamp() + " ERRO: " + errorMessage);
  }

  private static String timestamp()
  {
    return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
  }

  static class Product {
    public int id;
    public String eancode;
    public int quantity;
    public int wrapper;
    public int storeId;
    public double price;

    @Override
    public String toString()
    {
      return "{ id: " + this.id + ", eancode: " + this.eancode + ", quantity: " + this.quantity + ", wrapper: " + this.wrapper + ", storeId: " + this.storeId + ", price: " + this.price + " }";
    }

    public static Product fromTokens(String[] tokens)
    {
      Product p = new Product();
      p.storeId = tokenToInt(tokens[0]);
      p.eancode = tokens[1];
      p.quantity = tokenToInt(tokens[2]);
      p.wrapper = tokenToInt(tokens[3]);
      p.price = tokenToDouble(tokens[4]);

      return p;
    }
  }

  static class Order {
    public Integer id;
    public Integer storeId;
    public Integer providerId;
    public LocalDate purchaseDate;
    public LocalDate deliveryDate;
    public Double totalPurchase;
    public Integer purchaserId;

    public Order()
    {
      this.purchaseDate = LocalDate.now();
    }
  }
}