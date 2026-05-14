package agencia.configuracion;


public class ConfiguracionRed {
    private String ipServidor;
    private int puerto;

    public ConfiguracionRed() {
      
        this.ipServidor = "172.20.10.3";
        this.puerto = 5000;
    }

    public String getIpServidor() { return ipServidor; }
    public int getPuerto() { return puerto; }
}