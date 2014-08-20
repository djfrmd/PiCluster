package missouri.edu.pi_cluster;

public abstract class NodeInfo{
    
    public String getSerial(){
        return serialNumber;
    }
    
    public abstract float getCpuTemp();
    public abstract String getStatus();

    protected String serialNumber;
    protected float cpuTemp = 0;
    
}
